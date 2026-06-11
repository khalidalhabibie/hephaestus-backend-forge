package src.main.java.com.example;

import src.main.java.com.example.model.Customer;
import src.main.java.com.example.service.CustomerService;

public class Main {
    public static void main(String[] args) {
        CustomerService cust1 = new CustomerService();
        cust1.createCustomer("Dhimas", "dhimaswibowo999@gmail.com", "12345678");
        cust1.createCustomer("Adnan", "adnan999@gmail.com", "12345678");

        System.out.println("All Customer: ");
        for(Customer customer : cust1.getAllCustomer()){
            System.out.println(customer.getId() + ". " + customer.getFullName() + " - "  + customer.getEmail() + " - " + customer.getPhoneNumber());
        }
        System.out.println("");
        System.out.println("Customer by ID");
        System.out.println(cust1.getCustomerbyId(1L).getDisplayName());
    }
}
