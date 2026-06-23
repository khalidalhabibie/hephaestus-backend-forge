package com.example.demoSpringbootDatabase.service;

import com.example.demoSpringbootDatabase.dto.RepaymentScheduleResponse;
import com.example.demoSpringbootDatabase.entity.RepaymentScheduleEntity;
import com.example.demoSpringbootDatabase.exception.RepaymentScheduleNotFoundException;
import com.example.demoSpringbootDatabase.repository.RepaymentScheduleRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class RepaymentScheduleService {
    private final RepaymentScheduleRepository scheduleRepository;
    
    public RepaymentScheduleService(RepaymentScheduleRepository scheduleRepository) { 
        this.scheduleRepository = scheduleRepository; 
    }

    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponse> getByLoanId(Long loanId) {
        log.debug("Fetching all repayment schedules for loan ID: {}", loanId);
        return scheduleRepository.findByLoanApplicationId(loanId).stream().map(this::mapToResponse).toList();
    }

    @Transactional(readOnly = true)
    public RepaymentScheduleResponse getById(Long id) {
        return scheduleRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> {
                    // ✅ STRUCTURED ERROR LOG: Tercatat hanya saat ada anomali ID tidak ditemukan
                    log.error("{\"event\":\"UNEXPECTED_ERROR\", \"message\":\"Repayment schedule with ID {} not found\"}", id);
                    return new RepaymentScheduleNotFoundException(id);
                });
    }

    @Transactional(readOnly = true)
    public BigDecimal getCustomerOutstanding(Long customerId) {
        log.debug("Calculating outstanding balance metric for customer ID: {}", customerId);
        return scheduleRepository.getOutstandingAmountByCustomerId(customerId);
    }

    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponse> getByLoanIdAndStatus(Long loanId, String status) {
        log.debug("Filtering repayment schedules for loan ID: {} with status: {}", loanId, status);
        List<RepaymentScheduleEntity> entities;
        
        if (status != null && !status.isBlank()) {
            entities = scheduleRepository.findByLoanApplicationIdAndStatus(loanId, status.toUpperCase());
        } else {
            entities = scheduleRepository.findByLoanApplicationId(loanId);
        }
        
        return entities.stream().map(this::mapToResponse).toList();
    }

    private RepaymentScheduleResponse mapToResponse(RepaymentScheduleEntity schedule) {
        return RepaymentScheduleResponse.builder()
                .id(schedule.getId())
                .installmentNumber(schedule.getInstallmentNumber())
                .dueDate(schedule.getDueDate())
                .principalAmount(schedule.getPrincipalAmount())
                .interestAmount(schedule.getInterestAmount())
                .totalAmount(schedule.getTotalAmount()) 
                .status(schedule.getStatus())
                .build();
    }
}