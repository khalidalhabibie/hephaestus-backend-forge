package com.example.dbbackend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.dbbackend.common.exception.CustomerNotFoundException;
import com.example.dbbackend.common.exception.DuplicateCustomerException;
import com.example.dbbackend.customer.dto.CreateCustomerRequest;
import com.example.dbbackend.customer.entity.CustomerEntity;
import com.example.dbbackend.customer.repository.CustomerRepository;
import com.example.dbbackend.customer.service.CustomerService;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerService service;

    @Test
    void shouldCreateCustomerSuccessfully() {

        // Given
        CreateCustomerRequest req = new CreateCustomerRequest();
        req.setFullName("Budi");
        req.setNik("123");
        req.setEmail("budi@mail.com");

        when(repository.existsByNik("123")).thenReturn(false);
        when(repository.existsByEmail("budi@mail.com")).thenReturn(false);

        CustomerEntity saved = new CustomerEntity();
        saved.setId(1L);

        when(repository.save(any())).thenReturn(saved);

        // WHEN
        var result = service.createCustomer(req);

        // THEN
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldFailWhenNikDuplicate() {
        // GIVEN
        CreateCustomerRequest req = new CreateCustomerRequest();
        req.setNik("123");

        when(repository.existsByNik("123")).thenReturn(true);

        // WHEN & THEN
        assertThrows(DuplicateCustomerException.class,
                () -> service.createCustomer(req));
    }

    @Test
    void shouldFailWhenEmailDuplicate() {
        // GIVEN
        CreateCustomerRequest req = new CreateCustomerRequest();
        req.setNik("123");
        req.setEmail("a@mail.com");

        when(repository.existsByNik("123")).thenReturn(false);
        when(repository.existsByEmail("a@mail.com")).thenReturn(true);

        // WHEN & THEN
        assertThrows(DuplicateCustomerException.class,
                () -> service.createCustomer(req));
    }

    @Test
    void shouldGetCustomer() {
        // GIVEN
        CustomerEntity entity = new CustomerEntity();
        entity.setId(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(entity));

        // WHEN
        var result = service.getCustomerById(1L);
        // THEN
        assertEquals(1L, result.getId());
    }

    @Test
    void shouldThrowWhenCustomerNotFound() {
        // GIVEN
        when(repository.findById(1L)).thenReturn(Optional.empty());
        // WHEN & THEN
        assertThrows(CustomerNotFoundException.class,
                () -> service.getCustomerById(1L));
    }

}
