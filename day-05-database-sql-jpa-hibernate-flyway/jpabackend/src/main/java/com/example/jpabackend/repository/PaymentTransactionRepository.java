package com.example.jpabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.jpabackend.entity.PaymentTransactionEntity;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaymentTransactionRepository
        extends JpaRepository<PaymentTransactionEntity, Long> {

    List<PaymentTransactionEntity> findByRepaymentScheduleId(Long scheduleId);
    
    boolean existsByRepaymentSchedule_LoanApplication_IdAndPaymentReference(
            Long loanId,
            String paymentReference);

    @Query(value = """
                SELECT COALESCE(SUM(paid_amount), 0)
                FROM payment_transactions
                WHERE repayment_schedule_id = :scheduleId
                AND status = 'SUCCESS'
            """, nativeQuery = true)
    BigDecimal sumPaidAmount(@Param("scheduleId") Long scheduleId);
}