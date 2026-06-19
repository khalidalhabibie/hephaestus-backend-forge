package com.fif.training.exercisespringboot.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fif.training.exercisespringboot.DTO.ApiResponse;
import com.fif.training.exercisespringboot.DTO.CreateCustomerRequest;
import com.fif.training.exercisespringboot.DTO.CustomerResponse;
import com.fif.training.exercisespringboot.Service.CustomerService;

import jakarta.validation.Valid;

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

    // Search Customer By Name API
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerResponse> searchCustomerByName(@RequestParam String name) {
        return service.searchCustomerByName(name);
    }

    // POST Customer API
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
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

    // DELETE Customer By ID API
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<CustomerResponse> deleteCustomerById(@PathVariable Long id) {
        CustomerResponse response = service.deleteCustomerById(id);
        return new ApiResponse<>("Customer Deleted!", response);
    }

    // PUT/UPDATE Customer By ID API
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<CustomerResponse> editCustomerById(@PathVariable Long id,
            @Valid @RequestBody CreateCustomerRequest request) {

        CustomerResponse response = service.editCustomerById(id, request);
        return new ApiResponse<>("Customer data updated successfully", response);
    }

}