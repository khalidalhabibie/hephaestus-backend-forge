import model.Customer;
import service.CustomerService;

public class Main {
    public static void main(String[] args) {
        CustomerService cs = new CustomerService();

        cs.createCustomer("Budi Santoso", "budi@mail.com", "08123456789");
        cs.createCustomer("Siti Aminah", "siti@mail.com", "08222222222");

        System.out.println("All Customers: ");

        for (Customer customer : cs.getAllCustomers()) {
            System.out.println(customer.getId() + " - " + customer.getDisplayName() + " - " + customer.getEmail()
                    + " - " + customer.getPhoneNumber());
        }

        System.out.println("Customer Detail: ");
        System.out.println("Customer: " + cs.getCustomerById(1L).getDisplayName());
    }
}
