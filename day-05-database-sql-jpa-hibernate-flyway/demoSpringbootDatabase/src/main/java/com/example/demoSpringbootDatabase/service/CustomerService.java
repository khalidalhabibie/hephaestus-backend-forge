package com.example.demoSpringbootDatabase.service;

import com.example.demoSpringbootDatabase.dto.CreateCustomerRequest;
import com.example.demoSpringbootDatabase.dto.CustomerResponse;
import com.example.demoSpringbootDatabase.entity.CustomerEntity;
import com.example.demoSpringbootDatabase.exception.CustomerNotFoundException;
import com.example.demoSpringbootDatabase.exception.DuplicateCustomerException;
import com.example.demoSpringbootDatabase.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) { this.customerRepository = customerRepository; }

    @Transactional
    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        if (customerRepository.existsByNik(request.getNik())) throw new DuplicateCustomerException("NIK already registered");
        if (customerRepository.existsByEmail(request.getEmail())) throw new DuplicateCustomerException("Email already registered");

        CustomerEntity customer = CustomerEntity.builder()
                .fullName(request.getFullName()).nik(request.getNik())
                .email(request.getEmail()).phoneNumber(request.getPhoneNumber()).build();
        customerRepository.save(customer);
        return mapToResponse(customer);
    }

    @Transactional(readOnly = true)
    public CustomerResponse getById(Long id) {
        return customerRepository.findById(id).map(this::mapToResponse).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getAll() {
        return customerRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> searchByName(String name) {
        return customerRepository.findByFullNameContainingIgnoreCase(name).stream().map(this::mapToResponse).toList();
    }

    private CustomerResponse mapToResponse(CustomerEntity entity) {
        return CustomerResponse.builder().id(entity.getId()).fullName(entity.getFullName())
                .nik(entity.getNik()).email(entity.getEmail()).phoneNumber(entity.getPhoneNumber()).build();
    }

    @Transactional
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) throw new CustomerNotFoundException(id);
        customerRepository.deleteById(id); 
    }
}
