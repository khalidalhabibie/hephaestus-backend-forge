package com.example.training.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.training.entity.RepaymentScheduleEntity;

public interface RepaymentScheduleRepository extends JpaRepository<RepaymentScheduleEntity, UUID> {

    List<RepaymentScheduleEntity> findByLoanApplication_Id(UUID loanApplicationId);

    boolean existsByLoanApplication_Id(UUID loanApplicationId);

    @Query("SELECT r FROM RepaymentScheduleEntity r JOIN FETCH r.loanApplication WHERE r.id = :id")
    Optional<RepaymentScheduleEntity> findByIdWithLoanApplication(@Param("id") UUID id);

}
