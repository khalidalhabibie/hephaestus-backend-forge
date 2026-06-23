package com.example.training.service;

import com.example.training.dto.RepaymentScheduleResponse;
import com.example.training.entity.RepaymentScheduleEntity;
import com.example.training.enums.RepaymentStatus;
import com.example.training.exception.LoanApplicationNotFoundException;
import com.example.training.exception.RepaymentScheduleNotFoundException;
import com.example.training.repository.LoanApplicationRepository;
import com.example.training.repository.RepaymentScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RepaymentScheduleService {

    private final RepaymentScheduleRepository repaymentScheduleRepository;
    private final LoanApplicationRepository loanApplicationRepository;

    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponse> findByLoanApplicationId(Long loanApplicationId) {
        log.info("Fetching repayment schedules for loan: {}", loanApplicationId);
        if (!loanApplicationRepository.existsById(loanApplicationId)) {
            log.warn("Loan application not found: id={}", loanApplicationId);
            throw new LoanApplicationNotFoundException("Loan application not found with id: " + loanApplicationId);
        }
        List<RepaymentScheduleResponse> result = repaymentScheduleRepository.findByLoanApplicationId(loanApplicationId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        log.debug("Found {} repayment schedules for loan: {}", result.size(), loanApplicationId);
        return result;
    }

    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponse> findByStatus(RepaymentStatus param){
        log.info("Fetching repayment schedules by status: {}", param);
        List<RepaymentScheduleResponse> result = repaymentScheduleRepository.findByStatus(param).stream().map(this::toResponse).collect(Collectors.toList());
        log.debug("Found {} repayment schedules with status: {}", result.size(), param);
        return result;
    }

    @Transactional(readOnly = true)
    public RepaymentScheduleResponse findById(Long id) {
        log.info("Fetching repayment schedule by id: {}", id);
        RepaymentScheduleEntity entity = repaymentScheduleRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Repayment schedule not found: id={}", id);
                    return new RepaymentScheduleNotFoundException("Repayment schedule not found with id: " + id);
                });
        log.debug("Repayment schedule found: id={}, status={}", entity.getId(), entity.getStatus());
        return toResponse(entity);
    }

    private RepaymentScheduleResponse toResponse(RepaymentScheduleEntity entity) {
        return RepaymentScheduleResponse.builder()
                .id(entity.getId())
                .installmentNumber(entity.getInstallmentNumber())
                .dueDate(entity.getDueDate())
                .principalAmount(entity.getPrincipalAmount())
                .interestAmount(entity.getInterestAmount())
                .totalAmount(entity.getTotalAmount())
                .status(entity.getStatus())
                .loanApplicationId(entity.getLoanApplicationId())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
