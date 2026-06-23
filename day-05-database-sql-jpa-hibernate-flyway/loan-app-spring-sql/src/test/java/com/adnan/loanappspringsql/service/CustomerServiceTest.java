package com.adnan.loanappspringsql.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.adnan.loanappspringsql.dto.CreateCustomerRequest;
import com.adnan.loanappspringsql.dto.CustomerResponse;
import com.adnan.loanappspringsql.exception.BadRequestException;
import com.adnan.loanappspringsql.exception.NotFoundException;
import com.adnan.loanappspringsql.model.Customer;
import com.adnan.loanappspringsql.repository.CustomerRepository;
import com.adnan.loanappspringsql.service.impl.CustomerServiceImpl;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
  @Mock
  private CustomerRepository customerRepository;

  @InjectMocks
  private CustomerServiceImpl customerService;

  @Test
  void create_shouldCreateCustomerSuccessfully() {
    // Given
    CreateCustomerRequest request = CreateCustomerRequest.builder()
        .fullName("Budi Santoso")
        .nik("3173010101900001")
        .email("budi@mail.com")
        .phoneNumber("08123456789")
        .build();
    when(customerRepository.existsByNik(request.getNik())).thenReturn(false);
    when(customerRepository.existsByEmail(request.getEmail())).thenReturn(false);
    when(customerRepository.save(any(Customer.class)))
        .thenAnswer(invocation -> {
          Customer customer = invocation.getArgument(0);
          customer.setId(1L);
          return customer;
        });

    // When
    CustomerResponse response = customerService.create(request);

    // Then
    assertNotNull(response);
    assertEquals(1L, response.getId());
    assertEquals(request.getFullName(), response.getFullName());
    assertEquals(request.getEmail(), response.getEmail());

    ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
    verify(customerRepository).save(captor.capture());

    Customer savedCustomer = captor.getValue();
    assertEquals(request.getNik(), savedCustomer.getNik());
    assertEquals(request.getEmail(), savedCustomer.getEmail());
    assertEquals(request.getPhoneNumber(), savedCustomer.getPhoneNumber());
  }

  @Test
  void create_shouldThrowBadRequestException_whenNikAlreadyExists() {
    // Given
    CreateCustomerRequest request = CreateCustomerRequest.builder()
        .fullName("Budi")
        .nik("3173010101900001")
        .email("budi@mail.com")
        .phoneNumber("08123456789")
        .build();
    when(customerRepository.existsByNik(request.getNik())).thenReturn(true);

    // When & Then
    BadRequestException exception = assertThrows(BadRequestException.class, () -> customerService.create(request));
    assertEquals("NIK already exists", exception.getMessage());
    verify(customerRepository, never()).save(any());
  }

  @Test
  void create_shouldThrowBadRequestException_whenEmailAlreadyExists() {
    // Given
    CreateCustomerRequest request = CreateCustomerRequest.builder()
        .fullName("Budi")
        .nik("3173010101900001")
        .email("budi@mail.com")
        .phoneNumber("08123456789")
        .build();
    when(customerRepository.existsByNik(request.getNik())).thenReturn(false);
    when(customerRepository.existsByEmail(request.getEmail())).thenReturn(true);

    // When & Then
    BadRequestException exception = assertThrows(
        BadRequestException.class,
        () -> customerService.create(request));
    assertEquals("Email already exists", exception.getMessage());
    verify(customerRepository, never()).save(any());
  }

  @Test
  void findById_shouldReturnCustomerSuccessfully() {
    // Given
    Customer customer = Customer.builder()
        .id(1L)
        .fullName("Budi")
        .nik("3173010101900001")
        .email("budi@mail.com")
        .phoneNumber("08123456789")
        .build();
    when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

    // When
    CustomerResponse response = customerService.findById(1L);

    // Then
    assertNotNull(response);
    assertEquals(1L, response.getId());
    assertEquals("Budi", response.getFullName());
    verify(customerRepository).findById(1L);
  }

  @Test
  void findById_shouldThrowNotFoundException_whenCustomerNotFound() {
    // Given
    when(customerRepository.findById(1L)).thenReturn(Optional.empty());

    // When & Then
    NotFoundException exception = assertThrows(NotFoundException.class, () -> customerService.findById(1L));
    assertEquals("Customer not found with id: 1", exception.getMessage());
    verify(customerRepository).findById(1L);
  }

  @Test
  void findAll_shouldReturnAllCustomers() {
    // Given
    List<Customer> customers = List.of(
        Customer.builder().id(1L).fullName("Budi").build(),
        Customer.builder().id(2L).fullName("Andi").build());
    when(customerRepository.findAll()).thenReturn(customers);

    // When
    List<CustomerResponse> result = customerService.findAll();

    // Then
    assertEquals(2, result.size());
    verify(customerRepository).findAll();
  }

  @Test
  void search_shouldReturnMatchingCustomers() {
    // Given
    List<Customer> customers = List.of(
        Customer.builder()
            .id(1L)
            .fullName("Budi Santoso")
            .build());
    when(customerRepository.findByFullNameContainingIgnoreCase("Budi")).thenReturn(customers);

    // When
    List<CustomerResponse> result = customerService.search("Budi");

    // Then
    assertEquals(1, result.size());
    assertEquals("Budi Santoso", result.get(0).getFullName());
    verify(customerRepository).findByFullNameContainingIgnoreCase("Budi");
  }
}
