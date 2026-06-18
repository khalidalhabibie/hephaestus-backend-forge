import java.util.List;

import model.Customer;
import model.CustomerService;

public class App {
    public static void main(String[] args) throws Exception {
        CustomerService customerService = new CustomerService();

        // obj 1
        customerService.createCustomer("Budi Santoso", "budi@mail.com", "08123456789");
        // obj 2
        customerService.createCustomer("Siti Aminah", "siti@mail.com", "08222222222");

        // Print all Customer
        System.out.println("All Customer :");
        List<Customer> allCustomers = customerService.getAllCustomers();
        int i = 1;
        for (Customer customer : allCustomers) {
            System.out.println(i + " - " + customer.getFullName() + " - " + customer.getEmail() + " - "
                    + customer.getPhoneNumber());
            i++;
        }

        // By id
        System.out.println("Customer Detail : ");
        Customer selectedCustomer = customerService.getCustomerById(1L);
        selectedCustomer.getDisplayName();

    }
}
