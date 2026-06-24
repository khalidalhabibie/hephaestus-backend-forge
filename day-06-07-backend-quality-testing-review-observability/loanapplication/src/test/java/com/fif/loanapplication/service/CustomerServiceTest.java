package com.fif.loanapplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fif.loanapplication.dto.customer.CreateCustomerRequest;
import com.fif.loanapplication.dto.customer.CustomerResponse;
import com.fif.loanapplication.entity.CustomerEntity;
import com.fif.loanapplication.exception.CustomerNotFoundException;
import com.fif.loanapplication.exception.DuplicateCustomerException;
import com.fif.loanapplication.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void createCustomer_whenRequestValid_shouldSaveCustomerAndReturnResponse() {
        // Given
        CreateCustomerRequest request = createCustomerRequest();

        UUID customerUid = UUID.randomUUID();

        CustomerEntity savedCustomer = CustomerEntity.builder()
                .uid(customerUid)
                .nik("1234567890123456")
                .fullName("steven")
                .email("steven@gmail.com")
                .phoneNumber("081234567890")
                .build();

        when(customerRepository.existsByNik(request.getNik())).thenReturn(false);
        when(customerRepository.existsByEmailIgnoreCase(request.getEmail())).thenReturn(false);
        when(customerRepository.existsByPhoneNumber(request.getPhoneNumber())).thenReturn(false);
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(savedCustomer);

        // When
        CustomerResponse response = customerService.createCustomer(request);

        // Then
        assertEquals(customerUid, response.getUid());
        assertEquals("1234567890123456", response.getNik());
        assertEquals("steven", response.getFullName());
        assertEquals("steven@gmail.com", response.getEmail());
        assertEquals("081234567890", response.getPhoneNumber());

        ArgumentCaptor<CustomerEntity> captor = ArgumentCaptor.forClass(CustomerEntity.class);
        verify(customerRepository).save(captor.capture());

        CustomerEntity capturedCustomer = captor.getValue();

        assertEquals("1234567890123456", capturedCustomer.getNik());
        assertEquals("steven", capturedCustomer.getFullName());
        assertEquals("steven@gmail.com", capturedCustomer.getEmail());
        assertEquals("081234567890", capturedCustomer.getPhoneNumber());
    }

    @Test
    void createCustomer_whenNikAlreadyExists_shouldThrowDuplicateCustomerException() {
        // Given
        CreateCustomerRequest request = createCustomerRequest();

        when(customerRepository.existsByNik(request.getNik())).thenReturn(true);

        // When
        DuplicateCustomerException exception = assertThrows(
                DuplicateCustomerException.class,
                () -> customerService.createCustomer(request));

        // Then
        assertEquals("NIK sudah terdaftar!", exception.getMessage());

        verify(customerRepository, never()).existsByEmailIgnoreCase(request.getEmail());
        verify(customerRepository, never()).existsByPhoneNumber(request.getPhoneNumber());
        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    @Test
    void createCustomer_whenEmailAlreadyExists_shouldThrowDuplicateCustomerException() {
        // Given
        CreateCustomerRequest request = createCustomerRequest();

        when(customerRepository.existsByNik(request.getNik())).thenReturn(false);
        when(customerRepository.existsByEmailIgnoreCase(request.getEmail())).thenReturn(true);

        // When
        DuplicateCustomerException exception = assertThrows(
                DuplicateCustomerException.class,
                () -> customerService.createCustomer(request));

        // Then
        assertEquals("Email sudah terdaftar!", exception.getMessage());

        verify(customerRepository, never()).existsByPhoneNumber(request.getPhoneNumber());
        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    @Test
    void createCustomer_whenPhoneNumberAlreadyExists_shouldThrowDuplicateCustomerException() {
        // Given
        CreateCustomerRequest request = createCustomerRequest();

        when(customerRepository.existsByNik(request.getNik())).thenReturn(false);
        when(customerRepository.existsByEmailIgnoreCase(request.getEmail())).thenReturn(false);
        when(customerRepository.existsByPhoneNumber(request.getPhoneNumber())).thenReturn(true);

        // When
        DuplicateCustomerException exception = assertThrows(
                DuplicateCustomerException.class,
                () -> customerService.createCustomer(request));

        // Then
        assertEquals("Nomor telepon sudah terdaftar!", exception.getMessage());

        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    @Test
    void getCustomerById_whenCustomerExists_shouldReturnCustomerResponse() {
        // Given
        UUID customerUid = UUID.randomUUID();
        CustomerEntity customer = createCustomerEntity(customerUid);

        when(customerRepository.findById(customerUid)).thenReturn(Optional.of(customer));

        // When
        CustomerResponse response = customerService.getCustomerById(customerUid);

        // Then
        assertEquals(customerUid, response.getUid());
        assertEquals("1234567890123456", response.getNik());
        assertEquals("steven", response.getFullName());
        assertEquals("steven@gmail.com", response.getEmail());
        assertEquals("081234567890", response.getPhoneNumber());
    }

    @Test
    void getCustomerById_whenCustomerNotFound_shouldThrowCustomerNotFoundException() {
        // Given
        UUID customerUid = UUID.randomUUID();

        when(customerRepository.findById(customerUid)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(
                CustomerNotFoundException.class,
                () -> customerService.getCustomerById(customerUid));
    }

    @Test
    void getAllCustomers_whenNoFilterProvided_shouldReturnAllCustomers() {
        // Given
        CustomerEntity customer1 = createCustomerEntity(UUID.randomUUID());

        CustomerEntity customer2 = CustomerEntity.builder()
                .uid(UUID.randomUUID())
                .nik("9999999999999999")
                .fullName("budi")
                .email("budi@gmail.com")
                .phoneNumber("089999999999")
                .build();

        when(customerRepository.findAll()).thenReturn(List.of(customer1, customer2));

        // When
        List<CustomerResponse> responses = customerService.getAllCustomers(null, null, null);

        // Then
        assertEquals(2, responses.size());
    }

    @Test
    void getAllCustomers_whenFullNameFilterMatches_shouldReturnMatchedCustomer() {
        // Given
        CustomerEntity customer1 = createCustomerEntity(UUID.randomUUID());

        CustomerEntity customer2 = CustomerEntity.builder()
                .uid(UUID.randomUUID())
                .nik("9999999999999999")
                .fullName("budi")
                .email("budi@gmail.com")
                .phoneNumber("089999999999")
                .build();

        when(customerRepository.findAll()).thenReturn(List.of(customer1, customer2));

        // When
        List<CustomerResponse> responses = customerService.getAllCustomers("stev", null, null);

        // Then
        assertEquals(1, responses.size());
        assertEquals("steven", responses.get(0).getFullName());
    }

    @Test
    void getAllCustomers_whenNikFilterMatches_shouldReturnMatchedCustomer() {
        // Given
        CustomerEntity customer = createCustomerEntity(UUID.randomUUID());

        when(customerRepository.findAll()).thenReturn(List.of(customer));

        // When
        List<CustomerResponse> responses = customerService.getAllCustomers(null, "1234", null);

        // Then
        assertEquals(1, responses.size());
        assertEquals("1234567890123456", responses.get(0).getNik());
    }

    @Test
    void getAllCustomers_whenEmailFilterMatches_shouldReturnMatchedCustomer() {
        // Given
        CustomerEntity customer = createCustomerEntity(UUID.randomUUID());

        when(customerRepository.findAll()).thenReturn(List.of(customer));

        // When
        List<CustomerResponse> responses = customerService.getAllCustomers(null, null, "gmail");

        // Then
        assertEquals(1, responses.size());
        assertEquals("steven@gmail.com", responses.get(0).getEmail());
    }

    @Test
    void getAllCustomers_whenNoFilterMatches_shouldReturnEmptyList() {
        // Given
        CustomerEntity customer = createCustomerEntity(UUID.randomUUID());

        when(customerRepository.findAll()).thenReturn(List.of(customer));

        // When
        List<CustomerResponse> responses = customerService.getAllCustomers("not-found", "0000", "yahoo");

        // Then
        assertEquals(0, responses.size());
    }

    @Test
    void editCustomer_whenRequestValid_shouldUpdateCustomerAndReturnResponse() {
        // Given
        UUID customerUid = UUID.randomUUID();

        CreateCustomerRequest request = createCustomerRequest();

        CustomerEntity existingCustomer = CustomerEntity.builder()
                .uid(customerUid)
                .nik("0000000000000000")
                .fullName("old name")
                .email("old@gmail.com")
                .phoneNumber("080000000000")
                .build();

        CustomerEntity updatedCustomer = CustomerEntity.builder()
                .uid(customerUid)
                .nik("1234567890123456")
                .fullName("steven")
                .email("steven@gmail.com")
                .phoneNumber("081234567890")
                .build();

        when(customerRepository.findById(customerUid)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.existsByNikAndUidNot(request.getNik(), customerUid)).thenReturn(false);
        when(customerRepository.existsByEmailIgnoreCaseAndUidNot(request.getEmail(), customerUid)).thenReturn(false);
        when(customerRepository.existsByPhoneNumberAndUidNot(request.getPhoneNumber(), customerUid)).thenReturn(false);
        when(customerRepository.save(existingCustomer)).thenReturn(updatedCustomer);

        // When
        CustomerResponse response = customerService.editCustomer(customerUid, request);

        // Then
        assertEquals(customerUid, response.getUid());
        assertEquals("1234567890123456", response.getNik());
        assertEquals("steven", response.getFullName());
        assertEquals("steven@gmail.com", response.getEmail());
        assertEquals("081234567890", response.getPhoneNumber());

        assertEquals("1234567890123456", existingCustomer.getNik());
        assertEquals("steven", existingCustomer.getFullName());
        assertEquals("steven@gmail.com", existingCustomer.getEmail());
        assertEquals("081234567890", existingCustomer.getPhoneNumber());
    }

    @Test
    void editCustomer_whenCustomerNotFound_shouldThrowCustomerNotFoundException() {
        // Given
        UUID customerUid = UUID.randomUUID();
        CreateCustomerRequest request = createCustomerRequest();

        when(customerRepository.findById(customerUid)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(
                CustomerNotFoundException.class,
                () -> customerService.editCustomer(customerUid, request));

        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    @Test
    void editCustomer_whenNikAlreadyUsed_shouldThrowDuplicateCustomerException() {
        // Given
        UUID customerUid = UUID.randomUUID();
        CreateCustomerRequest request = createCustomerRequest();
        CustomerEntity customer = createCustomerEntity(customerUid);

        when(customerRepository.findById(customerUid)).thenReturn(Optional.of(customer));
        when(customerRepository.existsByNikAndUidNot(request.getNik(), customerUid)).thenReturn(true);

        // When
        DuplicateCustomerException exception = assertThrows(
                DuplicateCustomerException.class,
                () -> customerService.editCustomer(customerUid, request));

        // Then
        assertEquals("NIK sudah digunakan!", exception.getMessage());

        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    @Test
    void editCustomer_whenEmailAlreadyUsed_shouldThrowDuplicateCustomerException() {
        // Given
        UUID customerUid = UUID.randomUUID();
        CreateCustomerRequest request = createCustomerRequest();
        CustomerEntity customer = createCustomerEntity(customerUid);

        when(customerRepository.findById(customerUid)).thenReturn(Optional.of(customer));
        when(customerRepository.existsByNikAndUidNot(request.getNik(), customerUid)).thenReturn(false);
        when(customerRepository.existsByEmailIgnoreCaseAndUidNot(request.getEmail(), customerUid)).thenReturn(true);

        // When
        DuplicateCustomerException exception = assertThrows(
                DuplicateCustomerException.class,
                () -> customerService.editCustomer(customerUid, request));

        // Then
        assertEquals("Email sudah digunakan!", exception.getMessage());

        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    @Test
    void editCustomer_whenPhoneNumberAlreadyUsed_shouldThrowDuplicateCustomerException() {
        // Given
        UUID customerUid = UUID.randomUUID();
        CreateCustomerRequest request = createCustomerRequest();
        CustomerEntity customer = createCustomerEntity(customerUid);

        when(customerRepository.findById(customerUid)).thenReturn(Optional.of(customer));
        when(customerRepository.existsByNikAndUidNot(request.getNik(), customerUid)).thenReturn(false);
        when(customerRepository.existsByEmailIgnoreCaseAndUidNot(request.getEmail(), customerUid)).thenReturn(false);
        when(customerRepository.existsByPhoneNumberAndUidNot(request.getPhoneNumber(), customerUid)).thenReturn(true);

        // When
        DuplicateCustomerException exception = assertThrows(
                DuplicateCustomerException.class,
                () -> customerService.editCustomer(customerUid, request));

        // Then
        assertEquals("Nomor telepon sudah digunakan!", exception.getMessage());

        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    @Test
    void deleteCustomer_whenCustomerExists_shouldDeleteCustomer() {
        // Given
        UUID customerUid = UUID.randomUUID();
        CustomerEntity customer = createCustomerEntity(customerUid);

        when(customerRepository.findById(customerUid)).thenReturn(Optional.of(customer));

        // When
        customerService.deleteCustomer(customerUid);

        // Then
        verify(customerRepository).delete(customer);
    }

    @Test
    void deleteCustomer_whenCustomerNotFound_shouldThrowCustomerNotFoundException() {
        // Given
        UUID customerUid = UUID.randomUUID();

        when(customerRepository.findById(customerUid)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(
                CustomerNotFoundException.class,
                () -> customerService.deleteCustomer(customerUid));

        verify(customerRepository, never()).delete(any(CustomerEntity.class));
    }

    private CreateCustomerRequest createCustomerRequest() {
        CreateCustomerRequest request = CreateCustomerRequest.builder()
                .nik("1234567890123456")
                .fullName("steven")
                .email("steven@gmail.com")
                .phoneNumber("081234567890")
                .build();
        return request;
    }

    private CustomerEntity createCustomerEntity(UUID customerUid) {
        return CustomerEntity.builder()
                .uid(customerUid)
                .nik("1234567890123456")
                .fullName("steven")
                .email("steven@gmail.com")
                .phoneNumber("081234567890")
                .build();
    }
}