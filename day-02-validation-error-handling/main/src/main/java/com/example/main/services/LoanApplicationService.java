package com.example.main.services;

import com.example.main.dto.LoanApplicationRequest;
import com.example.main.dto.LoanApplicationResponse;
import com.example.main.models.LoanApplication;
import com.example.main.repositories.LoanApplicationRepository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;

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

        return new LoanApplicationResponse(
                savedLoan.getId(),
                savedLoan.getCustomerId(),
                savedLoan.getLoanAmount(),
                savedLoan.getTenorMonth(),
                savedLoan.getPurpose(),
                savedLoan.getStatus()
        );
    }

    public List<LoanApplicationResponse> getAllLoanApplications() {
        return loanApplicationRepository.findAll().stream()
                .map(loan -> new LoanApplicationResponse(
                        loan.getId(),
                        loan.getCustomerId(),
                        loan.getLoanAmount(),
                        loan.getTenorMonth(),
                        loan.getPurpose(),
                        loan.getStatus()
                ))
                .collect(Collectors.toList());
    }

    public LoanApplicationResponse getLoanApplicationById(Long id) {
        LoanApplication loan = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new com.example.main.exceptions.LoanApplicationNotFoundException("Loan application not found"));

        return new LoanApplicationResponse(
                loan.getId(),
                loan.getCustomerId(),
                loan.getLoanAmount(),
                loan.getTenorMonth(),
                loan.getPurpose(),
                loan.getStatus()
        );
    }

    public LoanApplicationResponse approveLoanApplication(Long id) {
        LoanApplication loan = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new com.example.main.exceptions.LoanApplicationNotFoundException("Loan application not found"));

        loan.setStatus("APPROVED");
        
        LoanApplication updatedLoan = loanApplicationRepository.save(loan);

        return new LoanApplicationResponse(
                updatedLoan.getId(),
                updatedLoan.getCustomerId(),
                updatedLoan.getLoanAmount(),
                updatedLoan.getTenorMonth(),
                updatedLoan.getPurpose(),
                updatedLoan.getStatus()
        );
    }

    public LoanApplicationResponse rejectLoanApplication(Long id) {
        LoanApplication loan = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new com.example.main.exceptions.LoanApplicationNotFoundException("Loan application not found"));

        loan.setStatus("REJECTED");
        
        LoanApplication updatedLoan = loanApplicationRepository.save(loan);

        return new LoanApplicationResponse(
                updatedLoan.getId(),
                updatedLoan.getCustomerId(),
                updatedLoan.getLoanAmount(),
                updatedLoan.getTenorMonth(),
                updatedLoan.getPurpose(),
                updatedLoan.getStatus()
        );
    }
}