package com.example.main.services;

import com.example.main.dto.LoanApplicationRequest;
import com.example.main.dto.LoanApplicationResponse;
import com.example.main.models.LoanApplication;
import com.example.main.repositories.LoanApplicationRepository;
import com.example.main.exceptions.LoanApplicationNotFoundException;
import com.example.main.exceptions.ForbiddenException;
import com.example.main.security.UserRole;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;
    
    private static final BigDecimal MANAGER_MINIMUM_AMOUNT = new BigDecimal("10000000");

    public LoanApplicationService(LoanApplicationRepository loanApplicationRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
    }

    public LoanApplicationResponse createLoanApplication(LoanApplicationRequest request) {
        LoanApplication loan = new LoanApplication();
        loan.setCustomerId(request.getCustomerId());
        loan.setLoanAmount(request.getLoanAmount());
        loan.setTenorMonth(request.getTenorMonth());
        loan.setPurpose(request.getPurpose());
        loan.setStatus("SUBMITTED");
        LoanApplication savedLoan = loanApplicationRepository.save(loan);
        return mapToResponse(savedLoan);
    }

    public List<LoanApplicationResponse> getAllLoanApplications(String status, Long customerId) {
        return loanApplicationRepository.findAll().stream()
                .filter(loan -> status == null || loan.getStatus().equalsIgnoreCase(status))
                .filter(loan -> customerId == null || loan.getCustomerId().equals(customerId))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public LoanApplicationResponse getLoanApplicationById(Long id) {
        LoanApplication loan = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new LoanApplicationNotFoundException("Loan application not found"));
        return mapToResponse(loan);
    }

    public LoanApplicationResponse approveLoanApplication(Long id, UserRole userRole) {
        LoanApplication loan = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new LoanApplicationNotFoundException("Loan application not found"));

        if (userRole == UserRole.MANAGER && loan.getLoanAmount().compareTo(MANAGER_MINIMUM_AMOUNT) <= 0) {
            throw new ForbiddenException("Manager is only allowed to approve loans above 10,000,000");
        }

        loan.setStatus("APPROVED");
        LoanApplication updatedLoan = loanApplicationRepository.save(loan);
        return mapToResponse(updatedLoan);
    }

    public LoanApplicationResponse rejectLoanApplication(Long id) {
        LoanApplication loan = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new LoanApplicationNotFoundException("Loan application not found"));
        loan.setStatus("REJECTED");
        LoanApplication updatedLoan = loanApplicationRepository.save(loan);
        return mapToResponse(updatedLoan);
    }

    public LoanApplicationResponse cancelLoanApplication(Long id) {
        LoanApplication loan = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new LoanApplicationNotFoundException("Loan application not found"));
        
        loan.setStatus("CANCELLED");
        LoanApplication updatedLoan = loanApplicationRepository.save(loan);
        return mapToResponse(updatedLoan);
    }

    private LoanApplicationResponse mapToResponse(LoanApplication loan) {
        return new LoanApplicationResponse(
                loan.getId(), loan.getCustomerId(), loan.getLoanAmount(),
                loan.getTenorMonth(), loan.getPurpose(), loan.getStatus()
        );
    }
}