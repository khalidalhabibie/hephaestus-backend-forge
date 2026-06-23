package com.example.jpabackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.jpabackend.entity.CustomerEntity;
import com.example.jpabackend.exception.CustomerNotFoundException;
import com.example.jpabackend.exception.DuplicateCustomerException;
import com.example.jpabackend.repository.CustomerRepository;
import com.example.jpabackend.dto.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class CustomerService {
    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    // CREATE CUSTOMER
    @Transactional
    public CustomerResponse createCustomer(CreateCustomerRequest req) {

        if (repository.existsByNik(req.getNik())) {

            log.warn(
                "event=validation_error field=nik error=duplicate_nik"
            );
        
            throw new DuplicateCustomerException("Customer NIK already exists");
        }

        if (repository.existsByEmail(req.getEmail())) {

            log.info(
                "event=validation_error field=email error=duplicate_email"
            );
        
            throw new DuplicateCustomerException("Customer email already exists");
        }

        CustomerEntity customer = new CustomerEntity();
        customer.setFullName(req.getFullName());
        customer.setNik(req.getNik());
        customer.setEmail(req.getEmail());
        customer.setPhoneNumber(req.getPhoneNumber());

        customer.setCreatedAt(ZonedDateTime.now());
        customer.setUpdatedAt(ZonedDateTime.now());

        repository.save(customer);
        
        
        log.info(
            "event=customer_created customer_id={}",
            customer.getId()
        );


        return toResponse(customer);
    }

    // GET BY ID
    @Transactional(readOnly = true)
    public CustomerResponse getById(Long id) {

        CustomerEntity customer = repository.findById(id)
        .orElseThrow(() -> {
            log.warn(
                "event=customer_not_found customer_id={}",
                id
            );
            return new CustomerNotFoundException(id);
        });

        return toResponse(customer);
    }

    // GET ALL
    @Transactional(readOnly = true)
    public List<CustomerResponse> getAll() {
        return repository.findAllActive()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // SEARCH BY NAME
    @Transactional(readOnly = true)
    public List<CustomerResponse> searchByName(String name) {

        return repository.searchActive(name)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // SOFT DELETE
    @Transactional
    public void deleteCustomer(Long id) {

        CustomerEntity customer = repository.findById(id)
        .orElseThrow(() -> {
            log.warn(
                "event=customer_not_found customer_id={}",
                id
            );
            return new CustomerNotFoundException(id);
        });

        customer.setIsDeleted(true);
    }

    // MAPPING ENTITY → DTO
    private CustomerResponse toResponse(CustomerEntity c) {
        return new CustomerResponse(
                c.getId(),
                c.getFullName(),
                c.getNik(),
                c.getEmail(),
                c.getPhoneNumber(),
                c.getCreatedAt(),
                c.getUpdatedAt());
    }
}
