
import service.CustomerService;

public class App {

    public App() {
        CustomerService customerService = new CustomerService();

        customerService.createCustomer("Budi Santoso", "budi@mail.com", "08123456789");
        customerService.createCustomer("Siti Aminah", "siti@mail.com", "082222222");
        customerService.createCustomer("Bennett", "bennett@mail.com", "083333333");

        customerService.updateCustomerEmail(1L, "budi.updated@mail.com");

        customerService.deleteCustomer(2L);

        System.out.println("\nAll Customers:");

        customerService.getAllCustomer().forEach(customer -> {
            System.out.printf("%d - %s - %s - %s%n", 
                customer.getId(), customer.getFullName(), customer.getEmail(), customer.getPhoneNumber());
        });

        System.out.println("\nCustomer Detail:");
        System.out.println(customerService.getCustomerById(1L).getDisplayName());
    }

    public static void main(String[] args) throws Exception {
        new App();
    }
}
