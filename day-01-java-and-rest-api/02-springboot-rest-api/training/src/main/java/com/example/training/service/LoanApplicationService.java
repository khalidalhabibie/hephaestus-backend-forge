package com.example.training.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.training.dto.CreateLoanApplicationRequest;
import com.example.training.dto.LoanApplicationResponse;
import com.example.training.exception.NotFoundException;
import com.example.training.model.LoanApplication;
import com.example.training.model.LoanStatus;

@Service
public class LoanApplicationService {
    private final List<LoanApplication> loans = new ArrayList<>();

    public LoanApplicationResponse create(CreateLoanApplicationRequest request) {
        LoanApplication loan = new LoanApplication(
                UUID.randomUUID(),
                request.getCustomerId(),
                request.getLoanAmount(),
                request.getTenorMonth(),
                request.getPurpose(),
                LoanStatus.SUBMITTED);
        loans.add(loan);

        return toLoanApplicationResponse(loan);
    }

    public List<LoanApplicationResponse> findAll(String status, UUID customerUuid) {
        List<LoanApplicationResponse> responses = new ArrayList<>();

        for (LoanApplication loan : loans) {

            boolean match = true;

            if (status != null && !status.isEmpty()) {
                match = loan.getStatus().toString().equalsIgnoreCase(status);
            }

            if (match && customerUuid != null) {
                match = loan.getCustomerId().equals(customerUuid);
            }

            if (match) {
                responses.add(toLoanApplicationResponse(loan));
            }
        }

        return responses;
    }

    // helper
    public LoanApplicationResponse toLoanApplicationResponse(LoanApplication loan) {
        LoanApplicationResponse loanApplicationResponse = new LoanApplicationResponse();
        loanApplicationResponse.setCustomerId(loan.getCustomerId());
        loanApplicationResponse.setId(loan.getId());
        loanApplicationResponse.setLoanAmount(loan.getLoanAmount());
        loanApplicationResponse.setPurpose(loan.getPurpose());
        loanApplicationResponse.setTenorMonth(loan.getTenorMonth());
        loanApplicationResponse.setStatus(loan.getStatus());
        return loanApplicationResponse;
    }

    public LoanApplication findById(UUID id) {
        return loans.stream()
                .filter(loan -> loan.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Data not found"));
    }

    public LoanApplicationResponse approve(UUID id) {

        LoanApplication loan = findById(id);

        loan.setStatus(
                LoanStatus.APPROVED);

        return toLoanApplicationResponse(loan);
    }

    public LoanApplicationResponse reject(UUID id) {

        LoanApplication loan = findById(id);

        loan.setStatus(
                LoanStatus.REJECTED);

        return toLoanApplicationResponse(loan);
    }

    public LoanApplicationResponse cancel(UUID id) {

        LoanApplication loan = findById(id);

        loan.setStatus(
                LoanStatus.CANCELED);

        return toLoanApplicationResponse(loan);
    }
}
