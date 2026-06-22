package com.fif.finance_training.service;

import com.fif.finance_training.dto.RepaymentScheduleResponse;
import com.fif.finance_training.entity.LoanApplicationEntity;
import com.fif.finance_training.entity.RepaymentScheduleEntity;
import com.fif.finance_training.exception.RepaymentScheduleNotFoundException;
import com.fif.finance_training.repository.RepaymentScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fif.finance_training.entity.enums.RepaymentStatus;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepaymentScheduleService {

    private final RepaymentScheduleRepository repaymentScheduleRepository;

    @Value("${loan.interest.annual-rate}")
    private BigDecimal annualInterestRate;

    @Transactional
    public void generateSchedulesForLoan(LoanApplicationEntity loan) {
        BigDecimal monthlyInterestRate = annualInterestRate.divide(BigDecimal.valueOf(12), 10, RoundingMode.HALF_UP);
        
        BigDecimal loanAmount = loan.getLoanAmount();
        BigDecimal tenor = BigDecimal.valueOf(loan.getTenorMonth());

        BigDecimal principalAmount = loanAmount.divide(tenor, 2, RoundingMode.HALF_UP);
        BigDecimal interestAmount = loanAmount.multiply(monthlyInterestRate).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalAmount = principalAmount.add(interestAmount);

        List<RepaymentScheduleEntity> schedules = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for (int i = 1; i <= loan.getTenorMonth(); i++) {
            RepaymentScheduleEntity schedule = RepaymentScheduleEntity.builder()
                    .loanApplication(loan)
                    .installmentNumber(i)
                    .dueDate(currentDate.plusMonths(i))
                    .principalAmount(principalAmount)
                    .interestAmount(interestAmount)
                    .totalAmount(totalAmount)
                    .status(RepaymentStatus.UNPAID)
                    .build();
            schedules.add(schedule);
        }
        repaymentScheduleRepository.saveAll(schedules);
    }

    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponse> getSchedulesByLoanId(Long loanId) {
        return repaymentScheduleRepository.findByLoanApplicationId(loanId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponse> getSchedulesByLoanIdAndStatus(Long loanId, String status) {
        RepaymentStatus repaymentStatus = RepaymentStatus.valueOf(status.toUpperCase());
        return repaymentScheduleRepository.findByLoanApplicationIdAndStatus(loanId, repaymentStatus).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RepaymentScheduleResponse getScheduleById(Long id) {
        RepaymentScheduleEntity entity = repaymentScheduleRepository.findById(id)
                .orElseThrow(() -> new RepaymentScheduleNotFoundException("Schedule not found with id: " + id));
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
                .status(entity.getStatus().name())
                .build();
    }
}