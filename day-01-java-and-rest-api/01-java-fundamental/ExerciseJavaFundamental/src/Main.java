import model.Customer;
import service.CustomerService;

public class Main {
    
    public static void main(String[] args) {

        CustomerService cs = new CustomerService();

        cs.createCustomer(
            "Budi Santoso",
            "budi@mail.com",
            "08123456789");

        cs.createCustomer(
            "Siti Aminah",
            "siti@mail.com",
            "08222222222");

        cs.getCustomerById(1L);

        System.out.println("All Customers:");
        for(Customer customer : cs.getAllCustomer()) {
            System.out.println(
                customer.getId() + " - " + 
                customer.getFullName() + " - " + 
                customer.getEmail() + " - " + 
                customer.getPhoneNumber());
        }

        System.out.println("\nCustomer Detail:");
        System.out.println(cs.getCustomerById(1L).getDisplayName());
    }
}