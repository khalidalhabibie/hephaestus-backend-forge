package com.fif.training.exercisespringboot.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fif.training.exercisespringboot.DTO.CreateLoanApplicationRequest;
import com.fif.training.exercisespringboot.DTO.LoanApplicationResponse;
import com.fif.training.exercisespringboot.Model.LoanApplication;
import com.fif.training.exercisespringboot.exception.LoanApplicationNotFoundException;

@Service
public class LoanApplicationService {

    private final Map<Long, LoanApplication> db = new HashMap<>();
    private Long idCounter = 1L;

    private LoanApplicationResponse toResponse(LoanApplication loan) {
        LoanApplicationResponse response = new LoanApplicationResponse();
        response.setId(loan.getId());
        response.setCustomerId(loan.getCustomerId());
        response.setLoanAmount(loan.getLoanAmount());
        response.setTenorMonth(loan.getTenorMonth());
        response.setPurpose(loan.getPurpose());
        response.setStatus(loan.getStatus());
        return response;
    }

    public LoanApplicationResponse create(CreateLoanApplicationRequest request) {
        LoanApplication loan = new LoanApplication();
        loan.setId(idCounter);
        loan.setCustomerId(request.getCustomerId());
        loan.setLoanAmount(request.getLoanAmount());
        loan.setTenorMonth(request.getTenorMonth());
        loan.setPurpose(request.getPurpose());
        loan.setStatus("SUBMITTED");

        db.put(idCounter, loan);
        idCounter++;
        return toResponse(loan);
    }

    public List<LoanApplicationResponse> getAll() {
        return db.values().stream()
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

    public LoanApplicationResponse approve(Long id) {
        LoanApplication loan = db.get(id);
        if (loan == null) {
            throw new LoanApplicationNotFoundException(id);
        }
        loan.setStatus("APPROVED");
        return toResponse(loan);
    }

    public LoanApplicationResponse reject(Long id) {
        LoanApplication loan = db.get(id);
        if (loan == null) {
            throw new LoanApplicationNotFoundException(id);
        }
        loan.setStatus("REJECTED");
        return toResponse(loan);
    }
}