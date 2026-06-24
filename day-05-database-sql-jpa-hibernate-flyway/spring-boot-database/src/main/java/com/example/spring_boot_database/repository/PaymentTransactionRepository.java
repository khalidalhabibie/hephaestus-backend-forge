package com.example.spring_boot_database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.spring_boot_database.entity.PaymentTransactionEntity;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransactionEntity, Long> {

    List<PaymentTransactionEntity> findByRepaymentScheduleId(Long repaymentScheduleId);

    @Query(value = """
            SELECT COALESCE(SUM(paid_amount), 0)
            FROM payment_transactions
            WHERE repayment_schedule_id = :scheduleId
              AND status = 'PAID'
            """, nativeQuery = true)
    BigDecimal sumPaidAmountByScheduleId(@Param("scheduleId") Long scheduleId);
}
