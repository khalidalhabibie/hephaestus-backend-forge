import java.util.List;

import Model.Customer;
import Services.CustomerService;

public class App {
    public static void main(String[] args) {
        List<Customer> customers;
        CustomerService customerService = new CustomerService();
        customerService.createCustomer("Budi Santoso", "budi@mail.com", "08123456789");
        customerService.createCustomer("Siti Aminah", "siti@mail.com", "08222222222");

        customers = customerService.getAllCustomer();
        int i = 1;
        for (Customer customer : customers) {
            System.out.println(i + " - " + customer.getFullName() + " - " + customer.getEmail() + " - "
                    + customer.getPhoneNumber());
            i++;
        }
        Customer cust1 = customerService.getCustomerById(1L);
        System.out.println("Customer Detail : ");
        cust1.getDisplayName();
    }
}
