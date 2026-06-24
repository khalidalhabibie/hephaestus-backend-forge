// Business logic repayment: get by loan, filter by status (PAID/UNPAID).

package com.example.training.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.training.DTO.RepaymentScheduleResponse;
import com.example.training.Entity.RepaymentScheduleEntity;
import com.example.training.Exception.LoanApplicationNotFoundException;
import com.example.training.Exception.RepaymentScheduleNotFoundException;
import com.example.training.Repository.LoanApplicationRepository;
import com.example.training.Repository.RepaymentScheduleRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepaymentScheduleService {

    private final RepaymentScheduleRepository repaymentScheduleRepository;
    private final LoanApplicationRepository loanApplicationRepository;

    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponse> getByLoanApplicationId(Long loanApplicationId) {
        log.info("event=repayment_list_by_loan_requested, loan_id={}", loanApplicationId); 
        
        if (!loanApplicationRepository.existsById(loanApplicationId)) {
            log.warn("event=repayment_list_by_loan_failed, reason=loan_not_found, loan_id={}", loanApplicationId);  // ← TAMBAHKAN
            throw new LoanApplicationNotFoundException(loanApplicationId);
        }
        return repaymentScheduleRepository.findByLoanApplicationId(loanApplicationId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponse> getByLoanIdAndStatus(Long loanApplicationId, String status) {
        log.info("event=repayment_list_by_loan_and_status_requested, loan_id={}, status={}", 
                loanApplicationId, status);
        
        if (!loanApplicationRepository.existsById(loanApplicationId)) {
            log.warn("event=repayment_list_by_loan_and_status_failed, reason=loan_not_found, loan_id={}", 
                    loanApplicationId);
            throw new LoanApplicationNotFoundException(loanApplicationId);
        }
        RepaymentScheduleEntity.ScheduleStatus scheduleStatus = null;
        if (status != null && !status.isBlank()) {
            scheduleStatus = RepaymentScheduleEntity.ScheduleStatus.valueOf(status.toUpperCase());
        }
        return repaymentScheduleRepository.findByLoanIdAndStatus(loanApplicationId, scheduleStatus).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RepaymentScheduleResponse getById(Long id) {
        log.info("event=repayment_fetch_requested, schedule_id={}", id);
        
        RepaymentScheduleEntity entity = repaymentScheduleRepository.findByIdWithLoanApplication(id)
                .orElseThrow(() -> {
                    log.warn("event=repayment_fetch_failed, reason=not_found, schedule_id={}", id);
                    return new RepaymentScheduleNotFoundException(id);
                });
        return mapToResponse(entity);
    }

    private RepaymentScheduleResponse mapToResponse(RepaymentScheduleEntity entity) {
        return RepaymentScheduleResponse.builder()
                .id(entity.getId())
                .installmentNumber(entity.getInstallmentNumber())
                .dueDate(entity.getDueDate())
                .principalAmount(entity.getPrincipalAmount())
                .interestAmount(entity.getInterestAmount())
                .totalAmount(entity.getTotalAmount())
                .status(entity.getStatus() != null ? entity.getStatus().name() : null)
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}