package com.example.main.impl;

import com.example.main.models.LoanApplication;
import com.example.main.repositories.LoanApplicationRepository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class LoanApplicationRepositoryImpl implements LoanApplicationRepository {

    private final Map<Long, LoanApplication> loanDatabase = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public LoanApplication save(LoanApplication loanApplication) {
        if (loanApplication.getId() == null) {
            loanApplication.setId(idGenerator.getAndIncrement());
        }
        loanDatabase.put(loanApplication.getId(), loanApplication);
        return loanApplication;
    }

    @Override
    public List<LoanApplication> findAll() {
        return new ArrayList<>(loanDatabase.values());
    }

    @Override
    public Optional<LoanApplication> findById(Long id) {
        return Optional.ofNullable(loanDatabase.get(id));
    }
}