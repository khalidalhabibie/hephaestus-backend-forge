package com.fif.loanapplication.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fif.loanapplication.entity.LoanApplicationEntity;
import com.fif.loanapplication.entity.enums.LoanStatus;

public interface LoanApplicationRepository extends JpaRepository<LoanApplicationEntity, UUID> {

    List<LoanApplicationEntity> findByCustomerUid(UUID customerUid);

    List<LoanApplicationEntity> findByStatus(LoanStatus status);

    @Query("""
            SELECT l
            FROM LoanApplicationEntity l
            JOIN FETCH l.customer
            """)
    List<LoanApplicationEntity> findAllWithCustomer();

    @Query("""
            SELECT l
            FROM LoanApplicationEntity l
            JOIN FETCH l.customer
            WHERE l.uid = :uid
            """)
    Optional<LoanApplicationEntity> findByUidWithCustomer(@Param("uid") UUID uid);

}
