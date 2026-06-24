package com.example.spring_boot_database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.spring_boot_database.dto.LoanApplicationReportProjection;
import com.example.spring_boot_database.entity.LoanApplicationEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LoanApplicationRepository extends JpaRepository<LoanApplicationEntity, Long>, JpaSpecificationExecutor<LoanApplicationEntity> {

    List<LoanApplicationEntity> findByCustomerId(Long customerId);

    List<LoanApplicationEntity> findByStatus(String status);

    @Query("""
            SELECT l
            FROM LoanApplicationEntity l
            JOIN FETCH l.customer c
            WHERE l.id = :id
              AND c.deletedAt IS NULL
            """)
    Optional<LoanApplicationEntity> findByIdWithCustomer(@Param("id") Long id);

    @Query("""
            SELECT l
            FROM LoanApplicationEntity l
            JOIN FETCH l.customer c
            WHERE c.id = :customerId
              AND c.deletedAt IS NULL
            """)
    List<LoanApplicationEntity> findLoansByCustomerId(@Param("customerId") Long customerId);

    @Query("""
            SELECT l.status, COUNT(l), COALESCE(SUM(l.loanAmount), 0)
            FROM LoanApplicationEntity l
            JOIN l.customer c
            WHERE c.deletedAt IS NULL
            GROUP BY l.status
            """)
    List<Object[]> summarizeTotalLoanByStatus();

    @Query(value = """
            SELECT
                la.id AS loanId,
                c.id AS customerId,
                c.full_name AS customerName,
                c.nik AS nik,
                la.status AS status,
                la.loan_amount AS loanAmount,
                la.tenor_month AS tenorMonth,
                la.purpose AS purpose,
                la.created_at AS createdAt,
                COALESCE(schedule_summary.total_schedule_amount, 0) AS totalScheduleAmount,
                COALESCE(payment_summary.total_paid_amount, 0) AS totalPaidAmount,
                GREATEST(
                    COALESCE(schedule_summary.total_schedule_amount, 0)
                    - COALESCE(payment_summary.total_paid_amount, 0),
                    0
                ) AS outstandingAmount
            FROM loan_applications la
            JOIN customers c
                ON c.id = la.customer_id
            LEFT JOIN (
                SELECT
                    rs.loan_application_id,
                    SUM(rs.total_amount) AS total_schedule_amount
                FROM repayment_schedules rs
                GROUP BY rs.loan_application_id
            ) schedule_summary
                ON schedule_summary.loan_application_id = la.id
            LEFT JOIN (
                SELECT
                    rs.loan_application_id,
                    SUM(
                        CASE
                            WHEN pt.status = 'PAID'
                            THEN pt.paid_amount
                            ELSE 0
                        END
                    ) AS total_paid_amount
                FROM repayment_schedules rs
                LEFT JOIN payment_transactions pt
                    ON pt.repayment_schedule_id = rs.id
                GROUP BY rs.loan_application_id
            ) payment_summary
                ON payment_summary.loan_application_id = la.id
            WHERE c.deleted_at IS NULL
              AND (
                    CAST(:status AS VARCHAR) IS NULL
                    OR la.status = CAST(:status AS VARCHAR)
              )
              AND (
                    CAST(:startDate AS TIMESTAMP) IS NULL
                    OR la.created_at >= CAST(:startDate AS TIMESTAMP)
              )
              AND (
                    CAST(:endDateExclusive AS TIMESTAMP) IS NULL
                    OR la.created_at < CAST(:endDateExclusive AS TIMESTAMP)
              )
            ORDER BY la.created_at DESC
            """, nativeQuery = true)
    List<LoanApplicationReportProjection> findLoanApplicationReport(
            @Param("status") String status,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDateExclusive") LocalDateTime endDateExclusive
    );
}