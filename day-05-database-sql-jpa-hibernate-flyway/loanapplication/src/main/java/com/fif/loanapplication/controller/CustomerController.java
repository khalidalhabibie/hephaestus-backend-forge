package com.fif.loanapplication.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fif.loanapplication.dto.common.ApiResponseDto;
import com.fif.loanapplication.dto.customer.CreateCustomerRequest;
import com.fif.loanapplication.dto.customer.CustomerResponse;
import com.fif.loanapplication.dto.loanapplication.LoanApplicationResponse;
import com.fif.loanapplication.service.CustomerService;
import com.fif.loanapplication.service.LoanApplicationService;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final LoanApplicationService loanApplicationService;

    // POST CUSTOMERS
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        return new ApiResponseDto<>(true, "Customer Created!", response);
    }

    // GET CUSTOMER BY UID
    @GetMapping("/{uid}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<CustomerResponse> getCustomerById(@Valid @PathVariable UUID uid) {
        CustomerResponse response = customerService.getCustomerById(uid);
        return new ApiResponseDto<>(true, "Customer retrieved", response);
    }

    // GET ALL CUSTOMER
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<CustomerResponse>> getAllCustomer(@Valid @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String nik,
            @RequestParam(required = false) String email) {

        List<CustomerResponse> response = customerService.getAllCustomers(fullName, nik, email);
        return new ApiResponseDto<>(true, "List of customers", response);

    }

    @GetMapping({ "/{customerUid}/loan-applications" })
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<List<LoanApplicationResponse>> getLoanApplicationByCustomerUid(
            @Valid @PathVariable("customerUid") UUID CustomerUid) {
        List<LoanApplicationResponse> responses = loanApplicationService.getLoanApplicationByCustomerUid(CustomerUid);
        return new ApiResponseDto<>(true, "List loan application by customer retrived succesfully!", responses);
    }

    // PUT Customer
    @PutMapping("/{uid}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<CustomerResponse> updateCustomer(@Valid @PathVariable UUID uid,
            @RequestBody CreateCustomerRequest request) {
        CustomerResponse response = customerService.editCustomer(uid, request);
        return new ApiResponseDto<>(true, "Customer data updated successfully!", response);
    }

    // DELETE Customer
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteCustomer(@PathVariable UUID uid) {
        customerService.deleteCustomer(uid);
        return "Customer Deleted!";
    }

}
