package com.example.dbbackend.loanapplication.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dbbackend.common.exception.CustomerNotFoundException;
import com.example.dbbackend.common.exception.LoanApplicationNotFoundException;
import com.example.dbbackend.customer.dto.CustomerSummaryResponse;
import com.example.dbbackend.customer.entity.CustomerEntity;
import com.example.dbbackend.customer.repository.CustomerRepository;
import com.example.dbbackend.loanapplication.dto.CreateLoanApplicationRequest;
import com.example.dbbackend.loanapplication.dto.LoanApplicationResponse;
import com.example.dbbackend.loanapplication.entity.LoanApplicationEntity;
import com.example.dbbackend.loanapplication.entity.LoanApplicationStatus;
import com.example.dbbackend.loanapplication.repository.LoanApplicationRepository;
import com.example.dbbackend.repaymentschedule.entity.RepaymentScheduleEntity;
import com.example.dbbackend.repaymentschedule.repository.RepaymentScheduleRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class LoanApplicationService {

    private final LoanApplicationRepository loanRepository;
    private final CustomerRepository customerRepository;
    private final RepaymentScheduleRepository repaymentScheduleRepository;

    public LoanApplicationService(LoanApplicationRepository loanRepository,
            CustomerRepository customerRepository, RepaymentScheduleRepository repaymentScheduleRepository) {
        this.loanRepository = loanRepository;
        this.customerRepository = customerRepository;
        this.repaymentScheduleRepository = repaymentScheduleRepository;
    }

    @Transactional
    public LoanApplicationResponse createLoan(CreateLoanApplicationRequest request) {

        CustomerEntity customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(
                        () -> new CustomerNotFoundException("Customer not found with id: " + request.getCustomerId()));

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setCustomer(customer);
        loan.setLoanAmount(request.getLoanAmount());
        loan.setTenorMonth(request.getTenorMonth());
        loan.setPurpose(request.getPurpose());
        loan.setStatus(LoanApplicationStatus.SUBMITTED.name());
        loan.setCreatedAt(LocalDateTime.now());
        loan.setUpdatedAt(LocalDateTime.now());

        LoanApplicationEntity saved = loanRepository.save(loan);

        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public LoanApplicationResponse getLoanById(Long id) {

        LoanApplicationEntity loan = loanRepository.findByIdWithCustomer(id)
                .orElseThrow(() -> new LoanApplicationNotFoundException("Loan not found with id: " + id));

        return mapToResponse(loan);
    }

    private LoanApplicationResponse mapToResponse(LoanApplicationEntity loan) {

        LoanApplicationResponse response = new LoanApplicationResponse();
        response.setId(loan.getId());
        response.setLoanAmount(loan.getLoanAmount());
        response.setTenorMonth(loan.getTenorMonth());
        response.setPurpose(loan.getPurpose());
        response.setStatus(LoanApplicationStatus.valueOf(loan.getStatus()));

        CustomerSummaryResponse customer = new CustomerSummaryResponse();
        customer.setId(loan.getCustomer().getId());
        customer.setFullName(loan.getCustomer().getFullName());
        customer.setNik(loan.getCustomer().getNik());
        customer.setEmail(loan.getCustomer().getEmail());

        response.setCustomer(customer);

        return response;
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getAll() {
        return loanRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getByStatus(String status) {
        return loanRepository.findByStatus(status).stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public LoanApplicationResponse updateStatus(Long id, LoanApplicationStatus status) {
        LoanApplicationEntity loan = loanRepository.findById(id)
                .orElseThrow(() -> new LoanApplicationNotFoundException("Loan not found with id: " + id));

        validateStatusTransition(LoanApplicationStatus.valueOf(loan.getStatus()), status);
        loan.setStatus(status.name());
        loan.setUpdatedAt(LocalDateTime.now());

        if (status == LoanApplicationStatus.DISBURSED) {
            generateRepaymentSchedule(loan);
        }

        return mapToResponse(loanRepository.save(loan));
    }

    private void validateStatusTransition(
            LoanApplicationStatus current,
            LoanApplicationStatus next) {

        switch (current) {
            case SUBMITTED -> {
                if (!(next == LoanApplicationStatus.APPROVED ||
                        next == LoanApplicationStatus.REJECTED)) {
                    throw new IllegalArgumentException("Invalid status transition");
                }
            }

            case APPROVED -> {
                if (next != LoanApplicationStatus.DISBURSED) {
                    throw new IllegalArgumentException("Invalid status transition");
                }
            }

            case DISBURSED -> {
                if (next != LoanApplicationStatus.CLOSED) {
                    throw new IllegalArgumentException("Invalid status transition");
                }
            }

            case REJECTED, CLOSED -> {
                throw new IllegalArgumentException("Cannot change final status");
            }
        }
    }

    private void generateRepaymentSchedule(LoanApplicationEntity loan) {
        BigDecimal annualRate = BigDecimal.valueOf(0.12);
        BigDecimal monthlyRate = annualRate.divide(BigDecimal.valueOf(12), 6, RoundingMode.HALF_UP);

        int tenor = loan.getTenorMonth();

        BigDecimal principal = loan.getLoanAmount()
                .divide(BigDecimal.valueOf(tenor), 0, RoundingMode.HALF_UP);

        BigDecimal interest = loan.getLoanAmount()
                .multiply(monthlyRate)
                .setScale(0, RoundingMode.HALF_UP);

        BigDecimal total = principal.add(interest);

        for (int i = 1; i <= tenor; i++) {

            RepaymentScheduleEntity schedule = new RepaymentScheduleEntity();

            schedule.setLoanApplication(loan);
            schedule.setInstallmentNumber(i);

            schedule.setDueDate(LocalDate.now().plusMonths(i));

            schedule.setPrincipalAmount(principal);
            schedule.setInterestAmount(interest);
            schedule.setTotalAmount(total);

            schedule.setStatus("UNPAID");

            schedule.setCreatedAt(LocalDateTime.now());
            schedule.setUpdatedAt(LocalDateTime.now());

            repaymentScheduleRepository.save(schedule);
        }
    }

}