package com.example.training.service;

import com.example.training.dto.*;
import com.example.training.entity.LoanApplicationEntity;
import com.example.training.entity.RepaymentScheduleEntity;
import com.example.training.enums.LoanStatus;
import com.example.training.enums.RepaymentStatus;
import com.example.training.exception.CustomerNotFoundException;
import com.example.training.exception.LoanApplicationNotFoundException;
import com.example.training.repository.CustomerRepository;
import com.example.training.repository.LoanApplicationRepository;
import com.example.training.repository.RepaymentScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    private final LoanApplicationRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final RepaymentScheduleRepository repaymentScheduleRepository;

    @Transactional
    public LoanApplicationResponse create(CreateLoanApplicationRequest request) {
        if (!customerRepository.existsById(request.getCustomerId())) {
            throw new CustomerNotFoundException("Customer not found with id: " + request.getCustomerId());
        }

        LoanApplicationEntity loan = LoanApplicationEntity.builder()
                .customerId(request.getCustomerId())
                .loanAmount(request.getLoanAmount())
                .tenorMonth(request.getTenorMonth())
                .purpose(request.getPurpose())
                .status(LoanStatus.SUBMITTED)
                .build();

        LoanApplicationEntity saved = loanRepository.save(loan);

        // Generate repayment schedules
        generateRepaymentSchedules(saved);

        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public LoanApplicationDetailResponse findById(Long id) {
        LoanApplicationEntity loan = loanRepository.findByIdWithCustomer(id)
                .orElseThrow(() -> new LoanApplicationNotFoundException("Loan application not found with id: " + id));
        return toDetailResponse(loan);
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> findAll() {
        return loanRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> findByStatus(LoanStatus status) {
        return loanRepository.findByStatus(status).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> findByCustomerId(Long customerId) {
        return loanRepository.findByCustomerId(customerId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public LoanApplicationResponse updateStatus(Long id, UpdateLoanStatusRequest request) {
        LoanApplicationEntity loan = loanRepository.findById(id)
                .orElseThrow(() -> new LoanApplicationNotFoundException("Loan application not found with id: " + id));
        loan.setStatus(request.getStatus());
        return toResponse(loanRepository.save(loan));
    }

    private void generateRepaymentSchedules(LoanApplicationEntity loan) {
        BigDecimal monthlyPrincipal = loan.getLoanAmount()
                .divide(BigDecimal.valueOf(loan.getTenorMonth()), 2, RoundingMode.HALF_UP);
        BigDecimal interestRate = new BigDecimal("0.012"); // 1.2% per month example
        BigDecimal monthlyInterest = loan.getLoanAmount().multiply(interestRate)
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalMonthly = monthlyPrincipal.add(monthlyInterest);

        LocalDate dueDate = LocalDate.now().plusMonths(1);

        for (int i = 1; i <= loan.getTenorMonth(); i++) {
            RepaymentScheduleEntity schedule = RepaymentScheduleEntity.builder()
                    .loanApplicationId(loan.getId())
                    .installmentNumber(i)
                    .dueDate(dueDate)
                    .principalAmount(monthlyPrincipal)
                    .interestAmount(monthlyInterest)
                    .totalAmount(totalMonthly)
                    .status(RepaymentStatus.UNPAID)
                    .build();
            repaymentScheduleRepository.save(schedule);
            dueDate = dueDate.plusMonths(1);
        }
    }

    private LoanApplicationResponse toResponse(LoanApplicationEntity entity) {
        return LoanApplicationResponse.builder()
                .id(entity.getId())
                .loanAmount(entity.getLoanAmount())
                .tenorMonth(entity.getTenorMonth())
                .purpose(entity.getPurpose())
                .status(entity.getStatus())
                .customerId(entity.getCustomerId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private LoanApplicationDetailResponse toDetailResponse(LoanApplicationEntity entity) {
        return LoanApplicationDetailResponse.builder()
                .id(entity.getId())
                .loanAmount(entity.getLoanAmount())
                .tenorMonth(entity.getTenorMonth())
                .purpose(entity.getPurpose())
                .status(entity.getStatus())
                .customer(CustomerResponse.builder()
                        .id(entity.getCustomer().getId())
                        .fullName(entity.getCustomer().getFullName())
                        .nik(entity.getCustomer().getNik())
                        .email(entity.getCustomer().getEmail())
                        .phoneNumber(entity.getCustomer().getPhoneNumber())
                        .build())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
