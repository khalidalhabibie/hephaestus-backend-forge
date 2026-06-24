package com.example.training.service;

import java.time.LocalDateTime;
import java.util.*;
import org.springframework.stereotype.Service;
import com.example.training.exception.CustomerNotFoundException;
import com.example.training.dto.CreateCustomerRequest;
import com.example.training.dto.PatchCustomerRequest;
import com.example.training.dto.UpdateCustomerRequest;
import com.example.training.model.Customer;

@Service
public class CustomerService {

    private final Map<Long, Customer> db = new HashMap<>();
    private Long sequence = 1L;

    public Customer createCustomer(CreateCustomerRequest request) {
        Long id = sequence++;
        Customer customer = new Customer(
                id,
                request.getFullName(),
                request.getEmail(),
                request.getPhoneNumber(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        db.put(id, customer);
        return customer;
    }

    public List<Customer> getAll() {
        return new ArrayList<>(db.values());
    }
    
    public Customer getCustomerById(Long id) {
        Customer customer = db.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException(
                "Customer " + id + " not found.");
        }
        return customer;
    }

    public Customer updateCustomer(Long id, UpdateCustomerRequest request) {
        Customer customer = db.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException(
                "Customer " + id + " not found.");
        }
        customer.setFullName(request.getFullName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setUpdatedAt(LocalDateTime.now());
        return customer;
    }

    public Customer patchCustomer(Long id, PatchCustomerRequest request) {
        Customer customer = db.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException(
                "Customer " + id + " not found.");
        }
        if (request.getFullName() != null) {
            customer.setFullName(request.getFullName());
        }
        if (request.getEmail() != null) {
            customer.setEmail(request.getEmail());
        }
        if (request.getPhoneNumber() != null) {
            customer.setPhoneNumber(request.getPhoneNumber());
        }
        customer.setUpdatedAt(LocalDateTime.now());
        return customer;
    }
    
    public void deleteCustomer(Long id) {
        if (!db.containsKey(id)) {
            throw new CustomerNotFoundException(
                "Customer " + id + " not found.");
        }
        db.remove(id);
    }
}