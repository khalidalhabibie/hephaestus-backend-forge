package com.fif.training.exercisespringboot.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fif.training.exercisespringboot.DTO.CreateCustomerRequest;
import com.fif.training.exercisespringboot.DTO.CustomerResponse;
import com.fif.training.exercisespringboot.DTO.PatchCustomerRequest;
import com.fif.training.exercisespringboot.DTO.UpdateCustomerRequest;
import com.fif.training.exercisespringboot.Model.Customer;
import com.fif.training.exercisespringboot.exception.CustomerNotFoundException;

@Service
public class CustomerService {

    private Map<Long, Customer> customerStorage = new TreeMap<>();
    private Long idCounter = 0L;

    private CustomerResponse toCustomerResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setFullName(customer.getFullName());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setCreatedAt(customer.getCreatedAt().toString());
        response.setUpdatedAt(customer.getUpdatedAt().toString());
        return response;
    }

    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        idCounter++;
        LocalDateTime now = LocalDateTime.now();

        Customer customer = new Customer(
                idCounter,
                request.getFullName().trim(),
                request.getEmail().trim().toLowerCase(),
                request.getPhoneNumber().trim(),
                now,
                now);

        customerStorage.put(idCounter, customer);
        return toCustomerResponse(customer);
    }

    public List<CustomerResponse> getAllCustomer() {
        return customerStorage.values().stream()
                .map(this::toCustomerResponse)
                .collect(Collectors.toList());
    }

    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerStorage.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException(id);
        }
        return toCustomerResponse(customer);
    }

    public CustomerResponse deleteCustomerById(Long id) {
        Customer customer = customerStorage.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException(id);
        }
        CustomerResponse response = toCustomerResponse(customer);
        customerStorage.remove(id);
        return response;
    }

    public CustomerResponse putCustomerById(Long id, UpdateCustomerRequest request) {
        Customer customer = customerStorage.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException(id);
        }

        customer.setFullName(request.getFullName().trim());
        customer.setEmail(request.getEmail().trim().toLowerCase());
        customer.setPhoneNumber(request.getPhoneNumber().trim());
        customer.setUpdatedAt(LocalDateTime.now());

        return toCustomerResponse(customer);
    }

    public CustomerResponse patchCustomerById(Long id, PatchCustomerRequest request) {
        Customer customer = customerStorage.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException(id);
        }

        if (request.getFullName() != null) {
            customer.setFullName(request.getFullName().trim());
        }
        if (request.getEmail() != null) {
            customer.setEmail(request.getEmail().trim().toLowerCase());
        }
        if (request.getPhoneNumber() != null) {
            customer.setPhoneNumber(request.getPhoneNumber().trim());
        }
        customer.setUpdatedAt(LocalDateTime.now());

        return toCustomerResponse(customer);
    }

    public List<CustomerResponse> searchCustomerByEmail(String email) {
        String keyword = email.toLowerCase();
        return customerStorage.values().stream()
                .filter(customer -> customer.getEmail().toLowerCase().contains(keyword))
                .map(this::toCustomerResponse)
                .collect(Collectors.toList());
    }
}