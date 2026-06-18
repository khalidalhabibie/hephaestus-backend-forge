import model.Customer;
import service.CustomerService;

public class Main {
    public static void main(String[] args) {
        CustomerService customerService = new CustomerService();

        customerService.createCustomer("Budi Santoso", "budi@mail.com", "08123456789");
        customerService.createCustomer("Siti Aminah", "siti@mail.com", "08222222222");
        customerService.createCustomer("", "siti@mail.com", "08222222222");

        customerService.getAllCustomers();

        Customer customerInput = customerService.getCustomerById(1L);

        System.out.println("Customer Detail:");
        if (customerInput != null) {
            System.out.println(customerInput.getDisplayName());
        }

        customerService.updateCustomerEmail(1L, "thor@gmail.com");
        customerService.deleteCustomer(2L);
    }
}
