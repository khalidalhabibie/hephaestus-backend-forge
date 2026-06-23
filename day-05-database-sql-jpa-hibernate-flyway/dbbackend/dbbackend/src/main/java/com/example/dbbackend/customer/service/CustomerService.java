package com.example.dbbackend.customer.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.dbbackend.common.exception.CustomerNotFoundException;
import com.example.dbbackend.common.exception.DuplicateCustomerException;
import com.example.dbbackend.customer.dto.CreateCustomerRequest;
import com.example.dbbackend.customer.dto.CustomerResponse;
import com.example.dbbackend.customer.entity.CustomerEntity;
import com.example.dbbackend.customer.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerResponse createCustomer(CreateCustomerRequest request) {

        if (customerRepository.existsByNik(request.getNik())) {
            log.warn("event=CustomerCreateFailed reason=DuplicateNIK");
            throw new DuplicateCustomerException("NIK already exists");
        }

        if (customerRepository.existsByEmail(request.getEmail())) {
            log.warn("event=CustomerCreateFailed reason=DuplicateEmail");
            throw new DuplicateCustomerException("Email already exists");
        }

        CustomerEntity entity = new CustomerEntity();
        entity.setFullName(request.getFullName());
        entity.setNik(request.getNik());
        entity.setEmail(request.getEmail());
        entity.setPhoneNumber(request.getPhoneNumber());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        CustomerEntity saved = customerRepository.save(entity);

        // Success log
        log.info("event=CustomerCreated customerId={}", saved.getId());

        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(Long id) {
        CustomerEntity entity = customerRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("event=CustomerNotFound customerId={}", id);
                    return new CustomerNotFoundException("Customer not found with id: " + id);
                });

        // success untuk fetch
        log.info("event=CustomerFetched customerId={}", id);

        return mapToResponse(entity);
    }

    private CustomerResponse mapToResponse(CustomerEntity entity) {
        CustomerResponse response = new CustomerResponse();
        response.setId(entity.getId());
        response.setFullName(entity.getFullName());
        response.setNik(entity.getNik());
        response.setEmail(entity.getEmail());
        response.setPhoneNumber(entity.getPhoneNumber());
        return response;
    }
}
