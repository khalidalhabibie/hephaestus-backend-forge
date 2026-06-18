package com.fif.training.exercisespringboot.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.fif.training.exercisespringboot.DTO.CreateCustomerRequest;
import com.fif.training.exercisespringboot.DTO.CustomerResponse;
import com.fif.training.exercisespringboot.Model.Customer;
import com.fif.training.exercisespringboot.exception.CustomerNotFoundException;

import com.fif.training.exercisespringboot.DTO.UpdateCustomerRequest;

public class CustomerService {

    // Database Using Map
    private Map<Long, Customer> customerStorage = new TreeMap<>();
    private Long id = 0L;

    // Helper to create customer Response
    private CustomerResponse toCustomerResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setFullName(customer.getFullName());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setCreated_at(customer.getCreatedAt().toString());
        response.setUpdated_at(customer.getUpdatedAt().toString());
        return response;
    }

    // Service Create Customer
    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        // Increment ID
        id++;

        // Fullname Validation
        if (request.getFullName() == null || request.getFullName().isBlank()) {
            throw new IllegalArgumentException("Full Name Tidak Boleh Kosong!");
        }

        // Cleaning RequestBody
        String cleanFullname = request.getFullName();
        String cleanPhoneNumber = request.getPhoneNumber().trim();
        String cleanEmail = request.getEmail().trim().toLowerCase();

        LocalDateTime now = LocalDateTime.now();

        // Create new Customer
        Customer customer = new Customer(
                id,
                cleanFullname,
                cleanEmail,
                cleanPhoneNumber,
                now,
                now);

        // Save in storage
        customerStorage.put(id, customer);

        // Create Customer Response
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setFullName(customer.getFullName());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setCreated_at(customer.getCreatedAt().toString());
        response.setUpdated_at(customer.getUpdatedAt().toString());
        return response;
    }

    // Service getAllCustomer
    public List<CustomerResponse> getAllCustomer() {

        List<CustomerResponse> response = new ArrayList<>();
        for (Customer customer : customerStorage.values()) {
            CustomerResponse customerResponse = new CustomerResponse();
            customerResponse.setId(customer.getId());
            customerResponse.setFullName(customer.getFullName());
            customerResponse.setEmail(customer.getEmail());
            customerResponse.setPhoneNumber(customer.getPhoneNumber());
            customerResponse.setCreated_at(customer.getCreatedAt().toString());
            customerResponse.setUpdated_at(customer.getUpdatedAt().toString());
            response.add(customerResponse);
        }
        return response;
    }

    // Service getCustomerById
    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerStorage.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException(id);
        }
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setFullName(customer.getFullName());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setCreated_at(customer.getCreatedAt().toString());
        response.setUpdated_at(customer.getUpdatedAt().toString());
        return response;
    }

    // Service deleteCustomerById
    public CustomerResponse deleteCustomerById(Long id) {
        if (customerStorage.get(id) == null) {
            throw new CustomerNotFoundException(id);
        }
        Customer customer = customerStorage.get(id);
        CustomerResponse response = toCustomerResponse(customer);
        customerStorage.remove(id);
        return response;
    }

    // Service putCustomerById
    public CustomerResponse putCustomerById(Long id, UpdateCustomerRequest request) {
        Customer customer = customerStorage.get(id);

        if (customer == null) {
            throw new CustomerNotFoundException(id);
        }

        // Fullname Validation
        if (request.getFullName() == null || request.getFullName().isBlank()) {
            throw new IllegalArgumentException("Full Name Tidak Boleh Kosong!");
        }

        // Cleaning RequestBody
        String cleanFullname = request.getFullName();
        String cleanPhoneNumber = request.getPhoneNumber().trim();
        String cleanEmail = request.getEmail().trim().toLowerCase();

        // Edit Value
        customer.setFullName(cleanFullname);
        customer.setEmail(cleanEmail);
        customer.setPhoneNumber(cleanPhoneNumber);
        customer.setUpdatedAt(LocalDateTime.now());

        // Mapping Response
        CustomerResponse response = toCustomerResponse(customer);
        return response;
    }

    // Service patchCustomerById
    public CustomerResponse patchCustomerById(Long id,
            com.fif.training.exercisespringboot.DTO.PatchCustomerRequest request) {
        Customer customer = customerStorage.get(id);

        if (customer == null) {
            throw new CustomerNotFoundException(id);
        }

        // Fullname Validation
        if (request.getFullName() != null) {
            customer.setFullName(request.getFullName());
        }

        if (request.getEmail() != null) {
            customer.setEmail(request.getEmail());
        }

        if (request.getPhoneNumber() != null) {
            customer.setPhoneNumber(request.getPhoneNumber());
        }

        // Cleaning RequestBody

        // Edit Value
        if (customer != null) {
            customer.setFullName(customer.getFullName());
            customer.setEmail(customer.getEmail());
            customer.setPhoneNumber(customer.getPhoneNumber());
            customer.setUpdatedAt(LocalDateTime.now());
        }

        // Mapping Response
        CustomerResponse response = toCustomerResponse(customer);
        return response;
    }

    // Search searchCustomerByName
    public List<CustomerResponse> searchCustomerByName(String name) {
        String keyword = name.toLowerCase();

        return customerStorage.values().stream()
                .filter(customer -> customer.getFullName().contains(keyword))
                .map(this::toCustomerResponse)
                .collect(Collectors.toList());
    }

    // Search searchCustomerByEmail
    public List<CustomerResponse> searchCustomerByEmail(String email) {
        String keyword = email.toLowerCase();

        return customerStorage.values().stream()
                .filter(customer -> customer.getEmail().toLowerCase().contains(keyword))
                .map(this::toCustomerResponse)
                .collect(Collectors.toList());
    }

}