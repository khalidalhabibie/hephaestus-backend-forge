package com.example.training.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.training.dto.CreateCustomerRequest;
import com.example.training.dto.CustomerResponse;
import com.example.training.model.Customer;

@Service
public class CustomerService {
    private Map<Long, Customer> customerStorage = new HashMap<>();
    private Long sequence = 1L;

    public List<CustomerResponse> getCustomers() {
        List<CustomerResponse> responses = new ArrayList<>();
        for(Customer customer : customerStorage.values()) {
            CustomerResponse customerResponse = new CustomerResponse();
            customerResponse.setId(customer.getId());
            customerResponse.setFullName(customer.getFullName());
            customerResponse.setEmail(customer.getEmail());
            customerResponse.setPhoneNumber(customer.getPhoneNumber());

            responses.add(customerResponse);
        }
        return responses;
    }

	// @PostMapping
	public CustomerResponse createCustomer(@RequestBody CreateCustomerRequest request) {
        Customer newCust = new Customer(sequence, request.getFullName(), request.getEmail(), request.getPhoneNumber());
        customerStorage.put(sequence, newCust);
        sequence++;

        CustomerResponse response = new CustomerResponse();
		response.setId(newCust.getId());
		response.setFullName(newCust.getFullName());
		response.setEmail(newCust.getEmail());
		response.setPhoneNumber(newCust.getPhoneNumber());
        return response;
	}

	// @GetMapping("/{id}")
	public CustomerResponse getCustomerById(@PathVariable Long id) {
        Customer customer = customerStorage.get(id);

		CustomerResponse response = new CustomerResponse();
		response.setId(id);
		response.setFullName(customer.getFullName());
		response.setEmail(customer.getEmail());
		response.setPhoneNumber(customer.getPhoneNumber());
        return response;
    } 

    // private CustomerResponse getDefaultCustomer() {
    //     return buildCustomerResponse(
    //         1L, 
    //         "Budi Santoso", 
    //         "budi@gmail.com", 
    //         "081122334455");
    // }

    // private CustomerResponse buildCustomerResponse(
    //     Long id,
    //     String fullName,
    //     String email,
    //     String phoneNumber
    // ) {
    //     CustomerResponse response = new CustomerResponse();
    //     response.setId(id);
    //     response.setFullName(fullName);
    //     response.setEmail(email);
    //     response.setPhoneNumber(phoneNumber);
    //     return response;
    // }
}
