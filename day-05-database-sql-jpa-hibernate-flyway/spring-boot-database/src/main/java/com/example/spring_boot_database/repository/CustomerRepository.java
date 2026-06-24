package com.example.spring_boot_database.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.spring_boot_database.entity.CustomerEntity;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByNik(String nik);

    Optional<CustomerEntity> findByEmail(String email);

    boolean existsByNik(String nik);

    boolean existsByEmail(String email);

    List<CustomerEntity> findByFullNameContainingIgnoreCase(String fullName);

    Optional<CustomerEntity> findByIdAndDeletedAtIsNull(Long id);

    Optional<CustomerEntity> findByNikAndDeletedAtIsNull(String nik);

    Optional<CustomerEntity> findByEmailAndDeletedAtIsNull(String email);

    boolean existsByNikAndDeletedAtIsNull(String nik);

    boolean existsByEmailAndDeletedAtIsNull(String email);

    List<CustomerEntity> findByDeletedAtIsNull();

    List<CustomerEntity> findByFullNameContainingIgnoreCaseAndDeletedAtIsNull(String fullName);
}