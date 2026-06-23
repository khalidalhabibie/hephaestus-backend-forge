package com.example.spring_boot_database.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;

import com.example.spring_boot_database.dto.CreateCustomerRequest;
import com.example.spring_boot_database.dto.CustomerResponse;
import com.example.spring_boot_database.dto.LoanApplicationResponse;
import com.example.spring_boot_database.dto.OutstandingAmountResponse;
import com.example.spring_boot_database.entity.CustomerEntity;
import com.example.spring_boot_database.entity.LoanApplicationEntity;
import com.example.spring_boot_database.entity.Status;
import com.example.spring_boot_database.exception.CustomerNotFoundException;
import com.example.spring_boot_database.exception.DuplicateCustomerException;
import com.example.spring_boot_database.repository.CustomerRepository;
import com.example.spring_boot_database.repository.LoanApplicationRepository;
import com.example.spring_boot_database.repository.RepaymentScheduleRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private LoanApplicationRepository loanApplicationRepository;

    @Mock
    private RepaymentScheduleRepository repaymentScheduleRepository;

    @InjectMocks
    private CustomerService customerService;

    private static final Long CUSTOMER_ID = 1L;
    private static final String NIK = "3173010101010001";
    private static final String FULL_NAME = "Budi Santoso";
    private static final String EMAIL = "budi@example.com";
    private static final String PHONE_NUMBER = "081234567890";

    private CreateCustomerRequest createRequest() {
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setNik(NIK);
        request.setFullName(FULL_NAME);
        request.setEmail(EMAIL);
        request.setPhoneNumber(PHONE_NUMBER);
        return request;
    }

    private CustomerEntity createCustomerEntity() {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(CUSTOMER_ID);
        entity.setNik(NIK);
        entity.setFullName(FULL_NAME);
        entity.setEmail(EMAIL);
        entity.setPhoneNumber(PHONE_NUMBER);
        return entity;
    }

    private CustomerEntity createCustomerEntity(
            Long id,
            String nik,
            String fullName,
            String email,
            String phoneNumber
    ) {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(id);
        entity.setNik(nik);
        entity.setFullName(fullName);
        entity.setEmail(email);
        entity.setPhoneNumber(phoneNumber);
        return entity;
    }

    @Nested
    @DisplayName("createCustomer")
    class CreateCustomerTests {

        @Test
        @DisplayName("Should create customer successfully when NIK and email are unique")
        void testCreateCustomer_success() {
            CreateCustomerRequest request = createRequest();
            CustomerEntity savedEntity = createCustomerEntity();

            when(customerRepository.existsByNikAndDeletedAtIsNull(NIK)).thenReturn(false);
            when(customerRepository.existsByEmailAndDeletedAtIsNull(EMAIL)).thenReturn(false);
            when(customerRepository.save(any(CustomerEntity.class))).thenReturn(savedEntity);

            CustomerResponse response = customerService.createCustomer(request);

            assertAll(
                    () -> assertNotNull(response),
                    () -> assertEquals(CUSTOMER_ID, response.getId()),
                    () -> assertEquals(NIK, response.getNik()),
                    () -> assertEquals(FULL_NAME, response.getFullName()),
                    () -> assertEquals(EMAIL, response.getEmail()),
                    () -> assertEquals(PHONE_NUMBER, response.getPhoneNumber())
            );

            ArgumentCaptor<CustomerEntity> entityCaptor = ArgumentCaptor.forClass(CustomerEntity.class);
            verify(customerRepository).existsByNikAndDeletedAtIsNull(NIK);
            verify(customerRepository).existsByEmailAndDeletedAtIsNull(EMAIL);
            verify(customerRepository).save(entityCaptor.capture());

            CustomerEntity entityToSave = entityCaptor.getValue();

            assertAll(
                    () -> assertNull(entityToSave.getId()),
                    () -> assertEquals(NIK, entityToSave.getNik()),
                    () -> assertEquals(FULL_NAME, entityToSave.getFullName()),
                    () -> assertEquals(EMAIL, entityToSave.getEmail()),
                    () -> assertEquals(PHONE_NUMBER, entityToSave.getPhoneNumber()),
                    () -> assertNull(entityToSave.getDeletedAt())
            );
        }

        @Test
        @DisplayName("Should throw DuplicateCustomerException when NIK already exists")
        void testCreateCustomer_duplicateNik() {
            CreateCustomerRequest request = createRequest();

            when(customerRepository.existsByNikAndDeletedAtIsNull(NIK)).thenReturn(true);

            DuplicateCustomerException exception = assertThrows(
                    DuplicateCustomerException.class,
                    () -> customerService.createCustomer(request)
            );

            assertNotNull(exception);

            verify(customerRepository).existsByNikAndDeletedAtIsNull(NIK);
            verify(customerRepository, never()).existsByEmailAndDeletedAtIsNull(any());
            verify(customerRepository, never()).save(any());
        }
    }

    @Nested
    @DisplayName("getById")
    class GetByIdTests {

        @Test
        @DisplayName("Should return customer entity when active customer exists")
        void testGetById_success() {
            CustomerEntity customer = createCustomerEntity();

            when(customerRepository.findByIdAndDeletedAtIsNull(CUSTOMER_ID))
                    .thenReturn(Optional.of(customer));

            CustomerEntity result = customerService.getById(CUSTOMER_ID);

            assertSame(customer, result);
            verify(customerRepository).findByIdAndDeletedAtIsNull(CUSTOMER_ID);
        }

        @Test
        @DisplayName("Should throw CustomerNotFoundException when customer does not exist or is deleted")
        void testGetById_notFound() {
            when(customerRepository.findByIdAndDeletedAtIsNull(CUSTOMER_ID))
                    .thenReturn(Optional.empty());

            CustomerNotFoundException exception = assertThrows(
                    CustomerNotFoundException.class,
                    () -> customerService.getById(CUSTOMER_ID)
            );

            assertNotNull(exception);
            verify(customerRepository).findByIdAndDeletedAtIsNull(CUSTOMER_ID);
        }
    }

    @Nested
    @DisplayName("findById")
    class FindByIdTests {

        @Test
        @DisplayName("Should return customer response when customer exists")
        void testFindById_success() {
            CustomerEntity customer = createCustomerEntity();

            when(customerRepository.findByIdAndDeletedAtIsNull(CUSTOMER_ID))
                    .thenReturn(Optional.of(customer));

            CustomerResponse response = customerService.findById(CUSTOMER_ID);

            assertAll(
                    () -> assertNotNull(response),
                    () -> assertEquals(CUSTOMER_ID, response.getId()),
                    () -> assertEquals(NIK, response.getNik()),
                    () -> assertEquals(FULL_NAME, response.getFullName()),
                    () -> assertEquals(EMAIL, response.getEmail()),
                    () -> assertEquals(PHONE_NUMBER, response.getPhoneNumber())
            );

            verify(customerRepository).findByIdAndDeletedAtIsNull(CUSTOMER_ID);
        }

        @Test
        @DisplayName("Should throw CustomerNotFoundException when customer does not exist")
        void testFindById_notFound() {
            when(customerRepository.findByIdAndDeletedAtIsNull(CUSTOMER_ID))
                    .thenReturn(Optional.empty());

            assertThrows(
                    CustomerNotFoundException.class,
                    () -> customerService.findById(CUSTOMER_ID)
            );

            verify(customerRepository).findByIdAndDeletedAtIsNull(CUSTOMER_ID);
        }
    }

    @Nested
    @DisplayName("findCustomer")
    class FindCustomerTests {

        @Test
        @DisplayName("Should search customer by name when name is provided")
        void testFindCustomer_searchByName_success() {
            String keyword = "budi";

            CustomerEntity customer1 = createCustomerEntity(
                    1L,
                    "3173010101010001",
                    "Budi Santoso",
                    "budi@example.com",
                    "081111111111"
            );

            CustomerEntity customer2 = createCustomerEntity(
                    2L,
                    "3173010101010002",
                    "Budi Hartono",
                    "hartono@example.com",
                    "082222222222"
            );

            when(customerRepository.findByFullNameContainingIgnoreCaseAndDeletedAtIsNull(keyword))
                    .thenReturn(List.of(customer1, customer2));

            List<CustomerResponse> responses = customerService.findCustomer(keyword);

            assertAll(
                    () -> assertNotNull(responses),
                    () -> assertEquals(2, responses.size()),
                    () -> assertEquals("Budi Santoso", responses.get(0).getFullName()),
                    () -> assertEquals("Budi Hartono", responses.get(1).getFullName())
            );

            verify(customerRepository).findByFullNameContainingIgnoreCaseAndDeletedAtIsNull(keyword);
            verify(customerRepository, never()).findByDeletedAtIsNull();
        }

        @Test
        @DisplayName("Should return empty list when search by name has no result")
        void testFindCustomer_searchByName_empty() {
            String keyword = "unknown";

            when(customerRepository.findByFullNameContainingIgnoreCaseAndDeletedAtIsNull(keyword))
                    .thenReturn(List.of());

            List<CustomerResponse> responses = customerService.findCustomer(keyword);

            assertAll(
                    () -> assertNotNull(responses),
                    () -> assertEquals(0, responses.size())
            );

            verify(customerRepository).findByFullNameContainingIgnoreCaseAndDeletedAtIsNull(keyword);
            verify(customerRepository, never()).findByDeletedAtIsNull();
        }

        @Test
        @DisplayName("Should return all active customers when name is null")
        void testFindCustomer_nameNull_success() {
            CustomerEntity customer = createCustomerEntity();

            when(customerRepository.findByDeletedAtIsNull())
                    .thenReturn(List.of(customer));

            List<CustomerResponse> responses = customerService.findCustomer(null);

            assertAll(
                    () -> assertNotNull(responses),
                    () -> assertEquals(1, responses.size()),
                    () -> assertEquals(CUSTOMER_ID, responses.get(0).getId()),
                    () -> assertEquals(FULL_NAME, responses.get(0).getFullName())
            );

            verify(customerRepository).findByDeletedAtIsNull();
            verify(customerRepository, never())
                    .findByFullNameContainingIgnoreCaseAndDeletedAtIsNull(any());
        }

        @Test
        @DisplayName("Should return all active customers when name is blank")
        void testFindCustomer_nameBlank_success() {
            when(customerRepository.findByDeletedAtIsNull())
                    .thenReturn(List.of());

            List<CustomerResponse> responses = customerService.findCustomer("   ");

            assertAll(
                    () -> assertNotNull(responses),
                    () -> assertEquals(0, responses.size())
            );

            verify(customerRepository).findByDeletedAtIsNull();
            verify(customerRepository, never())
                    .findByFullNameContainingIgnoreCaseAndDeletedAtIsNull(any());
        }
    }

    @Nested
    @DisplayName("findLoanByCustomer")
    class FindLoanByCustomerTests {

        @Test
        @DisplayName("Should return loan responses when customer exists and has loans")
        void testFindLoanByCustomer_success() {
            CustomerEntity customer = createCustomerEntity();

            Status expectedStatus = Status.values()[0];

            LoanApplicationEntity loan = new LoanApplicationEntity();
            loan.setLoanAmount(new BigDecimal("10000000"));
            loan.setTenorMonth(12);
            loan.setPurpose("Modal usaha");
            loan.setStatus(expectedStatus.name());
            loan.setCustomer(customer);

            when(customerRepository.findByIdAndDeletedAtIsNull(CUSTOMER_ID))
                    .thenReturn(Optional.of(customer));
            when(loanApplicationRepository.findLoansByCustomerId(CUSTOMER_ID))
                    .thenReturn(List.of(loan));

            List<LoanApplicationResponse> responses =
                    customerService.findLoanByCustomer(CUSTOMER_ID);

            assertAll(
                    () -> assertNotNull(responses),
                    () -> assertEquals(1, responses.size()),
                    () -> assertEquals(new BigDecimal("10000000"), responses.get(0).getLoanAmount()),
                    () -> assertEquals(12, responses.get(0).getTenorMonth()),
                    () -> assertEquals("Modal usaha", responses.get(0).getPurpose()),
                    () -> assertEquals(expectedStatus, responses.get(0).getStatus()),
                    () -> assertNotNull(responses.get(0).getCustomer()),
                    () -> assertEquals(CUSTOMER_ID, responses.get(0).getCustomer().getId()),
                    () -> assertEquals(FULL_NAME, responses.get(0).getCustomer().getFullName())
            );

            verify(customerRepository).findByIdAndDeletedAtIsNull(CUSTOMER_ID);
            verify(loanApplicationRepository).findLoansByCustomerId(CUSTOMER_ID);
        }

        @Test
        @DisplayName("Should return empty list when customer exists but has no loans")
        void testFindLoanByCustomer_emptyLoans() {
            CustomerEntity customer = createCustomerEntity();

            when(customerRepository.findByIdAndDeletedAtIsNull(CUSTOMER_ID))
                    .thenReturn(Optional.of(customer));
            when(loanApplicationRepository.findLoansByCustomerId(CUSTOMER_ID))
                    .thenReturn(List.of());

            List<LoanApplicationResponse> responses =
                    customerService.findLoanByCustomer(CUSTOMER_ID);

            assertAll(
                    () -> assertNotNull(responses),
                    () -> assertEquals(0, responses.size())
            );

            verify(customerRepository).findByIdAndDeletedAtIsNull(CUSTOMER_ID);
            verify(loanApplicationRepository).findLoansByCustomerId(CUSTOMER_ID);
        }

        @Test
        @DisplayName("Should throw CustomerNotFoundException and not query loans when customer does not exist")
        void testFindLoanByCustomer_customerNotFound() {
            when(customerRepository.findByIdAndDeletedAtIsNull(CUSTOMER_ID))
                    .thenReturn(Optional.empty());

            assertThrows(
                    CustomerNotFoundException.class,
                    () -> customerService.findLoanByCustomer(CUSTOMER_ID)
            );

            verify(customerRepository).findByIdAndDeletedAtIsNull(CUSTOMER_ID);
            verifyNoInteractions(loanApplicationRepository);
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when loan status is invalid")
        void testFindLoanByCustomer_invalidLoanStatus() {
            CustomerEntity customer = createCustomerEntity();

            LoanApplicationEntity loan = new LoanApplicationEntity();
            loan.setLoanAmount(new BigDecimal("5000000"));
            loan.setTenorMonth(6);
            loan.setPurpose("Renovasi rumah");
            loan.setStatus("INVALID_STATUS");
            loan.setCustomer(customer);

            when(customerRepository.findByIdAndDeletedAtIsNull(CUSTOMER_ID))
                    .thenReturn(Optional.of(customer));
            when(loanApplicationRepository.findLoansByCustomerId(CUSTOMER_ID))
                    .thenReturn(List.of(loan));

            assertThrows(
                    IllegalArgumentException.class,
                    () -> customerService.findLoanByCustomer(CUSTOMER_ID)
            );

            verify(customerRepository).findByIdAndDeletedAtIsNull(CUSTOMER_ID);
            verify(loanApplicationRepository).findLoansByCustomerId(CUSTOMER_ID);
        }
    }

    @Nested
    @DisplayName("getOutstandingAmountByCustomer")
    class GetOutstandingAmountByCustomerTests {

        @Test
        @DisplayName("Should return outstanding amount when calculated amount is positive")
        void testGetOutstandingAmountByCustomer_positiveAmount() {
            CustomerEntity customer = createCustomerEntity();
            BigDecimal outstandingAmount = new BigDecimal("1500000.50");

            when(customerRepository.findByIdAndDeletedAtIsNull(CUSTOMER_ID))
                    .thenReturn(Optional.of(customer));
            when(repaymentScheduleRepository.calculateOutstandingAmountByCustomerId(CUSTOMER_ID))
                    .thenReturn(outstandingAmount);

            OutstandingAmountResponse response =
                    customerService.getOutstandingAmountByCustomer(CUSTOMER_ID);

            assertAll(
                    () -> assertNotNull(response),
                    () -> assertEquals(CUSTOMER_ID, response.getCustomerId()),
                    () -> assertEquals(outstandingAmount, response.getOutstandingAmount())
            );

            verify(customerRepository).findByIdAndDeletedAtIsNull(CUSTOMER_ID);
            verify(repaymentScheduleRepository).calculateOutstandingAmountByCustomerId(CUSTOMER_ID);
        }

        @Test
        @DisplayName("Should return zero when repository returns zero outstanding amount")
        void testGetOutstandingAmountByCustomer_zeroAmount() {
            CustomerEntity customer = createCustomerEntity();

            when(customerRepository.findByIdAndDeletedAtIsNull(CUSTOMER_ID))
                    .thenReturn(Optional.of(customer));
            when(repaymentScheduleRepository.calculateOutstandingAmountByCustomerId(CUSTOMER_ID))
                    .thenReturn(BigDecimal.ZERO);

            OutstandingAmountResponse response =
                    customerService.getOutstandingAmountByCustomer(CUSTOMER_ID);

            assertAll(
                    () -> assertNotNull(response),
                    () -> assertEquals(CUSTOMER_ID, response.getCustomerId()),
                    () -> assertEquals(BigDecimal.ZERO, response.getOutstandingAmount())
            );

            verify(customerRepository).findByIdAndDeletedAtIsNull(CUSTOMER_ID);
            verify(repaymentScheduleRepository).calculateOutstandingAmountByCustomerId(CUSTOMER_ID);
        }

        @Test
        @DisplayName("Should return zero when repository returns null outstanding amount")
        void testGetOutstandingAmountByCustomer_nullAmount() {
            CustomerEntity customer = createCustomerEntity();

            when(customerRepository.findByIdAndDeletedAtIsNull(CUSTOMER_ID))
                    .thenReturn(Optional.of(customer));
            when(repaymentScheduleRepository.calculateOutstandingAmountByCustomerId(CUSTOMER_ID))
                    .thenReturn(null);

            OutstandingAmountResponse response =
                    customerService.getOutstandingAmountByCustomer(CUSTOMER_ID);

            assertAll(
                    () -> assertNotNull(response),
                    () -> assertEquals(CUSTOMER_ID, response.getCustomerId()),
                    () -> assertEquals(BigDecimal.ZERO, response.getOutstandingAmount())
            );

            verify(customerRepository).findByIdAndDeletedAtIsNull(CUSTOMER_ID);
            verify(repaymentScheduleRepository).calculateOutstandingAmountByCustomerId(CUSTOMER_ID);
        }

        @Test
        @DisplayName("Should throw CustomerNotFoundException and not calculate outstanding when customer does not exist")
        void testGetOutstandingAmountByCustomer_customerNotFound() {
            when(customerRepository.findByIdAndDeletedAtIsNull(CUSTOMER_ID))
                    .thenReturn(Optional.empty());

            assertThrows(
                    CustomerNotFoundException.class,
                    () -> customerService.getOutstandingAmountByCustomer(CUSTOMER_ID)
            );

            verify(customerRepository).findByIdAndDeletedAtIsNull(CUSTOMER_ID);
            verifyNoInteractions(repaymentScheduleRepository);
        }
    }

    @Nested
    @DisplayName("softDeleteCustomer")
    class SoftDeleteCustomerTests {

        @Test
        @DisplayName("Should soft delete customer successfully")
        void testSoftDeleteCustomer_success() {
            CustomerEntity customer = createCustomerEntity();

            when(customerRepository.findByIdAndDeletedAtIsNull(CUSTOMER_ID))
                    .thenReturn(Optional.of(customer));
            when(customerRepository.save(any(CustomerEntity.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            CustomerResponse response = customerService.softDeleteCustomer(CUSTOMER_ID);

            assertAll(
                    () -> assertNotNull(response),
                    () -> assertEquals(CUSTOMER_ID, response.getId()),
                    () -> assertEquals(FULL_NAME, response.getFullName()),
                    () -> assertEquals(NIK, response.getNik()),
                    () -> assertNotNull(customer.getDeletedAt())
            );

            ArgumentCaptor<CustomerEntity> entityCaptor =
                    ArgumentCaptor.forClass(CustomerEntity.class);

            verify(customerRepository).findByIdAndDeletedAtIsNull(CUSTOMER_ID);
            verify(customerRepository).save(entityCaptor.capture());

            CustomerEntity savedEntity = entityCaptor.getValue();

            assertAll(
                    () -> assertEquals(CUSTOMER_ID, savedEntity.getId()),
                    () -> assertNotNull(savedEntity.getDeletedAt()),
                    () -> assertEquals(NIK, savedEntity.getNik()),
                    () -> assertEquals(FULL_NAME, savedEntity.getFullName())
            );
        }

        @Test
        @DisplayName("Should throw CustomerNotFoundException when customer to delete does not exist")
        void testSoftDeleteCustomer_customerNotFound() {
            when(customerRepository.findByIdAndDeletedAtIsNull(CUSTOMER_ID))
                    .thenReturn(Optional.empty());

            assertThrows(
                    CustomerNotFoundException.class,
                    () -> customerService.softDeleteCustomer(CUSTOMER_ID)
            );

            verify(customerRepository).findByIdAndDeletedAtIsNull(CUSTOMER_ID);
            verify(customerRepository, never()).save(any());
        }

        @Test
        @DisplayName("Should rethrow DataAccessException when soft delete save fails")
        void testSoftDeleteCustomer_databaseError() {
            CustomerEntity customer = createCustomerEntity();
            DataAccessException databaseException =
                    new DataRetrievalFailureException("database error");

            when(customerRepository.findByIdAndDeletedAtIsNull(CUSTOMER_ID))
                    .thenReturn(Optional.of(customer));
            when(customerRepository.save(any(CustomerEntity.class)))
                    .thenThrow(databaseException);

            DataAccessException thrown = assertThrows(
                    DataAccessException.class,
                    () -> customerService.softDeleteCustomer(CUSTOMER_ID)
            );

            assertSame(databaseException, thrown);
            assertNotNull(customer.getDeletedAt());

            verify(customerRepository).findByIdAndDeletedAtIsNull(CUSTOMER_ID);
            verify(customerRepository).save(any(CustomerEntity.class));
        }
    }

    @Nested
    @DisplayName("toResponse")
    class ToResponseTests {

        @Test
        @DisplayName("Should map CustomerEntity to CustomerResponse correctly")
        void testToResponse() {
            CustomerEntity entity = createCustomerEntity();

            CustomerResponse response = customerService.toResponse(entity);

            assertAll(
                    () -> assertNotNull(response),
                    () -> assertEquals(CUSTOMER_ID, response.getId()),
                    () -> assertEquals(NIK, response.getNik()),
                    () -> assertEquals(FULL_NAME, response.getFullName()),
                    () -> assertEquals(EMAIL, response.getEmail()),
                    () -> assertEquals(PHONE_NUMBER, response.getPhoneNumber())
            );
        }

        @Test
        @DisplayName("Should map null fields as null without throwing exception")
        void testToResponse_withNullFields() {
            CustomerEntity entity = new CustomerEntity();

            CustomerResponse response = customerService.toResponse(entity);

            assertAll(
                    () -> assertNotNull(response),
                    () -> assertNull(response.getId()),
                    () -> assertNull(response.getNik()),
                    () -> assertNull(response.getFullName()),
                    () -> assertNull(response.getEmail()),
                    () -> assertNull(response.getPhoneNumber())
            );
        }
    }
}