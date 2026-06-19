package com.fif.exercise02.service;


import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.fif.exercise02.dto.CreateCustomerRequest;
import com.fif.exercise02.dto.CustomerResponse;
import com.fif.exercise02.dto.PatchCustomerRequest;
import com.fif.exercise02.dto.PatchCustomerResponse;
import com.fif.exercise02.exception.CustomerNotFoundException;
import com.fif.exercise02.model.Customer;

@Service
public class CustomerService {
    private Map<Long, Customer> customerStorage = new HashMap<>();
    private Long sequence = 1L;

    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        Customer customer = new Customer(sequence, request.getFullName(), request.getEmail(), 
        request.getPhoneNumber(), ZonedDateTime.now(), ZonedDateTime.now()
);

        customerStorage.put(sequence, customer);
        sequence++;

        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setFullName(customer.getFullName());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setCreatedAt(customer.getCreatedAt());
        return response;

    }

    public List<CustomerResponse> getAllCustomers() {
        return customerStorage.values().stream()
                .map(customer -> {
                    CustomerResponse response = new CustomerResponse();

                    response.setId(customer.getId());
                    response.setFullName(customer.getFullName());
                    response.setEmail(customer.getEmail());
                    response.setPhoneNumber(customer.getPhoneNumber());
                    return response;
                })
                .collect(Collectors.toList());
    }

    public List<CustomerResponse> getAllCustomers(int page, int size) {
        List<Customer> customers = customerStorage.values().stream().toList();

        int start = page * size;
        int end = Math.min(start + size, customers.size());

        if (start > customers.size()) {
            return List.of();
        }

        return customers.subList(start, end).stream()
                .map(customer -> buildCustomerResponse(
                        customer.getId(),
                        customer.getFullName(),
                        customer.getEmail(),
                        customer.getPhoneNumber(),
                        customer.getCreatedAt(),
                        customer.getUpdatedAt()))
                .toList();
    }

    public CustomerResponse getCustomerById(Long id) {
        Customer cust = customerStorage.get(id);
        CustomerResponse response = new CustomerResponse();
        
        if (cust == null) {
            throw new CustomerNotFoundException("customer tidak ditemukan dengan id:" + id);
        }

        response.setId(cust.getId());
        response.setFullName(cust.getFullName());
        response.setEmail(cust.getEmail());
        response.setPhoneNumber(cust.getPhoneNumber());

        return response;
    }

    // private CustomerResponse getDefaultCustomer() {
    //     return buildCustomerResponse(
    //             1L,
    //             "Budi Santoso",
    //             "budi@gmail.com",
    //             "298292233");
    // }

    private CustomerResponse buildCustomerResponse(
            Long id,
            String fullName,
            String email,
            String phoneNumber,
            ZonedDateTime createdAt,
            ZonedDateTime updatedAt
        ) {
        CustomerResponse response = new CustomerResponse();
        response.setId(id);
        response.setFullName(fullName);
        response.setEmail(email);
        response.setPhoneNumber(phoneNumber);
        response.setCreatedAt(createdAt);
        response.setUpdatedAt(updatedAt);

        return response;
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerStorage.get(id);

        if (customer == null) {
            throw new CustomerNotFoundException("customer tidak ditemukan dengan id: " + id);
        }

        customerStorage.remove(id);
    }
    
    public CustomerResponse updateCustomer(Long id, CreateCustomerRequest request) {
        Customer customer = customerStorage.get(id);

        if (customer == null) {
            throw new CustomerNotFoundException("customer tidak ditemukan dengan id: " + id);
        }

        customer.setFullName(request.getFullName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());

        customer.setUpdatedAt(ZonedDateTime.now());

        return buildCustomerResponse(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }

    public CustomerResponse patchCustomer(Long id, PatchCustomerRequest request) {
        Customer customer = customerStorage.get(id);

        if (customer == null) {
            throw new CustomerNotFoundException("customer tidak ditemukan dengan id: " + id);
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
        customer.setUpdatedAt(ZonedDateTime.now());

        return buildCustomerResponse(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getCreatedAt(),
                customer.getUpdatedAt());
    }
    
    public List<CustomerResponse> getCustomerByEmail(String email) {

        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email tidak boleh kosong");
        }

        List<CustomerResponse> result = customerStorage.values().stream()
                .filter(customer -> customer.getEmail() != null &&
                        customer.getEmail().toLowerCase().contains(email.toLowerCase()))
                .map(customer -> buildCustomerResponse(
                        customer.getId(),
                        customer.getFullName(),
                        customer.getEmail(),
                        customer.getPhoneNumber(),
                        customer.getCreatedAt(),
                        customer.getUpdatedAt()))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            throw new CustomerNotFoundException(
                    "Customer tidak ditemukan dengan email: " + email);
        }

        return result;
    }    
    
}
