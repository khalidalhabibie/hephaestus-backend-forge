// package com.example.spring_boot_database.service;

// import java.math.BigDecimal;
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.List;

// import jakarta.persistence.criteria.Predicate;

// import org.springframework.data.domain.Pageable;
// import org.springframework.data.jpa.domain.Specification;
// import org.springframework.data.domain.Page;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.example.spring_boot_database.dto.CreateLoanApplicationRequest;
// import com.example.spring_boot_database.dto.LoanApplicationResponse;
// import com.example.spring_boot_database.dto.LoanSummaryByStatusResponse;
// import com.example.spring_boot_database.dto.RepaymentScheduleResponse;
// import com.example.spring_boot_database.dto.UpdateLoanStatusRequest;
// import com.example.spring_boot_database.entity.CustomerEntity;
// import com.example.spring_boot_database.entity.LoanApplicationEntity;
// import com.example.spring_boot_database.entity.Status;
// import com.example.spring_boot_database.entity.StatusRepayment;
// import com.example.spring_boot_database.exception.BadRequestException;
// import com.example.spring_boot_database.exception.LoanNotFoundException;
// import com.example.spring_boot_database.repository.LoanApplicationRepository;
// import com.example.spring_boot_database.repository.RepaymentScheduleRepository;

// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;

// @Slf4j
// @Service
// @RequiredArgsConstructor
// // public class LoanApplicationService {

//     private final LoanApplicationRepository loanRepo;
//     private final CustomerService customerService;
//     private final RepaymentScheduleRepository scheduleRepo;
//     private final RepaymentScheduleService scheduleService;

//     @Transactional
//     public LoanApplicationResponse createLoanApplication(CreateLoanApplicationRequest req) {

//         log.info("event_name=loan_application_create_attempt customer_id={} amount={} tenor={}",
//                 req.getCustomerId(), req.getLoanAmount(), req.getTenorMonth());

//         try {
//             CustomerEntity customer = customerService.getById(req.getCustomerId());

//             LoanApplicationEntity entity = new LoanApplicationEntity();
//             entity.setCustomer(customer);
//             entity.setLoanAmount(req.getLoanAmount());
//             entity.setTenorMonth(req.getTenorMonth());
//             entity.setPurpose(req.getPurpose());
//             entity.setStatus(Status.SUBMITTED.name());

//             LoanApplicationEntity saved = loanRepo.save(entity);

//             log.info("event_name=loan_application_created resource_id={} customer_id={} amount={} status={}",
//                     saved.getId(), req.getCustomerId(), saved.getLoanAmount(), saved.getStatus());

//             return toResponse(saved);

//         } catch (Exception e) {
//             log.error("event_name=loan_application_create_failed customer_id={} error={}",
//                     req.getCustomerId(), e.getMessage(), e);
//             throw e;
//         }
//     }

//     @Transactional(readOnly = true)
//     public LoanApplicationEntity getById(Long id) {

//         log.debug("event_name=loan_get_by_id_attempt resource_id={}", id);

//         return loanRepo.findByIdWithCustomer(id)
//                 .orElseThrow(() -> {
//                     log.warn("event_name=loan_not_found resource_id={}", id);
//                     return new LoanNotFoundException(id);
//                 });
//     }

//     @Transactional(readOnly = true)
//     public LoanApplicationResponse findById(Long id) {

//         log.info("event_name=loan_find_by_id resource_id={}", id);

//         return toResponse(getById(id));
//     }

//     @Transactional(readOnly = true)
//     public List<LoanApplicationResponse> findLoan(
//             Status status,
//             LocalDate startDate,
//             LocalDate endDate) {

//         log.debug("event_name=loan_search_filter status={} start_date={} end_date={}",
//                 status, startDate, endDate);

//         validateDateRange(startDate, endDate);

//         Specification<LoanApplicationEntity> spec =
//                 buildLoanSpecification(status, startDate, endDate);

//         List<LoanApplicationEntity> results = loanRepo.findAll(spec);

