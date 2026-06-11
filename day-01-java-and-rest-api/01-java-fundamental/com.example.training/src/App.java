import model.Customer;
import service.CustomerService;

public class App {
    public static void main(String[] args) throws Exception {
        CustomerService customerService = new CustomerService();
        customerService.createCustomer("Budi Santoso", "budi@mail.com", "08123456789");
        customerService.createCustomer("Siti Aminah", "siti@mail.com", "08222222222");

        for (Customer customer : customerService.getAllCustomers()) {
            System.out.println(customer.getFullName());
        }

        System.out.println("\nDetail:");
        System.out.println(customerService.getCustomerById(2L).getDisplayName());
    }
}
