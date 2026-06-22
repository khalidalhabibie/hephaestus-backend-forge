package com.example.spring_boot_database.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring_boot_database.dto.CreateCustomerRequest;
import com.example.spring_boot_database.dto.CustomerResponse;
import com.example.spring_boot_database.dto.LoanApplicationResponse;
import com.example.spring_boot_database.entity.CustomerEntity;
import com.example.spring_boot_database.entity.LoanApplicationEntity;
import com.example.spring_boot_database.entity.Status;
import com.example.spring_boot_database.exception.CustomerNotFoundException;
import com.example.spring_boot_database.repository.CustomerRepository;
import com.example.spring_boot_database.repository.LoanApplicationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final LoanApplicationRepository loanApplicationRepository;

    private void fill(CustomerEntity customer, CreateCustomerRequest request) {
        customer.setNik(request.getNik());
        customer.setFullName(request.getFullName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
    }

    @Transactional
    public CustomerResponse createCustomer(CreateCustomerRequest request) {

        if (customerRepository.existsByNik(request.getNik())) {
            throw new RuntimeException("Customer NIK already exists");
        }

        CustomerEntity customer = new CustomerEntity();
        fill(customer, request);

        return toResponse(customerRepository.save(customer));
    }

    public CustomerEntity getById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public CustomerResponse findById(Long id) {
        return toResponse(getById(id));
    }

    public List<CustomerResponse> findCustomer(String name) {
        if (name != null && !name.isBlank()) {
            return customerRepository.findByFullNameContainingIgnoreCase(name)
                    .stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
        }
        return customerRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public List<LoanApplicationResponse> findLoanApplicationByCustomerId(Long customerId) {

        List<LoanApplicationEntity> data;

        if (customerId != null) {
            data = loanApplicationRepository.findByCustomerId(customerId);
        } else {
            data = loanApplicationRepository.findAll();
        }

        return data.stream()
                .map(this::toLoanApplicationResponse)
                .collect(Collectors.toList());
    }

    public LoanApplicationResponse toLoanApplicationResponse(LoanApplicationEntity loanApplication) {
        return LoanApplicationResponse.builder()
                .loanAmount(loanApplication.getLoanAmount())
                .tenorMonth(loanApplication.getTenorMonth())
                .purpose(loanApplication.getPurpose())
                .status(Status.valueOf(loanApplication.getStatus()))
                .customer(toResponse(loanApplication.getCustomer()))
                .build();
    }

    public CustomerResponse toResponse(CustomerEntity customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .nik(customer.getNik())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }
}