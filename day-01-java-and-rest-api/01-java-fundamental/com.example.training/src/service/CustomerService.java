package service;

import java.util.*;
import model.Customer;

public class CustomerService {
    private Map<Long, Customer> customerStorage = new HashMap<>();

    private Long sequence = 1L;

    public Customer createCustomer(String fullName, String email, String phoneNumber) {
        Customer customer = new Customer(sequence, fullName, email, phoneNumber);
        Long id = sequence;
        sequence++;
        customerStorage.put(id, customer);
        return customer;
    }

    public Customer getCustomerById(Long id) {
        return customerStorage.get(id);
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customerStorage.values());
    }

    public Customer updateCustomer(Long id, String email) {
        Customer customer = customerStorage.get(id);
        if (customer != null) {
            customer.setEmail(email);
            customerStorage.put(id, customer);
            return customer;
        }
        return null;
    }

    public boolean deleteCustomer(Long id) {
        if (customerStorage.containsKey(id)) {
            customerStorage.remove(id);
            return true;
        }
        return false;
    }

    public List<Customer> searchCustomersByName(String name) {
        List<Customer> result = new ArrayList<>();
        for (Customer customer : customerStorage.values()) {
            if (customer.getFullName().toLowerCase().contains(name.toLowerCase())) {
                result.add(customer);
            }
        }
        return result;
    }




    
}