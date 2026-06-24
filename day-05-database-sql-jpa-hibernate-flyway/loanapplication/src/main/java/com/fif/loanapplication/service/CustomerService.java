package com.fif.loanapplication.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fif.loanapplication.dto.customer.CreateCustomerRequest;
import com.fif.loanapplication.dto.customer.CustomerResponse;
import com.fif.loanapplication.entity.CustomerEntity;
import com.fif.loanapplication.exception.CustomerNotFoundException;
import com.fif.loanapplication.exception.DuplicateCustomerException;
import com.fif.loanapplication.repository.CustomerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    // Helper to Response
    private CustomerResponse toCustomerResponse(CustomerEntity customer) {
        return CustomerResponse.builder()
                .uid(customer.getUid())
                .nik(customer.getNik())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }

    // Service Create Customer!
    @Transactional
    public CustomerResponse createCustomer(CreateCustomerRequest request) {

        if (customerRepository.existsByNik(request.getNik())) {
            throw new DuplicateCustomerException("NIK sudah terdaftar!");
        }

        if (customerRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new DuplicateCustomerException("Email sudah terdaftar!");
        }

        if (customerRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new DuplicateCustomerException("Nomor telepon sudah terdaftar!");
        }

        CustomerEntity customer = CustomerEntity.builder()
                .nik(request.getNik())
                .fullName(request.getFullName().trim().toLowerCase())
                .email(request.getEmail().trim().toLowerCase())
                .phoneNumber(request.getPhoneNumber())
                .build();

        CustomerEntity savedCustomer = customerRepository.save(customer);

        return toCustomerResponse(savedCustomer);
    }

    // Service Get Customer By UID
    @Transactional
    public CustomerResponse getCustomerById(UUID uid) {
        CustomerEntity customer = customerRepository.findById(uid)
                .orElseThrow(() -> new CustomerNotFoundException(uid));
        return toCustomerResponse(customer);
    }

    // Service Get All Customer with Query Param
    @Transactional
    public List<CustomerResponse> getAllCustomers(String fullName, String nik, String email) {
        String keywordFullName = fullName == null ? "" : fullName.trim().toLowerCase();
        String keywordNik = nik == null ? "" : nik.trim();
        String keywordEmail = email == null ? "" : email.trim().toLowerCase();

        // Validate Param is provided?
        boolean fullNameProvided = !keywordFullName.isBlank();
        boolean nikProvided = !keywordNik.isBlank();
        boolean emailProvided = !keywordEmail.isBlank();

        return customerRepository.findAll()
                .stream()
                .filter(customer -> {
                    boolean fullNameMatch = fullNameProvided
                            && customer.getFullName().toLowerCase().contains(keywordFullName);

                    boolean nikMatch = nikProvided && customer.getNik().contains(keywordNik);

                    boolean emailMatch = emailProvided && customer.getEmail().toLowerCase().contains(keywordEmail);

                    if (!fullNameProvided && !emailProvided && !nikProvided) {
                        return true;
                    }
                    return fullNameMatch || emailMatch || nikMatch;
                })
                .map(customer -> toCustomerResponse(customer))
                .collect(Collectors.toList());

    }

    // Service Update Data Customer
    @Transactional
    public CustomerResponse editCustomer(UUID uid, CreateCustomerRequest request) {
        CustomerEntity customer = customerRepository.findById(uid)
                .orElseThrow(() -> new CustomerNotFoundException(uid));

        if (customerRepository.existsByNikAndUidNot(request.getNik(), uid)) {
            throw new DuplicateCustomerException("NIK sudah digunakan!");
        }

        if (customerRepository.existsByEmailIgnoreCaseAndUidNot(request.getEmail(), uid)) {
            throw new DuplicateCustomerException("Email sudah digunakan!");
        }

        if (customerRepository.existsByPhoneNumberAndUidNot(request.getPhoneNumber(), uid)) {
            throw new DuplicateCustomerException("Nomor telepon sudah digunakan!");
        }

        customer.setFullName(request.getFullName().trim().toLowerCase());
        customer.setEmail(request.getEmail().trim().toLowerCase());
        customer.setNik(request.getNik());
        customer.setPhoneNumber(request.getPhoneNumber());

        CustomerEntity updatedCustomer = customerRepository.save(customer);
        return toCustomerResponse(updatedCustomer);
    }

    // Service Delete Customer By Uid
    @Transactional
    public void deleteCustomer(UUID uid) {
        CustomerEntity customer = customerRepository.findById(uid)
                .orElseThrow(() -> new CustomerNotFoundException(uid));
        customerRepository.delete(customer);
    }
    

}
