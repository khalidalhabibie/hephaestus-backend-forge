package com.example.demoSpringbootDatabase.repository;

import com.example.demoSpringbootDatabase.entity.RepaymentScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface RepaymentScheduleRepository extends JpaRepository<RepaymentScheduleEntity, Long> {
    List<RepaymentScheduleEntity> findByLoanApplicationId(Long loanApplicationId);

    @Query("SELECT r FROM RepaymentScheduleEntity r JOIN FETCH r.loanApplication WHERE r.id = :id")
    Optional<RepaymentScheduleEntity> findByIdWithLoanApplication(@Param("id") Long id);
}
