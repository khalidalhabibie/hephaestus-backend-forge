package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Customer;

public class CustomerService {
  private Map<Long, Customer> customerStorage = new HashMap<>();
  private Long sequence = 1L;

  public Customer createCustomer(String fullName, String email, String phoneNumber) {
    Customer customer = new Customer(sequence, fullName, email, phoneNumber);
    customerStorage.put(sequence++, customer);
    return customer;
  }

  public Customer getCustomerById(Long id) {
    return customerStorage.get(id);
  }

  public List<Customer> getAllCustomers() {
    List<Customer> customerList = new ArrayList<>(customerStorage.values());
    return customerList;
  }
}
