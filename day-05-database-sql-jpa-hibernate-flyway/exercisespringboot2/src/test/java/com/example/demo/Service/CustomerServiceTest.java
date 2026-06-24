package com.example.demo.Service;

import com.example.training.DTO.CreateCustomerRequest;
import com.example.training.DTO.CustomerResponse;
import com.example.training.Entity.CustomerEntity;
import com.example.training.Exception.CustomerNotFoundException;
import com.example.training.Exception.DuplicateCustomerException;
import com.example.training.Repository.CustomerRepository;
import com.example.training.Service.CustomerService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
@DisplayName("Customer Service Tests")
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    private static final Long CUSTOMER_ID = 1L;
    private static final String FULL_NAME = "John Doe";
    private static final String NIK = "1234567890123456";
    private static final String EMAIL = "john@example.com";
    private static final String PHONE = "081234567890";
    private static final Long NON_EXISTING_ID = 999L;

    private CreateCustomerRequest buildCreateRequest() {
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setFullName(FULL_NAME);
        request.setNik(NIK);
        request.setEmail(EMAIL);
        request.setPhoneNumber(PHONE);
        return request;
    }

    private CustomerEntity buildCustomerEntity(Long id) {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(id);
        entity.setFullName(FULL_NAME);
        entity.setNik(NIK);
        entity.setEmail(EMAIL);
        entity.setPhoneNumber(PHONE);
        entity.setCreatedAt(ZonedDateTime.now());
        entity.setUpdatedAt(ZonedDateTime.now());
        return entity;
    }

    // ========== Nested: Create Customer ==========
    @Nested
    @DisplayName("Create Customer")
    class CreateCustomerTests {

        @Test
        @DisplayName("Given valid request → should create and return customer response")
        void givenValidRequest_shouldCreateAndReturnCustomer() {
            // Given
            CreateCustomerRequest request = buildCreateRequest();
            CustomerEntity savedEntity = buildCustomerEntity(CUSTOMER_ID);

            given(customerRepository.existsByNik(NIK)).willReturn(false);
            given(customerRepository.existsByEmail(EMAIL)).willReturn(false);
            given(customerRepository.save(any(CustomerEntity.class))).willReturn(savedEntity);

            // When
            CustomerResponse result = customerService.create(request);

            // Then
            assertThat(result)
                    .isNotNull()
                    .satisfies(r -> {
                        assertThat(r.getId()).isEqualTo(CUSTOMER_ID);
                        assertThat(r.getFullName()).isEqualTo(FULL_NAME);
                        assertThat(r.getEmail()).isEqualTo(EMAIL);
                        assertThat(r.getPhoneNumber()).isEqualTo(PHONE);
                    });
            then(customerRepository).should().save(any(CustomerEntity.class));
        }

        @Test
        @DisplayName("Given duplicate NIK → should throw DuplicateCustomerException")
        void givenDuplicateNik_shouldThrowDuplicateCustomerException() {
            // Given
            CreateCustomerRequest request = buildCreateRequest();
            given(customerRepository.existsByNik(NIK)).willReturn(true);

            // When & Then
            assertThatThrownBy(() -> customerService.create(request))
                    .isInstanceOf(DuplicateCustomerException.class)
                    .hasMessageContaining("nik");
            then(customerRepository).should(never()).save(any(CustomerEntity.class));
        }

        @Test
        @DisplayName("Given duplicate email → should throw DuplicateCustomerException")
        void givenDuplicateEmail_shouldThrowDuplicateCustomerException() {
            // Given
            CreateCustomerRequest request = buildCreateRequest();
            given(customerRepository.existsByNik(NIK)).willReturn(false);
            given(customerRepository.existsByEmail(EMAIL)).willReturn(true);

            // When & Then
            assertThatThrownBy(() -> customerService.create(request))
                    .isInstanceOf(DuplicateCustomerException.class)
                    .hasMessageContaining("email");
        }
    }

    // ========== Nested: Get Customer ==========
    @Nested
    @DisplayName("Get Customer")
    class GetCustomerTests {

        @Test
        @DisplayName("Given existing ID → should return customer response")
        void givenExistingId_shouldReturnCustomer() {
            // Given
            CustomerEntity entity = buildCustomerEntity(CUSTOMER_ID);
            given(customerRepository.findById(CUSTOMER_ID)).willReturn(Optional.of(entity));

            // When
            CustomerResponse result = customerService.getById(CUSTOMER_ID);

            // Then
            assertThat(result)
                    .isNotNull()
                    .satisfies(r -> {
                        assertThat(r.getId()).isEqualTo(CUSTOMER_ID);
                        assertThat(r.getFullName()).isEqualTo(FULL_NAME);
                        assertThat(r.getNik()).isEqualTo(NIK);
                    });
        }

        @Test
        @DisplayName("Given non-existing ID → should throw CustomerNotFoundException")
        void givenNonExistingId_shouldThrowCustomerNotFoundException() {
            // Given
            given(customerRepository.findById(NON_EXISTING_ID)).willReturn(Optional.empty());

            // When & Then
            assertThatThrownBy(() -> customerService.getById(NON_EXISTING_ID))
                    .isInstanceOf(CustomerNotFoundException.class)
                    .hasMessageContaining(String.valueOf(NON_EXISTING_ID));
        }

        @Test
        @DisplayName("Given customers exist → should return list of all customers")
        void givenCustomersExist_shouldReturnList() {
            // Given
            CustomerEntity customer1 = buildCustomerEntity(1L);
            CustomerEntity customer2 = buildCustomerEntity(2L);
            customer2.setFullName("Jane Doe");
            customer2.setEmail("jane@example.com");

            given(customerRepository.findAll()).willReturn(List.of(customer1, customer2));

            // When
            List<CustomerResponse> result = customerService.getAll();

            // Then
            assertThat(result)
                    .hasSize(2)
                    .extracting(CustomerResponse::getFullName)
                    .containsExactly(FULL_NAME, "Jane Doe");
        }

        @Test
        @DisplayName("Given search name → should return matching customers")
        void givenSearchName_shouldReturnMatchingCustomers() {
            // Given
            String searchName = "John";
            CustomerEntity entity = buildCustomerEntity(CUSTOMER_ID);
            given(customerRepository.findByFullNameContainingIgnoreCase(searchName))
                    .willReturn(List.of(entity));

            // When
            List<CustomerResponse> result = customerService.searchByName(searchName);

            // Then
            assertThat(result)
                    .hasSize(1)
                    .first()
                    .satisfies(r -> assertThat(r.getFullName()).isEqualTo(FULL_NAME));
        }
    }

    // ========== Nested: Delete Customer ==========
    @Nested
    @DisplayName("Delete Customer")
    class DeleteCustomerTests {

        @Test
        @DisplayName("Given existing ID → should delete customer successfully")
        void givenExistingId_shouldDeleteSuccessfully() {
            // Given
            given(customerRepository.existsById(CUSTOMER_ID)).willReturn(true);

            // When
            customerService.delete(CUSTOMER_ID);

            // Then
            then(customerRepository).should().deleteById(CUSTOMER_ID);
        }

        @Test
        @DisplayName("Given non-existing ID → should throw CustomerNotFoundException")
        void givenNonExistingId_shouldThrowCustomerNotFoundException() {
            // Given
            given(customerRepository.existsById(NON_EXISTING_ID)).willReturn(false);

            // When & Then
            assertThatThrownBy(() -> customerService.delete(NON_EXISTING_ID))
                    .isInstanceOf(CustomerNotFoundException.class);
            then(customerRepository).should(never()).deleteById(any());
        }
    }
}