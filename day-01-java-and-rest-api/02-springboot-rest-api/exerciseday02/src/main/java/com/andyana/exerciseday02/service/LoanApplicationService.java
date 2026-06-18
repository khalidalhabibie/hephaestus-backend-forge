package com.andyana.exerciseday02.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.andyana.exerciseday02.dto.CreateLoanApplicationRequest;
import com.andyana.exerciseday02.dto.LoanApplicationResponse;
import com.andyana.exerciseday02.exception.LoanApplicationNotFoundException;
import com.andyana.exerciseday02.model.LoanApplication;

@Service
public class LoanApplicationService {

    private final Map<Long, LoanApplication> loanApplicationMap = new HashMap<>();
    private Long idCounter = 1L;

    private final CustomerService customerService;

    public LoanApplicationService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public LoanApplicationResponse createLoanApplication(CreateLoanApplicationRequest request) {
        // Validate customer existence; throws CustomerNotFoundException if not found
        customerService.getCustomerById(request.getCustomerId());

        Long newId = idCounter++;
        LoanApplication loanApplication = new LoanApplication(
                newId,
                request.getCustomerId(),
                request.getLoanAmount(),
                request.getTenorMonth(),
                request.getPurpose(),
                "SUBMITTED"
        );

        loanApplicationMap.put(newId, loanApplication);
        return convertToResponse(loanApplication);
    }

    public LoanApplicationResponse getLoanApplicationById(Long id) {
        return Optional.ofNullable(loanApplicationMap.get(id))
                .map(this::convertToResponse)
                .orElseThrow(() -> new LoanApplicationNotFoundException("Loan application not found"));
    }

    public List<LoanApplicationResponse> getAllLoanApplications() {
        List<LoanApplicationResponse> responses = new ArrayList<>();
        for (LoanApplication loan : loanApplicationMap.values()) {
            responses.add(convertToResponse(loan));
        }
        return responses;
    }

    public LoanApplicationResponse approveLoanApplication(Long id) {
        LoanApplication loan = loanApplicationMap.get(id);
        if (loan == null) {
            throw new LoanApplicationNotFoundException("Loan application not found");
        }
        loan.setStatus("APPROVED");
        return convertToResponse(loan);
    }

    public LoanApplicationResponse rejectLoanApplication(Long id) {
        LoanApplication loan = loanApplicationMap.get(id);
        if (loan == null) {
            throw new LoanApplicationNotFoundException("Loan application not found");
        }
        loan.setStatus("REJECTED");
        return convertToResponse(loan);
    }

    private LoanApplicationResponse convertToResponse(LoanApplication loan) {
        return new LoanApplicationResponse(
                loan.getId(),
                loan.getCustomerId(),
                loan.getLoanAmount(),
                loan.getTenorMonth(),
                loan.getPurpose(),
                loan.getStatus()
        );
    }
}
