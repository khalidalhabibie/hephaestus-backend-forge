package com.example.day2.service;

import com.example.day2.dto.CreateLoanApplicationRequest;
import com.example.day2.dto.LoanApplicationResponse;
import com.example.day2.model.LoanApplication;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class LoanApplicationService {
    private final Map<String, LoanApplication> loanStorage = new HashMap<>();

    public LoanApplicationResponse createLoan(CreateLoanApplicationRequest request) {
        String id = "LOAN-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        LoanApplication loan = new LoanApplication(id, request.getCustomerId(), request.getAmount(), "PENDING");
        loanStorage.put(id, loan);
        return mapToResponse(loan);
    }

    public List<LoanApplicationResponse> getAllLoans() {
        List<LoanApplicationResponse> responses = new ArrayList<>();
        for (LoanApplication loan : loanStorage.values()) {
            responses.add(mapToResponse(loan));
        }
        return responses;
    }

    public LoanApplicationResponse getLoanById(String id) {
        LoanApplication loan = loanStorage.get(id);
        return loan != null ? mapToResponse(loan) : null;
    }

    public LoanApplicationResponse updateStatus(String id, String newStatus) {
        LoanApplication loan = loanStorage.get(id);
        if (loan == null) return null;
        loan.setStatus(newStatus);
        return mapToResponse(loan);
    }

    private LoanApplicationResponse mapToResponse(LoanApplication loan) {
        return new LoanApplicationResponse(loan.getId(), loan.getCustomerId(), loan.getAmount(), loan.getStatus());
    }
}
