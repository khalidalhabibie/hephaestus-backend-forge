package com.example.training.service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.training.dto.CreateCustomerRequest;
import com.example.training.dto.CustomerResponse;
import com.example.training.entity.CustomerEntity;
import com.example.training.exception.DuplicateCustomerException;
import com.example.training.exception.NotFoundException;
import com.example.training.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Transactional
    public CustomerResponse create(CreateCustomerRequest request) {

        if (customerRepository.existsByNik(request.getNik())) {
            throw new DuplicateCustomerException("DUPLICATE_NIK", "NIK already exists");
        }

        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateCustomerException("DUPLICATE_EMAIL", "Email already exists");
        }

        CustomerEntity customer = new CustomerEntity();
        fill(customer, request);

        return toResponse(customerRepository.save(customer));
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> findAll() {
        return customerRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CustomerResponse findById(UUID id) {
        return customerRepository.findById(id).map(this::toResponse).orElseThrow(() -> new NotFoundException("CUSTOMER_NOT_FOUND", "Customer not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> searchByName(String fullName) {
    return customerRepository.findByFullNameContainingIgnoreCase(fullName)
            .stream()
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
