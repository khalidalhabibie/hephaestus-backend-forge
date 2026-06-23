package com.fif.loanapplication.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fif.loanapplication.entity.PaymentTransactionEntity;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransactionEntity, UUID> {

    List<PaymentTransactionEntity> findByRepaymentScheduleUid(UUID repaymentScheduleUid);

}
