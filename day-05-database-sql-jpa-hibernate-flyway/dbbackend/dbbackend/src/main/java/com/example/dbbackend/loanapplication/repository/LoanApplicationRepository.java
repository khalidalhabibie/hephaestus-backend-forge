package com.example.dbbackend.loanapplication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.dbbackend.loanapplication.entity.LoanApplicationEntity;

public interface LoanApplicationRepository extends JpaRepository<LoanApplicationEntity, Long> {

    List<LoanApplicationEntity> findByCustomerId(Long customerId);

    List<LoanApplicationEntity> findByStatus(String status);

    @Query("SELECT l FROM LoanApplicationEntity l JOIN FETCH l.customer WHERE l.id = :id")
    Optional<LoanApplicationEntity> findByIdWithCustomer(@Param("id") Long id);

    @Query("SELECT l FROM LoanApplicationEntity l JOIN l.customer c WHERE c.id = :customerId")
    List<LoanApplicationEntity> findLoansByCustomerId(@Param("customerId") Long customerId);
}