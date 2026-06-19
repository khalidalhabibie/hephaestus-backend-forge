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
import com.example.demo.exception.LoanApplicationNotFoundException;
import com.example.demo.model.ApplicationStatus;
import com.example.demo.model.LoanApplication;
import com.example.demo.model.Role;
import com.example.demo.security.RoleValidation;

import lombok.RequiredArgsConstructor;

    // public List<CustomerResponse> getCustomers(String name, String email) {
    //     roleValidation.assign(Role.ADMIN, Role.STAFF, Role.APPROVER);

    //     List<CustomerResponse> responses = new ArrayList<>();

        // boolean hasName = name != null && !name.isBlank();
        // boolean hasEmail = email != null && !email.isBlank();

    //     for (Customer customer : customerStorage.values()) {

    //         if (!hasName && !hasEmail) {
    //             CustomerResponse response = new CustomerResponse();
    //             response.setId(customer.getId());
    //             response.setFullName(customer.getFullName());
    //             response.setEmail(customer.getEmail());
    //             response.setPhoneNumber(customer.getPhoneNumber());
    //             response.setCreatedAt(customer.getCreatedAt());
    //             response.setUpdatedAt(customer.getUpdatedAt());

    //             responses.add(response);
    //             continue;
    //         }

            // boolean matchName = hasName
            //         && customer.getFullName() != null
            //         && customer.getFullName().toLowerCase().contains(name.toLowerCase());

            // boolean matchEmail = hasEmail
            //         && customer.getEmail() != null
            //         && customer.getEmail().toLowerCase().contains(email.toLowerCase());

            // if (matchName || matchEmail) {
            //     CustomerResponse response = new CustomerResponse();
            //     response.setId(customer.getId());
            //     response.setFullName(customer.getFullName());
            //     response.setEmail(customer.getEmail());
            //     response.setPhoneNumber(customer.getPhoneNumber());
            //     response.setCreatedAt(customer.getCreatedAt());
            //     response.setUpdatedAt(customer.getUpdatedAt());

            //     responses.add(response);
            // }
    //     }

    //     return responses;
    // }

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

    public List<LoanApplicationResponse> getLoanApplications(Long customerId, String status) {

        roleValidation.assign(Role.ADMIN, Role.APPROVER);
        
        boolean hasCustomerId = customerId != null && !customerId.toString().isBlank();
        boolean hasStatus = status != null && !status.isBlank();

        List<LoanApplicationResponse> responses = new ArrayList<>();

        for (LoanApplication loanApplication : loanApplicationStorage.values()) {

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

            if (matchCustomerId || matchStatus) {
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

        roleValidation.assign(Role.ADMIN, Role.APPROVER, Role.MANAGER);


        if (loanApplicationStorage.get(id) == null) {
            throw new LoanApplicationNotFoundException(id);
        }

        LoanApplication loanApplication = loanApplicationStorage.get(id);

        if (loanApplication.getLoanAmount() > 500000000){
            roleValidation.assign(Role.ADMIN, Role.MANAGER);
        }

        if(status.isBlank()){
            throw new BadRequestException("No status set");
        } else if(status.contains("approve")){
            loanApplication.setStatus(ApplicationStatus.APPROVED);
        } else if(status.contains("reject")){
            loanApplication.setStatus(ApplicationStatus.REJECTED);
        } else if(status.contains("cancel")){
            loanApplication.setStatus(ApplicationStatus.CANCELLED);
        } else{
            throw new BadRequestException("Status not valid");
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
