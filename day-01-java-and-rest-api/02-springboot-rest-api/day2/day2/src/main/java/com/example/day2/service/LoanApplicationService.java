package com.example.day2.service;

import com.example.day2.controller.LoanApplicationController;
import com.example.day2.dto.CreateLoanApplicationRequest;
import com.example.day2.dto.LoanApplicationResponse;
import com.example.day2.model.LoanApplication;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class LoanApplicationService {
    private final Map<Long, LoanApplication> loanStorage = new LinkedHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);


    public LoanApplicationResponse createLoan(CreateLoanApplicationRequest request) {
        Long id = idGenerator.incrementAndGet();
        LoanApplication loan = new LoanApplication(
                id, request.getCustomerId(), request.getLoanAmount(), 
                request.getTenorMonth(), request.getPurpose(), "SUBMITTED"
        );
        loanStorage.put(id, loan);
        return mapToResponse(loan);
    }

    // public List<LoanApplicationResponse> getAllLoans() {
    //     List<LoanApplicationResponse> responses = new ArrayList<>();
    //     for (LoanApplication loan : loanStorage.values()) {
    //         responses.add(mapToResponse(loan));
    //     }
    //     return responses;
    // }

    public LoanApplicationResponse getLoanById(Long id) {
        LoanApplication loan = loanStorage.get(id);
        return loan != null ? mapToResponse(loan) : null;
    }

    public List<LoanApplicationResponse> getLoanByCustomerIdStatus(String status, Long customerId) {
        boolean hasCustomerId = customerId != null && !customerId.toString().isBlank();
        boolean hasStatus = status != null && !status.isBlank();

        List<LoanApplicationResponse> responses = new ArrayList<>();

        for (LoanApplication loanApplication : loanStorage.values()){
            if (!hasCustomerId && !hasStatus){
                LoanApplicationResponse response = new LoanApplicationResponse();
                response.setCustomerId(loanApplication.getCustomerId());
                response.setId(loanApplication.getId());
                response.setLoanAmount(loanApplication.getLoanAmount());
                response.setPurpose(loanApplication.getPurpose());
                response.setStatus(loanApplication.getStatus());
                response.setTenorMonth(loanApplication.getTenorMonth());

                responses.add(response);
                continue;

            }

            boolean matchCustomerId = hasCustomerId 
                && loanApplication.getCustomerId() != null 
                && loanApplication.getCustomerId() == customerId;

            boolean matchStatus = hasStatus 
                && loanApplication.getStatus() != null 
                && loanApplication.getStatus().toString().equals(status.toUpperCase());

            if (matchCustomerId || matchStatus){
                LoanApplicationResponse response = new LoanApplicationResponse();
                response.setCustomerId(loanApplication.getCustomerId());
                response.setId(loanApplication.getId());
                response.setLoanAmount(loanApplication.getLoanAmount());
                response.setPurpose(loanApplication.getPurpose());
                response.setStatus(loanApplication.getStatus());
                response.setTenorMonth(loanApplication.getTenorMonth());

                responses.add(response);
                
            }
            
        }
        return responses;
    }
    public LoanApplicationResponse updateStatus(Long id, String newStatus) {
        LoanApplication loan = loanStorage.get(id);
        if (loan == null) return null;
        loan.setStatus(newStatus);
        return mapToResponse(loan);
    }

    private LoanApplicationResponse mapToResponse(LoanApplication loan) {
        return new LoanApplicationResponse(
                loan.getId(), loan.getCustomerId(), loan.getLoanAmount(), 
                loan.getTenorMonth(), loan.getPurpose(), loan.getStatus()
        );
    }
}