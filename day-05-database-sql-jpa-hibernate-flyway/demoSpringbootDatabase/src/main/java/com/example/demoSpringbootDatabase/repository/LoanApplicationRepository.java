package com.example.demoSpringbootDatabase.repository;

import com.example.demoSpringbootDatabase.dto.LoanStatusSummaryDto;
import com.example.demoSpringbootDatabase.entity.LoanApplicationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

public interface LoanApplicationRepository extends JpaRepository<LoanApplicationEntity, Long> {
    List<LoanApplicationEntity> findByCustomerId(Long customerId);
    List<LoanApplicationEntity> findByStatus(String status);

    @Query("SELECT l FROM LoanApplicationEntity l JOIN FETCH l.customer WHERE l.id = :id")
    Optional<LoanApplicationEntity> findByIdWithCustomer(@Param("id") Long id);

    @Query("SELECT l FROM LoanApplicationEntity l JOIN l.customer c WHERE c.id = :customerId")
    List<LoanApplicationEntity> findLoansByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT l.status AS status, COUNT(l) AS totalLoan, SUM(l.loanAmount) AS totalAmount " +
        "FROM LoanApplicationEntity l GROUP BY l.status")
    List<LoanStatusSummaryDto> getSummaryByStatus();

    // Pagination + Filter Status + Filter Rentang Tanggal Pengajuan
    // ✅ BENAR: Kembaliannya harus membungkus target Entity-nya (LoanApplicationEntity)
    Page<LoanApplicationEntity> findByStatusAndCreatedAtBetween(String status, LocalDateTime start, LocalDateTime end, Pageable pageable);

    Page<LoanApplicationEntity> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
    
}