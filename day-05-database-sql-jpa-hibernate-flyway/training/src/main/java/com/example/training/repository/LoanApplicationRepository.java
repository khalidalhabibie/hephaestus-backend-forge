package com.example.training.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.training.entity.LoanApplicationEntity;
import com.example.training.enums.LoanStatus;

public interface LoanApplicationRepository extends JpaRepository<LoanApplicationEntity, UUID> {

    List<LoanApplicationEntity> findByCustomer_Id(UUID customerId);

    List<LoanApplicationEntity> findByStatus(LoanStatus status);

    @Query("SELECT l FROM LoanApplicationEntity l JOIN FETCH l.customer WHERE l.id = :id")
    Optional<LoanApplicationEntity> findByIdWithCustomer(@Param("id") UUID id);

    @Query("""
            SELECT l
            FROM LoanApplicationEntity l
            WHERE l.customer.id = :customerId
            """)
    List<LoanApplicationEntity> findLoansByCustomerId(
            @Param("customerId") UUID customerId);
}
