package com.adnan.exercisespring.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.adnan.exercisespring.dto.CreateCustomerRequest;
import com.adnan.exercisespring.dto.UpdateCustomerRequest;
import com.adnan.exercisespring.dto.CustomerResponse;
import com.adnan.exercisespring.dto.PatchCustomerRequest;
import com.adnan.exercisespring.exception.CustomerNotFoundException;
import com.adnan.exercisespring.model.Customer;

@Service
public class CustomerService {
  private Map<Long, Customer> customerStorage = new HashMap<>();
  private Long sequence = 1L;

  public CustomerResponse createCustomer(CreateCustomerRequest entity) {
    LocalDateTime now = LocalDateTime.now();

    CustomerResponse customerResponse = new CustomerResponse(sequence, entity.getFullName(), entity.getEmail(),
        entity.getPhoneNumber());
    customerResponse.setCreatedAt(now);
    customerResponse.setUpdatedAt(now);

    Customer customer = new Customer(sequence, customerResponse.getFullName(), customerResponse.getEmail(),
        customerResponse.getPhoneNumber());
    customer.setCreatedAt(now);
    customer.setUpdatedAt(now);
    customerStorage.put(sequence++, customer);

    return customerResponse;
  }

  public List<CustomerResponse> getAllCustomers(String searchByName, String searchByEmail) {
    List<Customer> customerList = new ArrayList<>(customerStorage.values());
    List<CustomerResponse> result = new ArrayList<>();

    for (Customer customer : customerList) {
      boolean match = true;
      if (searchByName != null && !customer.getFullName().toLowerCase().contains(searchByName.toLowerCase())) {
        match = false;
      }
      if (searchByEmail != null && !customer.getEmail().toLowerCase().contains(searchByEmail.toLowerCase())) {
        match = false;
      }
      if (match) {
        CustomerResponse customerResponse = new CustomerResponse(customer.getId(), customer.getFullName(),
            customer.getEmail(), customer.getPhoneNumber());
        customerResponse.setCreatedAt(customer.getCreatedAt());
        customerResponse.setUpdatedAt(customer.getUpdatedAt());
        result.add(customerResponse);
      }
    }

    return result;
  }

  public CustomerResponse getCustomerById(long id) {
    Customer customer = customerStorage.get(id);
    if (customer == null) {
      throw new CustomerNotFoundException(String.format("Customer not found with id: %d", id));
    }

    CustomerResponse customerResponse = new CustomerResponse(customer.getId(), customer.getFullName(),
        customer.getEmail(),
        customer.getPhoneNumber());
    customerResponse.setCreatedAt(customer.getCreatedAt());
    customerResponse.setUpdatedAt(customer.getUpdatedAt());
    return customerResponse;
  }

  public CustomerResponse updateCustomer(long id, UpdateCustomerRequest entity) {
    LocalDateTime now = LocalDateTime.now();

    Customer customer = customerStorage.get(id);
    if (customer == null) {
      throw new CustomerNotFoundException(String.format("Customer not found with id: %d", id));
    }
    customer.setFullName(entity.getFullName());
    customer.setEmail(entity.getEmail());
    customer.setPhoneNumber(entity.getPhoneNumber());
    customer.setUpdatedAt(now);

    customerStorage.put(id, customer);

    CustomerResponse customerResponse = new CustomerResponse(customer.getId(), customer.getFullName(),
        customer.getEmail(),
        customer.getPhoneNumber());
    customerResponse.setCreatedAt(customer.getCreatedAt());
    customerResponse.setUpdatedAt(now);
    return customerResponse;
  }

  public CustomerResponse patchCustomer(long id, PatchCustomerRequest entity) {
    LocalDateTime now = LocalDateTime.now();

    Customer customer = customerStorage.get(id);
    if (customer == null) {
      throw new CustomerNotFoundException(String.format("Customer not found with id: %d", id));
    }

    if (entity.getFullName() != null) {
      customer.setFullName(entity.getFullName());
    }
    if (entity.getEmail() != null) {
      customer.setEmail(entity.getEmail());
    }
    if (entity.getPhoneNumber() != null) {
      customer.setPhoneNumber(entity.getPhoneNumber());
    }
    customer.setUpdatedAt(now);

    customerStorage.put(id, customer);

    CustomerResponse customerResponse = new CustomerResponse(customer.getId(), customer.getFullName(),
        customer.getEmail(),
        customer.getPhoneNumber());
    customerResponse.setCreatedAt(customer.getCreatedAt());
    customerResponse.setUpdatedAt(now);
    return customerResponse;
  }

  public CustomerResponse deleteCustomer(long id) {
    Customer customer = customerStorage.get(id);
    if (customer == null) {
      throw new CustomerNotFoundException(String.format("Customer not found with id: %d", id));
    }

    customerStorage.remove(id);

    CustomerResponse customerResponse = new CustomerResponse(customer.getId(), customer.getFullName(),
        customer.getEmail(),
        customer.getPhoneNumber());
    customerResponse.setCreatedAt(customer.getCreatedAt());
    customerResponse.setUpdatedAt(customer.getUpdatedAt());
    return customerResponse;
  }
}
