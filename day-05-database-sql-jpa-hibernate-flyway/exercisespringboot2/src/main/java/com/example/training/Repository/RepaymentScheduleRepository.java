// Query database repayment: find by loan ID, find by loan ID + filter status.

package com.example.training.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.training.Entity.RepaymentScheduleEntity;

public interface RepaymentScheduleRepository extends JpaRepository<RepaymentScheduleEntity, Long> {

        List<RepaymentScheduleEntity> findByLoanApplicationId(Long loanApplicationId);

        @Query("SELECT r FROM RepaymentScheduleEntity r JOIN FETCH r.loanApplication WHERE r.id = :id")
        Optional<RepaymentScheduleEntity> findByIdWithLoanApplication(@Param("id") Long id);

        // ========== Filter Repayment Schedule Berdasarkan Status PAID / UNPAID (START)
        // ========== //
        @Query("SELECT r FROM RepaymentScheduleEntity r WHERE r.loanApplication.id = :loanId AND " +
                        "(:status IS NULL OR r.status = :status)")
        List<RepaymentScheduleEntity> findByLoanIdAndStatus(
                        @Param("loanId") Long loanId,
                        @Param("status") RepaymentScheduleEntity.ScheduleStatus status);
        // ========== Filter Repayment Schedule Berdasarkan Status PAID / UNPAID (END)
        // ========== //

        // ========== Validasi CLOSED: cek apakah semua schedule sudah PAID (START)
        // ========== //
        @Query("SELECT COUNT(r) FROM RepaymentScheduleEntity r " +
                        "WHERE r.loanApplication.id = :loanId " +
                        "AND r.status <> com.example.training.Entity.RepaymentScheduleEntity.ScheduleStatus.PAID")
        long countByLoanIdAndStatusNotPaid(@Param("loanId") Long loanId);
        // ========== Validasi CLOSED: cek apakah semua schedule sudah PAID (END)
        // ========== //
}