package com.example.demoSpringbootDatabase.service;

import com.example.demoSpringbootDatabase.dto.CreateCustomerRequest;
import com.example.demoSpringbootDatabase.dto.CustomerResponse;
import com.example.demoSpringbootDatabase.entity.CustomerEntity;
import com.example.demoSpringbootDatabase.exception.CustomerNotFoundException;
import com.example.demoSpringbootDatabase.exception.DuplicateCustomerException;
import com.example.demoSpringbootDatabase.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private CreateCustomerRequest createRequest;
    private CustomerEntity customerEntity;

    @BeforeEach
    void setUp() {
        // ✅ Mengisi data request agar validasi Mockito .existsBy berjalan akurat
        createRequest = new CreateCustomerRequest();
        createRequest.setFullName("Budi Santoso");
        createRequest.setNik("3275010203040005");
        createRequest.setEmail("budi@mail.com");
        createRequest.setPhoneNumber("08123456789");

        customerEntity = CustomerEntity.builder()
                .id(1L)
                .fullName("Budi Santoso")
                .nik("3275010203040005")
                .email("budi@mail.com")
                .phoneNumber("08123456789")
                .build();
    }

    // --- SECTION 1: CREATE CUSTOMER ---

    @Test
    @DisplayName("Happy Path: Create Customer Sukses")
    void Test_createCustomer_HappyPath_Success() {
        // Given
        when(customerRepository.existsByNik(createRequest.getNik())).thenReturn(false);
        when(customerRepository.existsByEmail(createRequest.getEmail())).thenReturn(false);
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity);

        // When
        CustomerResponse response = customerService.createCustomer(createRequest);

        // Then
        assertNotNull(response);
        assertEquals(createRequest.getFullName(), response.getFullName());
        assertEquals(createRequest.getNik(), response.getNik());
        verify(customerRepository, times(1)).save(any(CustomerEntity.class));
    }

    @Test
    @DisplayName("Negative Path: Create Customer Gagal karena NIK Duplikat")
    void Test_createCustomer_NegativePath_DuplicateNik() {
        // Given
        when(customerRepository.existsByNik(createRequest.getNik())).thenReturn(true);

        // When & Then
        assertThrows(DuplicateCustomerException.class, () -> customerService.createCustomer(createRequest));
        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    @Test
    @DisplayName("Negative Path: Create Customer Gagal karena Email Duplikat")
    void Test_createCustomer_NegativePath_DuplicateEmail() {
        // Given
        when(customerRepository.existsByNik(createRequest.getNik())).thenReturn(false);
        when(customerRepository.existsByEmail(createRequest.getEmail())).thenReturn(true);

        // When & Then
        assertThrows(DuplicateCustomerException.class, () -> customerService.createCustomer(createRequest));
        verify(customerRepository, never()).save(any(CustomerEntity.class));
    }

    // --- SECTION 2: READ OPERATIONS ---

    @Test
    @DisplayName("Happy Path: Get Customer By ID Ditemukan")
    void Test_getById_HappyPath_Success() {
        // Given
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customerEntity));

        // When
        CustomerResponse response = customerService.getById(1L);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    @DisplayName("Negative Path: Get Customer By ID Tidak Ditemukan")
    void Test_getById_NegativePath_NotFound() {
        // Given
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CustomerNotFoundException.class, () -> customerService.getById(99L));
    }

    @Test
    @DisplayName("Happy Path: Menarik Seluruh Daftar Customer")
    void Test_getAll_HappyPath_Success() {
        // Given
        when(customerRepository.findAll()).thenReturn(List.of(customerEntity));

        // When
        List<CustomerResponse> responses = customerService.getAll();

        // Then
        assertNotNull(responses);
        assertEquals(1, responses.size());
    }

    @Test
    @DisplayName("Happy Path: Pencarian Nama Customer")
    void Test_searchByName_HappyPath_Success() {
        // Given
        when(customerRepository.findByFullNameContainingIgnoreCase("Budi")).thenReturn(List.of(customerEntity));

        // When
        List<CustomerResponse> responses = customerService.searchByName("Budi");

        // Then
        assertNotNull(responses);
        assertEquals(1, responses.size());
    }

    // --- SECTION 3: DELETE OPERATIONS & SECURITY ---

    @Test
    @DisplayName("Happy Path: Hapus Customer Sukses")
    void Test_deleteCustomer_HappyPath_Success() {
        // Given
        when(customerRepository.existsById(1L)).thenReturn(true);

        // When
        customerService.deleteCustomer(1L);

        // Then
        verify(customerRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Negative Path: Hapus Customer Gagal karena ID Tidak Ada")
    void Test_deleteCustomer_NegativePath_NotFound() {
        // Given
        when(customerRepository.existsById(99L)).thenReturn(false);

        // When & Then
        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(99L));
        verify(customerRepository, never()).deleteById(anyLong());
    }

    @Test
    @DisplayName("Behavior 403: Simulasi Forbidden Access saat Hapus Data Tanpa Hak Akses")
    void Test_deleteCustomer_Behavior403_Forbidden() {
        // Given
        when(customerRepository.existsById(1L)).thenReturn(true);
        
        doAnswer(invocation -> {
            throw new SecurityException("Access Denied: Insufficient Privileges");
        }).when(customerRepository).deleteById(1L);

        // When & Then
        SecurityException exception = assertThrows(SecurityException.class, () -> customerService.deleteCustomer(1L));
        assertTrue(exception.getMessage().contains("Access Denied"));
    }

    // --- SECTION 4: EDGE CASE UTILITY (MASKING) ---

    @Test
    @DisplayName("Edge Case Path: Proteksi Masking saat Data Pendek atau Null")
    void Test_maskData_EdgeCases() {
        // Menguji kondisi internal maskData saat panjang data <= visibleChars via method createCustomer
        CreateCustomerRequest shortRequest = new CreateCustomerRequest();
        shortRequest.setFullName("Short");
        shortRequest.setNik("12"); // Sangat pendek (< 4)
        shortRequest.setEmail("short@mail.com");
        shortRequest.setPhoneNumber(null); // Bernilai Null

        when(customerRepository.existsByNik("12")).thenReturn(false);
        when(customerRepository.existsByEmail("short@mail.com")).thenReturn(false);
        
        CustomerEntity shortEntity = CustomerEntity.builder().id(2L).fullName("Short").build();
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(shortEntity);

        // Memicu maskData internal untuk data pendek & null
        assertDoesNotThrow(() -> customerService.createCustomer(shortRequest));
    }
}