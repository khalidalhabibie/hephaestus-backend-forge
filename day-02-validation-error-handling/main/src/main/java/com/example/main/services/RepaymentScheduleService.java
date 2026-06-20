package com.example.main.services;

import com.example.main.dto.response.RepaymentScheduleResponse;
import com.example.main.entity.LoanApplicationEntity;
import com.example.main.entity.RepaymentScheduleEntity;
import com.example.main.enums.ScheduleStatus;
import com.example.main.exceptions.NotFoundException;
import com.example.main.repositories.RepaymentScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
public class RepaymentScheduleService {

    private final RepaymentScheduleRepository repaymentScheduleRepository;

    public RepaymentScheduleService(RepaymentScheduleRepository repaymentScheduleRepository) {
        this.repaymentScheduleRepository = repaymentScheduleRepository;
    }

    @Transactional(readOnly = true)
    public RepaymentScheduleResponse getRepaymentScheduleById(Long id) {
        RepaymentScheduleEntity schedule = repaymentScheduleRepository.findByIdWithLoanApplication(id)
                .orElseThrow(() -> new NotFoundException("Repayment schedule not found with ID: " + id));

        return mapToScheduleResponse(schedule);
    }

    @Transactional
    public void createRepaymentSchedules(LoanApplicationEntity loan) {
        int tenor = loan.getTenorMonth();
        BigDecimal loanAmount = loan.getLoanAmount();

        // 1. Hitung Angsuran Pokok per bulan (Pokok / Tenor)
        BigDecimal monthlyPrincipal = loanAmount.divide(BigDecimal.valueOf(tenor), 2, RoundingMode.HALF_UP);

        // 2. Hitung Angsuran Bunga per bulan (Contoh: Flat 2% dari total pokok)
        BigDecimal monthlyInterest = loanAmount.multiply(BigDecimal.valueOf(0.02)).setScale(2, RoundingMode.HALF_UP);

        // 3. Hitung Total Angsuran per bulan (Pokok + Bunga)
        BigDecimal monthlyTotal = monthlyPrincipal.add(monthlyInterest);

        // 4. Set tanggal jatuh tempo pertama (1 bulan dari sekarang)
        LocalDate nextDueDate = LocalDate.now().plusMonths(1);

        // 5. Looping pembuatan jadwal berdasarkan jumlah tenor
        for (int i = 1; i <= tenor; i++) {
            RepaymentScheduleEntity schedule = new RepaymentScheduleEntity();
            schedule.setLoanApplication(loan);
            schedule.setInstallmentNumber(i);
            schedule.setDueDate(nextDueDate);
            schedule.setPrincipalAmount(monthlyPrincipal);
            schedule.setInterestAmount(monthlyInterest);
            schedule.setTotalAmount(monthlyTotal);
            schedule.setStatus(ScheduleStatus.UNPAID);

            repaymentScheduleRepository.save(schedule);

            // Majukan tanggal jatuh tempo untuk bulan berikutnya
            nextDueDate = nextDueDate.plusMonths(1);
        }
    }

    private RepaymentScheduleResponse mapToScheduleResponse(RepaymentScheduleEntity schedule) {
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