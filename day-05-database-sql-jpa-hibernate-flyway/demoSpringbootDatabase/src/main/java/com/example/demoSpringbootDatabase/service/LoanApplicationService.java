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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class LoanApplicationService {
    private final LoanApplicationRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final RepaymentScheduleRepository scheduleRepository;

    public LoanApplicationService(LoanApplicationRepository loanRepository, CustomerRepository customerRepository, RepaymentScheduleRepository scheduleRepository) {
        this.loanRepository = loanRepository;
        this.customerRepository = customerRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional
    public LoanApplicationResponse createLoan(CreateLoanApplicationRequest request) {
        CustomerEntity customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(request.getCustomerId()));

        LoanApplicationEntity loan = LoanApplicationEntity.builder()
                .customer(customer).loanAmount(request.getLoanAmount())
                .tenorMonth(request.getTenorMonth()).purpose(request.getPurpose()).status("SUBMITTED").build();
        
        loanRepository.save(loan);
        return mapToResponse(loan);
    }

    @Transactional
    public LoanApplicationResponse updateStatus(Long id, String status) {
        LoanApplicationEntity loan = loanRepository.findById(id).orElseThrow(() -> new LoanApplicationNotFoundException(id));
        loan.setStatus(status.toUpperCase());

        // Jika status diubah menjadi APPROVED, generate repayment schedule otomatis secara proporsional
        if ("APPROVED".equalsIgnoreCase(status)) {
            generateRepaymentSchedules(loan);
        }

        return mapToResponse(loan);
    }

    private void generateRepaymentSchedules(LoanApplicationEntity loan) {
        long principalPerMonth = loan.getLoanAmount() / loan.getTenorMonth();
        long interestPerMonth = 100000; // Contoh interest konstan atau disesuaikan kalkulasi bisnis Anda
        long totalPerMonth = principalPerMonth + interestPerMonth;

        for (int i = 1; i <= loan.getTenorMonth(); i++) {
            RepaymentScheduleEntity schedule = RepaymentScheduleEntity.builder()
                    .loanApplication(loan).installmentNumber(i)
                    .dueDate(LocalDate.now().plusMonths(i)).principalAmount(principalPerMonth)
                    .interestAmount(interestPerMonth).totalAmount(totalPerMonth).status("UNPAID").build();
            scheduleRepository.save(schedule);
        }
    }

    @Transactional(readOnly = true)
    public LoanApplicationResponse getById(Long id) {
        return loanRepository.findByIdWithCustomer(id).map(this::mapToResponse)
                .orElseThrow(() -> new LoanApplicationNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getAll(String status) {
        List<LoanApplicationEntity> loans = (status != null) ? loanRepository.findByStatus(status.toUpperCase()) : loanRepository.findAll();
        return loans.stream().map(this::mapToResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getByCustomerId(Long customerId) {
        return loanRepository.findLoansByCustomerId(customerId).stream().map(this::mapToResponse).toList();
    }

    private LoanApplicationResponse mapToResponse(LoanApplicationEntity loan) {
        CustomerSummaryResponse customerSummary = CustomerSummaryResponse.builder()
                .id(loan.getCustomer().getId()).fullName(loan.getCustomer().getFullName())
                .nik(loan.getCustomer().getNik()).email(loan.getCustomer().getEmail()).build();

        return LoanApplicationResponse.builder().id(loan.getId()).loanAmount(loan.getLoanAmount())
                .tenorMonth(loan.getTenorMonth()).purpose(loan.getPurpose()).status(loan.getStatus())
                .customer(customerSummary).build();
    }
}
