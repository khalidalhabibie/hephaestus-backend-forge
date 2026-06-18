package com.fif.training.exercisespringboot.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fif.training.exercisespringboot.DTO.ApiResponse;
import com.fif.training.exercisespringboot.DTO.CreateCustomerRequest;
import com.fif.training.exercisespringboot.DTO.CustomerResponse;
import com.fif.training.exercisespringboot.Service.CustomerService;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    CustomerService service = new CustomerService();

    // GET All Customer API
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerResponse> getAllCustomer() {
        return service.getAllCustomer();
    }

    // POST Customer API
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CustomerResponse> createCustomer(@RequestBody CreateCustomerRequest request) {
        CustomerResponse response = service.createCustomer(request);
        return new ApiResponse<>("Customer Created!", response);
    }

    // GET Customer By ID API
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse getCustomerbyId(@PathVariable Long id) {
        CustomerResponse response = service.getCustomerById(id);
        return response;
    }

}
