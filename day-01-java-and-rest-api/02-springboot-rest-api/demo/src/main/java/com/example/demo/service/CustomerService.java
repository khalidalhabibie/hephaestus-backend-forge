package com.example.demo.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateCustomerRequest;
import com.example.demo.dto.CustomerResponse;
import com.example.demo.dto.DeleteCustomerResponse;
import com.example.demo.dto.PatchUpdateCustomerRequest;
import com.example.demo.dto.PutUpdateCustomerRequest;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.model.Customer;

@Service
public class CustomerService {


    private Map<Long, Customer> customerStorage = new HashMap<>();
    private Long sequence = 1L;


    public List<CustomerResponse> getCustomers() {
        List<CustomerResponse> responses = new ArrayList<>();

        for (Customer customer : customerStorage.values()) {

            CustomerResponse response = new CustomerResponse();
            response.setFullName(customer.getFullName());
            response.setEmail(customer.getEmail());
            response.setPhoneNumber(customer.getPhoneNumber());
            response.setId(customer.getId());
            response.setCreatedAt(customer.getCreatedAt());
            response.setUpdatedAt(customer.getUpdatedAt());

            responses.add(response);
        }
        return responses;
    }

    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        Customer customer = new Customer();
        customerStorage.put(sequence, customer);
        customer.setId(sequence);
        customer.setFullName(request.getFullName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setCreatedAt(ZonedDateTime.now());

        sequence++;

        CustomerResponse response = new CustomerResponse();

        response.setId(customer.getId());
        response.setFullName(customer.getFullName());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setCreatedAt(customer.getCreatedAt());

        return response;
    }

    public CustomerResponse getCustomerResponseById(Long id) {
        CustomerResponse response = new CustomerResponse();

        if (customerStorage.get(id) == null) {
            throw new CustomerNotFoundException(id);
        }

        Customer customer = customerStorage.get(id);

        response.setFullName(customer.getFullName());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setId(customer.getId());
        response.setCreatedAt(customer.getCreatedAt());
        response.setUpdatedAt(customer.getUpdatedAt());

        return response;
    }

    public DeleteCustomerResponse deleteCustomerResponseById(Long id) {
        if (customerStorage.get(id) == null) {
            throw new CustomerNotFoundException(id);
        }
        customerStorage.remove(id);
        DeleteCustomerResponse response = new DeleteCustomerResponse();

        response.setMessage(String.format("Berhasil menghapus customer dengan ID: %s", id));

        return response;
    }

    public CustomerResponse putCustomerResponseById(Long id,
            PutUpdateCustomerRequest request) {

        if (customerStorage.get(id) == null) {
            throw new CustomerNotFoundException(id);

        }

        Customer customer = customerStorage.get(id);
        customer.setFullName(request.getFullName());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setUpdatedAt(ZonedDateTime.now());

        CustomerResponse response = new CustomerResponse();

        response.setFullName(customer.getFullName());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setId(customer.getId());
        response.setCreatedAt(customer.getCreatedAt());        
        response.setUpdatedAt(customer.getUpdatedAt());

        return response;
    }

    public CustomerResponse patchCustomerResponseById(Long id,
            PatchUpdateCustomerRequest request) {

        if (customerStorage.get(id) == null) {
            throw new CustomerNotFoundException(id);
        }

        Customer customer = customerStorage.get(id);

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

        CustomerResponse response = new CustomerResponse();
        response.setFullName(customer.getFullName());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setId(customer.getId());
        response.setCreatedAt(customer.getCreatedAt());        
        response.setUpdatedAt(customer.getUpdatedAt());

        

        return response;
    }

    public List<CustomerResponse> getCustomerResponsesByNameOrEmail(String name, String email) {

        List<CustomerResponse> responses = new ArrayList<>();

        boolean hasName = name != null && !name.isBlank();
        boolean hasEmail = email != null && !email.isBlank();

        for (Customer customer : customerStorage.values()) {

            boolean matchName = hasName
                    && customer.getFullName() != null
                    && customer.getFullName().toLowerCase().contains(name.toLowerCase());

            boolean matchEmail = hasEmail
                    && customer.getEmail() != null
                    && customer.getEmail().toLowerCase().contains(email.toLowerCase());

            if (matchName || matchEmail) {

                CustomerResponse response = new CustomerResponse();
                response.setId(customer.getId());
                response.setFullName(customer.getFullName());
                response.setEmail(customer.getEmail());
                response.setPhoneNumber(customer.getPhoneNumber());
                response.setCreatedAt(customer.getCreatedAt());
                response.setUpdatedAt(customer.getUpdatedAt());

                responses.add(response);
            }
        }

        return responses;
    }

}
