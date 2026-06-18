package com.example.day2.service;

import com.example.day2.dto.CreateLoanApplicationRequest;
import com.example.day2.dto.LoanApplicationResponse;
import com.example.day2.enum_auth.LoanStatus;
import com.example.day2.model.LoanApplication;
import com.example.day2.repository.CustomerRepository;
import com.example.day2.repository.LoanApplicationRepository;
import com.example.day2.utils.CustomerNotFoundException;
import com.example.day2.utils.LoanApplicationNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final CustomerRepository customerRepository;

    private static final BigDecimal MANAGER_APPROVAL_THRESHOLD = new BigDecimal("10000000");

    @Transactional
    public LoanApplicationResponse create(CreateLoanApplicationRequest request) {
        // Validate customer exists
        customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer with ID " + request.getCustomerId() + " not found"));

        LoanApplication loan = LoanApplication.builder()
                .customerId(request.getCustomerId())
                .loanAmount(request.getLoanAmount())
                .tenorMonth(request.getTenorMonth())
                .purpose(request.getPurpose())
                .status(LoanStatus.SUBMITTED)
                .build();

        return toResponse(loanApplicationRepository.save(loan));
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> findAll() {
        return loanApplicationRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> findByStatus(LoanStatus status) {
        return loanApplicationRepository.findByStatus(status)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> findByCustomerId(Long customerId) {
        return loanApplicationRepository.findByCustomerId(customerId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LoanApplicationResponse findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> findByStatusAndCustomerId(LoanStatus status, Long customerId) {
        return loanApplicationRepository.findByStatusAndCustomerId(status, customerId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // @Transactional
    // public LoanApplicationResponse approve(Long id) {
    //     LoanApplication loan = getOrThrow(id);
    //     if (loan.getStatus() != LoanStatus.SUBMITTED) {
    //         throw new IllegalStateException(
    //             "Cannot approve loan application with status: " + loan.getStatus());
    //     }
    //     loan.setStatus(LoanStatus.APPROVED);
    //     return toResponse(loanApplicationRepository.save(loan));
    // }

    @Transactional
    public LoanApplicationResponse approve(Long id) {
        LoanApplication loan = getOrThrow(id);
        if (loan.getStatus() != LoanStatus.SUBMITTED) {
            throw new IllegalStateException(
                "Cannot approve loan application with status: " + loan.getStatus());
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isManager = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_MANAGER"));

        if (isManager && loan.getLoanAmount().compareTo(MANAGER_APPROVAL_THRESHOLD) > 0) {
            throw new AccessDeniedException(
                "MANAGER can only approve loans above " + MANAGER_APPROVAL_THRESHOLD);
        }

        loan.setStatus(LoanStatus.APPROVED);
        return toResponse(loanApplicationRepository.save(loan));
    }

    @Transactional
    public LoanApplicationResponse reject(Long id) {
        LoanApplication loan = getOrThrow(id);
        if (loan.getStatus() != LoanStatus.SUBMITTED) {
            throw new IllegalStateException(
                "Cannot reject loan application with status: " + loan.getStatus());
        }
        loan.setStatus(LoanStatus.REJECTED);
        return toResponse(loanApplicationRepository.save(loan));
    }

    @Transactional
    public LoanApplicationResponse cancel(Long id) {
        LoanApplication loan = getOrThrow(id);
        if (loan.getStatus() != LoanStatus.SUBMITTED) {
            throw new IllegalStateException(
                "Cannot cancel loan application with status: " + loan.getStatus());
        }
        loan.setStatus(LoanStatus.CANCELLED);
        return toResponse(loanApplicationRepository.save(loan));
    }

    private LoanApplication getOrThrow(Long id) {
        return loanApplicationRepository.findById(id)
                .orElseThrow(() -> new LoanApplicationNotFoundException(id));
    }

    private LoanApplicationResponse toResponse(LoanApplication loan) {
        return LoanApplicationResponse.builder()
                .id(loan.getId())
                .customerId(loan.getCustomerId())
                .loanAmount(loan.getLoanAmount())
                .tenorMonth(loan.getTenorMonth())
                .purpose(loan.getPurpose())
                .status(loan.getStatus())
                .build();
    }
}
