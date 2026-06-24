// Query database loan: find by ID with customer (eager), find by customer ID, find by status, pagination + filter, summary by status, outstanding per customer.

package com.example.training.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.training.Entity.LoanApplicationEntity;

public interface LoanApplicationRepository extends JpaRepository<LoanApplicationEntity, Long> {

        List<LoanApplicationEntity> findByCustomerId(Long customerId);

        List<LoanApplicationEntity> findByStatus(String status);

        @Query("SELECT l FROM LoanApplicationEntity l JOIN FETCH l.customer WHERE l.id = :id")
        Optional<LoanApplicationEntity> findByIdWithCustomer(@Param("id") Long id);

        @Query("SELECT l FROM LoanApplicationEntity l JOIN l.customer c WHERE c.id = :customerId")
        List<LoanApplicationEntity> findLoansByCustomerId(@Param("customerId") Long customerId);

        // ========== Pagination untuk List Loan Application (START) ========== //
        @Query("SELECT l FROM LoanApplicationEntity l JOIN FETCH l.customer WHERE " +
        "(:status IS NULL OR l.status = :status) AND " +
        "l.createdAt >= :startDate AND " +      // ========== Filter Loan Berdasarkan Tanggal Pengajuan ========== //
        "l.createdAt <= :endDate")              // ========== Filter Loan Berdasarkan Tanggal Pengajuan ========== //
        Page<LoanApplicationEntity> findAllWithFilters(
        @Param("status") LoanApplicationEntity.LoanStatus status,
        @Param("startDate") ZonedDateTime startDate,
        @Param("endDate") ZonedDateTime endDate,
        Pageable pageable);
        // ========== Pagination untuk List Loan Application (END) ========== //

        // ========== DTO Projection untuk Query Report (START) ========== //
        // ========== Endpoint Summary Total Loan by Status (START) ========== //
        @Query("SELECT l.status, COUNT(l), SUM(l.loanAmount) " +
                        "FROM LoanApplicationEntity l " +
                        "GROUP BY l.status")
        List<Object[]> getSummaryByStatusRaw();
        // ========== Endpoint Summary Total Loan by Status (END) ========== //

        // ========== Endpoint Outstanding Amount per Customer (START) ========== //
        @Query("SELECT c.id, c.fullName, SUM(r.totalAmount) " +
                        "FROM CustomerEntity c " +
                        "JOIN c.loanApplications l " +
                        "JOIN l.repaymentSchedules r " +
                        "WHERE r.status IN (com.example.training.Entity.RepaymentScheduleEntity.ScheduleStatus.UNPAID, "
                        +
                        "                   com.example.training.Entity.RepaymentScheduleEntity.ScheduleStatus.OVERDUE) "
                        +
                        "GROUP BY c.id, c.fullName")
        List<Object[]> getOutstandingPerCustomerRaw();
        // ========== Endpoint Outstanding Amount per Customer (END) ========== //
        // ========== DTO Projection untuk Query Report (END) ========== //
}