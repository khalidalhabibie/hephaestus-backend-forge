package com.adnan.exercisespring.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.adnan.exercisespring.dto.CreateCustomerRequest;
import com.adnan.exercisespring.dto.UpdateCustomerRequest;
import com.adnan.exercisespring.enums.RoleEnum;
import com.adnan.exercisespring.dto.CustomerResponse;
import com.adnan.exercisespring.dto.PatchCustomerRequest;
import com.adnan.exercisespring.exception.CustomerNotFoundException;
import com.adnan.exercisespring.exception.ForbiddenException;
import com.adnan.exercisespring.model.Customer;
import com.adnan.exercisespring.security.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
  private final SecurityUtil securityUtil;

  private Map<Long, Customer> customerStorage = new HashMap<>();
  private Long sequence = 1L;

  public CustomerResponse createCustomer(CreateCustomerRequest entity) {
    if (!securityUtil.hasRole(RoleEnum.ADMIN) && !securityUtil.hasRole(RoleEnum.STAFF)) {
      throw new ForbiddenException("You do not have permission to access this resource");
    }

    ZonedDateTime now = ZonedDateTime.now();

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

  public CustomerResponse getCustomerById(Long id) {
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
    ZonedDateTime now = ZonedDateTime.now();

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
    ZonedDateTime now = ZonedDateTime.now();

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
