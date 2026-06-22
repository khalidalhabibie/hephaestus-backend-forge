package com.example.jpabackend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.jpabackend.dto.request.UpdateRepaymentScheduleStatusRequest;
import com.example.jpabackend.dto.response.RepaymentScheduleResponse;
import com.example.jpabackend.entity.RepaymentScheduleEntity;
import com.example.jpabackend.enums.RepaymentStatus;
import com.example.jpabackend.exception.LoanApplicationNotFoundException;
import com.example.jpabackend.exception.RepaymentScheduleNotFoundException;
import com.example.jpabackend.repository.LoanApplicationRepository;
import com.example.jpabackend.repository.RepaymentScheduleRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class RepaymentScheduleService {
    private final RepaymentScheduleRepository repaymentScheduleRepository;
    private final LoanApplicationRepository loanApplicationRepository;

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

    public List<RepaymentScheduleResponse> getAll() {
        return repaymentScheduleRepository.findAll()
            .stream()
            .map(this::mapToResponse)
            .toList();
    }
    
    @Transactional(readOnly = true)
    public RepaymentScheduleResponse getScheduleById(Long id) {
        RepaymentScheduleEntity schedule = repaymentScheduleRepository
            .findByIdWithLoanApplication(id)
            .orElseThrow(() -> new RepaymentScheduleNotFoundException(
                "Repayment schedule " + id + " not found"));
        return mapToResponse(schedule);
    }

    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponse> getSchedulesByLoanApplication(Long loanApplicationId) {
        if (!loanApplicationRepository.existsById(loanApplicationId)) {
            throw new LoanApplicationNotFoundException(
                "Loan application " + loanApplicationId + " not found");
        }
        return repaymentScheduleRepository
            .findByLoanApplicationId(loanApplicationId)
            .stream()
            .map(this::mapToResponse)
            .toList();
    }

    public List<RepaymentScheduleResponse> getByStatus(RepaymentStatus status) {
        return repaymentScheduleRepository.findByStatus(status)
            .stream()
            .map(this::mapToResponse)
            .toList();
    }

    @Transactional
    public RepaymentScheduleResponse updateScheduleStatus(Long id, UpdateRepaymentScheduleStatusRequest request) {
        RepaymentScheduleEntity schedule = repaymentScheduleRepository.findById(id)
            .orElseThrow(() -> new RepaymentScheduleNotFoundException(
                "Repayment schedule " + id + " not found"));
            schedule.setStatus(request.getStatus());
        return mapToResponse(schedule);
    }

    @Transactional
    public void deleteSchedule(Long id) {
        RepaymentScheduleEntity schedule = repaymentScheduleRepository.findById(id)
            .orElseThrow(() -> new RepaymentScheduleNotFoundException(
                "Repayment schedule " + id + " not found"));
            schedule.setDeleted(true);
        repaymentScheduleRepository.save(schedule);
    }
}