package com.example.spring_boot_database.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.spring_boot_database.dto.CreateLoanApplicationRequest;
import com.example.spring_boot_database.dto.LoanApplicationResponse;
import com.example.spring_boot_database.dto.RepaymentScheduleResponse;
import com.example.spring_boot_database.dto.UpdateLoanStatusRequest;
import com.example.spring_boot_database.entity.CustomerEntity;
import com.example.spring_boot_database.entity.LoanApplicationEntity;
import com.example.spring_boot_database.entity.RepaymentScheduleEntity;
import com.example.spring_boot_database.entity.Status;
import com.example.spring_boot_database.entity.StatusRepayment;
import com.example.spring_boot_database.exception.CustomerNotFoundException;
import com.example.spring_boot_database.exception.LoanNotFoundException;
import com.example.spring_boot_database.repository.CustomerRepository;
import com.example.spring_boot_database.repository.LoanApplicationRepository;
import com.example.spring_boot_database.repository.RepaymentScheduleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    private final CustomerRepository customerRepository;
    private final LoanApplicationRepository loanApplicationRepository;
    private final RepaymentScheduleRepository repaymentScheduleRepository;
    private final CustomerService customerService;
    private final RepaymentScheduleSevice repaymentScheduleSevice;

    public CustomerEntity getByIdCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    private void fill(LoanApplicationEntity entity, CreateLoanApplicationRequest request) {
        entity.setCustomer(getByIdCustomer(request.getCustomerId()));
        entity.setLoanAmount(request.getLoanAmount());
        entity.setPurpose(request.getPurpose());
        entity.setTenorMonth(request.getTenorMonth());
        entity.setStatus(Status.SUBMITTED.name());
    }

    public LoanApplicationResponse createLoanApplication(CreateLoanApplicationRequest request) {
        LoanApplicationEntity entity = new LoanApplicationEntity();
        fill(entity, request);
        return toResponse(loanApplicationRepository.save(entity));
    }

    public LoanApplicationEntity getById(Long id) {
        return loanApplicationRepository.findById(id)
                .orElseThrow(() -> new LoanNotFoundException(id));
    }

    public LoanApplicationResponse findById(Long id) {
        return toResponse(getById(id));
    }

    public List<LoanApplicationResponse> findLoanApplication(Status status) {

        List<LoanApplicationEntity> data;

        if (status != null) {
            data = loanApplicationRepository.findByStatus(status.name());
        } else {
            data = loanApplicationRepository.findAll();
        }

        return data.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public LoanApplicationResponse updateStatusLoanApplication(Long id, UpdateLoanStatusRequest request) {

        if (request.getStatus() == null) {
            throw new RuntimeException("Status is required");
        }

        LoanApplicationEntity entity = getById(id);

        Status currentStatus = Status.valueOf(entity.getStatus());
        Status newStatus = request.getStatus();

        if (currentStatus == Status.REJECTED || currentStatus == Status.CLOSED) {
            throw new RuntimeException("Cannot change status from final state");
        }

        switch (currentStatus) {

            case SUBMITTED:
                if (!(newStatus == Status.APPROVED || newStatus == Status.REJECTED)) {
                    throw new RuntimeException("SUBMITTED can only go to APPROVED or REJECTED");
                }
                break;

            case APPROVED:
                if (newStatus != Status.DISBURSED) {
                    throw new RuntimeException("APPROVED can only go to DISBURSED");
                }
                break;

            case DISBURSED:
                if (newStatus != Status.CLOSED) {
                    throw new RuntimeException("DISBURSED can only go to CLOSED");
                }

                boolean allPaid = repaymentScheduleRepository
                        .findByLoanApplicationId(entity.getId())
                        .stream()
                        .allMatch(s -> s.getStatus() == StatusRepayment.PAID.name());

                if (!allPaid) {
                    throw new RuntimeException("Cannot close loan, repayment not fully paid");
                }
                break;

            case REJECTED:
            case CLOSED:
                throw new RuntimeException("Cannot change status from final state");
        }


        entity.setStatus(newStatus.name());
        LoanApplicationEntity updated = loanApplicationRepository.save(entity);

        if (newStatus == Status.DISBURSED) {

            if (repaymentScheduleRepository.findByLoanApplicationId(updated.getId()).isEmpty()) {

                List<RepaymentScheduleEntity> schedules =
                        repaymentScheduleSevice.generateRepaymentSchedule(updated);

                repaymentScheduleRepository.saveAll(schedules);
            }
        }

        return toResponse(updated);
    }

    public List<RepaymentScheduleResponse> findRepaymentScheduleByLoanId(Long loanId) {

        List<RepaymentScheduleEntity> data;

        if (loanId != null) {
            data = repaymentScheduleRepository.findByLoanApplicationId(loanId);
        } else {
            data = repaymentScheduleRepository.findAll();
        }

        return data.stream()
                .map(this::toResponseRepaymentSchedule)
                .collect(Collectors.toList());
    }

    public RepaymentScheduleResponse toResponseRepaymentSchedule(RepaymentScheduleEntity entity) {
        return RepaymentScheduleResponse.builder()
                .id(entity.getId())
                .installmentNumber(entity.getInstallmentNumber())
                .dueDate(entity.getDueDate())
                .principalAmount(entity.getPrincipalAmount())
                .interestAmount(entity.getInterestAmount())
                .totalAmount(entity.getTotalAmount())
                .build();
    }

    public LoanApplicationResponse toResponse(LoanApplicationEntity loanApplication) {
        return LoanApplicationResponse.builder()
                .loanAmount(loanApplication.getLoanAmount())
                .tenorMonth(loanApplication.getTenorMonth())
                .purpose(loanApplication.getPurpose())
                .status(Status.valueOf(loanApplication.getStatus()))
                .customer(customerService.toResponse(loanApplication.getCustomer()))
                .build();
    }
}