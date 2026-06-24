package com.example.demo_day2.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo_day2.dto.CreateLoanApplicationRequest;
import com.example.demo_day2.dto.LoanApplicationResponse;
import com.example.demo_day2.model.LoanApplication;
import com.example.demo_day2.model.LoanStatus;
import com.example.demo_day2.model.Role;
import com.example.demo_day2.model.User;

@Service
public class LoanApplicationService {

    private Map<Long, LoanApplication> storage = new HashMap<>();
    private Long seq = 1L;
    private static final double ManagerMinAmount = 500_000_000;

    public LoanApplicationResponse create(CreateLoanApplicationRequest req) {
        LoanApplication loan = new LoanApplication(
                seq,
                req.getCustomerId(),
                req.getLoanAmount(),
                req.getTenorMonth(),
                req.getPurpose(),
                LoanStatus.SUBMITTED);

        storage.put(seq, loan);
        seq++;

        return toResponse(loan);
    }

    public List<LoanApplicationResponse> getAll() {
        return storage.values().stream().map(this::toResponse).toList();
    }

    public LoanApplicationResponse getById(Long id) {
        LoanApplication loan = storage.get(id);

        if (loan == null) {
            throw new RuntimeException("Loan application not found");
        }

        return toResponse(loan);
    }

    public LoanApplicationResponse approve(Long id, User user) {
        LoanApplication loan = storage.get(id);

        if (loan == null) {
            throw new RuntimeException("Loan application not found");
        }

        if (user.getRole() == Role.MANAGER) {
            if (loan.getLoanAmount() <= ManagerMinAmount) {
                throw new RuntimeException("Manager can only approve loans above " + ManagerMinAmount);
            }
        }

        loan.setStatus(LoanStatus.APPROVED);

        return toResponse(loan);
    }

    public LoanApplicationResponse reject(Long id) {
        LoanApplication loan = storage.get(id);

        if (loan == null)
            throw new RuntimeException("Loan application not found");

        loan.setStatus(LoanStatus.REJECTED);
        return toResponse(loan);
    }

    private LoanApplicationResponse toResponse(LoanApplication loan) {
        LoanApplicationResponse res = new LoanApplicationResponse();
        res.setId(loan.getId());
        res.setCustomerId(loan.getCustomerId());
        res.setLoanAmount(loan.getLoanAmount());
        res.setTenorMonth(loan.getTenorMonth());
        res.setPurpose(loan.getPurpose());
        res.setStatus(loan.getStatus().name());
        return res;
    }

    public List<LoanApplicationResponse> getFiltered(LoanStatus status, Long customerId) {
        return storage.values().stream()
                .filter(loan -> status == null || loan.getStatus() == status)
                .filter(loan -> customerId == null || loan.getCustomerId().equals(customerId))
                .map(this::toResponse)
                .toList();
    }

    public LoanApplicationResponse cancel(Long id) {
        LoanApplication loan = storage.get(id);

        if (loan == null) {
            throw new RuntimeException("Loan not found");
        }

        if (loan.getStatus() != LoanStatus.SUBMITTED) {
            throw new RuntimeException("Only SUBMITTED loans can be cancelled");
        }

        loan.setStatus(LoanStatus.CANCELLED);
        return toResponse(loan);
    }

}
