package com.example.training.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.training.auth.AuthContext;
import com.example.training.dto.CreateCustomerRequest;
import com.example.training.dto.CustomerResponse;
import com.example.training.entity.CustomerEntity;
import com.example.training.exception.DuplicateCustomerException;
import com.example.training.exception.ForbiddenException;
import com.example.training.exception.NotFoundException;
import com.example.training.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private CreateCustomerRequest request;
    private CustomerEntity customer;
    private AuthContext staffAuth;
    private AuthContext approverAuth;

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

        staffAuth = new AuthContext("staff-1", "STAFF");
        approverAuth = new AuthContext("approver-1", "APPROVER");
    }

    @Test
    void should_create_customer_successfully() {
        // given
        when(customerRepository.existsByNik(request.getNik())).thenReturn(false);
        when(customerRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customer);

        // when
        CustomerResponse response = customerService.create(request, staffAuth);

        // then
        assertNotNull(response);
        assertEquals(customer.getId(), response.getId());
        verify(customerRepository).save(any(CustomerEntity.class));
    }

    @Test
    void should_create_customer_fail_on_duplicate_nik() {
        // given
        when(customerRepository.existsByNik(request.getNik())).thenReturn(true);

        // when & then
        assertThrows(DuplicateCustomerException.class,
                () -> customerService.create(request, staffAuth));
        verify(customerRepository, never()).save(any());
    }

    @Test
    void should_create_customer_fail_on_duplicate_email() {
        // given
        when(customerRepository.existsByNik(request.getNik())).thenReturn(false);
        when(customerRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // when & then
        assertThrows(DuplicateCustomerException.class,
                () -> customerService.create(request, staffAuth));
        verify(customerRepository, never()).save(any());
    }

    @Test
    void should_not_allow_approver_to_create_customer() {
        // given — auth check happens first, no repo stubs needed

        // when & then
        ForbiddenException ex = assertThrows(ForbiddenException.class,
                () -> customerService.create(request, approverAuth));
        assertEquals("FORBIDDEN", ex.getCode());
        verify(customerRepository, never()).save(any());
    }

    @Test
    void should_get_customer_by_id_successfully() {
        // given
        when(customerRepository.findById(customer.getId()))
                .thenReturn(Optional.of(customer));

        // when
        CustomerResponse response = customerService.findById(customer.getId());

        // then
        assertNotNull(response);
        assertEquals(customer.getId(), response.getId());
        assertEquals(customer.getFullName(), response.getFullName());
    }

    @Test
    void should_throw_not_found_when_customer_does_not_exist() {
        // given
        UUID id = UUID.randomUUID();
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        // when & then
        NotFoundException ex = assertThrows(NotFoundException.class,
                () -> customerService.findById(id));
        assertEquals("CUSTOMER_NOT_FOUND", ex.getCode());
    }

    @Test
    void should_return_all_customers() {
        // given
        when(customerRepository.findAll()).thenReturn(List.of(customer));

        // when
        List<CustomerResponse> result = customerService.findAll();

        // then
        assertEquals(1, result.size());
        assertEquals(customer.getId(), result.get(0).getId());
    }

    @Test
    void should_search_customers_by_name() {
        // given
        when(customerRepository.findByFullNameContainingIgnoreCase("John"))
                .thenReturn(List.of(customer));

        // when
        List<CustomerResponse> result = customerService.searchByName("John");

        // then
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getFullName());
    }
}
