package com.example.training_2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.training_2.dto.CustomerResponse;
import com.example.training_2.dto.LoanApplicationResponse;
import com.example.training_2.entity.Customer;
import com.example.training_2.repository.LoanApplicationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanApplicationService {
    private final LoanApplicationRepository loanApplicationRepository;

    private LoanApplicationResponse mapToResponse(Customer customer) {
        return LoanApplicationResponse.builder().id(null)
    }

    private List<LoanApplicationResponse> getAll() {
        loanApplicationRepository.findAll().stream().map
    }
}
