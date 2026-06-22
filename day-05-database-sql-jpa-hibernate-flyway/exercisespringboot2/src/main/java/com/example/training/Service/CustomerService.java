// Business logic customer: create (cek duplicate), get, list, search, soft delete.

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

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public CustomerResponse create(CreateCustomerRequest request) {
        if (customerRepository.existsByNik(request.getNik())) {
            throw new DuplicateCustomerException("nik", request.getNik());
        }
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateCustomerException("email", request.getEmail());
        }
        CustomerEntity entity = new CustomerEntity();
        entity.setFullName(request.getFullName());
        entity.setNik(request.getNik());
        entity.setEmail(request.getEmail());
        entity.setPhoneNumber(request.getPhoneNumber());
        CustomerEntity saved = customerRepository.save(entity);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public CustomerResponse getById(Long id) {
        CustomerEntity entity = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        return mapToResponse(entity);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getAll() {
        return customerRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> searchByName(String name) {
        return customerRepository.findByFullNameContainingIgnoreCase(name).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException(id);
        }
        customerRepository.deleteById(id);
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