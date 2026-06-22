package com.adnan.loanappspringsql.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adnan.loanappspringsql.dto.CreateCustomerRequest;
import com.adnan.loanappspringsql.dto.CustomerResponse;
import com.adnan.loanappspringsql.exception.BadRequestException;
import com.adnan.loanappspringsql.exception.CustomerNotFoundException;
import com.adnan.loanappspringsql.model.Customer;
import com.adnan.loanappspringsql.repository.CustomerRepository;
import com.adnan.loanappspringsql.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {
  private final CustomerRepository customerRepository;

  @Override
  public CustomerResponse create(CreateCustomerRequest request) {

    if (customerRepository.existsByNik(request.getNik())) {
      throw new BadRequestException("NIK already exists");
    }

    if (customerRepository.existsByEmail(request.getEmail())) {
      throw new BadRequestException("Email already exists");
    }

    Customer customer = Customer.builder()
        .fullName(request.getFullName())
        .nik(request.getNik())
        .email(request.getEmail())
        .phoneNumber(request.getPhoneNumber())
        .build();

    customerRepository.save(customer);

    return mapToResponse(customer);
  }

  @Override
  @Transactional(readOnly = true)
  public CustomerResponse findById(Long id) {

    Customer customer = customerRepository.findById(id)
        .orElseThrow(() -> new CustomerNotFoundException(
            "Customer not found with id: " + id));

    return mapToResponse(customer);
  }

  @Override
  @Transactional(readOnly = true)
  public List<CustomerResponse> findAll() {

    return customerRepository.findAll()
        .stream()
        .map(this::mapToResponse)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<CustomerResponse> search(String name) {

    return customerRepository
        .findByFullNameContainingIgnoreCase(name)
        .stream()
        .map(this::mapToResponse)
        .toList();
  }

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
