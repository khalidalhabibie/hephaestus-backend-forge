package com.fif.exercise02.service;


import com.fif.exercise02.model.LoanApplication;
import com.fif.exercise02.dto.CreateLoanApplicationRequest;
import com.fif.exercise02.dto.LoanApplicationResponse;

import java.util.*;

public class LoanApplicationService {

    private Map<String, LoanApplication> db = new HashMap<>();

    public LoanApplicationResponse create(CreateLoanApplicationRequest req) {
        String id = "LOAN-" + (db.size() + 1);

        LoanApplication loan = new LoanApplication(id, req.getCustomerId(), req.getAmount(),
                req.getTenorMonth(),
                req.getPurpose(), "SUBMITTED");
        db.put(id, loan);

        return toResponse(loan);
    }

    public List<LoanApplicationResponse> getAll() {
        return db.values().stream().map(this::toResponse).toList();
    }

    public LoanApplicationResponse getById(String id) {
        LoanApplication loan = db.get(id);
        return loan == null ? null : toResponse(loan);
    }

    public LoanApplicationResponse updateStatus(String id, String status) {
        LoanApplication loan = db.get(id);
        if (loan == null)
            return null;

        loan.setStatus(status);
        return toResponse(loan);
    }

    private LoanApplicationResponse toResponse(LoanApplication loan) {
        return new LoanApplicationResponse(
                loan.getId(),
                loan.getCustomerId(),
                loan.getAmount(),         
                loan.getTenorMonth(),
                loan.getPurpose(),
                loan.getStatus());
    }

    public List<LoanApplicationResponse> getAllFiltered(String status, String customerId) {

        return db.values().stream()
                .filter(l -> status == null || l.getStatus().equalsIgnoreCase(status))
                .filter(l -> customerId == null || l.getCustomerId().equals(customerId))
                .map(this::toResponse)
                .toList();
    }

    public LoanApplicationResponse cancel(String id) {

        LoanApplication loan = db.get(id);
        if (loan == null)
            return null;

        if ("APPROVED".equals(loan.getStatus())) {
            return null;
        }

        loan.setStatus("CANCELLED");
        return toResponse(loan);
    }
}
