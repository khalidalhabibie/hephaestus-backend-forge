package com.example.training.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.training.dto.CreateCustomerRequest;
import com.example.training.dto.CustomerResponse;
import com.example.training.entity.CustomerEntity;
import com.example.training.exception.DuplicateCustomerException;
import com.example.training.exception.NotFoundException;
import com.example.training.repository.CustomerRepository;

import com.example.training.auth.AuthContext;
import com.example.training.exception.ForbiddenException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public CustomerResponse create(CreateCustomerRequest request, AuthContext auth) {
        if ("APPROVER".equals(auth.getRole())) {
            throw new ForbiddenException("FORBIDDEN", "APPROVER is not allowed to create customers");
        }
        String correlationId = MDC.get("correlation_id");
        log.debug("event=customer_create_request correlation_id={}", correlationId);

        if (customerRepository.existsByNik(request.getNik())) {
            log.warn("event=customer_created_failed reason=DUPLICATE_NIK correlation_id={}", correlationId);
            throw new DuplicateCustomerException("DUPLICATE_NIK", "NIK already exists");
        }

        if (customerRepository.existsByEmail(request.getEmail())) {
            log.warn("event=customer_created_failed reason=DUPLICATE_EMAIL correlation_id={}", correlationId);
            throw new DuplicateCustomerException("DUPLICATE_EMAIL", "Email already exists");
        }

        CustomerEntity customer = new CustomerEntity();
        fill(customer, request);

        log.debug("event=customer_save_request correlation_id={}", correlationId);
        CustomerEntity savedCustomer = customerRepository.save(customer);
        log.debug("event=customer_saved customer_id={} correlation_id={}", savedCustomer.getId(), correlationId);

        log.info("event=customer_created customer_id={} correlation_id={}", savedCustomer.getId(), correlationId);

        return toResponse(savedCustomer);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> findAll() {
        log.debug("event=customer_find_all correlation_id={}", MDC.get("correlation_id"));
        List<CustomerEntity> customers = customerRepository.findAll();
        log.debug("event=customer_find_all_result count={} correlation_id={}", customers.size(), MDC.get("correlation_id"));
        return customers.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CustomerResponse findById(UUID id) {
        String correlationId = MDC.get("correlation_id");
        log.debug("event=customer_find_by_id customer_id={} correlation_id={}", id, correlationId);

        return customerRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> {
                    log.warn("event=customer_not_found customer_id={} correlation_id={}", id, correlationId);
                    return new NotFoundException("CUSTOMER_NOT_FOUND", "Customer not found with id: " + id);
                });
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> searchByName(String fullName) {
        String correlationId = MDC.get("correlation_id");
        log.debug("event=customer_search_by_name correlation_id={}", correlationId);
        List<CustomerEntity> customers = customerRepository.findByFullNameContainingIgnoreCase(fullName);
        log.debug("event=customer_search_by_name_result count={} correlation_id={}", customers.size(), correlationId);
        return customers.stream()
                .map(this::toResponse)
                .toList();
    }

    private void fill(CustomerEntity customer, CreateCustomerRequest request) {
        customer.setFullName(request.getFullName());
        customer.setNik(request.getNik());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
    }

    private CustomerResponse toResponse(CustomerEntity customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .nik(customer.getNik())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }
}
