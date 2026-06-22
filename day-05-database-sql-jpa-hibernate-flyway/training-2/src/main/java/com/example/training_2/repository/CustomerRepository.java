package com.example.training_2.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.training_2.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByNik(String nik);

    Optional<Customer> findByEmail(String email);

    boolean existsByNik(String nik);

    boolean existsByEmail(String email);
}
