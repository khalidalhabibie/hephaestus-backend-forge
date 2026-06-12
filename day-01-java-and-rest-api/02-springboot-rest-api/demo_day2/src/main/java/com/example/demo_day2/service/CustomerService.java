
package com.example.demo_day2.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo_day2.dto.CreateCustomerRequest;
import com.example.demo_day2.dto.CustomerResponse;
import com.example.demo_day2.model.Customer;

@Service
public class CustomerService {

    private Map<Long, Customer> customerStorage = new HashMap<>();
    private Long sequence = 1L;

    // helper
    private CustomerResponse toResponse(Customer cs) {
        CustomerResponse response = new CustomerResponse();
        response.setId(cs.getId());
        response.setFullName(cs.getFullName());
        response.setEmail(cs.getEmail());
        response.setPhoneNumber(cs.getPhoneNumber());
        return response;
    }

    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        Customer cs = new Customer(
                sequence,
                request.getFullName(),
                request.getEmail(),
                request.getPhoneNumber());

        customerStorage.put(sequence, cs);
        sequence++;

        return toResponse(cs);
    }

    public List<CustomerResponse> getCustomers() {
        return customerStorage.values()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CustomerResponse getCustomerById(Long id) {
        Customer cs = customerStorage.get(id);

        return toResponse(cs);
    }

}
