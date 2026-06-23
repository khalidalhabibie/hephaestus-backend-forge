package com.example.demoSpringbootDatabase.service;

import com.example.demoSpringbootDatabase.dto.*;
import com.example.demoSpringbootDatabase.entity.CustomerEntity;
import com.example.demoSpringbootDatabase.entity.LoanApplicationEntity;
import com.example.demoSpringbootDatabase.entity.RepaymentScheduleEntity;
import com.example.demoSpringbootDatabase.exception.CustomerNotFoundException;
import com.example.demoSpringbootDatabase.exception.LoanApplicationNotFoundException;
import com.example.demoSpringbootDatabase.repository.CustomerRepository;
import com.example.demoSpringbootDatabase.repository.LoanApplicationRepository;
import com.example.demoSpringbootDatabase.repository.RepaymentScheduleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoanApplicationService {
    private final LoanApplicationRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final RepaymentScheduleRepository scheduleRepository;

    @Value("${loan.interest.annual-rate:0.12}")
    private double annualInterestRate;

    public LoanApplicationService(LoanApplicationRepository loanRepository, 
                                CustomerRepository customerRepository, 
                                RepaymentScheduleRepository scheduleRepository) {
        this.loanRepository = loanRepository;
        this.customerRepository = customerRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public LoanApplicationResponse createLoan(CreateLoanApplicationRequest request) {
        CustomerEntity customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(request.getCustomerId()));

        LoanApplicationEntity loan = LoanApplicationEntity.builder()
                .customer(customer)
                .loanAmount(request.getLoanAmount())
                .tenorMonth(request.getTenorMonth())
                .purpose(request.getPurpose())
                .status("SUBMITTED") // Status awal wajib SUBMITTED
                .build();
        
        loanRepository.save(loan);
        return mapToResponse(loan);
    }

    @Transactional
    public LoanApplicationResponse updateStatus(Long id, String targetStatus) {
        LoanApplicationEntity loan = loanRepository.findById(id)
                .orElseThrow(() -> new LoanApplicationNotFoundException(id));
        
        String currentStatus = loan.getStatus().toUpperCase();
        String nextStatus = targetStatus.toUpperCase();

        // 1. VALIDASI ATURAN ALUR STATUS (STATE MACHINE RULES)
        if (currentStatus.equals(nextStatus)) {
            return mapToResponse(loan); // Tidak ada perubahan
        }

        // REJECTED dan CLOSED adalah status akhir, tidak boleh diubah lagi
        if ("REJECTED".equals(currentStatus) || "CLOSED".equals(currentStatus)) {
            throw new IllegalArgumentException("Cannot change status from final state: " + currentStatus);
        }

        switch (currentStatus) {
            case "SUBMITTED":
                if (!"APPROVED".equals(nextStatus) && !"REJECTED".equals(nextStatus)) {
                    throw new IllegalArgumentException("SUBMITTED loan can only transition to APPROVED or REJECTED");
                }
                break;
                
            case "APPROVED":
                if (!"DISBURSED".equals(nextStatus)) {
                    throw new IllegalArgumentException("APPROVED loan can only transition to DISBURSED");
                }
                break;
                
            case "DISBURSED":
                if (!"CLOSED".equals(nextStatus)) {
                    throw new IllegalArgumentException("DISBURSED loan can only transition to CLOSED");
                }
                // Validasi kelunasan: Semua schedule wajib berstatus 'PAID'
                List<RepaymentScheduleEntity> schedules = scheduleRepository.findByLoanApplicationId(id);
                boolean allPaid = !schedules.isEmpty() && schedules.stream()
                        .allMatch(schedule -> "PAID".equalsIgnoreCase(schedule.getStatus()));
                
                if (!allPaid) {
                    throw new IllegalArgumentException("Cannot CLOSE loan. Not all repayment schedules are PAID.");
                }
                break;
                
            default:
                throw new IllegalArgumentException("Unknown current loan status: " + currentStatus);
        }

        // 2. EKSEKUSI TRANSISI STATUS
        loan.setStatus(nextStatus);

        // TRIGGER UTAMA: Schedule hanya dibuat tepat saat status berubah menjadi DISBURSED
        if ("DISBURSED".equals(nextStatus)) {
            generateRepaymentSchedules(loan);
        }

        loanRepository.save(loan);
        return mapToResponse(loan);
    }

    private void generateRepaymentSchedules(LoanApplicationEntity loan) {
        BigDecimal loanAmount = BigDecimal.valueOf(loan.getLoanAmount());
        BigDecimal tenor = BigDecimal.valueOf(loan.getTenorMonth());
        
        // Pokok = loan_amount / tenor_month
        BigDecimal principalPerMonth = loanAmount.divide(tenor, 0, RoundingMode.HALF_UP);
        
        // Bunga = loan_amount * (annual_rate / 12)
        BigDecimal monthlyInterestRate = BigDecimal.valueOf(annualInterestRate)
                .divide(BigDecimal.valueOf(12), 4, RoundingMode.HALF_UP);
        BigDecimal interestPerMonth = loanAmount.multiply(monthlyInterestRate).setScale(0, RoundingMode.HALF_UP);
        
        // Total per bulan = Pokok + Bunga
        BigDecimal totalPerMonth = principalPerMonth.add(interestPerMonth);

        for (int i = 1; i <= loan.getTenorMonth(); i++) {
            RepaymentScheduleEntity schedule = RepaymentScheduleEntity.builder()
                    .loanApplication(loan)
                    .installmentNumber(i)
                    .dueDate(LocalDate.now().plusMonths(i)) // Jatuh tempo berurutan tiap bulan
                    .principalAmount(principalPerMonth.longValue())
                    .interestAmount(interestPerMonth.longValue())
                    .totalAmount(totalPerMonth.longValue()) // Menyesuaikan nama setter entitas Anda
                    .status("UNPAID")
                    .build();
            
            scheduleRepository.save(schedule);
        }
    }

    @Transactional(readOnly = true)
    public LoanApplicationResponse getById(Long id) {
        return loanRepository.findByIdWithCustomer(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new LoanApplicationNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getAll(String status) {
        List<LoanApplicationEntity> loans = (status != null) ? 
                loanRepository.findByStatus(status.toUpperCase()) : loanRepository.findAll();
        return loans.stream().map(this::mapToResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getByCustomerId(Long customerId) {
        return loanRepository.findLoansByCustomerId(customerId).stream().map(this::mapToResponse).toList();
    }

    /**
     * BARU: Mendukung filter rentang tanggal pengajuan dan pagination halaman data
     */
    @Transactional(readOnly = true)
    public Page<LoanApplicationResponse> getLoansWithPagination(String status, LocalDateTime start, LocalDateTime end, Pageable pageable) {
        Page<LoanApplicationEntity> entities;
        
        if (status != null && !status.isBlank()) {
            entities = loanRepository.findByStatusAndCreatedAtBetween(status.toUpperCase(), start, end, pageable);
        } else {
            entities = loanRepository.findByCreatedAtBetween(start, end, pageable);
        }
        
        return entities.map(this::mapToResponse);
    }

    /**
     * BARU: Mengambil data ringkasan total nominal berdasarkan status pinjaman
     */
    @Transactional(readOnly = true)
    public List<LoanStatusSummaryDto> getStatusSummary() {
        return loanRepository.getSummaryByStatus();
    }

    private LoanApplicationResponse mapToResponse(LoanApplicationEntity loan) {
        CustomerSummaryResponse customerSummary = CustomerSummaryResponse.builder()
                .id(loan.getCustomer().getId())
                .fullName(loan.getCustomer().getFullName())
                .nik(loan.getCustomer().getNik())
                .email(loan.getCustomer().getEmail())
                .build();

        return LoanApplicationResponse.builder()
                .id(loan.getId())
                .loanAmount(loan.getLoanAmount())
                .tenorMonth(loan.getTenorMonth())
                .purpose(loan.getPurpose())
                .status(loan.getStatus())
                .customer(customerSummary)
                .build();
    }
}