package com.example.spring_boot_database.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.spring_boot_database.entity.LoanApplicationEntity;

import java.util.List;
import java.util.Optional;

public interface LoanApplicationRepository extends JpaRepository<LoanApplicationEntity, Long>, JpaSpecificationExecutor<LoanApplicationEntity> {

    List<LoanApplicationEntity> findByCustomerId(Long customerId);

    List<LoanApplicationEntity> findByStatus(String status);

    @Query("SELECT l FROM LoanApplicationEntity l JOIN FETCH l.customer WHERE l.id = :id")
    Optional<LoanApplicationEntity> findByIdWithCustomer(@Param("id") Long id);

    @Query("SELECT l FROM LoanApplicationEntity l JOIN l.customer c WHERE c.id = :customerId")
    List<LoanApplicationEntity> findLoansByCustomerId(@Param("customerId") Long customerId);
}

