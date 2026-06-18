package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Service;

import com.example.demo.dto.CustomerResponse;
import com.example.demo.dto.LoanApplicationRequest;
import com.example.demo.dto.LoanApplicationResponse;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.exception.ForbiddenException;
import com.example.demo.exception.LoanApplicationNotFoundException;
import com.example.demo.model.ApplicationStatus;
import com.example.demo.model.Customer;
import com.example.demo.model.LoanApplication;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.security.AuthUtil;
import com.example.demo.security.RoleValidation;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    private Long sequence = 1L;
    private Map<Long, LoanApplication> loanApplicationStorage = new HashMap<>();

    private final RoleValidation roleValidation;

    public LoanApplicationResponse createLoanApplication(LoanApplicationRequest request){

        roleValidation.assign(Role.ADMIN, Role.STAFF);
        
        LoanApplication loanApplication = new LoanApplication();

        loanApplication.setCustomerId(request.getCustomerId());
        loanApplication.setLoanAmount(request.getLoanAmount());
        loanApplication.setTenorMonth(request.getTenorMonth());
        loanApplication.setPurpose(request.getPurpose());
        loanApplication.setStatus(ApplicationStatus.SUBMITTED);
        loanApplication.setId(sequence);

        loanApplicationStorage.put(sequence, loanApplication);

        sequence++;

        LoanApplicationResponse response = new LoanApplicationResponse();
        response.setCustomerId(loanApplication.getCustomerId());
        response.setId(loanApplication.getId());
        response.setLoanAmount(loanApplication.getLoanAmount());
        response.setPurpose(loanApplication.getPurpose());
        response.setStatus(loanApplication.getStatus());
        response.setTenorMonth(loanApplication.getTenorMonth());

        return response;

    }

    public List<LoanApplicationResponse> getLoanApplications() {

        roleValidation.assign(Role.ADMIN, Role.APPROVER);

        List<LoanApplicationResponse> responses = new ArrayList<>();

        for (LoanApplication loanApplication : loanApplicationStorage.values()) {

            LoanApplicationResponse response = new LoanApplicationResponse();
            response.setCustomerId(loanApplication.getCustomerId());
            response.setId(loanApplication.getId());
            response.setLoanAmount(loanApplication.getLoanAmount());
            response.setPurpose(loanApplication.getPurpose());
            response.setStatus(loanApplication.getStatus());
            response.setTenorMonth(loanApplication.getTenorMonth());

            responses.add(response);
        }
        return responses;
    }

    public LoanApplicationResponse getLoanApplicationById(Long id) {

        roleValidation.assign(Role.ADMIN, Role.APPROVER);

        if (loanApplicationStorage.get(id) == null) {
            throw new LoanApplicationNotFoundException(id);
        }

        LoanApplication loanApplication = loanApplicationStorage.get(id);


        LoanApplicationResponse response = new LoanApplicationResponse();
        response.setCustomerId(loanApplication.getCustomerId());
        response.setId(loanApplication.getId());
        response.setLoanAmount(loanApplication.getLoanAmount());
        response.setPurpose(loanApplication.getPurpose());
        response.setStatus(loanApplication.getStatus());
        response.setTenorMonth(loanApplication.getTenorMonth());

        return response;
    }

    public LoanApplicationResponse patchStatusLoanApplication(Long id, String status) {

        roleValidation.assign(Role.ADMIN, Role.APPROVER);

        if (loanApplicationStorage.get(id) == null) {
            throw new LoanApplicationNotFoundException(id);
        }

        LoanApplication loanApplication = loanApplicationStorage.get(id);

        if(status.isBlank()){
            throw new BadRequestException("No status set");
        }
        if(status.contains("approve")){
            loanApplication.setStatus(ApplicationStatus.APPROVED);
        }
        if(status.contains("reject")){
            loanApplication.setStatus(ApplicationStatus.REJECTED);
        }

        LoanApplicationResponse response = new LoanApplicationResponse();
        response.setCustomerId(loanApplication.getCustomerId());
        response.setId(loanApplication.getId());
        response.setLoanAmount(loanApplication.getLoanAmount());
        response.setPurpose(loanApplication.getPurpose());
        response.setStatus(loanApplication.getStatus());
        response.setTenorMonth(loanApplication.getTenorMonth());


        return response;
    }
    
}
