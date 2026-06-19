package src.main.java.com.example.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.main.java.com.example.model.Customer;

public class CustomerService {
    private Map<Long, Customer> customerStorage = new HashMap<>();
    private Long sequence = 1L;

    public Customer createCustomer(String fullName, String email, String phoneNumber){
        Long ids = sequence;
        ids++;

        Customer cust = new Customer(ids, fullName, email, phoneNumber);

        customerStorage.put(ids, cust);

        return cust;
    }

    public Customer getCustomerbyId(Long ids){
        return customerStorage.get(ids);
    }

    public List<Customer> getAllCustomer(){
        return new ArrayList<>(customerStorage.values());
    }

    public void updateCustomer(Long id, String email){
       Customer ubahCustomer = customerStorage.get(id);
       ubahCustomer.setEmail(email);
    }

    public void deleteCustomer(Long id){
        customerStorage.remove(id);
    }

}


