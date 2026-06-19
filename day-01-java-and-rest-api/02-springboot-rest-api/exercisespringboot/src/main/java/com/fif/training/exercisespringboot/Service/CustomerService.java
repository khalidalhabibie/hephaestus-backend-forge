package com.fif.training.exercisespringboot.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.fif.training.exercisespringboot.DTO.CreateCustomerRequest;
import com.fif.training.exercisespringboot.DTO.CustomerResponse;
import com.fif.training.exercisespringboot.exception.CustomerNotFoundException;
import com.fif.training.exercisespringboot.Model.Customer;

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
        String cleanFullname = request.getFullName().trim().toLowerCase();
        String cleanPhoneNumber = request.getPhoneNumber().trim();
        String cleanEmail = request.getEmail().trim().toLowerCase();

        // Create new Customer
        Customer customer = new Customer(
                id,
                cleanFullname,
                cleanEmail,
                cleanPhoneNumber);

        // Save in storage
        customerStorage.put(id, customer);

        // Create Customer Response
        CustomerResponse response = toCustomerResponse(customer);
        return response;
    }

    // Service getAllCustomer
    public List<CustomerResponse> getAllCustomer() {

        List<CustomerResponse> response = new ArrayList<>();
        for (Customer customer : customerStorage.values()) {
            response.add(toCustomerResponse(customer));
        }
        return response;
    }

    // Service getCustomerById
    public CustomerResponse getCustomerById(Long id) {
        if (customerStorage.get(id) == null) {
            throw new CustomerNotFoundException(id);
        }
        Customer customer = customerStorage.get(id);
        CustomerResponse response = toCustomerResponse(customer);
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

    // Service editCustomerById
    public CustomerResponse editCustomerById(Long id, CreateCustomerRequest request) {
        Customer customer = customerStorage.get(id);

        // Fullname Validation
        if (request.getFullName() == null || request.getFullName().isBlank()) {
            throw new IllegalArgumentException("Full Name Tidak Boleh Kosong!");
        }

        // Cleaning RequestBody
        String cleanFullname = request.getFullName().trim().toLowerCase();
        String cleanPhoneNumber = request.getPhoneNumber().trim();
        String cleanEmail = request.getEmail().trim().toLowerCase();

        // Edit Value
        if (customer != null) {
            customer.setFullName(cleanFullname);
            customer.setEmail(cleanEmail);
            customer.setPhoneNumber(cleanPhoneNumber);
        }

        // Mapping Response
        CustomerResponse response = toCustomerResponse(customer);
        return response;
    }

    // Search searchCustomerByName
    public List<CustomerResponse> searchCustomerByName(String name) {
        String keyword = name.toLowerCase();

        return customerStorage.values().stream()
                .filter(customer -> customer.getFullName().toLowerCase().contains(keyword))
                .map(this::toCustomerResponse)
                .collect(Collectors.toList());

    }

}