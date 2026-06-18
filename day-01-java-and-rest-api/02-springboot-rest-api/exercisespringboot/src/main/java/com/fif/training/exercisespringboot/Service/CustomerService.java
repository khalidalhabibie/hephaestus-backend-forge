package com.fif.training.exercisespringboot.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fif.training.exercisespringboot.DTO.CreateCustomerRequest;
import com.fif.training.exercisespringboot.DTO.CustomerResponse;
import com.fif.training.exercisespringboot.Model.Customer;

public class CustomerService {

    // Database Using Map
    private Map<Long, Customer> customerStorage = new TreeMap<>();
    private Long id = 0L;

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
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setFullName(customer.getFullName());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
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
            response.add(customerResponse);
        }
        return response;
    }

    // Service getCustomerById
    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerStorage.get(id);
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setFullName(customer.getFullName());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
        return response;
    }

}
