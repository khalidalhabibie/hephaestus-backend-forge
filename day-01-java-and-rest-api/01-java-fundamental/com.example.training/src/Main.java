import service.CustomerService;

import model.Customer;
import java.util.*;


public class Main {
    public void main(String[] args) {
        CustomerService customerService = new CustomerService();
        customerService.createCustomer("Budi Santuy", "buuy@mail.com", "08123456789");
        customerService.createCustomer("Siti Aminah", "sitam@mail.com", "082222222222");

        System.out.println("All Customers:\n");
        List<Customer> customers = customerService.getAllCustomers();
        for (Customer customer : customers) {
            System.out.println(customer.getFullName() + " - " + customer.getEmail() + " - " + customer.getPhoneNumber());
        }
        System.out.println();

        System.out.println("Customer Detail:");
        System.out.println(customerService.getCustomerById(1L).getDisplayName());

        System.out.println("\nUpdating Customer Email:");
        Customer updatedCustomer = customerService.updateCustomer(1L, "newemail@mail.com");
        if (updatedCustomer != null) {
            System.out.println("Customer updated successfully.");
        } else {
            System.out.println("Customer not found.");
        }

        System.out.println("\nDeleting Customer:");
        boolean isDeleted = customerService.deleteCustomer(1L);
        if (isDeleted) {
            System.out.println("Customer deleted successfully.");
        } else {
            System.out.println("Customer not found.");
        }

        System.out.println("\nSearching Customers by Name:");
        List<Customer> searchResults = customerService.searchCustomersByName("Siti");
        for (Customer customer : searchResults) {
            System.out.println(customer.getFullName() + " - " + customer.getEmail() + " - " + customer.getPhoneNumber());
        }   
    }
}