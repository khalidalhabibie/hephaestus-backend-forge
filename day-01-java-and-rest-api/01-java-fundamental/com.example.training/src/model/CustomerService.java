package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerService {

    private Map<Long, Customer> customerStorage = new HashMap<>();
    private Long sequence = 0L;

    public Customer createCustomer(String fullName, String email, String phoneNumber) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full name tidak boleh kosong");
        }
        Customer customer = new Customer(sequence, fullName, email, phoneNumber);
        sequence++;
        customerStorage.put(sequence, customer);
        return customer;
    }

    public Customer getCustomerById(Long id) {
        Customer customer = customerStorage.get(id);
        return customer;
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customerStorage.values());
    }

    public void updateCustomerEmail(Long id, String email) {
        Customer customer = getCustomerById(id);
        customer.setEmail(email);
    }

    public void deleteCustomer(Long id) {
        customerStorage.remove(id);
    }

}