//         log.info("event_name=loan_search_result status={} result_count={}",
//                 status, results.size());

//         return results.stream()
//                 .map(this::toResponse)
//                 .toList();
//     }

//     @Transactional
//     public LoanApplicationResponse updateStatus(Long id, UpdateLoanStatusRequest req) {

//         log.info("event_name=loan_status_update_attempt resource_id={} new_status={}",
//                 id, req.getStatus());

//         if (req.getStatus() == null) {
//             log.warn("event_name=loan_status_update_invalid resource_id={} reason=null_status", id);
//             throw new BadRequestException("Status is required");
//         }

//         LoanApplicationEntity entity = getById(id);

//         Status current = Status.valueOf(entity.getStatus());
//         Status next = req.getStatus();

//         log.debug("event_name=loan_status_transition_check resource_id={} current_status={} next_status={}",
//                 id, current, next);

//         try {
//             validateTransition(current, next, entity);

//             entity.setStatus(next.name());
//             entity.setUpdatedAt(LocalDateTime.now());

//             LoanApplicationEntity updated = loanRepo.save(entity);

//             log.info("event_name=loan_status_updated resource_id={} previous_status={} new_status={}",
//                     id, current, next);

//             if (next == Status.APPROVED) {
//                 log.info("event_name=loan_application_approved resource_id={} approved_amount={} next_status={}",
//                         id, updated.getLoanAmount(), next);
//             }

//             if (next == Status.DISBURSED) {

//                 log.info("event_name=loan_disbursement_started resource_id={}", id);

//                 if (scheduleRepo.findByLoanApplicationId(id).isEmpty()) {

//                     log.debug("event_name=repayment_schedule_generate_start resource_id={}", id);

//                     scheduleRepo.saveAll(scheduleService.generateRepaymentSchedule(updated));

//                     log.info("event_name=repayment_schedule_generated resource_id={}", id);
//                 } else {
//                     log.warn("event_name=repayment_schedule_already_exists resource_id={}", id);
//                 }
//             }

//             return toResponse(updated);

//         } catch (Exception e) {
//             log.error("event_name=loan_status_update_failed resource_id={} error={}",
//                     id, e.getMessage(), e);
//             throw e;
//         }
//     }

//     private void validateTransition(Status current, Status next, LoanApplicationEntity entity) {

//         if (current == Status.REJECTED || current == Status.CLOSED) {
//             log.warn("event_name=invalid_transition_final_state resource_id={} current_status={} next_status={}",
//                     entity.getId(), current, next);
//             throw new BadRequestException("Final state cannot be changed");
//         }

//         switch (current) {
//             case SUBMITTED -> {
//                 if (!(next == Status.APPROVED || next == Status.REJECTED)) {
//                     log.warn("event_name=invalid_transition resource_id={} from={} to={}",
//                             entity.getId(), current, next);
//                     throw new BadRequestException("Invalid transition");
//                 }
//             }
//             case APPROVED -> {
//                 if (next != Status.DISBURSED) {
//                     log.warn("event_name=invalid_transition resource_id={} from={} to={}",
//                             entity.getId(), current, next);
//                     throw new BadRequestException("Invalid transition");
//                 }
//             }
//             case DISBURSED -> {
//                 if (next != Status.CLOSED) {
//                     log.warn("event_name=invalid_transition resource_id={} from={} to={}",
//                             entity.getId(), current, next);
//                     throw new BadRequestException("Invalid transition");
//                 }

//                 boolean allPaid = scheduleRepo.findByLoanApplicationId(entity.getId())
//                         .stream()
//                         .allMatch(s -> s.getStatus().equals(StatusRepayment.PAID.name()));

//                 if (!allPaid) {
//                     log.warn("event_name=loan_close_failed_not_paid resource_id={}", entity.getId());
//                     throw new BadRequestException("Loan cannot be closed, not fully paid");
//                 }
//             }
//             default -> {
//                 log.error("event_name=unknown_transition_state resource_id={} current_status={}",
//                         entity.getId(), current);
//                 throw new BadRequestException("Invalid transition");
//             }
//         }
//     }

