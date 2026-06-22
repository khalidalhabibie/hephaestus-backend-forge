package com.example.jpabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpabackend.dto.LoanSummaryDTO;
import com.example.jpabackend.entity.LoanApplicationEntity;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LoanApplicationRepository
        extends JpaRepository<LoanApplicationEntity, Long> {

    List<LoanApplicationEntity> findByCustomerId(Long customerId);

    List<LoanApplicationEntity> findByStatus(String status);
    
    List<LoanApplicationEntity> findByCreatedAtBetween(
            ZonedDateTime start,
            ZonedDateTime end);

    @Query("SELECT l FROM LoanApplicationEntity l JOIN FETCH l.customer WHERE l.id = :id")
    Optional<LoanApplicationEntity> findByIdWithCustomer(@Param("id") Long id);

    @Query("""
                SELECT l.status, SUM(l.loanAmount)
                FROM LoanApplicationEntity l
                GROUP BY l.status
            """)
    List<Object[]> getTotalLoanByStatus();

    @Query("""
                SELECT l.status AS status, SUM(l.loanAmount) AS total
                FROM LoanApplicationEntity l
                GROUP BY l.status
            """)
    List<LoanSummaryDTO> getLoanSummaryDTO();
}