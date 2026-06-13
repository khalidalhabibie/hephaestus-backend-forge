package com.andyana.exerciseday02.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.andyana.exerciseday02.dto.CreateCustomerRequest;
import com.andyana.exerciseday02.dto.CustomerResponse;
import com.andyana.exerciseday02.model.Customer;

@Service
public class CustomerService {
    private Map<Long, Customer> customerMap = new HashMap<>();
    private Long idCounter = 1L;
    // fungsi dari createCustomer dibawah ini adalah untuk membuat customer baru berdasarkan data yang diterima dari request, menyimpan customer tersebut dalam map, dan mengembalikan response yang berisi data customer yang baru dibuat
    public CustomerResponse createCustomer(CreateCustomerRequest request) { 
        Long newId = idCounter++;
        Customer customer = new Customer(newId, request.getFullName(), request.getEmail(), request.getPhoneNumber());
        customerMap.put(newId, customer);
        return convertToResponse(customer);
    }
    

    public Optional<CustomerResponse> getCustomerById(Long id) {
    return Optional.ofNullable(customerMap.get(id))
            .map(this::convertToResponse);
}
    
    public List<CustomerResponse> getAllCustomers() {
        List<CustomerResponse> responses = new ArrayList<>();
        for (Customer customer : customerMap.values()) {
            responses.add(convertToResponse(customer));
        }
        return responses;
    }

    private CustomerResponse convertToResponse(Customer customer) {
        return new CustomerResponse(
            customer.getId(),
            customer.getFullName(),
            customer.getEmail(),
            customer.getPhoneNumber()
        );
    }

    public CustomerResponse updateCustomer(Long id, CreateCustomerRequest request) {
        if (!customerMap.containsKey(id)) {
            throw new IllegalArgumentException("Customer dengan ID " + id + " tidak ditemukan");
        }
        Customer existingCustomer = customerMap.get(id);
        existingCustomer.setFullName(request.getFullName());
        existingCustomer.setEmail(request.getEmail());
        existingCustomer.setPhoneNumber(request.getPhoneNumber());
        return convertToResponse(existingCustomer);

    }

    public void deleteCustomer(Long id) {
        if (!customerMap.containsKey(id)) {
            throw new IllegalArgumentException("Customer dengan ID " + id + " tidak ditemukan");
        }
        customerMap.remove(id);
    }

}
