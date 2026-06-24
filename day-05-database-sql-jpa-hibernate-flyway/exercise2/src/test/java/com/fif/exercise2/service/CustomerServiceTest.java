package com.fif.exercise2.service;

import com.fif.exercise2.dto.CreateCustomerRequest;
import com.fif.exercise2.dto.CustomerResponse;
import com.fif.exercise2.entity.CustomerEntity;
import com.fif.exercise2.exception.CustomerNotFoundException;
import com.fif.exercise2.exception.DuplicateCustomerException;
import com.fif.exercise2.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // aktifkan Mockito tanpa Spring context — lebih cepat
class CustomerServiceTest {

    // @Mock buat objek tiruan (tidak konek ke database sungguhan)
    // @InjectMocks buat objek service asli, lalu inject semua @Mock ke dalamnya
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    // ==========================================================================
    // createCustomer
    // ==========================================================================

    @Test
    void should_create_customer_successfully() {
        // given — siapkan input dan mock behaviour repository
        CreateCustomerRequest request = buildRequest("Budi Santoso", "3273012345678901", "budi@example.com", "081234567890");

        // existsByNik dan existsByEmail return false → tidak ada duplikat
        when(customerRepository.existsByNik("3273012345678901")).thenReturn(false);
        when(customerRepository.existsByEmail("budi@example.com")).thenReturn(false);

        // save() dipanggil → kembalikan entity dengan ID yang sudah di-set
        CustomerEntity saved = buildEntity(1L, "Budi Santoso", "3273012345678901", "budi@example.com", "081234567890");
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(saved);

        // when — jalankan method yang di-test
        CustomerResponse response = customerService.createCustomer(request);

        // then — verifikasi hasil
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Budi Santoso", response.getFullName());
        assertEquals("budi@example.com", response.getEmail());

        // pastikan save() benar-benar dipanggil 1x
        verify(customerRepository, times(1)).save(any(CustomerEntity.class));
    }

    @Test
    void should_throw_exception_when_nik_already_exists() {
        // given — NIK sudah ada di database
        CreateCustomerRequest request = buildRequest("Budi", "3273012345678901",
                "budi@example.com", "081234567890");
        when(customerRepository.existsByNik("3273012345678901")).thenReturn(true);

        // when & then — harus lempar DuplicateCustomerException
        assertThrows(DuplicateCustomerException.class,
                () -> customerService.createCustomer(request));

        // save() TIDAK boleh dipanggil karena sudah gagal di validasi NIK
        verify(customerRepository, never()).save(any());
    }

    @Test
    void should_throw_exception_when_email_already_exists() {
        // given — NIK belum ada, tapi email sudah terdaftar
        CreateCustomerRequest request = buildRequest("Budi", "3273012345678901",
                "budi@example.com", "081234567890");
        when(customerRepository.existsByNik("3273012345678901")).thenReturn(false);
        when(customerRepository.existsByEmail("budi@example.com")).thenReturn(true);

        // when & then
        assertThrows(DuplicateCustomerException.class,
                () -> customerService.createCustomer(request));

        verify(customerRepository, never()).save(any());
    }

    // ==========================================================================
    // getCustomerById
    // ==========================================================================

    @Test
    void should_get_customer_by_id_successfully() {
        // given
        CustomerEntity entity = buildEntity(1L, "Budi Santoso", "3273012345678901",
                "budi@example.com", "081234567890");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(entity));

        // when
        CustomerResponse response = customerService.getCustomerById(1L);

        // then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Budi Santoso", response.getFullName());
    }

    @Test
    void should_throw_not_found_when_customer_does_not_exist() {
        // given — repository tidak menemukan data
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(CustomerNotFoundException.class,
                () -> customerService.getCustomerById(99L));
    }

    // ==========================================================================
    // getAllCustomers
    // ==========================================================================

    @Test
    void should_return_all_customers() {
        // given — ada 2 customer di database
        List<CustomerEntity> entities = List.of(
                buildEntity(1L, "Budi", "1111111111111111", "budi@example.com", "081111111111"),
                buildEntity(2L, "Sari", "2222222222222222", "sari@example.com", "082222222222")
        );
        when(customerRepository.findAll()).thenReturn(entities);

        // when
        List<CustomerResponse> responses = customerService.getAllCustomers();

        // then
        assertEquals(2, responses.size());
        assertEquals("Budi", responses.get(0).getFullName());
        assertEquals("Sari", responses.get(1).getFullName());
    }

    @Test
    void should_return_empty_list_when_no_customers_exist() {
        // given
        when(customerRepository.findAll()).thenReturn(List.of());

        // when
        List<CustomerResponse> responses = customerService.getAllCustomers();

        // then — tidak error, hanya kembalikan list kosong
        assertNotNull(responses);
        assertTrue(responses.isEmpty());
    }

    // ==========================================================================
    // searchByName
    // ==========================================================================

    @Test
    void should_return_customers_matching_name_search() {
        // given
        List<CustomerEntity> entities = List.of(
                buildEntity(1L, "Budi Santoso", "1111111111111111", "budi@example.com", "081111111111")
        );
        when(customerRepository.findByFullNameContainingIgnoreCase("budi")).thenReturn(entities);

        // when
        List<CustomerResponse> responses = customerService.searchByName("budi");

        // then
        assertEquals(1, responses.size());
        assertEquals("Budi Santoso", responses.get(0).getFullName());
    }

    @Test
    void should_return_empty_list_when_no_customer_matches_name_search() {
        // given — tidak ada customer dengan nama tersebut
        when(customerRepository.findByFullNameContainingIgnoreCase("xyz")).thenReturn(List.of());

        // when
        List<CustomerResponse> responses = customerService.searchByName("xyz");

        // then
        assertTrue(responses.isEmpty());
    }

    // ==========================================================================
    // softDeleteCustomer
    // ==========================================================================

    @Test
    void should_soft_delete_customer_successfully() {
        // given
        CustomerEntity entity = buildEntity(1L, "Budi", "1111111111111111",
                "budi@example.com", "081111111111");
        when(customerRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(customerRepository.save(any(CustomerEntity.class))).thenReturn(entity);

        // when
        customerService.softDeleteCustomer(1L);

        // then — deletedAt harus terisi dan save dipanggil
        assertNotNull(entity.getDeletedAt());
        verify(customerRepository, times(1)).save(entity);
    }

    @Test
    void should_throw_not_found_when_deleting_nonexistent_customer() {
        // given
        when(customerRepository.findById(99L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(CustomerNotFoundException.class,
                () -> customerService.softDeleteCustomer(99L));

        verify(customerRepository, never()).save(any());
    }

    // ==========================================================================
    // Helpers — builder method agar test lebih ringkas dan mudah dibaca
    // ==========================================================================

    private CreateCustomerRequest buildRequest(String fullName, String nik,String email, String phone) {
        CreateCustomerRequest req = new CreateCustomerRequest();
        req.setFullName(fullName);
        req.setNik(nik);
        req.setEmail(email);
        req.setPhoneNumber(phone);
        return req;
    }

    private CustomerEntity buildEntity(Long id, String fullName, String nik,String email, String phone) {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(id);
        entity.setFullName(fullName);
        entity.setNik(nik);
        entity.setEmail(email);
        entity.setPhoneNumber(phone);
        entity.setCreatedAt(ZonedDateTime.now());
        entity.setUpdatedAt(ZonedDateTime.now());
        return entity;
    }
}
