package com.example.training.service;

import java.util.*;
import org.springframework.stereotype.Service;

import com.example.training.Exception.CustomerNotFoundException;
import com.example.training.dto.CreateCustomerRequest;
import com.example.training.dto.CustomerResponse;
import com.example.training.model.Customer;

@Service
public class CustomerService {

    private final Map<Long, Customer> db = new HashMap<>();
    private Long sequence = 1L;

    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        Customer customer = new Customer(
                sequence,
                request.getFullName(),
                request.getEmail(),
                request.getPhoneNumber()
        );

        db.put(sequence, customer);
        sequence++;
        return toResponse(customer);
    }

    public CustomerResponse getCustomerById(Long id) {
        Customer customer = db.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException(
                "Customer " + id + " not found.");
        }
        return toResponse(customer);
    }

    public List<CustomerResponse> getCustomers() {
        List<CustomerResponse> result = new ArrayList<>();
        for (Customer c : db.values()) {
            result.add(toResponse(c));
        }
        return result;
    }

    private CustomerResponse toResponse(Customer c) {
        return new CustomerResponse(
                c.getId(),
                c.getFullName(),
                c.getEmail(),
                c.getPhoneNumber()
        );
    }
    
    public void deleteCustomer(Long id) {
        if (!db.containsKey(id)) {
            throw new CustomerNotFoundException(
                "Customer " + id + " not found.");
            }
            db.remove(id);
}

    public CustomerResponse updateCustomer(Long id, CreateCustomerRequest request) {
        Customer customer = db.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException(
                "Customer " + id + " not found.");
        }
        customer.setFullName(request.getFullName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        return toResponse(customer);
    }

}