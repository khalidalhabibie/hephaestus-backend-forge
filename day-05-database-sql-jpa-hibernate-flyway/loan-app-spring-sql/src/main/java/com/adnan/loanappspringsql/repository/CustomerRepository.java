package com.adnan.loanappspringsql.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adnan.loanappspringsql.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
  Optional<Customer> findByNik(String nik);

  Optional<Customer> findByEmail(String email);

  boolean existsByNik(String nik);

  boolean existsByEmail(String email);

  List<Customer> findByFullNameContainingIgnoreCase(String fullName);
}
