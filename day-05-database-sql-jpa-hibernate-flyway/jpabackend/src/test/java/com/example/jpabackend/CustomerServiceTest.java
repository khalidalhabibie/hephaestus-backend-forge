package com.example.jpabackend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.jpabackend.dto.*;
import com.example.jpabackend.repository.*;
import com.example.jpabackend.service.CustomerService;
import com.example.jpabackend.entity.*;
import com.example.jpabackend.exception.*;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository repository;

    @Test
    void should_create_customer_successfully() {
        // given
        CreateCustomerRequest req = new CreateCustomerRequest();
        req.setFullName("Budi");
        req.setNik("123");
        req.setEmail("budi@mail.com");
        req.setPhoneNumber("08123");

        when(repository.existsByNik("123")).thenReturn(false);
        when(repository.existsByEmail("budi@mail.com")).thenReturn(false);

        CustomerEntity saved = new CustomerEntity();
        saved.setId(1L);
        saved.setFullName("Budi");
        saved.setNik("123");
        saved.setEmail("budi@mail.com");

        when(repository.save(any(CustomerEntity.class))).thenReturn(saved);

        // when
        CustomerResponse result = customerService.createCustomer(req);

        // then
        assertNotNull(result);
        assertEquals("Budi", result.getFullName());
        verify(repository).save(any(CustomerEntity.class));
    }

    @Test
    void should_throw_duplicate_when_nik_exists() {
        // given
        CreateCustomerRequest req = new CreateCustomerRequest();
        req.setNik("123");

        when(repository.existsByNik("123")).thenReturn(true);

        // when & then
        assertThrows(DuplicateCustomerException.class,
                () -> customerService.createCustomer(req));

        verify(repository, never()).save(any());
    }

    @Test
    void should_throw_duplicate_when_email_exists() {
        // given
        CreateCustomerRequest req = new CreateCustomerRequest();
        req.setNik("123");
        req.setEmail("test@mail.com");

        when(repository.existsByNik("123")).thenReturn(false);
        when(repository.existsByEmail("test@mail.com")).thenReturn(true);

        // when & then
        assertThrows(DuplicateCustomerException.class,
                () -> customerService.createCustomer(req));

        verify(repository, never()).save(any());
    }

    @Test
    void should_get_customer_by_id_successfully() {
        // given
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);
        customer.setFullName("Budi");

        when(repository.findById(1L)).thenReturn(Optional.of(customer));

        // when
        CustomerResponse result = customerService.getById(1L);

        // then
        assertEquals(1L, result.getId());
    }

    @Test
    void should_throw_not_found_when_customer_does_not_exist() {
        // given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(CustomerNotFoundException.class,
                () -> customerService.getById(1L));
    }

    @Test
    void should_return_all_customers() {
        // given
        when(repository.findAllActive())
                .thenReturn(List.of(new CustomerEntity(), new CustomerEntity()));

        // when
        List<CustomerResponse> result = customerService.getAll();

        // then
        assertEquals(2, result.size());
    }

    @Test
    void should_search_customer_by_name() {
        // given
        when(repository.searchActive("bud"))
                .thenReturn(List.of(new CustomerEntity()));

        // when
        List<CustomerResponse> result = customerService.searchByName("bud");

        // then
        assertEquals(1, result.size());
    }

    @Test
    void should_soft_delete_customer_successfully() {
        // given
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);
        customer.setIsDeleted(false);

        when(repository.findById(1L)).thenReturn(Optional.of(customer));

        // when
        customerService.deleteCustomer(1L);

        // then
        assertTrue(customer.getIsDeleted());
    }

    @Test
    void should_throw_not_found_when_delete_non_existing_customer() {
        // given
        when(repository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(CustomerNotFoundException.class,
                () -> customerService.deleteCustomer(1L));
    }
}