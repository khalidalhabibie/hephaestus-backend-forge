package com.example.main.services;

import org.springframework.stereotype.Service;

import com.example.main.dto.CreateCustomerRequest;
import com.example.main.dto.CustomerResponse;
import com.example.main.models.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CustomerService {

    private final Map<Long, Customer> customerDatabase = new ConcurrentHashMap<>();
    
    private final AtomicLong idGenerator = new AtomicLong(1);

    public CustomerService() {
        initDummyData();
    }

    private void initDummyData() {
        insertDummy("davin ben", "dav@mail.com", "08123456789");
        insertDummy("abcacacaac", "acacacac@mail.com", "08987654321");
        insertDummy("qqqqqq", "sdxsdsdsd@mail.com", "08111222333");
    }

    private void insertDummy(String fullName, String email, String phoneNumber) {
        Long newId = idGenerator.getAndIncrement();
        Customer customer = new Customer(newId, fullName, email, phoneNumber);
        customerDatabase.put(newId, customer);
    }

    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        Long newId = idGenerator.getAndIncrement();
        
        Customer customer = new Customer(
            newId, 
            request.getFullName(), 
            request.getEmail(), 
            request.getPhoneNumber()
        );
        
        customerDatabase.put(newId, customer);
        
        return toResponse(customer);
    }

    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerDatabase.get(id);
        if (customer == null) {
            return null;
        }
        return toResponse(customer);
    }

    public List<CustomerResponse> getAllCustomers() {
        List<CustomerResponse> responses = new ArrayList<>();
        for (Customer customer : customerDatabase.values()) {
            responses.add(toResponse(customer));
        }
        return responses;
    }

    public boolean deleteCustomer(Long id) {
        return customerDatabase.remove(id) != null;
    }

    public CustomerResponse updateCustomer(Long id, CreateCustomerRequest request) {
        Customer existingCustomer = customerDatabase.get(id);
        if (existingCustomer == null) {
            return null;
        }
        
        existingCustomer.setFullName(request.getFullName());
        existingCustomer.setEmail(request.getEmail());
        existingCustomer.setPhoneNumber(request.getPhoneNumber());
        
        customerDatabase.put(id, existingCustomer);
        return toResponse(existingCustomer);
    }

    public List<CustomerResponse> searchByName(String name) {
        List<CustomerResponse> responses = new ArrayList<>();
        for (Customer customer : customerDatabase.values()) {
            if (customer.getFullName().toLowerCase().contains(name.toLowerCase())) {
                responses.add(toResponse(customer));
            }
        }
        return responses;
    }

    private CustomerResponse toResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setFullName(customer.getFullName());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
        return response;
    }
}