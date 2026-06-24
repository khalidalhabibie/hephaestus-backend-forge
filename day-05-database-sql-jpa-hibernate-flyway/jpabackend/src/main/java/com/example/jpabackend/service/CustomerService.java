package com.example.jpabackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.jpabackend.dto.request.CreateCustomerRequest;
import com.example.jpabackend.dto.request.PatchCustomerRequest;
import com.example.jpabackend.dto.request.UpdateCustomerRequest;
import com.example.jpabackend.dto.response.CustomerResponse;
import com.example.jpabackend.dto.response.CustomerSummaryResponse;
import com.example.jpabackend.entity.CustomerEntity;
import com.example.jpabackend.exception.CustomerNotFoundException;
import com.example.jpabackend.exception.DuplicateCustomerException;
import com.example.jpabackend.repository.CustomerRepository;
import lombok.*;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private void validateDuplicateCustomer(CreateCustomerRequest request) {
        if (customerRepository.existsByNik(request.getNik())) {
            log.warn("event=validation_error message=duplicate_nik");
            throw new DuplicateCustomerException(
                "Nik " + request.getNik() + " already exists");
        }
        if (customerRepository.existsByEmail(request.getEmail())) {
            log.warn("event=validation_error message=duplicate_email");
            throw new DuplicateCustomerException(
                "Email " + request.getEmail() + " already exists");
        }
    }

    private CustomerResponse mapToResponse(CustomerEntity customer) {
        return CustomerResponse.builder()
            .id(customer.getId())
            .fullName(customer.getFullName())
            .nik(customer.getNik())
            .email(customer.getEmail())
            .phoneNumber(customer.getPhoneNumber())
            .build();
    }
    
    private CustomerSummaryResponse mapToCustomerSummaryResponse(CustomerEntity customer) {
        return CustomerSummaryResponse.builder()
            .id(customer.getId())
            .fullName(customer.getFullName())
            .nik(customer.getNik())
            .email(customer.getEmail())
            .build();
    }

    @Transactional
    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        validateDuplicateCustomer(request);
        CustomerEntity customer = CustomerEntity.builder()
            .fullName(request.getFullName())
            .nik(request.getNik())
            .email(request.getEmail())
            .phoneNumber(request.getPhoneNumber())
            .build();
        CustomerEntity savedCustomer = customerRepository.save(customer);
        log.info("event=customer_created customer_id={}", savedCustomer.getId());
        return mapToResponse(savedCustomer);
    }

    @Transactional(readOnly = true)
    public List<CustomerSummaryResponse> getAll() {
        return customerRepository.findAll()
            .stream()
            .map(this::mapToCustomerSummaryResponse)
            .toList();
    }
    
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(Long id) {
        CustomerEntity customer = customerRepository.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException(
                "Customer " + id + " not found"));
        return mapToResponse(customer);
    }

    @Transactional(readOnly = true)
    public List<CustomerSummaryResponse> searchCustomer(String name) {
        return customerRepository
            .findByFullNameContainingIgnoreCase(name)
            .stream()
            .map(this::mapToCustomerSummaryResponse)
            .toList();
    }

    @Transactional
    public CustomerResponse updateCustomer(Long id, UpdateCustomerRequest request) {
        CustomerEntity customer = customerRepository.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException(
                "Customer " + id + " not found"));
            if (!customer.getEmail().equals(request.getEmail())
                    && customerRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateCustomerException("Email already exists");
            }
            customer.setFullName(request.getFullName());
            customer.setEmail(request.getEmail());
            customer.setPhoneNumber(request.getPhoneNumber());
        CustomerEntity updateCustomer = customerRepository.save(customer);
        return mapToResponse(updateCustomer);
    }

    @Transactional
    public CustomerResponse patchCustomer(Long id, PatchCustomerRequest request) {
        CustomerEntity customer = customerRepository.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException(
                "Customer " + id + " not found"));
            if (request.getFullName() != null) {
                customer.setFullName(request.getFullName());
            }
            if (request.getEmail() != null) {
                if (!request.getEmail().equals(customer.getEmail())
                        && customerRepository.existsByEmail(request.getEmail())) {
                    throw new DuplicateCustomerException("Email already exists");
                } 
                customer.setEmail(request.getEmail());
            }
            if (request.getPhoneNumber() != null) {
                customer.setPhoneNumber(request.getPhoneNumber());
            }
        CustomerEntity updatedCustomer = customerRepository.save(customer);
        return mapToResponse(updatedCustomer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        CustomerEntity customer = customerRepository.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException(
                "Customer " + id + " not found"));
        customer.setDeleted(true);
        customerRepository.save(customer);  
    }
}