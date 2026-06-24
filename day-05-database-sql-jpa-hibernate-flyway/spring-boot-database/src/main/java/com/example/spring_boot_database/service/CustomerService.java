package com.example.spring_boot_database.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final LoanApplicationRepository loanApplicationRepository;
    private final RepaymentScheduleRepository repaymentScheduleRepository;

    private void fill(CustomerEntity entity, CreateCustomerRequest req) {
        entity.setNik(req.getNik());
        entity.setFullName(req.getFullName());
        entity.setEmail(req.getEmail());
        entity.setPhoneNumber(req.getPhoneNumber());
    }

    @Transactional
    public CustomerResponse createCustomer(CreateCustomerRequest request) {

        if (customerRepository.existsByNikAndDeletedAtIsNull(request.getNik())) {
            throw new DuplicateCustomerException("nik", request.getNik());
        }

        if (customerRepository.existsByEmailAndDeletedAtIsNull(request.getEmail())) {
            throw new DuplicateCustomerException("email", request.getEmail());
        }

        CustomerEntity entity = new CustomerEntity();
        fill(entity, request);

        return toResponse(customerRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public CustomerEntity getById(Long id) {
        return customerRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public CustomerResponse findById(Long id) {
        return toResponse(getById(id));
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> findCustomer(String name) {
        return (name != null && !name.isBlank()
                ? customerRepository.findByFullNameContainingIgnoreCaseAndDeletedAtIsNull(name)
                : customerRepository.findByDeletedAtIsNull())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> findLoanByCustomer(Long customerId) {

        getById(customerId);

        return loanApplicationRepository.findLoansByCustomerId(customerId)
                .stream()
                .map(this::toLoanResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public OutstandingAmountResponse getOutstandingAmountByCustomer(Long customerId) {

        getById(customerId);

        BigDecimal outstandingAmount =
                repaymentScheduleRepository.calculateOutstandingAmountByCustomerId(customerId);

        return OutstandingAmountResponse.builder()
                .customerId(customerId)
                .outstandingAmount(outstandingAmount)
                .build();
    }

    @Transactional
    public CustomerResponse softDeleteCustomer(Long id) {

        CustomerEntity customer = getById(id);

        customer.setDeletedAt(LocalDateTime.now());

        CustomerEntity updated = customerRepository.save(customer);

        return toResponse(updated);
    }

    private LoanApplicationResponse toLoanResponse(LoanApplicationEntity loan) {
        return LoanApplicationResponse.builder()
                .loanAmount(loan.getLoanAmount())
                .tenorMonth(loan.getTenorMonth())
                .purpose(loan.getPurpose())
                .status(Status.valueOf(loan.getStatus()))
                .customer(toResponse(loan.getCustomer()))
                .build();
    }

    public CustomerResponse toResponse(CustomerEntity entity) {
        return CustomerResponse.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .nik(entity.getNik())
                .email(entity.getEmail())
                .phoneNumber(entity.getPhoneNumber())
                .build();
    }
}