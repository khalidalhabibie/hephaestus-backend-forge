package com.adnan.loanappspringsql.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adnan.loanappspringsql.dto.CustomerSummaryResponse;
import com.adnan.loanappspringsql.dto.CreateLoanApplicationRequest;
import com.adnan.loanappspringsql.dto.LoanApplicationResponse;
import com.adnan.loanappspringsql.dto.LoanStatusSummaryResponse;
import com.adnan.loanappspringsql.dto.OutstandingAmountResponse;
import com.adnan.loanappspringsql.dto.UpdateLoanStatusRequest;
import com.adnan.loanappspringsql.dto.pagination.PageResponse;
import com.adnan.loanappspringsql.enums.LoanStatusEnum;
import com.adnan.loanappspringsql.enums.RepaymentStatusEnum;
import com.adnan.loanappspringsql.exception.BadRequestException;
import com.adnan.loanappspringsql.exception.NotFoundException;
import com.adnan.loanappspringsql.model.Customer;
import com.adnan.loanappspringsql.model.LoanApplication;
import com.adnan.loanappspringsql.model.RepaymentSchedule;
import com.adnan.loanappspringsql.repository.CustomerRepository;
import com.adnan.loanappspringsql.repository.LoanApplicationRepository;
import com.adnan.loanappspringsql.repository.RepaymentScheduleRepository;
import com.adnan.loanappspringsql.service.LoanApplicationService;
import com.adnan.loanappspringsql.utils.LogUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LoanApplicationServiceImpl implements LoanApplicationService {
        @Value("${loan.interest.annual-rate}")
        private BigDecimal annualInterestRate;

        private final LoanApplicationRepository loanApplicationRepository;
        private final CustomerRepository customerRepository;
        private final RepaymentScheduleRepository repaymentScheduleRepository;

        @Override
        public LoanApplicationResponse create(CreateLoanApplicationRequest request) {
                log.info(LogUtil.format(
                                "loan_application_create_requested"));
                Customer customer = customerRepository.findById(request.getCustomerId())
                                .orElseThrow(() -> {
                                        log.warn(LogUtil.format(
                                                        "customer_not_found",
                                                        "customerId", request.getCustomerId()));
                                        return new NotFoundException(
                                                        "Customer not found with id: " + request.getCustomerId());
                                });

                LoanApplication loan = LoanApplication.builder()
                                .customer(customer)
                                .loanAmount(request.getLoanAmount())
                                .tenorMonth(request.getTenorMonth())
                                .purpose(request.getPurpose())
                                .status(LoanStatusEnum.SUBMITTED)
                                .build();

                loanApplicationRepository.save(loan);
                log.info(LogUtil.format(
                                "loan_application_creted",
                                "loanId", loan.getId(),
                                "customerId", customer.getId(),
                                "status", loan.getStatus()));

                return mapToResponse(loan);
        }

        @Override
        @Transactional(readOnly = true)
        public LoanApplicationResponse findById(Long id) {
                log.info(LogUtil.format(
                                "loan_application_lookup",
                                "loanId", id));
                LoanApplication loan = loanApplicationRepository
                                .findByIdWithCustomer(id)
                                .orElseThrow(() -> {
                                        log.warn(LogUtil.format(
                                                        "loan_application_not_found",
                                                        "loanId", id));
                                        return new NotFoundException(
                                                        "Loan application not found with id: " + id);
                                });
                log.info(LogUtil.format(
                                "loan_application_found",
                                "loanId", id));

                return mapToResponse(loan);
        }

        @Override
        @Transactional(readOnly = true)
        public PageResponse<LoanApplicationResponse> findAll(LoanStatusEnum status, LocalDate startDate,
                        LocalDate endDate, int page, int size) {
                log.info(LogUtil.format(
                                "loan_application_search",
                                "hasStatus", status != null,
                                "hasDateRange", startDate != null && endDate != null));

                Pageable pageable = PageRequest.of(page, size);
                ZonedDateTime start = startDate == null ? null : startDate.atStartOfDay(ZoneId.systemDefault());
                ZonedDateTime end = endDate == null ? null
                                : endDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault());

                Page<LoanApplication> loanPage;
                if (status != null && startDate != null && endDate != null) {
                        loanPage = loanApplicationRepository.findByStatusAndCreatedAtBetween(status, start, end,
                                        pageable);
                } else if (status != null) {
                        loanPage = loanApplicationRepository.findByStatus(status, pageable);
                } else if (startDate != null && endDate != null) {
                        loanPage = loanApplicationRepository.findByCreatedAtBetween(start, end, pageable);
                } else {
                        loanPage = loanApplicationRepository.findAll(pageable);
                }
                log.info(LogUtil.format(
                                "loan_application_search_completed",
                                "page", loanPage.getNumber(),
                                "size", loanPage.getSize(),
                                "totalElements", loanPage.getTotalElements(),
                                "totalPages", loanPage.getTotalPages()));

                return toPageResponse(loanPage);
        }

        @Override
        @Transactional(readOnly = true)
        public List<LoanApplicationResponse> findByCustomerId(Long customerId) {
                log.info(LogUtil.format(
                                "loan_application_find_by_customer",
                                "customerId", customerId));
                List<LoanApplicationResponse> loans = loanApplicationRepository
                                .findByCustomerId(customerId)
                                .stream()
                                .map(this::mapToResponse)
                                .toList();
                log.info(LogUtil.format(
                                "loan_application_find_by_customer_completed",
                                "customerId", customerId,
                                "total", loans.size()));

                return loans;
        }

        @Override
        public LoanApplicationResponse updateStatus(
                        Long id,
                        UpdateLoanStatusRequest request) {
                log.info(LogUtil.format(
                                "loan_status_update_requested",
                                "loanId", id,
                                "newStatus", request.getStatus()));

                LoanApplication loan = loanApplicationRepository
                                .findByIdWithCustomer(id)
                                .orElseThrow(() -> {
                                        log.warn(LogUtil.format(
                                                        "loan_application_not_found",
                                                        "loanId", id));
                                        return new NotFoundException("Loan application not found with id: " + id);
                                });
                validateStatusFlow(loan, request.getStatus());

                LoanStatusEnum oldStatus = loan.getStatus();
                loan.setStatus(request.getStatus());
                if (request.getStatus() == LoanStatusEnum.DISBURSED) {
                        generateRepaymentSchedule(loan);
                }

                loanApplicationRepository.save(loan);
                log.info(LogUtil.format(
                                "loan_status_updated",
                                "loanId", loan.getId(),
                                "oldStatus", oldStatus,
                                "newStatus", loan.getStatus()));

                return mapToResponse(loan);
        }

        @Override
        @Transactional(readOnly = true)
        public List<LoanStatusSummaryResponse> getLoanSummaryByStatus() {
                log.info(LogUtil.format(
                                "loan_summary_requested"));

                List<LoanStatusSummaryResponse> summary = loanApplicationRepository
                                .countLoanByStatus()
                                .stream()
                                .map(row -> LoanStatusSummaryResponse.builder()
                                                .status((LoanStatusEnum) row[0])
                                                .total((Long) row[1])
                                                .build())
                                .toList();
                log.info(LogUtil.format(
                                "loan_summary_completed",
                                "totalStatus", summary.size()));

                return summary;
        }

        @Override
        @Transactional(readOnly = true)
        public OutstandingAmountResponse getOutstandingAmountCustomer(Long customerId) {
                log.info(LogUtil.format(
                                "outstanding_amount_requested",
                                "customerId", customerId));

                Customer customer = customerRepository.findById(customerId)
                                .orElseThrow(() -> {
                                        log.warn(LogUtil.format(
                                                        "customer_not_found",
                                                        "customerId", customerId));
                                        return new NotFoundException("Customer not found");
                                });

                BigDecimal totalRepayment = loanApplicationRepository.getTotalRepayment(customerId);
                BigDecimal totalPaid = loanApplicationRepository.getTotalPaid(customerId);
                BigDecimal outstanding = totalRepayment.subtract(totalPaid);
                log.info(LogUtil.format(
                                "outstanding_amount_completed",
                                "customerId", customerId,
                                "hasOutstanding", outstanding.compareTo(BigDecimal.ZERO) > 0));

                return OutstandingAmountResponse.builder()
                                .customerId(customer.getId())
                                .customerName(customer.getFullName())
                                .outstandingAmount(outstanding)
                                .build();
        }

        // Helper
        private void validateStatusFlow(LoanApplication loan, LoanStatusEnum newStatus) {
                LoanStatusEnum currentStatus = loan.getStatus();
                switch (currentStatus) {
                        case SUBMITTED -> {
                                if (newStatus != LoanStatusEnum.APPROVED
                                                && newStatus != LoanStatusEnum.REJECTED) {
                                        throw new BadRequestException(
                                                        "Invalid loan status transition");
                                }
                        }
                        case APPROVED -> {
                                if (newStatus != LoanStatusEnum.DISBURSED) {
                                        throw new BadRequestException(
                                                        "Invalid loan status transition");
                                }
                        }
                        case DISBURSED -> {
                                if (newStatus != LoanStatusEnum.CLOSED) {
                                        throw new BadRequestException(
                                                        "Invalid loan status transition");
                                }
                                boolean hasUnpaidSchedule = repaymentScheduleRepository
                                                .existsByLoanApplicationIdAndStatus(
                                                                loan.getId(),
                                                                RepaymentStatusEnum.UNPAID);
                                if (hasUnpaidSchedule) {
                                        throw new BadRequestException(
                                                        "Loan cannot be closed because there are unpaid repayment schedules");
                                }
                        }
                        case REJECTED, CLOSED ->
                                throw new BadRequestException(
                                                "Status cannot be changed");
                        default ->
                                throw new BadRequestException(
                                                "Invalid loan status transition");
                }
        }

        private void generateRepaymentSchedule(LoanApplication loan) {
                if (!repaymentScheduleRepository.findByLoanApplicationId(loan.getId()).isEmpty()) {
                        throw new BadRequestException(
                                        "Repayment schedule has already been generated");
                }

                BigDecimal monthlyInterestRate = annualInterestRate.divide(
                                BigDecimal.valueOf(12),
                                10,
                                RoundingMode.HALF_UP);
                BigDecimal principalAmount = loan.getLoanAmount().divide(
                                BigDecimal.valueOf(loan.getTenorMonth()),
                                2,
                                RoundingMode.HALF_UP);
                BigDecimal interestAmount = loan.getLoanAmount()
                                .multiply(monthlyInterestRate)
                                .setScale(2, RoundingMode.HALF_UP);

                BigDecimal totalAmount = principalAmount.add(interestAmount);
                LocalDate disbursementDate = LocalDate.now();
                List<RepaymentSchedule> schedules = new ArrayList<>();

                for (int i = 1; i <= loan.getTenorMonth(); i++) {
                        RepaymentSchedule schedule = RepaymentSchedule.builder()
                                        .loanApplication(loan)
                                        .installmentNumber(i)
                                        .dueDate(disbursementDate.plusMonths(i - 1))
                                        .principalAmount(principalAmount)
                                        .interestAmount(interestAmount)
                                        .totalAmount(totalAmount)
                                        .status(RepaymentStatusEnum.UNPAID)
                                        .build();
                        schedules.add(schedule);
                }

                repaymentScheduleRepository.saveAll(schedules);
        }

        private LoanApplicationResponse mapToResponse(LoanApplication loan) {
                return LoanApplicationResponse.builder()
                                .id(loan.getId())
                                .loanAmount(loan.getLoanAmount())
                                .tenorMonth(loan.getTenorMonth())
                                .purpose(loan.getPurpose())
                                .status(loan.getStatus())
                                .customer(CustomerSummaryResponse.builder()
                                                .id(loan.getCustomer().getId())
                                                .fullName(loan.getCustomer().getFullName())
                                                .nik(loan.getCustomer().getNik())
                                                .email(loan.getCustomer().getEmail())
                                                .build())
                                .build();
        }

        private PageResponse<LoanApplicationResponse> toPageResponse(Page<LoanApplication> loanPage) {
                return PageResponse.<LoanApplicationResponse>builder()
                                .content(
                                                loanPage.getContent()
                                                                .stream()
                                                                .map(this::mapToResponse)
                                                                .toList())
                                .page(loanPage.getNumber())
                                .size(loanPage.getSize())
                                .totalElements(loanPage.getTotalElements())
                                .totalPages(loanPage.getTotalPages())
                                .last(loanPage.isLast())
                                .build();
        }
}
