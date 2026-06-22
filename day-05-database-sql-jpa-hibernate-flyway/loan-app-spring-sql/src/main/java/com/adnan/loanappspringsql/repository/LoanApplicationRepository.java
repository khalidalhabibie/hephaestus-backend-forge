package com.adnan.loanappspringsql.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adnan.loanappspringsql.enums.LoanStatusEnum;
import com.adnan.loanappspringsql.model.LoanApplication;

public interface LoanApplicationRepository  extends JpaRepository<LoanApplication, Long> {
  List<LoanApplication> findByCustomerId(Long customerId);

  List<LoanApplication> findByStatus(LoanStatusEnum status);

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
}
