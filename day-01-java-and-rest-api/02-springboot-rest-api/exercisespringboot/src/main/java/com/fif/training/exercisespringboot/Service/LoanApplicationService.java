package com.fif.training.exercisespringboot.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fif.training.exercisespringboot.DTO.CreateLoanApplicationRequest;
import com.fif.training.exercisespringboot.DTO.LoanApplicationResponse;
import com.fif.training.exercisespringboot.Model.LoanApplication;
import com.fif.training.exercisespringboot.Model.LoanStatus;
import com.fif.training.exercisespringboot.Model.User;
import com.fif.training.exercisespringboot.exception.ForbiddenException;
import com.fif.training.exercisespringboot.exception.LoanApplicationNotFoundException;

@Service
public class LoanApplicationService {

    private final Map<Long, LoanApplication> db = new HashMap<>();
    private Long idCounter = 1L;
    private static final double MANAGER_APPROVE_LIMIT = 10_000_000.0;

    private LoanApplicationResponse toResponse(LoanApplication loan) {
        LoanApplicationResponse response = new LoanApplicationResponse();
        response.setId(loan.getId());
        response.setCustomerId(loan.getCustomerId());
        response.setLoanAmount(loan.getLoanAmount());
        response.setTenorMonth(loan.getTenorMonth());
        response.setPurpose(loan.getPurpose());
        response.setStatus(loan.getStatus().name());
        return response;
    }

    public LoanApplicationResponse create(CreateLoanApplicationRequest request) {
        LoanApplication loan = new LoanApplication();
        loan.setId(idCounter);
        loan.setCustomerId(request.getCustomerId());
        loan.setLoanAmount(request.getLoanAmount());
        loan.setTenorMonth(request.getTenorMonth());
        loan.setPurpose(request.getPurpose());
        loan.setStatus(LoanStatus.SUBMITTED);

        db.put(idCounter, loan);
        idCounter++;
        return toResponse(loan);
    }

    public List<LoanApplicationResponse> getAll(LoanStatus status, Long customerId) {
        return db.values().stream()
                .filter(l -> status == null || l.getStatus() == status)
                .filter(l -> customerId == null || l.getCustomerId().equals(customerId))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public LoanApplicationResponse getById(Long id) {
        LoanApplication loan = db.get(id);
        if (loan == null) {
            throw new LoanApplicationNotFoundException(id);
        }
        return toResponse(loan);
    }

    public LoanApplicationResponse approve(Long id, User user) {
        LoanApplication loan = db.get(id);
        if (loan == null) {
            throw new LoanApplicationNotFoundException(id);
        }

        if ("MANAGER".equals(user.getRole()) && loan.getLoanAmount() <= MANAGER_APPROVE_LIMIT) {
            throw new ForbiddenException("Manager can only approve loan above Rp 10.000.000");
        }

        loan.setStatus(LoanStatus.APPROVED);
        return toResponse(loan);
    }

    public LoanApplicationResponse reject(Long id) {
        LoanApplication loan = db.get(id);
        if (loan == null) {
            throw new LoanApplicationNotFoundException(id);
        }
        loan.setStatus(LoanStatus.REJECTED);
        return toResponse(loan);
    }

    public LoanApplicationResponse cancel(Long id) {
        LoanApplication loan = db.get(id);
        if (loan == null) {
            throw new LoanApplicationNotFoundException(id);
        }

        if (loan.getStatus() != LoanStatus.SUBMITTED) {
            throw new IllegalStateException("Only SUBMITTED loan can be cancelled");
        }

        loan.setStatus(LoanStatus.CANCELLED);
        return toResponse(loan);
    }
}