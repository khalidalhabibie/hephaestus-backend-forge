import java.util.List;

import model.Customer;
import service.CustomerService;

public class App {
    public static void main(String[] args) {
        CustomerService customerService = new CustomerService();
        customerService.createCustomer(
            "Budi Santoso", 
            "budi@mail.com", 
            "08123456789");
        customerService.createCustomer(
            "Siti Aminah", 
            "siti@mail.com", 
            "08222222222");
        customerService.createCustomer(
            "", 
            "sitihh@mail.com", 
            "08222222222");

        customerService.deleteCustomer(2L);
        customerService.updateCustomerEmail(1L, "budiaja@gmail.com");
            
        List<Customer> customers = customerService.getAllCustomers();
        System.out.println("All Customers : ");
        for(int i=0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            System.out.println(i+1 + " - " + customer.getFullName() + " - " + customer.getEmail() + " - " + customer.getPhoneNumber());
        }   

        System.out.println("\nCustomer Detail : ");
        System.out.println(customerService.getCustomerById(1L).getDisplayName());

        
        
    }
}
