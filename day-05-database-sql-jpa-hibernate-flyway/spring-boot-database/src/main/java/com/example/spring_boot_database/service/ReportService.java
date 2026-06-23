package com.example.spring_boot_database.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring_boot_database.dto.LoanApplicationReportProjection;
import com.example.spring_boot_database.entity.Status;
import com.example.spring_boot_database.exception.BadRequestException;
import com.example.spring_boot_database.repository.LoanApplicationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final LoanApplicationRepository loanApplicationRepository;

    @Transactional(readOnly = true)
    public List<LoanApplicationReportProjection> getLoanApplicationReport(
            Status status,
            LocalDate startDate,
            LocalDate endDate) {

        validateDateRange(startDate, endDate);

        LocalDateTime startDateTime = startDate != null
                ? startDate.atStartOfDay()
                : null;

        LocalDateTime endDateTimeExclusive = endDate != null
                ? endDate.plusDays(1).atStartOfDay()
                : null;

        String statusValue = status != null
                ? status.name()
                : null;

        return loanApplicationRepository.findLoanApplicationReport(
                statusValue,
                startDateTime,
                endDateTimeExclusive
        );
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new BadRequestException("startDate cannot be after endDate");
        }
    }
}