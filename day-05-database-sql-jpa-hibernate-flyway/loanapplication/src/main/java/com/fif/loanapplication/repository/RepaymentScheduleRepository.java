package com.fif.loanapplication.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fif.loanapplication.entity.RepaymentScheduleEntity;

public interface RepaymentScheduleRepository extends JpaRepository<RepaymentScheduleEntity, UUID> {
    List<RepaymentScheduleEntity> findByLoanApplicationUid(UUID loanApplicationUid);

    @Query("SELECT r FROM RepaymentScheduleEntity r JOIN FETCH r.loanApplication WHERE r.id = :id")
    Optional<RepaymentScheduleEntity> findByIdWithLoanApplication(@Param("id") Long id);
}
