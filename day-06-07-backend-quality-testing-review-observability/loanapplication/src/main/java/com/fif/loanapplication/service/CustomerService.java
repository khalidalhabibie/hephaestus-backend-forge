package com.fif.loanapplication.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fif.loanapplication.common.log.LogContext;
import com.fif.loanapplication.common.log.LogMaskingUtil;
import com.fif.loanapplication.dto.customer.CreateCustomerRequest;
import com.fif.loanapplication.dto.customer.CustomerResponse;
import com.fif.loanapplication.entity.CustomerEntity;
import com.fif.loanapplication.exception.CustomerNotFoundException;
import com.fif.loanapplication.exception.DuplicateCustomerException;
import com.fif.loanapplication.repository.CustomerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
        log.info(
                "event=customer_create_requested status=started correlation_id={} nik={} email={} phone={}",
                LogContext.getCorrelationId(),
                LogMaskingUtil.maskNik(request.getNik()),
                LogMaskingUtil.maskEmail(request.getEmail()),
                LogMaskingUtil.maskPhone(request.getPhoneNumber()));

        if (customerRepository.existsByNik(request.getNik())) {
            log.warn(
                    "event=customer_create_failed status=failed correlation_id={} reason=duplicate_nik nik={}",
                    LogContext.getCorrelationId(),
                    LogMaskingUtil.maskNik(request.getNik()));
            throw new DuplicateCustomerException("NIK sudah terdaftar!");
        }

        if (customerRepository.existsByEmailIgnoreCase(request.getEmail())) {
            log.warn(
                    "event=customer_create_failed status=failed correlation_id={} reason=duplicate_email email={}",
                    LogContext.getCorrelationId(),
                    LogMaskingUtil.maskEmail(request.getEmail()));
            throw new DuplicateCustomerException("Email sudah terdaftar!");
        }

        if (customerRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            log.warn(
                    "event=customer_create_failed status=failed correlation_id={} reason=duplicate_phone phone={}",
                    LogContext.getCorrelationId(),
                    LogMaskingUtil.maskPhone(request.getPhoneNumber()));
            throw new DuplicateCustomerException("Nomor telepon sudah terdaftar!");
        }

        CustomerEntity customer = CustomerEntity.builder()
                .nik(request.getNik())
                .fullName(request.getFullName().trim().toLowerCase())
                .email(request.getEmail().trim().toLowerCase())
                .phoneNumber(request.getPhoneNumber())
                .build();

        CustomerEntity savedCustomer = customerRepository.save(customer);

        log.info(
                "event=customer_created status=success correlation_id={} customer_uid={}",
                LogContext.getCorrelationId(),
                savedCustomer.getUid());

        return toCustomerResponse(savedCustomer);
    }

    // Service Get Customer By UID
    @Transactional
    public CustomerResponse getCustomerById(UUID uid) {
        log.info(
                "event=customer_get_by_id_requested status=started correlation_id={} customer_uid={}",
                LogContext.getCorrelationId(),
                uid);

        CustomerEntity customer = customerRepository.findById(uid)
                .orElseThrow(() -> {
                    log.warn(
                            "event=customer_get_by_id_failed status=failed correlation_id={} reason=customer_not_found customer_uid={}",
                            LogContext.getCorrelationId(),
                            uid);
                    return new CustomerNotFoundException(uid);
                });

        log.info(
                "event=customer_get_by_id_success status=success correlation_id={} customer_uid={}",
                LogContext.getCorrelationId(),
                customer.getUid());

        return toCustomerResponse(customer);
    }

    // Service Get All Customer with Query Param
    @Transactional
    public List<CustomerResponse> getAllCustomers(String fullName, String nik, String email) {
        String keywordFullName = fullName == null ? "" : fullName.trim().toLowerCase();
        String keywordNik = nik == null ? "" : nik.trim();
        String keywordEmail = email == null ? "" : email.trim().toLowerCase();

        boolean fullNameProvided = !keywordFullName.isBlank();
        boolean nikProvided = !keywordNik.isBlank();
        boolean emailProvided = !keywordEmail.isBlank();

        log.info(
                "event=customer_get_all_requested status=started correlation_id={} full_name_filter={} nik_filter={} email_filter={}",
                LogContext.getCorrelationId(),
                fullNameProvided,
                nikProvided,
                emailProvided);

        List<CustomerResponse> customers = customerRepository.findAll()
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
                .map(this::toCustomerResponse)
                .collect(Collectors.toList());

        log.info(
                "event=customer_get_all_success status=success correlation_id={} total_data={}",
                LogContext.getCorrelationId(),
                customers.size());

        return customers;
    }

    // Service Update Data Customer
    @Transactional
    public CustomerResponse editCustomer(UUID uid, CreateCustomerRequest request) {
        log.info(
                "event=customer_update_requested status=started correlation_id={} customer_uid={} nik={} email={} phone={}",
                LogContext.getCorrelationId(),
                uid,
                LogMaskingUtil.maskNik(request.getNik()),
                LogMaskingUtil.maskEmail(request.getEmail()),
                LogMaskingUtil.maskPhone(request.getPhoneNumber()));

        CustomerEntity customer = customerRepository.findById(uid)
                .orElseThrow(() -> {
                    log.warn(
                            "event=customer_update_failed status=failed correlation_id={} reason=customer_not_found customer_uid={}",
                            LogContext.getCorrelationId(),
                            uid);
                    return new CustomerNotFoundException(uid);
                });

        if (customerRepository.existsByNikAndUidNot(request.getNik(), uid)) {
            log.warn(
                    "event=customer_update_failed status=failed correlation_id={} reason=duplicate_nik customer_uid={} nik={}",
                    LogContext.getCorrelationId(),
                    uid,
                    LogMaskingUtil.maskNik(request.getNik()));
            throw new DuplicateCustomerException("NIK sudah digunakan!");
        }

        if (customerRepository.existsByEmailIgnoreCaseAndUidNot(request.getEmail(), uid)) {
            log.warn(
                    "event=customer_update_failed status=failed correlation_id={} reason=duplicate_email customer_uid={} email={}",
                    LogContext.getCorrelationId(),
                    uid,
                    LogMaskingUtil.maskEmail(request.getEmail()));
            throw new DuplicateCustomerException("Email sudah digunakan!");
        }

        if (customerRepository.existsByPhoneNumberAndUidNot(request.getPhoneNumber(), uid)) {
            log.warn(
                    "event=customer_update_failed status=failed correlation_id={} reason=duplicate_phone customer_uid={} phone={}",
                    LogContext.getCorrelationId(),
                    uid,
                    LogMaskingUtil.maskPhone(request.getPhoneNumber()));
            throw new DuplicateCustomerException("Nomor telepon sudah digunakan!");
        }

        customer.setFullName(request.getFullName().trim().toLowerCase());
        customer.setEmail(request.getEmail().trim().toLowerCase());
        customer.setNik(request.getNik());
        customer.setPhoneNumber(request.getPhoneNumber());

        CustomerEntity updatedCustomer = customerRepository.save(customer);

        log.info(
                "event=customer_updated status=success correlation_id={} customer_uid={}",
                LogContext.getCorrelationId(),
                updatedCustomer.getUid());

        return toCustomerResponse(updatedCustomer);
    }

    // Service Delete Customer By Uid
    @Transactional
    public void deleteCustomer(UUID uid) {
        log.info(
                "event=customer_delete_requested status=started correlation_id={} customer_uid={}",
                LogContext.getCorrelationId(),
                uid);

        CustomerEntity customer = customerRepository.findById(uid)
                .orElseThrow(() -> {
                    log.warn(
                            "event=customer_delete_failed status=failed correlation_id={} reason=customer_not_found customer_uid={}",
                            LogContext.getCorrelationId(),
                            uid);
                    return new CustomerNotFoundException(uid);
                });

        customerRepository.delete(customer);

        log.info(
                "event=customer_deleted status=success correlation_id={} customer_uid={}",
                LogContext.getCorrelationId(),
                uid);
    }
}