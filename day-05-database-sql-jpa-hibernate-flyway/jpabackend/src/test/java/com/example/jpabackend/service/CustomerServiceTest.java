package com.example.jpabackend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.List;
import java.util.Optional;
import com.example.jpabackend.dto.request.CreateCustomerRequest;
import com.example.jpabackend.dto.request.PatchCustomerRequest;
import com.example.jpabackend.dto.request.UpdateCustomerRequest;
import com.example.jpabackend.dto.response.CustomerResponse;
import com.example.jpabackend.dto.response.CustomerSummaryResponse;
import com.example.jpabackend.entity.CustomerEntity;
import com.example.jpabackend.exception.CustomerNotFoundException;
import com.example.jpabackend.exception.DuplicateCustomerException;
import com.example.jpabackend.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void should_create_customer_successfully() {
    CreateCustomerRequest request = CreateCustomerRequest.builder()
        .fullName("Edith")
        .nik("3123456789012345")
        .email("edith@gmail.com")
        .phoneNumber("08123456789012")
        .build();

    CustomerEntity savedCustomer = CustomerEntity.builder()
        .fullName(request.getFullName())
        .nik(request.getNik())
        .email(request.getEmail())
        .phoneNumber(request.getPhoneNumber())
        .build();
        savedCustomer.setId(1L);
    when(customerRepository.existsByNik(request.getNik()))
        .thenReturn(false);
    when(customerRepository.existsByEmail(request.getEmail()))
        .thenReturn(false);
    when(customerRepository.save(any(CustomerEntity.class)))
        .thenReturn(savedCustomer);
    
    CustomerResponse response = customerService.createCustomer(request);
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Edith", response.getFullName());
        verify(customerRepository).save(any(CustomerEntity.class));
    }

    @Test
    void should_throw_not_found_when_customer_does_not_exist() {
    Long customerId = 99L;
    when(customerRepository.findById(customerId))
        .thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomerById(customerId)
        );
    verify(customerRepository).findById(customerId);
    }

    @Test
    void should_throw_duplicate_customer_when_nik_already_exists() {
    CreateCustomerRequest request = CreateCustomerRequest.builder()
        .fullName("Edith")
        .nik("3123456789012345")
        .email("edith@gmail.com")
        .phoneNumber("081234567890")
        .build();
    when(customerRepository.existsByNik(request.getNik()))
        .thenReturn(true);
    DuplicateCustomerException exception =
        assertThrows(DuplicateCustomerException.class, () -> customerService.createCustomer(request));
        assertTrue(exception.getMessage().contains("already exists"));
    verify(customerRepository, never()).save(any());
    }

    @Test
    void should_throw_duplicate_customer_when_email_already_exists() {
    CreateCustomerRequest request = CreateCustomerRequest.builder()
        .fullName("Edith")
        .nik("3123456789012345")
        .email("edith@gmail.com")
        .phoneNumber("081234567890")
        .build();
    when(customerRepository.existsByNik(request.getNik()))
        .thenReturn(false);
    when(customerRepository.existsByEmail(request.getEmail()))
        .thenReturn(true);
        assertThrows(DuplicateCustomerException.class, () -> customerService.createCustomer(request));
    verify(customerRepository, never()).save(any());
    }

    @Test
    void should_return_all_customers() {
    CustomerEntity customer1 = CustomerEntity.builder()
        .fullName("Edith")
        .nik("3123456789012345")
        .email("edith@gmail.com")
        .build();
        customer1.setId(1L);
    CustomerEntity customer2 = CustomerEntity.builder()
        .fullName("Gracia")
        .nik("3114567890123457")
        .email("gracia@mail.com")
        .build();
        customer2.setId(2L);
    when(customerRepository.findAll())
        .thenReturn(List.of(customer1, customer2));
    List<CustomerSummaryResponse> result = customerService.getAll();
    assertEquals(2, result.size());
    }

    @Test
    void should_return_customer_by_id_successfully() {
    Long id = 1L;
    CustomerEntity customer = CustomerEntity.builder()
        .fullName("Edith")
        .nik("3123456789012345")
        .email("edith@mail.com")
        .phoneNumber("081234567890")
        .build();
        customer.setId(id);
    when(customerRepository.findById(id))
        .thenReturn(Optional.of(customer));
    CustomerResponse response = customerService.getCustomerById(id);
    assertEquals(id, response.getId());
    assertEquals("Edith", response.getFullName());
    verify(customerRepository).findById(id);
    }

    @Test
    void should_search_customer_by_name() {
    CustomerEntity customer = CustomerEntity.builder()
        .fullName("Gracia")
        .nik("3114567890123457")
        .email("gracia@mail.com")
        .build();
        customer.setId(1L);
    when(customerRepository.findByFullNameContainingIgnoreCase("Gracia"))
        .thenReturn(List.of(customer));
    List<CustomerSummaryResponse> result = customerService.searchCustomer("Gracia");
    assertEquals(1, result.size());
    assertEquals("Gracia", result.get(0).getFullName());
    }

    @Test
    void should_update_customer_successfully() {
    Long customerId = 1L;
    CustomerEntity customer = CustomerEntity.builder()
            .fullName("Gracia")
            .nik("3114567890123457")
            .email("gracia@mail.com")
            .phoneNumber("081234561527")
            .build();
            customer.setId(customerId);
    UpdateCustomerRequest request = UpdateCustomerRequest.builder()
            .fullName("Edith")
            .email("edith@gmail.com")
            .phoneNumber("081234567890")
            .build();
    when(customerRepository.findById(customerId))
        .thenReturn(Optional.of(customer));

    when(customerRepository.existsByEmail(request.getEmail()))
        .thenReturn(false);
    when(customerRepository.save(any()))
        .thenAnswer(invocation -> invocation.getArgument(0));
    CustomerResponse response = customerService.updateCustomer(customerId, request);
    assertEquals("Edith", response.getFullName());
    assertEquals("edith@gmail.com", response.getEmail());
    }

    @Test
    void should_throw_duplicate_email_when_updating_customer() {
    Long id = 1L;
    CustomerEntity customer = CustomerEntity.builder()
        .fullName("Edith")
        .email("old@mail.com")
        .phoneNumber("08123")
        .build();
        customer.setId(id);
    UpdateCustomerRequest request = UpdateCustomerRequest.builder()
        .fullName("Edith New")
        .email("new@mail.com")
        .phoneNumber("081999")
        .build();
    when(customerRepository.findById(id))
        .thenReturn(Optional.of(customer));
    when(customerRepository.existsByEmail(request.getEmail()))
        .thenReturn(true);
    assertThrows(DuplicateCustomerException.class, () -> customerService.updateCustomer(id, request));
    verify(customerRepository, never()).save(any());
    }

    @Test
    void should_patch_customer_successfully() {
    Long customerId = 1L;
    CustomerEntity customer = CustomerEntity.builder()
        .fullName("Edith")
        .nik("3123456789012345")
        .email("edith@mail.com")
        .phoneNumber("081234567890")
        .build();
        customer.setId(customerId);
    PatchCustomerRequest request = PatchCustomerRequest.builder()
        .fullName("John Updated")
        .build();
    when(customerRepository.findById(customerId))
        .thenReturn(Optional.of(customer));
    when(customerRepository.save(any()))
        .thenAnswer(invocation -> invocation.getArgument(0));
    CustomerResponse response = customerService.patchCustomer(customerId, request);
    assertEquals("John Updated", response.getFullName());
    }

    @Test
    void should_throw_duplicate_email_when_patching_customer() {
    Long id = 1L;
    CustomerEntity customer = CustomerEntity.builder()
        .fullName("Edith")
        .email("old@mail.com")
        .phoneNumber("08123")
        .build();
    customer.setId(id);
    PatchCustomerRequest request = PatchCustomerRequest.builder()
        .email("new@mail.com")
        .build();
    when(customerRepository.findById(id))
        .thenReturn(Optional.of(customer));
    when(customerRepository.existsByEmail(request.getEmail()))
        .thenReturn(true);
    assertThrows(DuplicateCustomerException.class, () -> customerService.patchCustomer(id, request));
    verify(customerRepository, never()).save(any());
    }

    @Test
    void should_patch_customer_partial_fields() {
    Long id = 1L;
    CustomerEntity customer = CustomerEntity.builder()
        .fullName("Edith")
        .email("edith@mail.com")
        .phoneNumber("08123")
        .build();
        customer.setId(id);
    PatchCustomerRequest request = PatchCustomerRequest.builder()
        .fullName(null)
        .email(null)
        .phoneNumber("99999")
        .build();
    when(customerRepository.findById(id))
        .thenReturn(Optional.of(customer));
    when(customerRepository.save(any()))
        .thenAnswer(invocation -> invocation.getArgument(0));
    CustomerResponse response = customerService.patchCustomer(id, request);
    assertEquals("Edith", response.getFullName());
    assertEquals("99999", response.getPhoneNumber());
    }

    @Test
    void should_delete_customer_successfully() {
    Long id = 1L;
    CustomerEntity customer = CustomerEntity.builder()
        .fullName("Edith")
        .email("edith@mail.com")
        .phoneNumber("08123")
        .build();
    customer.setId(id);
    customer.setDeleted(false);
    when(customerRepository.findById(id))
        .thenReturn(Optional.of(customer));
    when(customerRepository.save(any()))
        .thenAnswer(invocation -> invocation.getArgument(0));
    customerService.deleteCustomer(id);
    assertTrue(customer.isDeleted());
    verify(customerRepository).save(customer);
    }

    @Test
    void should_throw_not_found_when_deleting_non_existing_customer() {
    Long id = 99L;
    when(customerRepository.findById(id))
        .thenReturn(Optional.empty());
    assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(id));
    verify(customerRepository, never()).save(any());
    }
}