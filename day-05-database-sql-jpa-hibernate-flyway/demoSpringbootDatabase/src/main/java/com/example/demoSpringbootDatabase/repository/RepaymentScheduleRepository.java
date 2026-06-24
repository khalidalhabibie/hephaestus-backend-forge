package com.example.demoSpringbootDatabase.repository;

import com.example.demoSpringbootDatabase.entity.RepaymentScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RepaymentScheduleRepository extends JpaRepository<RepaymentScheduleEntity, Long> {
    List<RepaymentScheduleEntity> findByLoanApplicationId(Long loanApplicationId);

    @Query("SELECT r FROM RepaymentScheduleEntity r JOIN FETCH r.loanApplication WHERE r.id = :id")
    Optional<RepaymentScheduleEntity> findByIdWithLoanApplication(@Param("id") Long id);

    // Filter Repayment Schedule Berdasarkan Status (PAID / UNPAID)
    List<RepaymentScheduleEntity> findByLoanApplicationIdAndStatus(Long loanApplicationId, String status);

    // Hitung outstanding amount (sisa total_amount yang statusnya masih UNPAID)
    @Query(value = "SELECT COALESCE(SUM(r.total_amount), 0) FROM repayment_schedules r " +
                "JOIN loan_applications l ON l.id = r.loan_application_id " +
                "WHERE l.customer_id = :customerId AND r.status = 'UNPAID'", nativeQuery = true)
    BigDecimal getOutstandingAmountByCustomerId(@Param("customerId") Long customerId);
}
