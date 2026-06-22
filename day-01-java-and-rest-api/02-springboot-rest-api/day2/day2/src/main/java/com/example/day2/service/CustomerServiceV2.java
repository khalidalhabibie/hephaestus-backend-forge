package com.example.day2.service;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.day2.dto.CreateCustomerRequest;
import com.example.day2.dto.CustomerResponse;
import com.example.day2.dto.PatchCustomerRequest;
import com.example.day2.dto.PutCustomerRequest;
import com.example.day2.model.CustomerEntity;
import com.example.day2.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceV2 {

    private final CustomerRepository customerRepository;

    public CustomerServiceV2(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Page<CustomerResponse> getAllCustomerWithPage(Pageable pageable) {
        Page<CustomerEntity> entityPage = customerRepository.findAll(pageable);
        return entityPage.map(this::convertToResponse);
    }

    public List<CustomerResponse> getAllCustomer() {
        List<CustomerEntity> entities = customerRepository.findAll();
        return entities.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        CustomerEntity entity = new CustomerEntity();
        
        entity.setFullName(request.getFullName());
        entity.setEmail(request.getEmail());
        entity.setPhoneNumber(request.getPhoneNumber());
        
        entity.setCreatedAt(ZonedDateTime.now());
        entity.setUpdatedAt(ZonedDateTime.now());
        
        CustomerEntity savedEntity = customerRepository.save(entity);
        return convertToResponse(savedEntity);
    }

    public CustomerResponse getCustomerById(Long id) {
        CustomerEntity entity = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        return convertToResponse(entity);
    }

    public CustomerResponse getCustomerByEmail(String email) {
        CustomerEntity entity = customerRepository.findFirstByEmailContainingIgnoreCase(email);
        return convertToResponse(entity);
    }

    public CustomerResponse updateCustomer(Long id, PutCustomerRequest request) {
        CustomerEntity entity = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        
        entity.setFullName(request.getFullName());
        entity.setEmail(request.getEmail());
        entity.setPhoneNumber(request.getPhoneNumber());
        
        entity.setUpdatedAt(ZonedDateTime.now());
        
        CustomerEntity updatedEntity = customerRepository.save(entity);
        return convertToResponse(updatedEntity);
    }

    public CustomerResponse patchCustomer(Long id, PatchCustomerRequest request) {
        CustomerEntity entity = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        if (request.getFullName() != null) entity.setFullName(request.getFullName());
        if (request.getEmail() != null) entity.setEmail(request.getEmail());
        if (request.getPhoneNumber() != null) entity.setPhoneNumber(request.getPhoneNumber());

        entity.setUpdatedAt(ZonedDateTime.now());

        CustomerEntity updatedEntity = customerRepository.save(entity);
        return convertToResponse(updatedEntity);
    }

    public void deleteCustomerById(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        customerRepository.deleteById(id);
    }

    public List<CustomerResponse> searchCustomers(String keyword) {
        log.info("V2: Searching customers with keyword: {}", keyword);
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllCustomer();
        }
        return customerRepository.findByFullNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneNumberContaining(
                keyword, keyword, keyword).stream()
                .map(this::convertToResponse)
                .toList();
    }

    private CustomerResponse convertToResponse(CustomerEntity entity) {
        return CustomerResponse.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt()) 
                .build();
    }
}
