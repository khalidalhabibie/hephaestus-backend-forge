package com.example.training.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.training.dto.CreateCustomerRequest;
import com.example.training.dto.CreateLoanApplicationRequest;
import com.example.training.dto.CustomerResponse;
import com.example.training.dto.LoanApplicationResponse;
import com.example.training.exception.CustomerNotFoundException;
import com.example.training.exception.LoanNotFoundException;
import com.example.training.model.Customer;
import com.example.training.model.LoanApplication;
import com.example.training.model.LoanStatus;

@Service
public class LoanApplicationService {
    // private Map<UUID, LoanApplication> loanApplicationStorage = new HashMap<>();
    private final List<LoanApplication> loanApplicationStorage = new ArrayList<>();

    public LoanApplicationResponse createLoanApplication(CreateLoanApplicationRequest request) {
        LoanApplication loanApp = new LoanApplication(
            UUID.randomUUID(), 
            request.getCustomerId(), 
            request.getLoanAmount(), 
            request.getTenorMonth(), 
            request.getPurpose(), 
            LoanStatus.SUBMITTED);
        loanApplicationStorage.add(loanApp);

        LoanApplicationResponse response = new LoanApplicationResponse();
        response.setLoanApplicationId(loanApp.getLoanApplicationId());
		response.setId(loanApp.getId());
		response.setLoanAmount(loanApp.getLoanAmount());
		response.setTenorMonth(loanApp.getTenorMonth());
		response.setPurpose(loanApp.getPurpose());
        response.setStatus(loanApp.getStatus());

        return response;
	}

    public List<LoanApplicationResponse> getLoanApplication() {
        List<LoanApplicationResponse> response = new ArrayList<>();
        for(LoanApplication loanApp : loanApplicationStorage) {
            LoanApplicationResponse loanApplicationResponse = new LoanApplicationResponse();
            loanApplicationResponse.setLoanApplicationId(loanApp.getLoanApplicationId());
            loanApplicationResponse.setId(loanApp.getId());
            loanApplicationResponse.setLoanAmount(loanApp.getLoanAmount());
            loanApplicationResponse.setTenorMonth(loanApp.getTenorMonth());
            loanApplicationResponse.setPurpose(loanApp.getPurpose());
            loanApplicationResponse.setStatus(loanApp.getStatus());

            response.add(loanApplicationResponse);
            return response;
        }
        throw new LoanNotFoundException("Loan application not found.");
    }

    public LoanApplicationResponse getLoanApplicationById(@PathVariable UUID uuid) {
        LoanApplicationResponse response = new LoanApplicationResponse();

        for (LoanApplication loanApplication : loanApplicationStorage) {
            if(loanApplication.getLoanApplicationId().equals(uuid)){
                response.setLoanApplicationId(loanApplication.getLoanApplicationId());
                response.setId(loanApplication.getId());
                response.setLoanAmount(loanApplication.getLoanAmount());
                response.setTenorMonth(loanApplication.getTenorMonth());
                response.setPurpose(loanApplication.getPurpose());
                response.setStatus(loanApplication.getStatus());
                return response; 
            }
        }
        
        throw new LoanNotFoundException("Loan application not found.");
    } 

    public LoanApplicationResponse approveLoanApplication(@PathVariable UUID uuid) {
        LoanApplicationResponse response = new LoanApplicationResponse();
        for (LoanApplication loanApplication : loanApplicationStorage) {
            if(loanApplication.getLoanApplicationId().equals(uuid)){
                loanApplication.setStatus(LoanStatus.APPROVED);

                response.setLoanApplicationId(loanApplication.getLoanApplicationId());
                response.setId(loanApplication.getId());
                response.setLoanAmount(loanApplication.getLoanAmount());
                response.setTenorMonth(loanApplication.getTenorMonth());
                response.setPurpose(loanApplication.getPurpose());
                response.setStatus(loanApplication.getStatus());
                return response;
            }
        }
        throw new LoanNotFoundException("Loan application not found.");
    } 

    public LoanApplicationResponse rejectLoanApplication(@PathVariable UUID uuid) {
        LoanApplicationResponse response = new LoanApplicationResponse();
        for (LoanApplication loanApplication : loanApplicationStorage) {
            if(loanApplication.getLoanApplicationId().equals(uuid)){
                loanApplication.setStatus(LoanStatus.REJECTED);

                response.setLoanApplicationId(loanApplication.getLoanApplicationId());
                response.setId(loanApplication.getId());
                response.setLoanAmount(loanApplication.getLoanAmount());
                response.setTenorMonth(loanApplication.getTenorMonth());
                response.setPurpose(loanApplication.getPurpose());
                response.setStatus(loanApplication.getStatus());
                return response; 
            }
        }
        throw new LoanNotFoundException("Loan application not found.");
    } 

    public List<LoanApplicationResponse> searchSubmittedApplication(LoanStatus status) {
        List<LoanApplicationResponse> response = new ArrayList<>();
        for(LoanApplication loanApplication : loanApplicationStorage) {
            LoanApplicationResponse loanApplicationResponse = new LoanApplicationResponse();
            if(loanApplication.getStatus().equals(LoanStatus.SUBMITTED)) {
                loanApplicationResponse.setLoanApplicationId(loanApplication.getLoanApplicationId());
                loanApplicationResponse.setId(loanApplication.getId());
                loanApplicationResponse.setLoanAmount(loanApplication.getLoanAmount());
                loanApplicationResponse.setTenorMonth(loanApplication.getTenorMonth());
                loanApplicationResponse.setPurpose(loanApplication.getPurpose());
                loanApplicationResponse.setStatus(loanApplication.getStatus());

                response.add(loanApplicationResponse);
            }
        }
        return response;
    }

    public List<LoanApplicationResponse> searchCustomerId(Long id) {
        List<LoanApplicationResponse> response = new ArrayList<>();
        for(LoanApplication loanApplication : loanApplicationStorage) {
            LoanApplicationResponse loanApplicationResponse = new LoanApplicationResponse();
            if(loanApplication.getId().equals(id)) {
                loanApplicationResponse.setLoanApplicationId(loanApplication.getLoanApplicationId());
                loanApplicationResponse.setId(loanApplication.getId());
                loanApplicationResponse.setLoanAmount(loanApplication.getLoanAmount());
                loanApplicationResponse.setTenorMonth(loanApplication.getTenorMonth());
                loanApplicationResponse.setPurpose(loanApplication.getPurpose());
                loanApplicationResponse.setStatus(loanApplication.getStatus());

                response.add(loanApplicationResponse);
            }
        }
        return response;
    }

    public LoanApplicationResponse cancelLoanApplication(@PathVariable UUID uuid) {
        LoanApplicationResponse response = new LoanApplicationResponse();
        for (LoanApplication loanApplication : loanApplicationStorage) {
            if(loanApplication.getLoanApplicationId().equals(uuid)){
                loanApplication.setStatus(LoanStatus.CANCELLED);

                response.setLoanApplicationId(loanApplication.getLoanApplicationId());
                response.setId(loanApplication.getId());
                response.setLoanAmount(loanApplication.getLoanAmount());
                response.setTenorMonth(loanApplication.getTenorMonth());
                response.setPurpose(loanApplication.getPurpose());
                response.setStatus(loanApplication.getStatus());
                return response;
            }
        }
        throw new LoanNotFoundException("Loan application not found.");
    } 
}
