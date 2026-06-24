// Business logic loan: create (auto generate repayment schedule), get, list, pagination, filter status & tanggal, update status, summary report, outstanding report.

package com.example.training.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.training.DTO.CreateLoanApplicationRequest;
import com.example.training.DTO.CustomerSummaryResponse;
import com.example.training.DTO.LoanApplicationResponse;
import com.example.training.DTO.UpdateLoanStatusRequest;
import com.example.training.Entity.CustomerEntity;
import com.example.training.Entity.LoanApplicationEntity;
import com.example.training.Entity.RepaymentScheduleEntity;
import com.example.training.Exception.CustomerNotFoundException;
import com.example.training.Exception.LoanApplicationNotFoundException;
import com.example.training.Repository.CustomerRepository;
import com.example.training.Repository.LoanApplicationRepository;
import com.example.training.Repository.RepaymentScheduleRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final CustomerRepository customerRepository;
    private final RepaymentScheduleRepository repaymentScheduleRepository;

    @Transactional
    public LoanApplicationResponse create(CreateLoanApplicationRequest request) {
        CustomerEntity customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(request.getCustomerId()));

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setCustomer(customer);
        loan.setLoanAmount(request.getLoanAmount());
        loan.setTenorMonth(request.getTenorMonth());
        loan.setPurpose(request.getPurpose());
        loan.setStatus(LoanApplicationEntity.LoanStatus.SUBMITTED);

        LoanApplicationEntity savedLoan = loanApplicationRepository.save(loan);
        // Repayment schedule TIDAK dibuat saat create, hanya saat DISBURSED
        return mapToResponse(savedLoan);
    }

    private void generateRepaymentSchedules(LoanApplicationEntity loan) {
        BigDecimal monthlyPrincipal = loan.getLoanAmount()
                .divide(BigDecimal.valueOf(loan.getTenorMonth()), 2, RoundingMode.HALF_UP);
        BigDecimal interestRate = new BigDecimal("0.01");
        BigDecimal monthlyInterest = loan.getLoanAmount().multiply(interestRate)
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalMonthly = monthlyPrincipal.add(monthlyInterest);

        ZonedDateTime dueDate = ZonedDateTime.now().plusMonths(1)
                .withHour(0).withMinute(0).withSecond(0).withNano(0);

        for (int i = 1; i <= loan.getTenorMonth(); i++) {
            RepaymentScheduleEntity schedule = new RepaymentScheduleEntity();
            schedule.setLoanApplication(loan);
            schedule.setInstallmentNumber(i);
            schedule.setDueDate(dueDate);
            schedule.setPrincipalAmount(monthlyPrincipal);
            schedule.setInterestAmount(monthlyInterest);
            schedule.setTotalAmount(totalMonthly);
            schedule.setStatus(RepaymentScheduleEntity.ScheduleStatus.UNPAID);
            repaymentScheduleRepository.save(schedule);
            dueDate = dueDate.plusMonths(1);
        }
    }

    @Transactional(readOnly = true)
    public LoanApplicationResponse getById(Long id) {
        LoanApplicationEntity entity = loanApplicationRepository.findByIdWithCustomer(id)
                .orElseThrow(() -> new LoanApplicationNotFoundException(id));
        return mapToResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getAll() {
        return loanApplicationRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ========== Pagination untuk List Loan Application (START) ========== //
    @Transactional(readOnly = true)
    public Page<LoanApplicationResponse> getAllPaginated(
            String status,
            ZonedDateTime startDate,
            ZonedDateTime endDate,
            Pageable pageable) {
        LoanApplicationEntity.LoanStatus loanStatus = null;
        if (status != null && !status.isBlank()) {
            try {
                loanStatus = LoanApplicationEntity.LoanStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid loan status: " + status);
            }
        }
        if (startDate == null) {
            startDate = Instant.EPOCH.atZone(ZoneId.of("Asia/Jakarta"));
        }
        if (endDate == null) {
            endDate = ZonedDateTime.now();
        }
        // Filter loan berdasarkan tanggal pengajuan.
        Page<LoanApplicationEntity> result = loanApplicationRepository.findAllWithFilters(
                loanStatus, startDate, endDate, pageable);
        return result.map(this::mapToResponse);
    }
    // ========== Pagination untuk List Loan Application (END) ========== //

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getByCustomerId(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException(customerId);
        }
        return loanApplicationRepository.findLoansByCustomerId(customerId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getByStatus(String status) {
        LoanApplicationEntity.LoanStatus loanStatus;
        try {
            loanStatus = LoanApplicationEntity.LoanStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid loan status: " + status);
        }
        return loanApplicationRepository.findByStatus(loanStatus.name()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ========== VALIDASI FLOW STATUS & GENERATE SCHEDULE SAAT DISBURSED (START)
    // ========== //
    @Transactional
    public LoanApplicationResponse updateStatus(Long id, UpdateLoanStatusRequest request) {
        LoanApplicationEntity loan = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new LoanApplicationNotFoundException(id));

        LoanApplicationEntity.LoanStatus currentStatus = loan.getStatus();
        LoanApplicationEntity.LoanStatus newStatus;
        try {
            newStatus = LoanApplicationEntity.LoanStatus.valueOf(request.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + request.getStatus());
        }

        validateStatusTransition(currentStatus, newStatus, id);

        // Repayment schedule dibuat berdasarkan tenor saat loan berstatus DISBURSED.
        // Schedule hanya dibuat sekali.
        if (newStatus == LoanApplicationEntity.LoanStatus.DISBURSED) {
            if (loan.getRepaymentSchedules() == null || loan.getRepaymentSchedules().isEmpty()) {
                generateRepaymentSchedules(loan);
            }
        }

        loan.setStatus(newStatus);
        return mapToResponse(loanApplicationRepository.save(loan));
    }

    // Perubahan status harus mengikuti flow loan; status tidak boleh dilompati atau
    // dikembalikan ke status sebelumnya.
    private void validateStatusTransition(LoanApplicationEntity.LoanStatus current,
            LoanApplicationEntity.LoanStatus next,
            Long loanId) {
        switch (current) {
            case SUBMITTED:
                // Loan SUBMITTED hanya dapat diubah menjadi APPROVED atau REJECTED.
                if (next != LoanApplicationEntity.LoanStatus.APPROVED
                        && next != LoanApplicationEntity.LoanStatus.REJECTED) {
                    throw new IllegalArgumentException(
                            "SUBMITTED can only be changed to APPROVED or REJECTED");
                }
                break;
            case APPROVED:
                // Loan APPROVED hanya dapat diubah menjadi DISBURSED.
                if (next != LoanApplicationEntity.LoanStatus.DISBURSED) {
                    throw new IllegalArgumentException(
                            "APPROVED can only be changed to DISBURSED");
                }
                break;
            case DISBURSED:
                // Loan DISBURSED hanya dapat diubah menjadi CLOSED setelah seluruh repayment
                // schedule berstatus PAID.
                if (next != LoanApplicationEntity.LoanStatus.CLOSED) {
                    throw new IllegalArgumentException(
                            "DISBURSED can only be changed to CLOSED");
                }
                long unpaidCount = repaymentScheduleRepository.countByLoanIdAndStatusNotPaid(loanId);
                if (unpaidCount > 0) {
                    throw new IllegalArgumentException(
                            "Cannot close loan. All repayment schedules must be PAID first.");
                }
                break;
            case REJECTED:
                // REJECTED adalah status akhir; status tersebut tidak boleh diubah lagi.
                throw new IllegalArgumentException(
                        "REJECTED is a final status and cannot be changed");
            case CLOSED:
                // CLOSED adalah status akhir; status tersebut tidak boleh diubah lagi.
                throw new IllegalArgumentException(
                        "CLOSED is a final status and cannot be changed");
            default:
                throw new IllegalArgumentException("Invalid status transition");
        }
    }
    // ========== VALIDASI FLOW STATUS & GENERATE SCHEDULE SAAT DISBURSED (END)
    // ========== //

    // ========== DTO Projection untuk Query Report (START) ========== //
    // ========== Endpoint Summary Total Loan by Status (START) ========== //
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getSummaryByStatus() {
        List<Object[]> results = loanApplicationRepository.getSummaryByStatusRaw();
        return results.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("status", row[0]); // index 0 = l.status
            map.put("total_loan", row[1]); // index 1 = COUNT(l)
            map.put("total_amount", row[2]); // index 2 = SUM(l.loanAmount)
            return map;
        }).collect(Collectors.toList());
    }
    // ========== Endpoint Summary Total Loan by Status (END) ========== //
    // ========== DTO Projection untuk Query Report (END) ========== //

    // ========== Endpoint Outstanding Amount per Customer (START) ========== //
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getOutstandingPerCustomer() {
        List<Object[]> results = loanApplicationRepository.getOutstandingPerCustomerRaw();
        return results.stream().map(row -> {
            Map<String, Object> map = new HashMap<>();
            map.put("customer_id", row[0]);
            map.put("customer_name", row[1]);
            map.put("outstanding_amount", row[2]);
            return map;
        }).collect(Collectors.toList());
    }
    // ========== Endpoint Outstanding Amount per Customer (END) ========== //

    private LoanApplicationResponse mapToResponse(LoanApplicationEntity entity) {
        return LoanApplicationResponse.builder()
                .id(entity.getId())
                .loanAmount(entity.getLoanAmount())
                .tenorMonth(entity.getTenorMonth())
                .purpose(entity.getPurpose())
                .status(entity.getStatus() != null ? entity.getStatus().name() : null)
                .customer(mapCustomerSummary(entity.getCustomer()))
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private CustomerSummaryResponse mapCustomerSummary(CustomerEntity entity) {
        if (entity == null)
            return null;
        return CustomerSummaryResponse.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .nik(entity.getNik())
                .email(entity.getEmail())
                .build();
    }
}