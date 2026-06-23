package com.adnan.loanappspringsql.repository;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adnan.loanappspringsql.enums.LoanStatusEnum;
import com.adnan.loanappspringsql.model.LoanApplication;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
        List<LoanApplication> findByCustomerId(Long customerId);

        Page<LoanApplication> findAll(Pageable pageable);

        Page<LoanApplication> findByStatus(
                        LoanStatusEnum status,
                        Pageable pageable);

        Page<LoanApplication> findByCreatedAtBetween(
                        ZonedDateTime start,
                        ZonedDateTime end,
                        Pageable pageable);

        Page<LoanApplication> findByStatusAndCreatedAtBetween(
                        LoanStatusEnum status,
                        ZonedDateTime start,
                        ZonedDateTime end,
                        Pageable pageable);

        @Query("""
                        SELECT l
                        FROM LoanApplication l
                        JOIN FETCH l.customer
                        WHERE l.id = :id
                                """)
        Optional<LoanApplication> findByIdWithCustomer(@Param("id") Long id);

        @Query("""
                        SELECT l
                        FROM LoanApplication l
                        JOIN l.customer c
                        WHERE c.id = :customerId
                                """)
        List<LoanApplication> findLoansByCustomerId(@Param("customerId") Long customerId);

        @Query("""
                        SELECT l.status, COUNT(l)
                        FROM LoanApplication l
                        GROUP BY l.status
                                """)
        List<Object[]> countLoanByStatus();

        @Query("""
                        SELECT COALESCE(SUM(r.totalAmount), 0)
                        FROM LoanApplication l
                        JOIN l.customer c
                        JOIN RepaymentSchedule r
                        ON r.loanApplication.id = l.id
                        WHERE c.id = :customerId
                        """)
        BigDecimal getTotalRepayment(@Param("customerId") Long customerId);

        @Query("""
                        SELECT COALESCE(SUM(p.paidAmount),0)
                        FROM PaymentTransaction p
                        JOIN p.repaymentSchedule r
                        JOIN r.loanApplication l
                        WHERE l.customer.id = :customerId
                        AND p.status='SUCCESS'
                        """)
        BigDecimal getTotalPaid(
                        @Param("customerId") Long customerId);
}
