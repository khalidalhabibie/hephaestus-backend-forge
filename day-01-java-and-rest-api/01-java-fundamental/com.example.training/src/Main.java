import model.Customer;
import service.CustomerService;

public class Main {
    public static void main(String[] args) throws Exception {
        CustomerService customerService = new CustomerService();
        customerService.createCustomer("Budi Santoso", "budi@mail.com", "08123456789");
        customerService.createCustomer("Siti Aminah", "siti@mail.com", "08222222222");

        for (Customer customer : customerService.getAllCustomers()) {
            System.out.println(customer.getFullName());
        }

        System.out.println("\nDetail:");
        System.out.println(customerService.getCustomerById(2L).getDisplayName());

        // Optional Challenge
        System.out.println("\nOptional Challenge:");
        customerService.createCustomer("Manusia Tiga", "manusiatiga@mail.com", "081232443434");
        System.out.println(customerService.getCustomerById(3L).getDisplayName());
        System.out.println(customerService.getCustomerById(3L).getEmail());
        customerService.updateCustomerEmail(3L, "manusiatigaedit@mail.com");
        System.out.println(customerService.getCustomerById(3L).getEmail());
        customerService.deleteCustomer(3L);
        System.out
                .println(customerService.getCustomerById(3L) != null ? customerService.getCustomerById(3L).getEmail()
                        : "Data tidak ditemukan");
    }
}
