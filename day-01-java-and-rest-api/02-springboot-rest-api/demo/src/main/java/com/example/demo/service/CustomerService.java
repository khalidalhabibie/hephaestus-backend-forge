package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.CreateCustomerRequest;
import com.example.demo.dto.CustomerResponse;
import com.example.demo.model.Customer;

@Service
public class CustomerService {
	// public String getCustomers() {
	// 	return "GET - Get all customers";
	// }	

    private Map<Long, Customer> customerStorage = new HashMap<>();
    private Long sequence = 1L;


    // private CustomerResponse buildCustomerResponse(String fullName, String email, String phoneNumber){
    //     Customer customer = new Customer(sequence, fullName, email, phoneNumber);
    //     customerStorage.put(sequence, customer);
    //     sequence++;

    //     CustomerResponse response = new CustomerResponse();

    //     response.setId(customer.getId());
    //     response.setFullName(customer.getFullName());
    //     response.setEmail(customer.getEmail());
    //     response.setPhoneNumber(customer.getPhoneNumber());

    //     return response;

    // }

    // private CustomerResponse getDefaultCustomer(){
    //     return buildCustomerResponse(
    //         "Budi Santoso", 
    //         "budi@mail.com", 
    //         "08123456789");
    // }

    public List<CustomerResponse> getCustomers() {
        List<CustomerResponse> responses = new ArrayList<>();


        for (Customer customer : customerStorage.values()){

            CustomerResponse response = new CustomerResponse();
            response.setFullName(customer.getFullName());
            response.setEmail(customer.getEmail());
            response.setPhoneNumber(customer.getPhoneNumber());
            response.setId(customer.getId());

            responses.add(response);
        }
	    return responses;
	}	


	public CustomerResponse createCustomer(@RequestBody CreateCustomerRequest request){
        Customer customer = new Customer(sequence, request.getFullName(), request.getEmail(), request.getPhoneNumber());
        customerStorage.put(sequence, customer);
        sequence++;

		CustomerResponse response = new CustomerResponse();
	
        response.setId(customer.getId());
		response.setFullName(customer.getFullName());
		response.setEmail(customer.getEmail());
		response.setPhoneNumber(customer.getPhoneNumber());

		return response;
	}

	public CustomerResponse getCustomerResponseById(Long id){
		CustomerResponse response = new CustomerResponse();
		Customer customer = customerStorage.get(id);

		response.setFullName(customer.getFullName());
		response.setEmail(customer.getEmail());
		response.setPhoneNumber(customer.getPhoneNumber());
        response.setId(customer.getId());

		return response;
	}

    public String deleteCustomerResponseById(@PathVariable Long id){
        customerStorage.remove(id);
        return String.format("Berhasil menghapus customer dengan ID: %s", id);
    }

    public CustomerResponse putCustomerResponseById(@PathVariable Long id, @RequestBody CreateCustomerRequest request){

        Customer customer = customerStorage.get(id);

        if (request.getFullName() != null) {
            customer.setFullName(request.getFullName());
        } else{
            customer.setFullName(customer.getFullName());
        }

        if (request.getEmail() != null) {
            customer.setEmail(request.getEmail());
        } else{
            customer.setEmail(customer.getEmail());
        }

        if (request.getPhoneNumber() != null) {
            customer.setPhoneNumber(request.getPhoneNumber());
        } else{
            customer.setPhoneNumber(customer.getPhoneNumber());
        }
        
        CustomerResponse response = new CustomerResponse();

        response.setFullName(customer.getFullName());
        response.setEmail(customer.getEmail());
        response.setPhoneNumber(customer.getPhoneNumber());
        response.setId(customer.getId());

        return response;
    }    
    

    public List<CustomerResponse> getCustomerResponsesByName(String name){

    List<CustomerResponse> responses = new ArrayList<>();

    for (Customer customer : customerStorage.values()){
        if (customer.getFullName() != null && name != null && customer.getFullName().toLowerCase().contains(name.toLowerCase())) {

            CustomerResponse response = new CustomerResponse();
            response.setId(customer.getId());
            response.setFullName(customer.getFullName());
            response.setEmail(customer.getEmail());
            response.setPhoneNumber(customer.getPhoneNumber());

            responses.add(response);
        }
    }

    return responses;
}

}

   

