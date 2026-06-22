package com.adnan.loanappspringsql.service;

import java.util.List;

import com.adnan.loanappspringsql.dto.CreateCustomerRequest;
import com.adnan.loanappspringsql.dto.CustomerResponse;

public interface CustomerService {
  CustomerResponse create(CreateCustomerRequest request);

  CustomerResponse findById(Long id);

  List<CustomerResponse> findAll();

  List<CustomerResponse> search(String name);
}
