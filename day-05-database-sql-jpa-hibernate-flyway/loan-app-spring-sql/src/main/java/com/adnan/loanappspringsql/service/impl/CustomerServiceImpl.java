package com.adnan.loanappspringsql.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adnan.loanappspringsql.dto.CreateCustomerRequest;
import com.adnan.loanappspringsql.dto.CustomerResponse;
import com.adnan.loanappspringsql.exception.BadRequestException;
import com.adnan.loanappspringsql.exception.NotFoundException;
import com.adnan.loanappspringsql.model.Customer;
import com.adnan.loanappspringsql.repository.CustomerRepository;
import com.adnan.loanappspringsql.service.CustomerService;
import com.adnan.loanappspringsql.utils.LogUtil;
import com.adnan.loanappspringsql.utils.SensitiveDataLogUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {
  private final CustomerRepository customerRepository;

  @Override
  public CustomerResponse create(CreateCustomerRequest request) {
    log.info(LogUtil.format(
        "customer_create_requested"));
    if (customerRepository.existsByNik(request.getNik())) {
      log.warn(LogUtil.format(
          "customer_create_failed",
          "reason", "duplicate_nik",
          "nik", SensitiveDataLogUtil.maskNik(request.getNik())));
      throw new BadRequestException("NIK already exists");
    }

    if (customerRepository.existsByEmail(request.getEmail())) {
      log.warn(LogUtil.format(
          "customer_create_failed",
          "reason", "duplicate_email",
          "email", SensitiveDataLogUtil.maskEmail(request.getEmail())));
      throw new BadRequestException("Email already exists");
    }

    Customer customer = Customer.builder()
        .fullName(request.getFullName())
        .nik(request.getNik())
        .email(request.getEmail())
        .phoneNumber(request.getPhoneNumber())
        .build();

    customerRepository.save(customer);
    log.info(LogUtil.format(
        "customer_created",
        "customerId", customer.getId()));

    return mapToResponse(customer);
  }

  @Override
  @Transactional(readOnly = true)
  public CustomerResponse findById(Long id) {
    log.info(LogUtil.format(
        "customer_lookup",
        "customerId", id));
    Customer customer = customerRepository.findById(id)
        .orElseThrow(() -> {
          log.warn(LogUtil.format(
              "customer_not_found",
              "customerId", id));
          return new NotFoundException(
              "Customer not found with id: " + id);
        });
    log.info(LogUtil.format(
        "customer_found",
        "customerId", id));

    return mapToResponse(customer);
  }

  @Override
  @Transactional(readOnly = true)
  public List<CustomerResponse> findAll() {
    log.info(LogUtil.format(
        "customer_find_all"));
    List<CustomerResponse> customers = customerRepository.findAll()
        .stream()
        .map(this::mapToResponse)
        .toList();
    log.info(LogUtil.format(
        "customer_find_all_completed",
        "total", customers.size()));

    return customers;
  }

  @Override
  @Transactional(readOnly = true)
  public List<CustomerResponse> search(String name) {
    log.info(LogUtil.format(
        "customer_search"));
    List<CustomerResponse> customers = customerRepository
        .findByFullNameContainingIgnoreCase(name)
        .stream()
        .map(this::mapToResponse)
        .toList();
    log.info(LogUtil.format(
        "customer_search_completed",
        "total", customers.size()));

    return customers;
  }

  // Helper
  private CustomerResponse mapToResponse(Customer customer) {
    return CustomerResponse.builder()
        .id(customer.getId())
        .fullName(customer.getFullName())
        .nik(customer.getNik())
        .email(customer.getEmail())
        .phoneNumber(customer.getPhoneNumber())
        .build();
  }
}
