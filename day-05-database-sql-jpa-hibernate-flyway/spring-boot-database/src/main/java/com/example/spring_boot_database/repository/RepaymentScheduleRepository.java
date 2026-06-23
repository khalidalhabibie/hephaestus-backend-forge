package com.example.spring_boot_database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.spring_boot_database.entity.RepaymentScheduleEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RepaymentScheduleRepository extends JpaRepository<RepaymentScheduleEntity, Long> {

    List<RepaymentScheduleEntity> findByLoanApplicationId(Long loanApplicationId);

    List<RepaymentScheduleEntity> findByStatusIgnoreCase(String status);

    @Query("SELECT r FROM RepaymentScheduleEntity r JOIN FETCH r.loanApplication WHERE r.id = :id")
    Optional<RepaymentScheduleEntity> findByIdWithLoanApplication(@Param("id") Long id);

    @Query(value = """
            SELECT COALESCE(SUM(
                CASE
                    WHEN x.total_amount - x.paid_amount > 0
                    THEN x.total_amount - x.paid_amount
                    ELSE 0
                END
            ), 0)
            FROM (
                SELECT
                    rs.id,
                    rs.total_amount,
                    COALESCE(SUM(
                        CASE
                            WHEN pt.status = 'PAID'
                            THEN pt.paid_amount
                            ELSE 0
                        END
                    ), 0) AS paid_amount
                FROM repayment_schedules rs
                JOIN loan_applications la
                    ON la.id = rs.loan_application_id
                JOIN customers c
                    ON c.id = la.customer_id
                LEFT JOIN payment_transactions pt
                    ON pt.repayment_schedule_id = rs.id
                WHERE la.customer_id = :customerId
                  AND c.deleted_at IS NULL
                GROUP BY rs.id, rs.total_amount
            ) x
            """, nativeQuery = true)
    BigDecimal calculateOutstandingAmountByCustomerId(@Param("customerId") Long customerId);
}