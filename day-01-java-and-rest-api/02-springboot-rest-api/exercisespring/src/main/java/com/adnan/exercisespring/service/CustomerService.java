package com.adnan.exercisespring.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.adnan.exercisespring.dto.CreateCustomerRequest;
import com.adnan.exercisespring.dto.CustomerResponse;
import com.adnan.exercisespring.exception.CustomerNotFoundException;
import com.adnan.exercisespring.model.Customer;

@Service
public class CustomerService {
  private Map<Long, Customer> customerStorage = new HashMap<>();
  private Long sequence = 1L;

  public CustomerResponse createCustomer(CreateCustomerRequest entity) {
    CustomerResponse customerResponse = new CustomerResponse(entity.getFullName(), entity.getEmail(),
        entity.getPhoneNumber());

    Customer customer = new Customer(sequence, customerResponse.getFullName(), customerResponse.getEmail(),
        customerResponse.getPhoneNumber());
    customerStorage.put(sequence++, customer);

    return customerResponse;
  }

  public List<CustomerResponse> getAllCustomers(String searchByName) {
    List<Customer> customerList = new ArrayList<>(customerStorage.values());
    List<CustomerResponse> result = new ArrayList<>();

    for (Customer customer : customerList) {
      boolean match = true;
      if (searchByName != null && !customer.getFullName().toLowerCase().contains(searchByName.toLowerCase())) {
        match = false;
      }
      if (match) {
        CustomerResponse customerResponse = new CustomerResponse(customer.getId(), customer.getFullName(),
            customer.getEmail(), customer.getPhoneNumber());
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
    return customerResponse;
  }

  public CustomerResponse updateCustomer(long id, CreateCustomerRequest entity) {
    Customer customer = customerStorage.get(id);
    if (customer == null) {
      throw new CustomerNotFoundException(String.format("Customer not found with id: %d", id));
    }
    customer.setFullName(entity.getFullName());
    customer.setEmail(entity.getEmail());
    customer.setPhoneNumber(entity.getPhoneNumber());

    customerStorage.put(id, customer);

    CustomerResponse response = new CustomerResponse(customer.getId(), customer.getFullName(), customer.getEmail(),
        customer.getPhoneNumber());
    return response;
  }

  public CustomerResponse deleteCustomer(long id) {
    Customer customer = customerStorage.get(id);
    if (customer == null) {
      throw new CustomerNotFoundException(String.format("Customer not found with id: %d", id));
    }

    customerStorage.remove(id);

    CustomerResponse response = new CustomerResponse(customer.getId(), customer.getFullName(), customer.getEmail(),
        customer.getPhoneNumber());
    return response;
  }
}
