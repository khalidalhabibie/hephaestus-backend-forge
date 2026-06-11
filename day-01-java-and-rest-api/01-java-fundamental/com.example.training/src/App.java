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
        for (Customer customer : customers) {
            customer.getDisplayName();
        }
        Customer cust1 = customerService.getCustomerById(2L);
        cust1.getDisplayName();
    }
}
