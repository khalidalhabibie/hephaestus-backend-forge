package com.example.training.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.training.dto.CreateCustomerRequest;
import com.example.training.dto.CustomerResponse;
import com.example.training.entity.CustomerEntity;
import com.example.training.exception.DuplicateCustomerException;
import com.example.training.exception.NotFoundException;
import com.example.training.repository.CustomerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private CreateCustomerRequest request;
    private CustomerEntity customer;

    @BeforeEach
    void setUp() {
        request = new CreateCustomerRequest();
        request.setFullName("John Doe");
        request.setNik("123456789");
        request.setEmail("john@mail.com");
        request.setPhoneNumber("08123456789");

        customer = new CustomerEntity();
        customer.setId(UUID.randomUUID());
        customer.setFullName(request.getFullName());
        customer.setNik(request.getNik());
        customer.setEmail(request.getEmail());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setCreatedAt(ZonedDateTime.now());
        customer.setUpdatedAt(ZonedDateTime.now());
    }

    @Test
    void create_Success() {
        when(customerRepository.existsByNik(request.getNik())).thenReturn(false);
        when(customerRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customer);

        CustomerResponse response = customerService.create(request);

        assertNotNull(response);
        assertEquals(customer.getId(), response.getId());

        verify(customerRepository).save(any(CustomerEntity.class));
    }

    @Test
    void create_ShouldThrowDuplicateNik() {
        when(customerRepository.existsByNik(request.getNik())).thenReturn(true);

        assertThrows(
                DuplicateCustomerException.class,
                () -> customerService.create(request));

        verify(customerRepository, never()).save(any());
    }

    @Test
    void create_ShouldThrowDuplicateEmail() {
        when(customerRepository.existsByNik(request.getNik())).thenReturn(false);
        when(customerRepository.existsByEmail(request.getEmail())).thenReturn(true);

        assertThrows(
                DuplicateCustomerException.class,
                () -> customerService.create(request));

        verify(customerRepository, never()).save(any());
    }

    @Test
    void findAll_Success() {
        when(customerRepository.findAll())
                .thenReturn(List.of(customer));

        List<CustomerResponse> result = customerService.findAll();

        assertEquals(1, result.size());
        assertEquals(customer.getEmail(), result.get(0).getEmail());
    }

    @Test
    void findById_Success() {
        when(customerRepository.findById(customer.getId()))
                .thenReturn(Optional.of(customer));

        CustomerResponse response =
                customerService.findById(customer.getId());

        assertNotNull(response);
        assertEquals(customer.getId(), response.getId());
    }

    @Test
    void findById_NotFound() {
        UUID id = UUID.randomUUID();

        when(customerRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> customerService.findById(id));
    }

    @Test
    void searchByName_Success() {
        when(customerRepository.findByFullNameContainingIgnoreCase("John"))
                .thenReturn(List.of(customer));

        List<CustomerResponse> result =
                customerService.searchByName("John");

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getFullName());
    }
}