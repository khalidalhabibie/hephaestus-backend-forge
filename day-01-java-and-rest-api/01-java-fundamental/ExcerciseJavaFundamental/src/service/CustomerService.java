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

        customerStorage.put(sequence, customer);
        sequence++;
        return customer;
    }

    public Customer getCustomerById(Long id) {
        return customerStorage.get(id);
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customer = new ArrayList<>(customerStorage.values());
        return customer;
    }

    public void updateCustomerEmail(Long id, String email) {
        Customer cs = customerStorage.get(id);
        cs.setEmail(email);
    }

    public void deleteCustomer(Long id) {
        customerStorage.remove(id);
    }
}
