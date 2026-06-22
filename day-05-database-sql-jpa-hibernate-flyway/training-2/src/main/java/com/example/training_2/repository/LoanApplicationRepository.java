package com.example.training_2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.training_2.entity.LoanApplication;
import com.example.training_2.entity.LoanApplicationStatus;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    List<LoanApplication> findByStatusNot(LoanApplicationStatus status);

    List<LoanApplication> findByStatus(LoanApplicationStatus status);
}
