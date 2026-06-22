package com.example.training_2.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.training_2.dto.CreateCustomerRequest;
import com.example.training_2.dto.CustomerResponse;
import com.example.training_2.dto.PatchCustomerRequest;
import com.example.training_2.dto.UpdateCustomerRequest;
import com.example.training_2.entity.Customer;
import com.example.training_2.repository.CustomerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    private void fill(Customer customer, CreateCustomerRequest request) {
        customer.setFullName(request.getFullName());
        customer.setNik(request.getNik());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
    }

    public CustomerResponse toResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId()).fullName(customer.getFullName()).email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .createdAt(customer.getCreatedAt()).updatedAt(customer.getUpdatedAt()).build();
    }

    private CustomerResponse mapToResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .nik(customer.getNik())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }

    @Transactional
    public CustomerResponse create(CreateCustomerRequest request) {
        Customer customer = new Customer();
        fill(customer, request);
        return toResponse(customerRepository.save(customer));
    }

    public List<CustomerResponse> getAll(String full_name) {
        // return customerRepository.findAll().stream().map(this::toResponse).toList();
        List<CustomerResponse> customerResponses = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();

        customers = customerRepository.findAll();

        for (Customer customer : customers) {
            CustomerResponse customerResponse = new CustomerResponse();
            if (full_name != null && !full_name.isEmpty()) {
                if (customer.getFullName().toLowerCase().contains(full_name.toLowerCase())) {
                    customerResponse = mapToResponse(customer);
                    customerResponses.add(customerResponse);
                }
            } else {
                customerResponse = mapToResponse(customer);
                customerResponses.add(customerResponse);
            }
        }
        return customerResponses;
    }

    public CustomerResponse getById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer Not Found"));
        return mapToResponse(customer);
    }

    public CustomerResponse update(Long id, UpdateCustomerRequest request) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customer.setFullName(request.getFullName());
        customer.setNik(request.getNik());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());

        customerRepository.save(customer);

        return mapToResponse(customer);
    }

    public CustomerResponse patch(Long id, PatchCustomerRequest request) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (request.getFullName() != null) {
            customer.setFullName(request.getFullName());
        }

        if (request.getNik() != null) {
            customer.setNik(request.getNik());
        }

        if (request.getEmail() != null) {
            customer.setEmail(request.getEmail());
        }

        if (request.getPhoneNumber() != null) {
            customer.setPhoneNumber(request.getPhoneNumber());
        }

        customerRepository.save(customer);

        return mapToResponse(customer);
    }

    public void delete(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customerRepository.delete(customer);
    }
}
