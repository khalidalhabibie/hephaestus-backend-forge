package com.example.demoSpringbootDatabase.service;

import com.example.demoSpringbootDatabase.dto.RepaymentScheduleResponse;
import com.example.demoSpringbootDatabase.entity.RepaymentScheduleEntity;
import com.example.demoSpringbootDatabase.exception.RepaymentScheduleNotFoundException;
import com.example.demoSpringbootDatabase.repository.RepaymentScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class RepaymentScheduleService {
    private final RepaymentScheduleRepository scheduleRepository;
    public RepaymentScheduleService(RepaymentScheduleRepository scheduleRepository) { this.scheduleRepository = scheduleRepository; }

    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponse> getByLoanId(Long loanId) {
        return scheduleRepository.findByLoanApplicationId(loanId).stream().map(this::mapToResponse).toList();
    }

    @Transactional(readOnly = true)
    public RepaymentScheduleResponse getById(Long id) {
        return scheduleRepository.findById(id).map(this::mapToResponse).orElseThrow(() -> new RepaymentScheduleNotFoundException(id));
    }

    private RepaymentScheduleResponse mapToResponse(RepaymentScheduleEntity schedule) {
        return RepaymentScheduleResponse.builder().id(schedule.getId()).installmentNumber(schedule.getInstallmentNumber())
                .dueDate(schedule.getDueDate()).principalAmount(schedule.getPrincipalAmount())
                .interestAmount(schedule.getInterestAmount()).totalAmount(schedule.getTotalAmount())
                .status(schedule.getStatus()).build();
    }
}