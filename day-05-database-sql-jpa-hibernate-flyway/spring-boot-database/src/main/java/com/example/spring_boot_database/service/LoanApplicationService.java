package com.example.spring_boot_database.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring_boot_database.dto.CreateLoanApplicationRequest;
import com.example.spring_boot_database.dto.LoanApplicationResponse;
import com.example.spring_boot_database.dto.LoanSummaryByStatusResponse;
import com.example.spring_boot_database.dto.RepaymentScheduleResponse;
import com.example.spring_boot_database.dto.UpdateLoanStatusRequest;
import com.example.spring_boot_database.entity.CustomerEntity;
import com.example.spring_boot_database.entity.LoanApplicationEntity;
import com.example.spring_boot_database.entity.Status;
import com.example.spring_boot_database.entity.StatusRepayment;
import com.example.spring_boot_database.exception.BadRequestException;
import com.example.spring_boot_database.exception.LoanNotFoundException;
import com.example.spring_boot_database.repository.LoanApplicationRepository;
import com.example.spring_boot_database.repository.RepaymentScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    private final LoanApplicationRepository loanRepo;
    private final CustomerService customerService;
    private final RepaymentScheduleRepository scheduleRepo;
    private final RepaymentScheduleService scheduleService;

    @Transactional
    public LoanApplicationResponse createLoanApplication(CreateLoanApplicationRequest req) {

        CustomerEntity customer = customerService.getById(req.getCustomerId());

        LoanApplicationEntity entity = new LoanApplicationEntity();
        entity.setCustomer(customer);
        entity.setLoanAmount(req.getLoanAmount());
        entity.setTenorMonth(req.getTenorMonth());
        entity.setPurpose(req.getPurpose());
        entity.setStatus(Status.SUBMITTED.name());

        return toResponse(loanRepo.save(entity));
    }

    @Transactional(readOnly = true)
    public LoanApplicationEntity getById(Long id) {
        return loanRepo.findByIdWithCustomer(id)
                .orElseThrow(() -> new LoanNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public LoanApplicationResponse findById(Long id) {
        return toResponse(getById(id));
    }

    /**
     * Find loan applications by optional filters:
     * - status
     * - startDate
     * - endDate
     */
    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> findLoan(
            Status status,
            LocalDate startDate,
            LocalDate endDate) {

        validateDateRange(startDate, endDate);

        Specification<LoanApplicationEntity> spec = buildLoanSpecification(
                status,
                startDate,
                endDate
        );

        return loanRepo.findAll(spec)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public LoanApplicationResponse updateStatus(Long id, UpdateLoanStatusRequest req) {

        if (req.getStatus() == null) {
            throw new BadRequestException("Status is required");
        }

        LoanApplicationEntity entity = getById(id);

        Status current = Status.valueOf(entity.getStatus());
        Status next = req.getStatus();

        validateTransition(current, next, entity);

        entity.setStatus(next.name());
        entity.setUpdatedAt(LocalDateTime.now());

        LoanApplicationEntity updated = loanRepo.save(entity);

        if (next == Status.DISBURSED) {
            if (scheduleRepo.findByLoanApplicationId(id).isEmpty()) {
                scheduleRepo.saveAll(scheduleService.generateRepaymentSchedule(updated));
            }
        }

        return toResponse(updated);
    }

    private void validateTransition(Status current, Status next, LoanApplicationEntity entity) {

        if (current == Status.REJECTED || current == Status.CLOSED) {
            throw new BadRequestException("Final state cannot be changed");
        }

        switch (current) {
            case SUBMITTED -> {
                if (!(next == Status.APPROVED || next == Status.REJECTED)) {
                    throw new BadRequestException("Invalid transition");
                }
            }
            case APPROVED -> {
                if (next != Status.DISBURSED) {
                    throw new BadRequestException("Invalid transition");
                }
            }
            case DISBURSED -> {
                if (next != Status.CLOSED) {
                    throw new BadRequestException("Invalid transition");
                }

                boolean allPaid = scheduleRepo.findByLoanApplicationId(entity.getId())
                        .stream()
                        .allMatch(s -> s.getStatus().equals(StatusRepayment.PAID.name()));

                if (!allPaid) {
                    throw new BadRequestException("Loan cannot be closed, not fully paid");
                }
            }
            default -> throw new BadRequestException("Invalid transition");
        }
    }

    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponse> getSchedules(Long loanId) {
        return scheduleRepo.findByLoanApplicationId(loanId)
                .stream()
                .map(scheduleService::toResponse)
                .toList();
    }

    /**
     * Summary total loan grouped by status.
     */
    @Transactional(readOnly = true)
    public List<LoanSummaryByStatusResponse> getSummaryByStatus() {
        return loanRepo.summarizeTotalLoanByStatus()
                .stream()
                .map(row -> LoanSummaryByStatusResponse.builder()
                        .status(Status.valueOf((String) row[0]))
                        .totalLoan((Long) row[1])
                        .totalLoanAmount((BigDecimal) row[2])
                        .build())
                .toList();
    }

    private LoanApplicationResponse toResponse(LoanApplicationEntity entity) {
        return LoanApplicationResponse.builder()
                .loanAmount(entity.getLoanAmount())
                .tenorMonth(entity.getTenorMonth())
                .purpose(entity.getPurpose())
                .status(Status.valueOf(entity.getStatus()))
                .customer(customerService.toResponse(entity.getCustomer()))
                .build();
    }

    /**
     * Find loan applications with pagination and optional filters:
     * - status
     * - startDate
     * - endDate
     */
    @Transactional(readOnly = true)
    public Page<LoanApplicationResponse> findLoanPaged(
            Status status,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable) {

        validateDateRange(startDate, endDate);

        Specification<LoanApplicationEntity> spec = buildLoanSpecification(
                status,
                startDate,
                endDate
        );

        return loanRepo.findAll(spec, pageable)
                .map(this::toResponse);
    }

    private Specification<LoanApplicationEntity> buildLoanSpecification(
            Status status,
            LocalDate startDate,
            LocalDate endDate) {

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status.name()));
            }

            if (startDate != null) {
                LocalDateTime startDateTime = startDate.atStartOfDay();
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), startDateTime));
            }

            if (endDate != null) {
                LocalDateTime endDateTimeExclusive = endDate.plusDays(1).atStartOfDay();
                predicates.add(cb.lessThan(root.get("createdAt"), endDateTimeExclusive));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new BadRequestException("startDate cannot be after endDate");
        }
    }
}