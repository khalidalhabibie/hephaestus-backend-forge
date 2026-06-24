package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Customer;

public class CustomerService {
    private Map<Long, Customer> customerStorage = new HashMap<>();
    private Long sequence = 1L;
    
    public Customer createCustomer(
        String fullName,
        String email,
        String phoneNumber) {

        Customer customer = new Customer();
        
        customer.setId(sequence);
        customer.setFullName(fullName);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        customerStorage.put(sequence, customer);
        sequence++;
        return customer;
    }

    public Customer getCustomerById(Long id) {
        return customerStorage.get(id);
    }

    public List<Customer> getAllCustomer() {
        List<Customer> customers = new ArrayList<> (customerStorage.values());
        return customers;
    }

    public Customer updateCustomerEmail (
        Long id,
        String email) {
        
        Customer customer = new Customer();
        customerStorage.get(id);
        customer.setEmail(email);
        return customer;
    }

    public Customer deleteCustomer (
        Long id) {

        Customer customer = new Customer();
        customerStorage.remove(id);
        return customer;
    }
}