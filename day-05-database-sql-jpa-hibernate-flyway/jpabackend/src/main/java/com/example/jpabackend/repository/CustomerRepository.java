package com.example.jpabackend.repository;

import java.util.*;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.jpabackend.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByNik(String nik);

    Optional<CustomerEntity> findByEmail(String email);

    boolean existsByNik(String nik);

    boolean existsByEmail(String email);
    
    List<CustomerEntity> findByFullNameContainingIgnoreCase(String fullName);
}