package com.adnan.loanappspringsql.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adnan.loanappspringsql.dto.RepaymentScheduleResponse;
import com.adnan.loanappspringsql.exception.NotFoundException;
import com.adnan.loanappspringsql.model.RepaymentSchedule;
import com.adnan.loanappspringsql.repository.LoanApplicationRepository;
import com.adnan.loanappspringsql.repository.RepaymentScheduleRepository;
import com.adnan.loanappspringsql.service.RepaymentScheduleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RepaymentScheduleServiceImpl implements RepaymentScheduleService {
        private final RepaymentScheduleRepository repaymentScheduleRepository;
        private final LoanApplicationRepository loanApplicationRepository;

        @Override
        @Transactional(readOnly = true)
        public List<RepaymentScheduleResponse> findByLoanApplicationId(Long loanApplicationId) {
                if (!loanApplicationRepository.existsById(
                                loanApplicationId)) {

                        throw new NotFoundException(
                                        "Loan application not found with id: "
                                                        + loanApplicationId);
                }

                return repaymentScheduleRepository
                                .findByLoanApplicationId(loanApplicationId)
                                .stream()
                                .map(this::mapToResponse)
                                .toList();
        }

        @Override
        @Transactional(readOnly = true)
        public RepaymentScheduleResponse findById(Long id) {
                RepaymentSchedule schedule = repaymentScheduleRepository.findById(id)
                                .orElseThrow(() -> new NotFoundException(
                                                "Repayment schedule not found with id: "
                                                                + id));

                return mapToResponse(schedule);
        }

        private RepaymentScheduleResponse mapToResponse(
                        RepaymentSchedule schedule) {
                return RepaymentScheduleResponse.builder()
                                .id(schedule.getId())
                                .installmentNumber(
                                                schedule.getInstallmentNumber())
                                .dueDate(schedule.getDueDate())
                                .principalAmount(
                                                schedule.getPrincipalAmount())
                                .interestAmount(
                                                schedule.getInterestAmount())
                                .totalAmount(
                                                schedule.getTotalAmount())
                                .status(schedule.getStatus())
                                .build();
        }
}
