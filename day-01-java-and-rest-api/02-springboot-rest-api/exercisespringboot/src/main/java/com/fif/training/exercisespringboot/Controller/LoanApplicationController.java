package com.fif.training.exercisespringboot.Controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fif.training.exercisespringboot.DTO.LoanApplicationResponse;
import com.fif.training.exercisespringboot.Model.LoanApplication;
import com.fif.training.exercisespringboot.Service.LoanApplicationService;

@RestController
@RequestMapping("/api/v1/loan-applications")
public class LoanApplicationController {
    public LoanApplicationService loanApplicationService;

    public LoanApplicationResponse convertToResponse(LoanApplication loanApplication) {
        LoanApplicationResponse response = new LoanApplicationResponse();
        response.setId(loanApplication.getId());
        response.setCustomerId(loanApplication.getCustomerId());
        response.setLoanAmount(loanApplication.getLoanAmount());
        response.setTenorMonth(loanApplication.getTenorMonth());
        response.setPurpose(loanApplication.getPurpose());
        response.setStatus(loanApplication.getStatus());
        return response;
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<LoanApplicationResponse> approveLoanApplication(@PathVariable Long id) {
        LoanApplication approvedLoan = loanApplicationService.approve(id);
        if (approvedLoan == null) {
            return ResponseEntity.notFound().build();
        }
        LoanApplicationResponse response = convertToResponse(approvedLoan);
        return ResponseEntity.ok(response);
    }

    // Get All
    @GetMapping
    public ResponseEntity<List<LoanApplication>> getAllLoanApplications() {
        return ResponseEntity.status(HttpStatus.OK).body(loanApplicationService.getAll());
    }

    // Get By ID
    @GetMapping("/{id}")
    public ResponseEntity<LoanApplicationResponse> getCustomerbyId(@PathVariable Long id) {
        LoanApplicationResponse response = convertToResponse(loanApplicationService.getById(id));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Post create
    @PostMapping
    public ResponseEntity<LoanApplicationResponse> createLoanApplication(@PathVariable Long id) {
        LoanApplicationResponse response = convertToResponse(loanApplicationService.getById(id));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Patch
    @PatchMapping("/{id}/reject")
    public ResponseEntity<LoanApplicationResponse> rejectLoanApplication(@PathVariable Long id) {
        LoanApplication rejectedLoan = loanApplicationService.reject(id);
        if (rejectedLoan == null) {
            return ResponseEntity.notFound().build();
        }
        LoanApplicationResponse response = convertToResponse(rejectedLoan);
        return ResponseEntity.ok(response);
    }

    // put
    @PutMapping("/{id}")
    public ResponseEntity<LoanApplicationResponse> updateLoanApplication(@PathVariable Long id) {
        LoanApplicationResponse response = convertToResponse(loanApplicationService.getById(id));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
