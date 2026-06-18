package com.adnan.exercisespring.service;

import org.springframework.stereotype.Service;
import java.util.*;

import com.adnan.exercisespring.dto.CreateLoanApplicationRequest;
import com.adnan.exercisespring.dto.LoanApplicationResponse;
import com.adnan.exercisespring.exception.ForbiddenException;
import com.adnan.exercisespring.exception.LoanApplicationNotFoundException;
import com.adnan.exercisespring.model.LoanApplication;
import com.adnan.exercisespring.security.SecurityUtil;
import com.adnan.exercisespring.user.entity.Role;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanApplicationService {
  private final SecurityUtil securityUtil;

  private Map<Long, LoanApplication> storage = new HashMap<>();
  private Long sequence = 1L;

  public LoanApplicationResponse createLoanApplication(CreateLoanApplicationRequest request) {
    if (!securityUtil.hasRole(Role.ADMIN) && !securityUtil.hasRole(Role.STAFF)) {
      throw new ForbiddenException("You do not have permission");
    }

    LoanApplication loanApplication = new LoanApplication(sequence, request.getCustomerId(), request.getLoanAmount(),
        request.getTenorMonth(), request.getPurpose(), "SUBMITTED");

    LoanApplicationResponse loanApplicationResponse = mapToResponse(loanApplication);

    storage.put(sequence++, loanApplication);

    return loanApplicationResponse;
  }

  public List<LoanApplicationResponse> getAllLoanApplications() {
    List<LoanApplicationResponse> result = new ArrayList<>();

    for (LoanApplication loan : storage.values()) {
      result.add(mapToResponse(loan));
    }

    return result;
  }

  public LoanApplicationResponse getLoanApplicationById(Long id) {
    LoanApplication loan = storage.get(id);
    if (loan == null) {
      throw new LoanApplicationNotFoundException(String.format("Loan Application not found with id: %d", id));
    }
    return mapToResponse(loan);
  }

  public LoanApplicationResponse approve(Long id) {
    if (!securityUtil.hasRole(Role.ADMIN) && !securityUtil.hasRole(Role.APPROVER)) {
      throw new ForbiddenException("You do not have permission");
    }

    LoanApplication loan = storage.get(id);
    if (loan == null) {
      throw new LoanApplicationNotFoundException(String.format("Loan Application not found with id: %d", id));
    }
    loan.setStatus("APPROVED");
    return mapToResponse(loan);
  }

  public LoanApplicationResponse reject(Long id) {
    if (!securityUtil.hasRole(Role.ADMIN) && !securityUtil.hasRole(Role.APPROVER)) {
      throw new ForbiddenException("You do not have permission");
    }

    LoanApplication loan = storage.get(id);
    if (loan == null) {
      throw new LoanApplicationNotFoundException(String.format("Loan Application not found with id: %d", id));
    }
    loan.setStatus("REJECTED");
    return mapToResponse(loan);
  }

  private LoanApplicationResponse mapToResponse(LoanApplication loan) {
    return new LoanApplicationResponse(loan.getId(), loan.getCustomerId(), loan.getLoanAmount(), loan.getTenorMonth(),
        loan.getPurpose(), loan.getStatus());
  }
}