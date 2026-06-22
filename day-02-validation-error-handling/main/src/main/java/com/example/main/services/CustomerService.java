package com.example.main.services;

import com.example.main.dto.request.CreateCustomerRequest;
import com.example.main.dto.request.PatchCustomerRequest;
import com.example.main.dto.response.CustomerResponse;
import com.example.main.entity.CustomerEntity;
import com.example.main.exceptions.NotFoundException;
import com.example.main.exceptions.DuplicateException;
import com.example.main.repositories.CustomerRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        if (customerRepository.existsByNik(request.getNik())) {
            throw new DuplicateException("NIK already exists");
        }
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateException("Email already exists");
        }

        CustomerEntity customer = new CustomerEntity();
        customer.setFullName(request.getFullName());
        customer.setNik(request.getNik());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setCreatedAt(LocalDateTime.now());

        CustomerEntity savedCustomer = customerRepository.save(customer);
        return toResponse(savedCustomer);
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(Long id) {
        CustomerEntity customer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        return toResponse(customer);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new NotFoundException("Customer not found");
        }
        customerRepository.deleteById(id);
    }

    @Transactional
    public CustomerResponse updateCustomer(Long id, CreateCustomerRequest request) {
        CustomerEntity existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        if (!existingCustomer.getNik().equals(request.getNik()) && customerRepository.existsByNik(request.getNik())) {
            throw new DuplicateException("NIK already exists");
        }
        if (!existingCustomer.getEmail().equals(request.getEmail())
                && customerRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateException("Email already exists");
        }

        existingCustomer.setFullName(request.getFullName());
        existingCustomer.setNik(request.getNik());
        existingCustomer.setEmail(request.getEmail());
        existingCustomer.setPhoneNumber(request.getPhoneNumber());
        existingCustomer.setUpdatedAt(LocalDateTime.now());

        CustomerEntity updatedCustomer = customerRepository.save(existingCustomer);
        return toResponse(updatedCustomer);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return java.util.Collections.emptyList();
        }

        List<CustomerEntity> customers = customerRepository.findByFullNameContainingIgnoreCase(name.trim());
        
        if (customers.isEmpty()) {
            throw new NotFoundException("Customer with name containing '" + name + "' not found");
        }
        
        return customers.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CustomerResponse patchCustomer(Long id, PatchCustomerRequest request) {
        CustomerEntity existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        if (request.getFullName() != null) {
            existingCustomer.setFullName(request.getFullName());
        }

        if (request.getNik() != null) {
            if (!existingCustomer.getNik().equals(request.getNik())
                    && customerRepository.existsByNik(request.getNik())) {
                throw new DuplicateException("NIK already exists");
            }
            existingCustomer.setNik(request.getNik());
        }

        if (request.getEmail() != null) {
            if (!existingCustomer.getEmail().equals(request.getEmail())
                    && customerRepository.existsByEmail(request.getEmail())) {
                throw new DuplicateException("Email already exists");
            }
            existingCustomer.setEmail(request.getEmail());
        }

        if (request.getPhoneNumber() != null) {
            existingCustomer.setPhoneNumber(request.getPhoneNumber());
        }

        CustomerEntity updatedCustomer = customerRepository.save(existingCustomer);
        return toResponse(updatedCustomer);
    }

    private CustomerResponse toResponse(CustomerEntity customer) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setFullName(customer.getFullName());
        response.setNik(customer.getNik());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
        return response;
    }
}