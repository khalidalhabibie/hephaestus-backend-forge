package com.fif.finance_training.repository;

import java.util.Optional;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fif.finance_training.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByNik(String nik);
    Optional<CustomerEntity> findByEmail(String email);
    boolean existsByNik(String nik);
    boolean existsByEmail(String email);
    List<CustomerEntity> findByFullNameContainingIgnoreCase(String fullName);
}