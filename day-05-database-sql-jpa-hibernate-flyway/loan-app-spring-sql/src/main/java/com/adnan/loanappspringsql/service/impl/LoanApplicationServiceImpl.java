package com.adnan.loanappspringsql.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adnan.loanappspringsql.dto.CustomerSummaryResponse;
import com.adnan.loanappspringsql.dto.CreateLoanApplicationRequest;
import com.adnan.loanappspringsql.dto.LoanApplicationResponse;
import com.adnan.loanappspringsql.dto.UpdateLoanStatusRequest;
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

import lombok.RequiredArgsConstructor;

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
    Customer customer = customerRepository.findById(request.getCustomerId())
        .orElseThrow(() -> new NotFoundException(
            "Customer not found with id: "
                + request.getCustomerId()));

    LoanApplication loan = LoanApplication.builder()
        .customer(customer)
        .loanAmount(request.getLoanAmount())
        .tenorMonth(request.getTenorMonth())
        .purpose(request.getPurpose())
        .status(LoanStatusEnum.SUBMITTED)
        .build();

    loanApplicationRepository.save(loan);

    return mapToResponse(loan);
  }

  @Override
  @Transactional(readOnly = true)
  public LoanApplicationResponse findById(Long id) {
    LoanApplication loan = loanApplicationRepository
        .findByIdWithCustomer(id)
        .orElseThrow(() -> new NotFoundException(
            "Loan application not found with id: " + id));

    return mapToResponse(loan);
  }

  @Override
  @Transactional(readOnly = true)
  public List<LoanApplicationResponse> findAll() {
    return loanApplicationRepository.findAll()
        .stream()
        .map(this::mapToResponse)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<LoanApplicationResponse> findByCustomerId(Long customerId) {
    return loanApplicationRepository
        .findByCustomerId(customerId)
        .stream()
        .map(this::mapToResponse)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<LoanApplicationResponse> findByStatus(
      LoanStatusEnum status) {
    return loanApplicationRepository
        .findByStatus(status)
        .stream()
        .map(this::mapToResponse)
        .toList();
  }

  @Override
  public LoanApplicationResponse updateStatus(
      Long id,
      UpdateLoanStatusRequest request) {
    LoanApplication loan = loanApplicationRepository
        .findByIdWithCustomer(id)
        .orElseThrow(() -> new NotFoundException(
            "Loan application not found with id: " + id));

    validateStatusFlow(
        loan,
        request.getStatus());

    loan.setStatus(request.getStatus());

    if (request.getStatus() == LoanStatusEnum.DISBURSED) {
      generateRepaymentSchedule(loan);
    }

    loanApplicationRepository.save(loan);

    return mapToResponse(loan);
  }

  // Helper
  private void validateStatusFlow(
      LoanApplication loan,
      LoanStatusEnum newStatus) {
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
    if (!repaymentScheduleRepository
        .findByLoanApplicationId(loan.getId())
        .isEmpty()) {
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

  private LoanApplicationResponse mapToResponse(
      LoanApplication loan) {
    return LoanApplicationResponse.builder()
        .id(loan.getId())
        .loanAmount(loan.getLoanAmount())
        .tenorMonth(loan.getTenorMonth())
        .purpose(loan.getPurpose())
        .status(loan.getStatus())
        .customer(
            CustomerSummaryResponse.builder()
                .id(loan.getCustomer().getId())
                .fullName(loan.getCustomer().getFullName())
                .nik(loan.getCustomer().getNik())
                .email(loan.getCustomer().getEmail())
                .build())
        .build();
  }
}
