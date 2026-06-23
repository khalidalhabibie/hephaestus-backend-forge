package com.example.main.services;

import com.example.main.dto.request.CreateCustomerRequest;
import com.example.main.dto.request.PatchCustomerRequest;
import com.example.main.dto.response.CustomerResponse;
import com.example.main.entity.CustomerEntity;
import com.example.main.exceptions.DuplicateException;
import com.example.main.exceptions.NotFoundException;
import com.example.main.repositories.CustomerRepository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private final String CORRELATION_ID = "REQ-TEST-2026";

    @BeforeEach
    void setUp() {
        MDC.put("correlation_id", CORRELATION_ID);
    }

    @AfterEach
    void tearDown() {
        MDC.clear();
    }

    // ! SHOULD CREATE CUSTOMER SUCCESSFULLY
    @Test
    void should_create_customer_successfully() {
        // given
        CreateCustomerRequest request = new CreateCustomerRequest(
                "Davin Bennett", "3171012345678901", "davin.bennett@example.com", "081234567890"
        );
        
        CustomerEntity savedEntity = new CustomerEntity();
        savedEntity.setId(1L);
        savedEntity.setFullName(request.getFullName());
        savedEntity.setNik(request.getNik());
        savedEntity.setEmail(request.getEmail());
        savedEntity.setPhoneNumber(request.getPhoneNumber());

        when(customerRepository.existsByNik(request.getNik())).thenReturn(false);
        when(customerRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(savedEntity);

        // when
        CustomerResponse response = customerService.createCustomer(request);

        // then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Davin Bennett", response.getFullName());
        assertEquals("3171012345678901", response.getNik());
        verify(customerRepository, times(1)).save(any(CustomerEntity.class));
    }

    // ! SHOULD GET CUSTOMER BY ID SUCCESSFULLY
    @Test
    void should_get_customer_by_id_successfully() {
        Long customerId = 1L;
        CustomerEntity customer = new CustomerEntity();
        customer.setId(customerId);
        customer.setFullName("Jane Doe");
        customer.setNik("3171098765432109");
        customer.setEmail("jane.doe@example.com");
        customer.setPhoneNumber("081298765432");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        CustomerResponse response = customerService.getCustomerById(customerId);

        assertNotNull(response);
        assertEquals(customerId, response.getId());
        assertEquals("Jane Doe", response.getFullName());
        verify(customerRepository, times(1)).findById(customerId);
    }

    // ! SHOULD THROW NOT FOUND WHEN CUSTOMER DOES NOT EXIST
    @Test
    void should_throw_not_found_when_customer_does_not_exist() {
        // given
        Long nonExistentId = 99L;
        when(customerRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // when & then
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> customerService.getCustomerById(nonExistentId));

        assertEquals("Customer not found", exception.getMessage());
        verify(customerRepository, times(1)).findById(nonExistentId);
    }

    // ! SHOULD RETURN ALL CUSTOMERS
    @Test
    void should_return_all_customers() {
        // given
        CustomerEntity customer1 = new CustomerEntity();
        customer1.setId(1L);
        customer1.setFullName("Customer One");

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setId(2L);
        customer2.setFullName("Customer Two");

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

        // when
        List<CustomerResponse> responses = customerService.getAllCustomers();

        // then
        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals("Customer One", responses.get(0).getFullName());
        assertEquals("Customer Two", responses.get(1).getFullName());
        verify(customerRepository, times(1)).findAll();
    }

    // ! SHOULD THROW DUPLICATE WHEN NIK ALREADY EXISTS (NEGATIVE PATH)
    @Test
    void should_throw_duplicate_when_nik_already_exists() {
        // given
        CreateCustomerRequest request = new CreateCustomerRequest(
                "John Doe", "3171012345678901", "john.doe@example.com", "081234567890"
        );

        when(customerRepository.existsByNik(request.getNik())).thenReturn(true);

        // when & then
        DuplicateException exception = assertThrows(DuplicateException.class,
                () -> customerService.createCustomer(request));

        assertEquals("NIK already exists", exception.getMessage());
        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    // ! SHOULD THROW DUPLICATE WHEN EMAIL ALREADY EXISTS (NEGATIVE PATH)
    @Test
    void should_throw_duplicate_when_email_already_exists() {
        // given
        CreateCustomerRequest request = new CreateCustomerRequest(
                "John Doe", "3171012345678901", "john.doe@example.com", "081234567890"
        );

        when(customerRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // when & then
        DuplicateException exception = assertThrows(DuplicateException.class,
                () -> customerService.createCustomer(request));

        assertEquals("Email already exists", exception.getMessage());
        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    @Test
    void should_delete_customer_successfully() {
        // given
        Long id = 1L;
        when(customerRepository.existsById(id)).thenReturn(true);
        doNothing().when(customerRepository).deleteById(id);

        // when & then
        assertDoesNotThrow(() -> customerService.deleteCustomer(id));

        verify(customerRepository, times(1)).existsById(id);
        verify(customerRepository, times(1)).deleteById(id);
    }

    @Test
    void should_throw_not_found_when_deleting_non_existent_customer() {
        // given
        Long id = 99L;
        when(customerRepository.existsById(id)).thenReturn(false);

        // when & then
        NotFoundException exception = assertThrows(NotFoundException.class, 
                () -> customerService.deleteCustomer(id));

        assertEquals("Customer not found", exception.getMessage());
        verify(customerRepository, times(1)).existsById(id);
        verify(customerRepository, never()).deleteById(anyLong());
    }


    @Test
    void should_update_customer_successfully_when_data_is_valid() {
        // given
        Long id = 1L;
        CreateCustomerRequest request = new CreateCustomerRequest(
                "John Doe Updated", "1234567890123456", "new.email@example.com", "0812999999"
        );

        CustomerEntity existingCustomer = new CustomerEntity();
        existingCustomer.setId(id);
        existingCustomer.setFullName("John Doe");
        existingCustomer.setNik("1234567890123456");
        existingCustomer.setEmail("john.doe@example.com"); 

        when(customerRepository.findById(id)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(existingCustomer);

        // when
        CustomerResponse response = customerService.updateCustomer(id, request);

        // then
        assertNotNull(response);
        assertEquals("John Doe Updated", response.getFullName());
        verify(customerRepository, times(1)).findById(id);
        verify(customerRepository, never()).existsByNik(anyString());
        verify(customerRepository, times(1)).existsByEmail(request.getEmail());
        verify(customerRepository, times(1)).save(any(CustomerEntity.class));
    }

    @Test
    void should_throw_duplicate_when_updating_with_existing_nik() {
        // given
        Long id = 1L;
        CreateCustomerRequest request = new CreateCustomerRequest(
                "John Doe Updated", "3171000000000002", "john.doe@example.com", "0812999999"
        );

        CustomerEntity existingCustomer = new CustomerEntity();
        existingCustomer.setId(id);
        existingCustomer.setFullName("John Doe");
        existingCustomer.setNik("1234567890123456");

        when(customerRepository.findById(id)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.existsByNik(request.getNik())).thenReturn(true);

        // when & then
        DuplicateException exception = assertThrows(DuplicateException.class, 
                () -> customerService.updateCustomer(id, request));

        assertEquals("NIK already exists", exception.getMessage());
        verify(customerRepository, times(1)).findById(id);
        verify(customerRepository, times(1)).existsByNik(request.getNik());
        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    @Test
    void should_return_matching_customers_when_searching_by_valid_name() {
        // given
        String keyword = "Davin";
        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);
        customer.setFullName("Davin Bennett");

        when(customerRepository.findByFullNameContainingIgnoreCase(keyword)).thenReturn(List.of(customer));

        // when
        List<CustomerResponse> responses = customerService.searchByName(keyword);

        // then
        assertFalse(responses.isEmpty());
        assertEquals(1, responses.size());
        assertEquals("Davin Bennett", responses.get(0).getFullName());
        verify(customerRepository, times(1)).findByFullNameContainingIgnoreCase(keyword);
    }

    @Test
    void should_return_empty_list_instantly_when_search_name_is_blank() {
        // when
        List<CustomerResponse> responses = customerService.searchByName("   ");

        // then
        assertTrue(responses.isEmpty());
        verifyNoInteractions(customerRepository);
    }

    @Test
    void should_throw_not_found_when_search_result_is_empty() {
        // given
        String keyword = "GhostName";
        when(customerRepository.findByFullNameContainingIgnoreCase(keyword)).thenReturn(List.of());

        // when & then
        NotFoundException exception = assertThrows(NotFoundException.class, 
                () -> customerService.searchByName(keyword));

        assertTrue(exception.getMessage().contains("not found"));
        verify(customerRepository, times(1)).findByFullNameContainingIgnoreCase(keyword);
    }

    @Test
    void should_throw_duplicate_exception_when_updating_customer_with_existing_email() {
        // given
        Long customerId = 1L;
        CreateCustomerRequest request = new CreateCustomerRequest(
                "Davin Bennett", 
                "3171012345678901",   
                "duplicate.email@example.com", 
                "081234567890"
        );

        CustomerEntity existingCustomer = new CustomerEntity();
        existingCustomer.setId(customerId);
        existingCustomer.setFullName("Davin Old Name");
        existingCustomer.setNik("3171012345678901");  
        existingCustomer.setEmail("davin.old@example.com");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // when & then
        DuplicateException exception = assertThrows(DuplicateException.class, () -> {
            customerService.updateCustomer(customerId, request);
        });

        assertEquals("Email already exists", exception.getMessage());
        verify(customerRepository, times(1)).findById(customerId);
        verify(customerRepository, never()).existsByNik(anyString());
        verify(customerRepository, times(1)).existsByEmail(request.getEmail());
        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    // @Test
    // void should_patch_only_provided_fields_successfully() {
    //     // given
    //     Long id = 1L;
    //     PatchCustomerRequest request = new PatchCustomerRequest();
    //     request.setFullName("Only Update This Name");

    //     CustomerEntity existingCustomer = new CustomerEntity();
    //     existingCustomer.setId(id);
    //     existingCustomer.setFullName("Old Name");
    //     existingCustomer.setNik("1234567890123456");
    //     existingCustomer.setEmail("old.email@example.com");

    //     when(customerRepository.findById(id)).thenReturn(Optional.of(existingCustomer));
    //     when(customerRepository.save(any(CustomerEntity.class))).thenReturn(existingCustomer);

    //     // when
    //     CustomerResponse response = customerService.patchCustomer(id, request);

    //     // then
    //     assertNotNull(response);
    //     assertEquals("Only Update This Name", response.getFullName());
    //     assertEquals("1234567890123456", existingCustomer.getNik());
    //     verify(customerRepository, times(1)).findById(id);
    //     verify(customerRepository, never()).existsByNik(anyString());
    //     verify(customerRepository, never()).existsByEmail(anyString());
    //     verify(customerRepository, times(1)).save(any(CustomerEntity.class));
    // }
}