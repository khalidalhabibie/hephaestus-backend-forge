import java.util.Scanner;

import model.Customer;
import service.CustomerService;

public class Main {
    public static void main(String[] args) {
        CustomerService cs = new CustomerService();

        Scanner input = new Scanner(System.in);

        cs.createCustomer("Budi Santoso", "budi@mail.com", "08123456789");
        cs.createCustomer("Siti Aminah", "siti@mail.com", "08222222222");
        cs.createCustomer("dudy", "dudy@mail.com", "082234343434");

        System.out.println("All Customers: ");

        for (Customer customer : cs.getAllCustomers()) {
            System.out.println(customer.getId() + " - " + customer.getDisplayName() + " - " + customer.getEmail()
                    + " - " + customer.getPhoneNumber());
        }

        System.out.println("Customer Detail: ");
        System.out.println("Customer: " + cs.getCustomerById(1L).getDisplayName());

        System.out.println("pilih user yang mau di update emailnya (ID) = ");
        long idEdit = input.nextLong();
        System.out.println("pilih user yang mau di update emailnya (email) = ");
        String email = input.nextLine();

        input.nextLine();

        cs.updateCustomerEmail(idEdit, email);

        System.out.println("All Customers: ");

        for (Customer customer : cs.getAllCustomers()) {
            System.out.println(customer.getId() + " - " + customer.getDisplayName() + " - " + customer.getEmail()
                    + " - " + customer.getPhoneNumber());
        }

        System.out.println("pilih user yang mau di delete (ID) = ");
        long idDelete = input.nextLong();

        cs.deleteCustomer(idDelete);

    }
}
