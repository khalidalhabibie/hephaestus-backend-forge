import java.util.List;

import model.Customer;
import services.CustomerService;

public class Main {
    public static void main(String[] args) {
        
        CustomerService customerService = new CustomerService();

        customerService.createCustomer(
            "Budi Santoso", "budi@mail.com",  "08123456789"
        );
        customerService.createCustomer(
            "Siti Aminah", "siti@mail.com", "08222222222"
        );

        List<Customer> customers = customerService.getAllCustomers();
        
        for (Customer customer : customers){
            System.out.println(customer.getFullName() + " - " + customer.getEmail() + " - " + customer.getPhoneNumber());
        }

        System.out.println();
        System.out.println("Customer Detail:");
        System.out.println(customerService.getCustomerById(1L).getDisplayName());

    }
}
