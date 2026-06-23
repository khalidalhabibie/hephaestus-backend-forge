package com.example.training.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.training.DTO.CreateCustomerRequest;
import com.example.training.DTO.CustomerResponse;
import com.example.training.Entity.CustomerEntity;
import com.example.training.Exception.CustomerNotFoundException;
import com.example.training.Exception.DuplicateCustomerException;
import com.example.training.Repository.CustomerRepository;
import com.example.training.Util.LogMaskingUtil;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public CustomerResponse create(CreateCustomerRequest request) {
        log.info("event=customer_creation_requested, nik_masked={}, email_masked={}, phone_masked={}",  
                LogMaskingUtil.maskNik(request.getNik()),
                LogMaskingUtil.maskEmail(request.getEmail()), 
                LogMaskingUtil.maskPhone(request.getPhoneNumber()));

        if (customerRepository.existsByNik(request.getNik())) {
            log.warn("event=customer_creation_failed, reason=duplicate_nik, nik_masked={}", 
                    LogMaskingUtil.maskNik(request.getNik()));
            throw new DuplicateCustomerException("nik", request.getNik());
        }
        if (customerRepository.existsByEmail(request.getEmail())) {
            log.warn("event=customer_creation_failed, reason=duplicate_email, email_masked={}",  
                    LogMaskingUtil.maskEmail(request.getEmail()));  
            throw new DuplicateCustomerException("email", request.getEmail());
        }
        CustomerEntity entity = new CustomerEntity();
        entity.setFullName(request.getFullName());
        entity.setNik(request.getNik());
        entity.setEmail(request.getEmail());
        entity.setPhoneNumber(request.getPhoneNumber());
        CustomerEntity saved = customerRepository.save(entity);

        log.info("event=customer_created, customer_id={}, nik_masked={}, email_masked={}, phone_masked={}",  
                saved.getId(),
                LogMaskingUtil.maskNik(saved.getNik()),
                LogMaskingUtil.maskEmail(saved.getEmail()),  
                LogMaskingUtil.maskPhone(saved.getPhoneNumber()));  

        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public CustomerResponse getById(Long id) {
        log.info("event=customer_fetch_requested, customer_id={}", id);
        
        CustomerEntity entity = customerRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("event=customer_fetch_failed, reason=not_found, customer_id={}", id);  
                    return new CustomerNotFoundException(id);
                });
        
        return mapToResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getAll() {
        log.info("event=customer_list_requested");
        return customerRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> searchByName(String name) {
        log.info("event=customer_search_requested, name_masked={}", LogMaskingUtil.maskName(name));  
        return customerRepository.findByFullNameContainingIgnoreCase(name).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        log.info("event=customer_deletion_requested, customer_id={}", id);  
        
        if (!customerRepository.existsById(id)) {
            log.warn("event=customer_deletion_failed, reason=not_found, customer_id={}", id);  
            throw new CustomerNotFoundException(id);
        }
        customerRepository.deleteById(id);
        log.info("event=customer_deleted, customer_id={}", id);  
    }

    private CustomerResponse mapToResponse(CustomerEntity entity) {
        return CustomerResponse.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .nik(entity.getNik())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}