package com.adnan.exercisespring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.*;

import com.adnan.exercisespring.dto.CreateLoanApplicationRequest;
import com.adnan.exercisespring.dto.LoanApplicationResponse;
import com.adnan.exercisespring.enums.LoanApplicationStatusEnum;
import com.adnan.exercisespring.enums.RoleEnum;
import com.adnan.exercisespring.exception.BadRequestException;
import com.adnan.exercisespring.exception.ForbiddenException;
import com.adnan.exercisespring.exception.NotFoundException;
import com.adnan.exercisespring.model.LoanApplication;
import com.adnan.exercisespring.security.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanApplicationService {
  private final SecurityUtil securityUtil;

  @Value("${application.min_loan_amount_manager_approval}")
  private double MIN_LOAN_AMOUNT_MANAGER_APPROVAL;

  private Map<Long, LoanApplication> storage = new HashMap<>();
  private Long sequence = 1L;

  public LoanApplicationResponse createLoanApplication(CreateLoanApplicationRequest request) {
    if (!securityUtil.hasRole(RoleEnum.ADMIN) && !securityUtil.hasRole(RoleEnum.STAFF)) {
      throw new ForbiddenException("You do not have permission to access this resource");
    }

    LoanApplication loanApplication = new LoanApplication(sequence, request.getCustomerId(), request.getLoanAmount(),
        request.getTenorMonth(), request.getPurpose(), LoanApplicationStatusEnum.SUBMITTED);

    LoanApplicationResponse loanApplicationResponse = mapToResponse(loanApplication);

    storage.put(sequence++, loanApplication);

    return loanApplicationResponse;
  }

  public List<LoanApplicationResponse> getAllLoanApplications(String searchByStatus, Long customerId) {
    List<LoanApplicationResponse> result = new ArrayList<>();

    for (LoanApplication loanApplication : storage.values()) {
      boolean match = true;
      if (searchByStatus != null
          && !loanApplication.getStatus().toString().toLowerCase().contains(searchByStatus.toLowerCase())) {
        match = false;
      }
      if (customerId != null && !loanApplication.getCustomerId().equals(customerId)) {
        match = false;
      }
      if (match) {
        result.add(mapToResponse(loanApplication));
      }
    }

    return result;
  }

  public LoanApplicationResponse getLoanApplicationById(Long id) {
    LoanApplication loan = storage.get(id);
    if (loan == null) {
      throw new NotFoundException(String.format("Loan Application not found with id: %d", id));
    }
    return mapToResponse(loan);
  }

  public LoanApplicationResponse approveLoanApplication(Long id) {
    LoanApplication loan = storage.get(id);
    if (loan == null) {
      throw new NotFoundException(String.format("Loan Application not found with id: %d", id));
    }

    // Filter STAFF
    if (!securityUtil.hasRole(RoleEnum.ADMIN) && !securityUtil.hasRole(RoleEnum.APPROVER)
        && !securityUtil.hasRole(RoleEnum.MANAGER)) {
      throw new ForbiddenException("You do not have permission to approve this loan");
    }

    // Small amount, Manager can't approve
    if (securityUtil.hasRole(RoleEnum.MANAGER)) {
      if (loan.getLoanAmount() < MIN_LOAN_AMOUNT_MANAGER_APPROVAL) {
        throw new BadRequestException("Manager can only approve large loan");
      }
    }

    loan.setStatus(LoanApplicationStatusEnum.APPROVED);
    return mapToResponse(loan);
  }

  public LoanApplicationResponse rejectLoanApplication(Long id) {
    if (!securityUtil.hasRole(RoleEnum.ADMIN) && !securityUtil.hasRole(RoleEnum.APPROVER)) {
      throw new ForbiddenException("You do not have permission to access this resource");
    }

    LoanApplication loan = storage.get(id);
    if (loan == null) {
      throw new NotFoundException(String.format("Loan Application not found with id: %d", id));
    }
    loan.setStatus(LoanApplicationStatusEnum.REJECTED);
    return mapToResponse(loan);
  }

  public LoanApplicationResponse cancelLoanApplication(Long id) {
    if (!securityUtil.hasRole(RoleEnum.ADMIN) && !securityUtil.hasRole(RoleEnum.APPROVER)) {
      throw new ForbiddenException("You do not have permission to access this resource");
    }

    LoanApplication loan = storage.get(id);
    if (loan == null) {
      throw new NotFoundException(String.format("Loan Application not found with id: %d", id));
    }
    loan.setStatus(LoanApplicationStatusEnum.CANCELED);
    return mapToResponse(loan);
  }

  // Helper
  private LoanApplicationResponse mapToResponse(LoanApplication loan) {
    return new LoanApplicationResponse(loan.getId(), loan.getCustomerId(), loan.getLoanAmount(), loan.getTenorMonth(),
        loan.getPurpose(), loan.getStatus());
  }
}