
package com.example.demo_day2.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo_day2.dto.CreateCustomerRequest;
import com.example.demo_day2.dto.CustomerResponse;
import com.example.demo_day2.dto.PatchCustomerRequest;
import com.example.demo_day2.dto.UpdateCustomerRequest;
import com.example.demo_day2.exception.CustomerNotFoundException;
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
        response.setCreatedAt(cs.getCreatedAt());
        response.setUpdatedAt(cs.getUpdatedAt());
        return response;
    }

    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        Customer cs = new Customer(
                sequence,
                request.getFullName(),
                request.getEmail(),
                request.getPhoneNumber(),
                LocalDateTime.now(),
                LocalDateTime.now());

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

        if (cs == null) {
            throw new CustomerNotFoundException("Customer dengan id (" + id + ") tidak bisa ditemukan");
        }
        return toResponse(cs);
    }

    public CustomerResponse updateCustomer(Long id, UpdateCustomerRequest request) {
        // 1. Cari data customer lama berdasarkan ID
        Customer cs = customerStorage.get(id);

        if (cs == null) {
            throw new CustomerNotFoundException("Customer dengan id (" + id + ") tidak bisa ditemukan");
        }

        cs.setFullName(request.getFullName());
        cs.setEmail(request.getEmail());
        cs.setPhoneNumber(request.getPhoneNumber());
        cs.setUpdatedAt(LocalDateTime.now());

        customerStorage.put(id, cs);

        return toResponse(cs);
    }

    public CustomerResponse patchCustomer(Long id, PatchCustomerRequest request) {
        Customer cs = customerStorage.get(id);

        if (cs == null) {
            throw new CustomerNotFoundException("Customer dengan id (" + id + ") tidak bisa ditemukan");
        }

        // hanya update jika inputan bersifat tidak null
        if (request.getFullName() != null) {
            cs.setFullName(request.getFullName());
        }

        if (request.getEmail() != null) {
            cs.setEmail(request.getEmail());
        }

        if (request.getPhoneNumber() != null) {
            cs.setPhoneNumber(request.getPhoneNumber());
        }
        cs.setUpdatedAt(LocalDateTime.now());

        customerStorage.put(id, cs);

        return toResponse(cs);
    }

    public void deleteCustomer(Long id) {

        if (!customerStorage.containsKey(id)) {
            throw new CustomerNotFoundException("Customer dengan id (" + id + ") tidak bisa ditemukan");
        }

        customerStorage.remove(id);
    }

    public CustomerResponse getCustomerByEmail(String email) {
        Customer cs = customerStorage.values()
                .stream()
                .filter(customer -> customer.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer dengan email (" + email + ") tidak bisa ditemukan"));

        return toResponse(cs);
    }

}
