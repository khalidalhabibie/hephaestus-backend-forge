package com.example.main.services;

import com.example.main.dto.request.LoanApplicationRequest;
import com.example.main.dto.response.LoanApplicationResponse;
import com.example.main.dto.response.RepaymentScheduleResponse;
import com.example.main.entity.CustomerEntity;
import com.example.main.entity.LoanApplicationEntity;
import com.example.main.entity.RepaymentScheduleEntity;
import com.example.main.enums.LoanStatus;
import com.example.main.exceptions.BadRequestException;
import com.example.main.exceptions.ForbiddenException;
import com.example.main.exceptions.LoanApplicationNotFoundException;
import com.example.main.exceptions.NotFoundException;
import com.example.main.repositories.CustomerRepository;
import com.example.main.repositories.LoanApplicationRepository;
import com.example.main.repositories.RepaymentScheduleRepository;
import com.example.main.security.UserRole;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final CustomerRepository customerRepository;
    private final RepaymentScheduleRepository repaymentScheduleRepository;
    private final RepaymentScheduleService repaymentScheduleService;

    private static final BigDecimal MANAGER_MINIMUM_AMOUNT = new BigDecimal("10000000");

    public LoanApplicationService(LoanApplicationRepository loanApplicationRepository,
            CustomerRepository customerRepository, RepaymentScheduleRepository repaymentScheduleRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.customerRepository = customerRepository;
        this.repaymentScheduleRepository = repaymentScheduleRepository;
        this.repaymentScheduleService = new RepaymentScheduleService(repaymentScheduleRepository);
    }

    @Transactional
    public LoanApplicationResponse createLoanApplication(LoanApplicationRequest request) {
        CustomerEntity customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new NotFoundException("Customer Not Found"));

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setCustomer(customer);
        loan.setLoanAmount(request.getLoanAmount());
        loan.setTenorMonth(request.getTenorMonth());
        loan.setPurpose(request.getPurpose());
        loan.setStatus(LoanStatus.SUBMITTED);

        LoanApplicationEntity savedLoan = loanApplicationRepository.save(loan);
        return mapToLoanApplicationResponse(savedLoan);
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getAllLoanApplications(String status, Long customerId) {
        List<LoanApplicationEntity> loans;

        if (status != null && customerId != null) {
            loans = loanApplicationRepository.findLoansByCustomerId(customerId).stream()
                    .filter(l -> l.getStatus() == LoanStatus.valueOf(status.toUpperCase()))
                    .collect(Collectors.toList());
        } else if (status != null) {
            loans = loanApplicationRepository.findByStatus(LoanStatus.valueOf(status.toUpperCase()));
        } else if (customerId != null) {
            loans = loanApplicationRepository.findLoansByCustomerId(customerId);
        } else {
            loans = loanApplicationRepository.findAll();
        }

        return loans.stream().map(this::mapToLoanApplicationResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LoanApplicationResponse getLoanApplicationById(Long id) {
        LoanApplicationEntity loan = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new LoanApplicationNotFoundException("Loan application not found"));
        return mapToLoanApplicationResponse(loan);
    }

    @Transactional
    public LoanApplicationResponse approveLoanApplication(Long id, UserRole userRole) {
        LoanApplicationEntity loan = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new LoanApplicationNotFoundException("Loan application not found"));

        if (userRole == UserRole.MANAGER && loan.getLoanAmount().compareTo(MANAGER_MINIMUM_AMOUNT) <= 0) {
            throw new ForbiddenException("Manager is only allowed to approve loans above 10,000,000");
        }

        // 1. Update status pinjaman menjadi APPROVED
        loan.setStatus(LoanStatus.APPROVED);
        LoanApplicationEntity updatedLoan = loanApplicationRepository.save(loan);

        // 2. TRIGGER PEMBUATAN JADWAL CICILAN OTOMATIS BERDASARKAN TENOR
        repaymentScheduleService.createRepaymentSchedules(updatedLoan);

        return mapToLoanApplicationResponse(updatedLoan);
    }

    @Transactional
    public LoanApplicationResponse rejectLoanApplication(Long id) {
        LoanApplicationEntity loan = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new LoanApplicationNotFoundException("Loan application not found"));
        loan.setStatus(LoanStatus.REJECTED);
        LoanApplicationEntity updatedLoan = loanApplicationRepository.save(loan);
        return mapToLoanApplicationResponse(updatedLoan);
    }

    @Transactional
    public LoanApplicationResponse cancelLoanApplication(Long id) {
        LoanApplicationEntity loan = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new LoanApplicationNotFoundException("Loan application not found"));

        loan.setStatus(LoanStatus.CANCELLED);
        LoanApplicationEntity updatedLoan = loanApplicationRepository.save(loan);
        return mapToLoanApplicationResponse(updatedLoan);
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getLoansByCustomerId(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new NotFoundException("Customer not found");
        }

        List<LoanApplicationEntity> loans = loanApplicationRepository.findLoansByCustomerId(customerId);

        return loans.stream()
                .map(this::mapToLoanApplicationResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getLoansByStatus(String statusStr) {
        List<LoanApplicationEntity> loans;

        if (statusStr != null && !statusStr.trim().isEmpty()) {
            try {
                LoanStatus statusEnum = LoanStatus.valueOf(statusStr.trim().toUpperCase());
                
                loans = loanApplicationRepository.findByStatus(statusEnum);
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Invalid loan status value: " + statusStr);
            }
        } 
        else {
            loans = loanApplicationRepository.findAll();
        }

        return loans.stream()
                .map(this::mapToLoanApplicationResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public LoanApplicationResponse updateLoanStatus(Long id, String statusStr) {
        LoanApplicationEntity loan = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new LoanApplicationNotFoundException("Loan application not found"));

        LoanStatus newStatus;
        try {
            newStatus = LoanStatus.valueOf(statusStr.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid loan status value: " + statusStr);
        }

        // loan yang sudah CLOSED atau REJECTED gabisa diubah statusnya lagi
        if (loan.getStatus() == LoanStatus.CLOSED || loan.getStatus() == LoanStatus.REJECTED) {
            throw new BadRequestException("Cannot change status of a " + loan.getStatus() + " loan application");
        }

        loan.setStatus(newStatus);
        LoanApplicationEntity updatedLoan = loanApplicationRepository.save(loan);

        return mapToLoanApplicationResponse(updatedLoan);
    }

    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponse> getRepaymentSchedulesByLoanId(Long loanApplicationId) {
        if (!loanApplicationRepository.existsById(loanApplicationId)) {
            throw new LoanApplicationNotFoundException("Loan application not found");
        }

        List<RepaymentScheduleEntity> schedules = repaymentScheduleRepository.findByLoanApplicationId(loanApplicationId);

        return schedules.stream()
                .map(this::mapToScheduleResponse)
                .collect(Collectors.toList());
    }

    private RepaymentScheduleResponse mapToScheduleResponse(RepaymentScheduleEntity schedule) {
        RepaymentScheduleResponse response = new RepaymentScheduleResponse();
        response.setId(schedule.getId());
        response.setInstallmentNumber(schedule.getInstallmentNumber());
        response.setDueDate(schedule.getDueDate());
        
        response.setPrincipalAmount(schedule.getPrincipalAmount());
        response.setInterestAmount(schedule.getInterestAmount());
        response.setTotalAmount(schedule.getTotalAmount());
        response.setStatus(schedule.getStatus()); 
        return response;
    }

    private LoanApplicationResponse mapToLoanApplicationResponse(LoanApplicationEntity loan) {
        return new LoanApplicationResponse(
                loan.getId(),
                loan.getCustomer().getId(),
                loan.getLoanAmount(),
                loan.getTenorMonth(),
                loan.getPurpose(),
                loan.getStatus());
    }
}