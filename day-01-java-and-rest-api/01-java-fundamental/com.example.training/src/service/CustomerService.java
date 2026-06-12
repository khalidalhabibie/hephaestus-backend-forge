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
        try{

            if (fullName == null || fullName.trim().isEmpty()) {
                throw new IllegalArgumentException("Nama lengkap tidak boleh kosong!");
            }
    
            Long id = sequence;
            sequence++; 
            
            Customer newCustomer = new Customer(id, fullName, email, phoneNumber);
            customerStorage.put(id, newCustomer);
            
            return newCustomer;
        } catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Customer getCustomerById(Long id) {
        return customerStorage.get(id);
    }

    public List<Customer> getAllCustomers() {
        System.out.println("All Customers:");
        for (Customer c : customerStorage.values()) {
            System.out.println(c.getId() + " - " + c.getFullName() + " - " + c.getEmail() + " - " + c.getPhoneNumber());
        }
        System.out.println(); 
        return new ArrayList<>(customerStorage.values());
    }

    public void updateCustomerEmail(Long id, String email) {
        Customer customer = customerStorage.get(id);
        if (customer != null) {
            System.out.println("Data Sebelum Update");
            System.out.println(customer.getId() + " - " + customer.getFullName() + " - " + customer.getEmail() + " - " + customer.getPhoneNumber());
            customer.setEmail(email);
            System.out.println("Data sesudah update");
            System.out.println(customer.getId() + " - " + customer.getFullName() + " - " + customer.getEmail() + " - " + customer.getPhoneNumber());
        } else {
            System.out.println("Customer dengan ID " + id + " tidak ditemukan.");
        }
    }

    public void deleteCustomer(Long id) {
        if (customerStorage.containsKey(id)) {
            customerStorage.remove(id);
        } else {
            System.out.println("Gagal menghapus. ID " + id + " tidak ditemukan.");
        }
    }
    
}
