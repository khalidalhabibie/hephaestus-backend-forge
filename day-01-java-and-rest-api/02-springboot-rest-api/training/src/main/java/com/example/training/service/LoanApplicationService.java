package com.example.training.service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.example.training.exception.LoanApplicationNotFoundException;
import com.example.training.dto.CreateLoanApplicationRequest;
import com.example.training.model.LoanApplication;

@Service
public class LoanApplicationService {

    private final Map<Long, LoanApplication> db = new HashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    public LoanApplication createLoanApplication(CreateLoanApplicationRequest request) {
        Long id = sequence.getAndIncrement();
        LoanApplication loanApplication = new LoanApplication(
                id,
                request.getCustomerId(),
                request.getLoanAmount(),
                request.getTenorMonth(),
                request.getPurpose(),
                "SUBMITTED",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        db.put(id, loanApplication);
        return loanApplication;
    }

    public List<LoanApplication> getAll() {
        return new ArrayList<>(db.values());
    }

    public LoanApplication getLoanApplicationById(Long id) {
        LoanApplication loan = db.get(id);
        if (loan == null) {
            throw new LoanApplicationNotFoundException(
                    "Loan Application " + id + " not found."
            );
        }
        return loan;
    }

    public List<LoanApplication> getLoanApplicationByStatus(String status) {
        return db.values()
            .stream()
            .filter(loan -> loan.getStatus().equalsIgnoreCase(status))
            .toList();
    }
    
    public List<LoanApplication> getLoanApplicationByCustomerId(Long customerId) {
        return db.values()
            .stream()
            .filter(loan -> loan.getCustomerId().equals(customerId))
            .collect(Collectors.toList());
    }

    public LoanApplication approve(Long id) {
        LoanApplication loan = getLoanApplicationById(id);
        loan.setStatus("APPROVED");
        loan.setUpdatedAt(LocalDateTime.now());
        return loan;
    }

    public LoanApplication reject(Long id) {
        LoanApplication loan = getLoanApplicationById(id);
        loan.setStatus("REJECTED");
        loan.setUpdatedAt(LocalDateTime.now());
        return loan;
    }
    
    public LoanApplication cancel(Long id) {
        LoanApplication loan = getLoanApplicationById(id);
        loan.setStatus("CANCELLED");
        loan.setUpdatedAt(LocalDateTime.now());
        return loan;
    }
}
