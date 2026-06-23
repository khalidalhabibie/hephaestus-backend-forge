package com.fif.finance_training.service;

import com.fif.finance_training.dto.RepaymentScheduleResponse;
import com.fif.finance_training.entity.LoanApplicationEntity;
import com.fif.finance_training.entity.RepaymentScheduleEntity;
import com.fif.finance_training.exception.RepaymentScheduleNotFoundException;
import com.fif.finance_training.web.StructuredLogger;
import com.fif.finance_training.repository.RepaymentScheduleRepository;
import com.fif.finance_training.entity.enums.RepaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepaymentScheduleService {

    private final RepaymentScheduleRepository repaymentScheduleRepository;
    private final StructuredLogger logger;

    @Value("${loan.interest.annual-rate}")
    private BigDecimal annualInterestRate;

    /**
     * Generate repayment schedules saat loan DISBURSED.
     * 
     * logger.info → karena ini alur normal, bukan error
     * "SCHEDULE_GENERATE_ATTEMPT" → event ID untuk tracking
     * "loanId" → data yang mau di-log (bukan scheduleId, karena schedule belum ada)
     */
    @Transactional
    public void generateSchedulesForLoan(LoanApplicationEntity loan) {
        // Log: mau mulai generate schedule
        logger.info("SCHEDULE_GENERATE_ATTEMPT", "Generating repayment schedules for loan",
                "loanId", loan.getId().toString(),
                "loanAmount", loan.getLoanAmount().toString(),
                "tenorMonth", loan.getTenorMonth().toString(),
                "annualRate", annualInterestRate.toString());

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
        
        // Log: berhasil generate
        logger.info("SCHEDULE_GENERATED", "Repayment schedules generated successfully",
                "loanId", loan.getId().toString(),
                "scheduleCount", String.valueOf(schedules.size()),
                "principalAmount", principalAmount.toString(),
                "interestAmount", interestAmount.toString(),
                "totalAmount", totalAmount.toString());
    }

    /**
     * Get schedules by loan ID.
     * Read-only, nggak perlu log bisnis (opsional).
     */
    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponse> getSchedulesByLoanId(Long loanId) {
        return repaymentScheduleRepository.findByLoanApplicationId(loanId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get schedules by loan ID + status filter.
     */
    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponse> getSchedulesByLoanIdAndStatus(Long loanId, String status) {
        RepaymentStatus repaymentStatus = RepaymentStatus.valueOf(status.toUpperCase());
        return repaymentScheduleRepository.findByLoanApplicationIdAndStatus(loanId, repaymentStatus).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get single schedule by ID.
     * Kalau nggak ketemu → throw exception → nanti di-handle GlobalExceptionHandler
     */
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