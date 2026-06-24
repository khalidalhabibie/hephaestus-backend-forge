package com.fif.training.exercisespringboot.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.fif.training.exercisespringboot.DTO.Loan.LoanApplicationRequest;
import com.fif.training.exercisespringboot.DTO.Loan.LoanApplicationResponse;
import com.fif.training.exercisespringboot.Exception.ForbiddenException;
import com.fif.training.exercisespringboot.Exception.LoanNotFoundException;
import com.fif.training.exercisespringboot.Model.LoanApplication;
import com.fif.training.exercisespringboot.Model.LoanStatus;
import com.fif.training.exercisespringboot.Model.Roles;

@Service
public class LoanApplicationService {

    private final Map<Long, LoanApplication> loanApplicationStorage = new TreeMap<>();
    private final CustomerService customerService;
    private Long id = 0L;

    public LoanApplicationService(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Helper to create loanappresponse
    private LoanApplicationResponse toLoanAppResponse(LoanApplication loanApplication) {
        return new LoanApplicationResponse(
                loanApplication.getId(),
                loanApplication.getCustomerId(),
                loanApplication.getLoanAmount(),
                loanApplication.getTenorMonth(),
                loanApplication.getPurpose(),
                loanApplication.getStatus(),
                loanApplication.getCreatedAt(),
                loanApplication.getUpdatedAt(),
                loanApplication.getApprovedAt(),
                loanApplication.getApprovedBy());
    }

    public LoanApplicationResponse createLoanApplication(LoanApplicationRequest request) {

        // Customer Validation
        customerService.getCustomerById(request.customerId());

        // Loan Amount Validation
        if (request.loanAmount() == null || request.loanAmount() <= 0) {
            throw new IllegalArgumentException("Loan Amount Tidak Valid!");
        }

        // Tenor Month Validation
        if (request.tenorMonth() == null || request.tenorMonth() <= 0) {
            throw new IllegalArgumentException("Tenor Month Tidak Valid!");
        }

        // Purpose Validation
        if (request.purpose() == null || request.purpose().isBlank()) {
            throw new IllegalArgumentException("Purpose Tidak Boleh Kosong!");
        }

        // Increment ID
        id++;

        // Cleaning RequestBody
        String cleanPurpose = request.purpose().trim();

        // Zone Date Time
        ZonedDateTime now = ZonedDateTime.now();

        // Create new Loan Application
        LoanApplication loanApplication = new LoanApplication(
                id,
                request.customerId(),
                request.loanAmount(),
                request.tenorMonth(),
                cleanPurpose,
                LoanStatus.SUBMITED,
                now,
                now,
                null,
                null);

        // Save in storage
        loanApplicationStorage.put(id, loanApplication);

        // Create Loan Application Response
        LoanApplicationResponse response = toLoanAppResponse(loanApplication);
        return response;
    }

    // Service Get All Loan
    public List<LoanApplicationResponse> getAllLoanApplications(String status, Long customerId) {
        String keywordStatus = status == null ? "" : status.trim().toUpperCase();

        boolean statusProvided = !keywordStatus.isBlank();
        boolean customerIdProvided = customerId != null;

        return loanApplicationStorage.values()
                .stream()
                .filter(loan -> {
                    boolean statusMatch = statusProvided &&
                            loan.getStatus().name().equals(keywordStatus);

                    boolean customerIdMatch = customerIdProvided &&
                            loan.getCustomerId().equals(customerId);

                    if (!statusProvided && !customerIdProvided) {
                        return true;
                    }

                    if (statusProvided && customerIdProvided) {
                        return statusMatch && customerIdMatch;
                    }

                    return statusMatch || customerIdMatch;
                })
                .map(this::toLoanAppResponse)
                .toList();
    }

    // Service Get Loan By Id
    public LoanApplicationResponse getLoanApplicationById(Long id) {
        LoanApplication loanApplication = loanApplicationStorage.get(id);

        if (loanApplication == null) {
            throw new LoanNotFoundException(id);
        }

        return toLoanAppResponse(loanApplication);
    }

    // Service PATCH / APPROVE Loan
    public LoanApplicationResponse approveLoanApplication(Long id, String approvedBy, Roles role) {
        LoanApplication loanApplication = loanApplicationStorage.get(id);

        if (loanApplication == null) {
            throw new LoanNotFoundException(id);
        }

        if (!loanApplication.getStatus().equals(LoanStatus.SUBMITED)) {
            throw new IllegalArgumentException("Loan Application Processed!");
        }

        if (role == Roles.MANAGER && loanApplication.getLoanAmount() <= 50_000_000) {
            throw new ForbiddenException("Manager hanya boleh approve loan di atas 50 juta!");
        }

        ZonedDateTime now = ZonedDateTime.now();

        loanApplication.setStatus(LoanStatus.APPROVED);
        loanApplication.setApprovedAt(now);
        loanApplication.setApprovedBy(approvedBy);
        loanApplication.setUpdatedAt(now);

        loanApplicationStorage.put(id, loanApplication);

        return toLoanAppResponse(loanApplication);
    }

    // Service Reject
    public LoanApplicationResponse rejectLoanApplication(Long id, String rejectedBy) {
        LoanApplication loanApplication = loanApplicationStorage.get(id);

        if (loanApplication == null) {
            throw new LoanNotFoundException(id);
        }

        if (!loanApplication.getStatus().equals(LoanStatus.SUBMITED)) {
            throw new IllegalArgumentException("Loan Application Processed!");
        }

        ZonedDateTime now = ZonedDateTime.now();

        loanApplication.setStatus(LoanStatus.REJECTED);
        loanApplication.setApprovedAt(now);
        loanApplication.setApprovedBy(rejectedBy);
        loanApplication.setUpdatedAt(now);

        loanApplicationStorage.put(id, loanApplication);

        return toLoanAppResponse(loanApplication);
    }

    // Service Cancled
    public LoanApplicationResponse cancleLoanApplication(Long id, String cancledBy) {
        LoanApplication loanApplication = loanApplicationStorage.get(id);

        if (loanApplication == null) {
            throw new LoanNotFoundException(id);
        }

        if (!loanApplication.getStatus().equals(LoanStatus.SUBMITED)) {
            throw new IllegalArgumentException("Loan Application Processed!");
        }
        

        ZonedDateTime now = ZonedDateTime.now();

        loanApplication.setStatus(LoanStatus.CANCLED);
        loanApplication.setApprovedAt(now);
        loanApplication.setApprovedBy(cancledBy);
        loanApplication.setUpdatedAt(now);

        loanApplicationStorage.put(id, loanApplication);

        return toLoanAppResponse(loanApplication);
    }
}
