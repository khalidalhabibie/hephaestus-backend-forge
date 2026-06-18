import java.util.List;

import model.Customer;
import service.CustomerService;

public class Main {
    public static void main(String[] args) throws Exception {
        CustomerService customer1 = new CustomerService();
        customer1.createCustomer("", "tes@gmail.com", "123456");
        customer1.createCustomer("Fadka", "coba@gmail.com", "654321");

        List<Customer> customer = customer1.getAllCustomers();
        System.out.println("All Customers: ");
        for (int i = 0; i < customer.size(); i++) {
            System.out.println(customer.get(i).getId() + " - " + customer.get(i).getFullName() + " - "
                    + customer.get(i).getEmail() + " - " + customer.get(i).getPhoneNumber());
        }

        System.out.println();
        System.out.println("Customer Detail: ");
        System.out.println(customer1.getCustomerById(1L).getDisplayName());
    }
}