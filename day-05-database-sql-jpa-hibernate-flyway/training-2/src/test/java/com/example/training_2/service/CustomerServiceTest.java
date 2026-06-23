package com.example.training_2.service;

import com.example.training_2.dto.CreateCustomerRequest;
import com.example.training_2.dto.CustomerResponse;
import com.example.training_2.dto.PatchCustomerRequest;
import com.example.training_2.dto.UpdateCustomerRequest;
import com.example.training_2.entity.Customer;
import com.example.training_2.exception.CustomerAlreadyExistsException;
import com.example.training_2.repository.CustomerRepository;
import com.example.training_2.repository.LoanApplicationRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setFullName("Tony Stark");
        customer.setNik("123456789");
        customer.setEmail("tony@stark.com");
        customer.setPhoneNumber("08123456789");
    }

    @Test
    void createCustomer_Success() {

        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setFullName("Tony Stark");
        request.setNik("123456789");
        request.setEmail("tony@stark.com");
        request.setPhoneNumber("08123456789");

        when(customerRepository.existsByNik(request.getNik()))
                .thenReturn(false);

        when(customerRepository.existsByEmail(request.getEmail()))
                .thenReturn(false);

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(customer);

        CustomerResponse response = customerService.create(request);

        assertNotNull(response);
        assertEquals("Tony Stark", response.getFullName());
        assertEquals("tony@stark.com", response.getEmail());

        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void createCustomer_ShouldThrowException_WhenNikAlreadyExists() {

        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setNik("123456789");
        request.setEmail("tony@stark.com");

        when(customerRepository.existsByNik(request.getNik()))
                .thenReturn(true);

        assertThrows(
                CustomerAlreadyExistsException.class,
                () -> customerService.create(request));

        verify(customerRepository, never()).save(any());
    }

    @Test
    void createCustomer_ShouldThrowException_WhenEmailAlreadyExists() {

        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setNik("123456789");
        request.setEmail("tony@stark.com");

        when(customerRepository.existsByEmail(request.getEmail()))
                .thenReturn(true);

        assertThrows(
                CustomerAlreadyExistsException.class,
                () -> customerService.create(request));

        verify(customerRepository, never()).save(any());
    }

    @Test
    void getAll_ShouldReturnAllCustomers() {

        when(customerRepository.findAll())
                .thenReturn(List.of(customer));

        List<CustomerResponse> result = customerService.getAll(null);

        assertEquals(1, result.size());
        assertEquals("Tony Stark", result.get(0).getFullName());
    }

    @Test
    void getById_ShouldReturnCustomer() {

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        CustomerResponse response = customerService.getById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Tony Stark", response.getFullName());
    }

    @Test
    void getById_ShouldThrowException_WhenCustomerNotFound() {

        when(customerRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> customerService.getById(1L));

        assertEquals("Customer not found", exception.getMessage());
    }

    @Test
    void update_ShouldUpdateCustomerSuccessfully() {

        UpdateCustomerRequest request = new UpdateCustomerRequest();
        request.setFullName("Peter Parker");
        request.setNik("999999999");
        request.setEmail("peter@marvel.com");
        request.setPhoneNumber("089999999");

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(customer);

        CustomerResponse response = customerService.update(1L, request);

        assertEquals("Peter Parker", response.getFullName());
        assertEquals("999999999", response.getNik());

        verify(customerRepository).save(customer);
    }

    @Test
    void update_ShouldThrowException_WhenCustomerNotFound() {

        UpdateCustomerRequest request = new UpdateCustomerRequest();
        request.setFullName("Peter Parker");
        request.setNik("999999999");
        request.setEmail("peter@marvel.com");
        request.setPhoneNumber("089999999");

        when(customerRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> customerService.update(1L, request));

        assertEquals("Customer not found", exception.getMessage());

        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void patch_ShouldUpdateOnlyProvidedFieldsFullname() {

        PatchCustomerRequest request = new PatchCustomerRequest();
        request.setFullName("Bruce Wayne");

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(customer);

        CustomerResponse response = customerService.patch(1L, request);

        assertEquals("Bruce Wayne", response.getFullName());
        assertEquals("123456789", response.getNik());
        assertEquals("tony@stark.com", response.getEmail());

        verify(customerRepository).save(customer);
    }

    @Test
    void patch_ShouldUpdateOnlyProvidedFieldsNik() {

        PatchCustomerRequest request = new PatchCustomerRequest();
        request.setNik("123456789");

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(customer);

        CustomerResponse response = customerService.patch(1L, request);

        assertEquals("123456789", response.getNik());

        verify(customerRepository).save(customer);
    }

    @Test
    void patch_ShouldUpdateOnlyProvidedFieldsEmail() {

        PatchCustomerRequest request = new PatchCustomerRequest();
        request.setEmail("tony@stark.com");

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(customer);

        CustomerResponse response = customerService.patch(1L, request);

        assertEquals("tony@stark.com", response.getEmail());

        verify(customerRepository).save(customer);
    }

    @Test
    void patch_ShouldUpdateOnlyProvidedFieldsPhoneNumber() {

        PatchCustomerRequest request = new PatchCustomerRequest();
        request.setPhoneNumber("08112233");

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(customer);

        CustomerResponse response = customerService.patch(1L, request);

        assertEquals("08112233", response.getPhoneNumber());

        verify(customerRepository).save(customer);
    }

    @Test
    void delete_ShouldDeleteCustomerSuccessfully() {

        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        doNothing().when(customerRepository).delete(customer);

        customerService.delete(1L);

        verify(customerRepository).delete(customer);
    }

    @Test
    void delete_ShouldThrowException_WhenCustomerNotFound() {

        when(customerRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                RuntimeException.class,
                () -> customerService.delete(1L));

        verify(customerRepository, never()).delete(any());
    }
}