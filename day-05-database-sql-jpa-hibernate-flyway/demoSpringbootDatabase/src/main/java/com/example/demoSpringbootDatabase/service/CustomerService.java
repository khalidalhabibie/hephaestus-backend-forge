package com.example.demoSpringbootDatabase.service;

    import com.example.demoSpringbootDatabase.dto.CreateCustomerRequest;
import com.example.demoSpringbootDatabase.dto.CustomerResponse;
import com.example.demoSpringbootDatabase.entity.CustomerEntity;
import com.example.demoSpringbootDatabase.exception.CustomerNotFoundException;
import com.example.demoSpringbootDatabase.exception.DuplicateCustomerException;
import com.example.demoSpringbootDatabase.repository.CustomerRepository;


import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    
    public CustomerService(CustomerRepository customerRepository) { 
        this.customerRepository = customerRepository; 
    }

    @Transactional
    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        // Validasi Duplikasi NIK
        if (customerRepository.existsByNik(request.getNik())) {
            log.warn("{\"event\":\"VALIDATION_ERROR\", \"message\":\"NIK already registered\"}");
            throw new DuplicateCustomerException("NIK already registered");
        }
        
        // Validasi Duplikasi Email
        if (customerRepository.existsByEmail(request.getEmail())) {
            log.warn("{\"event\":\"VALIDATION_ERROR\", \"message\":\"Email already registered\"}");
            throw new DuplicateCustomerException("Email already registered");
        }

        CustomerEntity customer = CustomerEntity.builder()
                .fullName(request.getFullName())
                .nik(request.getNik())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .build();
                
        customerRepository.save(customer);

        // ✅ STRUCTURED LOG & PII MASKING (Hanya mencetak data aman setelah berhasil dibuat)
        String maskedNik = maskData(request.getNik(), 4);
        String maskedPhone = maskData(request.getPhoneNumber(), 3);
        log.info("{\"event\":\"CUSTOMER_CREATED\", \"customer_id\":{}, \"name\":\"{}\", \"nik\":\"{}\", \"phone\":\"{}\"}",
                customer.getId(), customer.getFullName(), maskedNik, maskedPhone);
        
        return mapToResponse(customer);
    }

    @Transactional(readOnly = true)
    public CustomerResponse getById(Long id) {
        return customerRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> {
                    // Log error hanya jika ada anomali bisnis (pencarian ID yang tidak eksis)
                    log.error("{\"event\":\"UNEXPECTED_ERROR\", \"message\":\"Customer with ID {} not found\"}", id);
                    return new CustomerNotFoundException(id);
                });
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getAll() {
        // Log tingkat get-all/search dipindahkan ke DEBUG agar tidak memenuhi berkas log produksi
        log.debug("Fetching all active customers from database");
        return customerRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> searchByName(String name) {
        log.debug("Searching customer by name: {}", name);
        return customerRepository.findByFullNameContainingIgnoreCase(name).stream().map(this::mapToResponse).toList();
    }

    @Transactional
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            log.error("{\"event\":\"UNEXPECTED_ERROR\", \"message\":\"Failed to delete customer. ID {} not found\"}", id);
            throw new CustomerNotFoundException(id);
        }
        
        customerRepository.deleteById(id); 
        
        // Menggunakan log terstruktur untuk pelacakan penghapusan entitas
        log.info("{\"event\":\"CUSTOMER_DELETED\", \"customer_id\":{}}", id);
    }

    /**
     * ✅ Helper untuk menyamarkan PII (Personally Identifiable Information) Data
     */
    private String maskData(String data, int visibleChars) {
        if (data == null || data.length() <= visibleChars) return "******";
        return data.substring(0, visibleChars) + "******" + data.substring(data.length() - visibleChars);
    }

    private CustomerResponse mapToResponse(CustomerEntity entity) {
        return CustomerResponse.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .nik(entity.getNik())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .build();
    }
}