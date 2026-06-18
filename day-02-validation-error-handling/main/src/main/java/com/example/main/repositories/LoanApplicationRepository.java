package com.example.main.repositories;

import java.util.List;
import java.util.Optional;

import com.example.main.models.LoanApplication;

public interface LoanApplicationRepository {
    LoanApplication save(LoanApplication loanApplication);
    List<LoanApplication> findAll();
    Optional<LoanApplication> findById(Long id);
}