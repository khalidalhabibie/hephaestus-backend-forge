package com.example.jpabackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.jpabackend.entity.RepaymentScheduleEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RepaymentScheduleRepository
        extends JpaRepository<RepaymentScheduleEntity, Long> {

    List<RepaymentScheduleEntity> findByLoanApplicationId(Long loanApplicationId);

    List<RepaymentScheduleEntity> findByStatus(String status);

    @Query("SELECT r FROM RepaymentScheduleEntity r JOIN FETCH r.loanApplication WHERE r.id = :id")
    Optional<RepaymentScheduleEntity> findByIdWithLoan(@Param("id") Long id);

    @Query("""
                SELECT c.id, SUM(r.totalAmount)
                FROM RepaymentScheduleEntity r
                JOIN r.loanApplication l
                JOIN l.customer c
                WHERE r.status = 'UNPAID'
                GROUP BY c.id
            """)
    List<Object[]> getOutstandingByCustomer();

}
