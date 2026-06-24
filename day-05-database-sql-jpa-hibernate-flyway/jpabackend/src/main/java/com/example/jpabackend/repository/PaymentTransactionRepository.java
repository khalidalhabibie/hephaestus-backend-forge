package com.example.jpabackend.repository;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.jpabackend.entity.PaymentTransactionEntity;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransactionEntity, Long> {
    List<PaymentTransactionEntity> findByRepaymentScheduleId(Long repaymentScheduleId);

    // SUM paid amount (native query sesuai requirement)
    @Query(value = """
        SELECT COALESCE(SUM(p.paid_amount), 0)
        FROM payment_transactions p
        WHERE p.repayment_schedule_id = :scheduleId
        AND p.status = 'SUCCESS'
    """, nativeQuery = true)
    BigDecimal sumPaidAmountByScheduleId(@Param("scheduleId") Long scheduleId);

    // optional: all payments by loan (join example)
    @Query("""
        SELECT p
        FROM PaymentTransactionEntity p
        JOIN p.repaymentSchedule r
        JOIN r.loanApplication l
        WHERE l.id = :loanId
    """)
    List<PaymentTransactionEntity> findByLoanId(@Param("loanId") Long loanId);
}