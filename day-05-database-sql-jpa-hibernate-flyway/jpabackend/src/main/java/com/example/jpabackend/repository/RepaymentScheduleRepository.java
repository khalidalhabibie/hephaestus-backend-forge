package com.example.jpabackend.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.jpabackend.entity.RepaymentScheduleEntity;

public interface RepaymentScheduleRepository extends JpaRepository<RepaymentScheduleEntity, Long> {
    
    List<RepaymentScheduleEntity> findByLoanApplicationId(Long loanApplicationId);

    List<RepaymentScheduleEntity> findByStatus(String status);

    @Query("""
        SELECT r
        FROM RepaymentScheduleEntity r
        JOIN FETCH r.loanApplication l
        WHERE r.id = :id
    """)
    Optional<RepaymentScheduleEntity> findByIdWithLoanApplication(@Param("id") Long id);

    @Query("""
        SELECT r
        FROM RepaymentScheduleEntity r
        WHERE r.status = 'UNPAID'
    """)
    List<RepaymentScheduleEntity> findAllUnpaid();
}