//     @Transactional(readOnly = true)
//     public List<RepaymentScheduleResponse> getSchedules(Long loanId) {

//         log.info("event_name=loan_schedule_fetch resource_id={}", loanId);

//         getById(loanId);

//         List<RepaymentScheduleResponse> schedules =
//                 scheduleRepo.findByLoanApplicationId(loanId)
//                         .stream()
//                         .map(scheduleService::toResponse)
//                         .toList();

//         log.info("event_name=loan_schedule_result resource_id={} schedule_count={}",
//                 loanId, schedules.size());

//         return schedules;
//     }

//     @Transactional(readOnly = true)
//     public List<LoanSummaryByStatusResponse> getSummaryByStatus() {

//         log.info("event_name=loan_summary_by_status_requested");

//         List<LoanSummaryByStatusResponse> result =
//                 loanRepo.summarizeTotalLoanByStatus()
//                         .stream()
//                         .map(row -> LoanSummaryByStatusResponse.builder()
//                                 .status(Status.valueOf((String) row[0]))
//                                 .totalLoan((Long) row[1])
//                                 .totalLoanAmount((BigDecimal) row[2])
//                                 .build())
//                         .toList();

//         log.info("event_name=loan_summary_by_status_result total_rows={}",
//                 result.size());

//         return result;
//     }

//     @Transactional(readOnly = true)
//     public Page<LoanApplicationResponse> findLoanPaged(
//             Status status,
//             LocalDate startDate,
//             LocalDate endDate,
//             Pageable pageable) {

//         log.debug("event_name=loan_search_paged status={} page={} size={}",
//                 status, pageable.getPageNumber(), pageable.getPageSize());

//         validateDateRange(startDate, endDate);

//         Specification<LoanApplicationEntity> spec =
//                 buildLoanSpecification(status, startDate, endDate);

//         Page<LoanApplicationResponse> result =
//                 loanRepo.findAll(spec, pageable).map(this::toResponse);

//         log.info("event_name=loan_search_paged_result total_elements={} total_pages={}",
//                 result.getTotalElements(), result.getTotalPages());

//         return result;
//     }

//     private Specification<LoanApplicationEntity> buildLoanSpecification(
//             Status status,
//             LocalDate startDate,
//             LocalDate endDate) {

//         return (root, query, cb) -> {

//             List<Predicate> predicates = new ArrayList<>();

//             predicates.add(cb.isNull(root.get("customer").get("deletedAt")));

//             if (status != null) {
//                 predicates.add(cb.equal(root.get("status"), status.name()));
//             }

//             if (startDate != null) {
//                 LocalDateTime startDateTime = startDate.atStartOfDay();
//                 predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), startDateTime));
//             }

//             if (endDate != null) {
//                 LocalDateTime endDateTimeExclusive = endDate.plusDays(1).atStartOfDay();
//                 predicates.add(cb.lessThan(root.get("createdAt"), endDateTimeExclusive));
//             }

//             return cb.and(predicates.toArray(new Predicate[0]));
//         };
//     }

//     private void validateDateRange(LocalDate startDate, LocalDate endDate) {

//         if (startDate != null && endDate != null && startDate.isAfter(endDate)) {

//             log.warn("event_name=invalid_date_range start_date={} end_date={}",
//                     startDate, endDate);

//             throw new BadRequestException("startDate cannot be after endDate");
//         }
//     }

//     private LoanApplicationResponse toResponse(LoanApplicationEntity entity) {
//         return LoanApplicationResponse.builder()
//                 .loanAmount(entity.getLoanAmount())
//                 .tenorMonth(entity.getTenorMonth())
//                 .purpose(entity.getPurpose())
//                 .status(Status.valueOf(entity.getStatus()))
//                 .customer(customerService.toResponse(entity.getCustomer()))
//                 .build();
//     }
// }