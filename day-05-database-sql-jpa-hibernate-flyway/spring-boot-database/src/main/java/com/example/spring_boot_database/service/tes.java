// package com.example.spring_boot_database.service;

// public class tes {
//     package com.example.spring_boot_database.service;

// import java.math.BigDecimal;
// import java.time.LocalDateTime;
// import java.util.List;

// import org.springframework.dao.DataAccessException;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.example.spring_boot_database.dto.CreateCustomerRequest;
// import com.example.spring_boot_database.dto.CustomerResponse;
// import com.example.spring_boot_database.dto.LoanApplicationResponse;
// import com.example.spring_boot_database.dto.OutstandingAmountResponse;
// import com.example.spring_boot_database.entity.CustomerEntity;
// import com.example.spring_boot_database.entity.LoanApplicationEntity;
// import com.example.spring_boot_database.entity.Status;
// import com.example.spring_boot_database.exception.CustomerNotFoundException;
// import com.example.spring_boot_database.exception.DuplicateCustomerException;
// import com.example.spring_boot_database.repository.CustomerRepository;
// import com.example.spring_boot_database.repository.LoanApplicationRepository;
// import com.example.spring_boot_database.repository.RepaymentScheduleRepository;

// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;

// @Slf4j
// @Service
// @RequiredArgsConstructor
// public class CustomerService {

//     private final CustomerRepository customerRepository;
//     private final LoanApplicationRepository loanApplicationRepository;
//     private final RepaymentScheduleRepository repaymentScheduleRepository;

//     private void fill(CustomerEntity entity, CreateCustomerRequest req) {
//         entity.setNik(req.getNik());
//         entity.setFullName(req.getFullName());
//         entity.setEmail(req.getEmail());
//         entity.setPhoneNumber(req.getPhoneNumber());
//     }

//     @Transactional
//     public CustomerResponse createCustomer(CreateCustomerRequest request) {
//         log.debug("event_name=customer_create_requested");

//         if (customerRepository.existsByNikAndDeletedAtIsNull(request.getNik())) {
//             log.warn("event_name=customer_create_rejected reason=duplicate_nik");
//             throw new DuplicateCustomerException("nik", request.getNik());
//         }

//         if (customerRepository.existsByEmailAndDeletedAtIsNull(request.getEmail())) {
//             log.warn("event_name=customer_create_rejected reason=duplicate_email");
//             throw new DuplicateCustomerException("email", request.getEmail());
//         }

//         CustomerEntity entity = new CustomerEntity();
//         fill(entity, request);

//         try {
//             CustomerEntity saved = customerRepository.save(entity);

//             log.info("event_name=customer_created customer_id={}", saved.getId());

//             return toResponse(saved);
//         } catch (DataAccessException ex) {
//             log.error("event_name=customer_create_failed reason=database_error", ex);
//             throw ex;
//         }
//     }

//     @Transactional(readOnly = true)
//     public CustomerEntity getById(Long id) {
//         log.debug("event_name=customer_lookup_requested customer_id={}", id);

//         return customerRepository.findByIdAndDeletedAtIsNull(id)
//                 .orElseThrow(() -> {
//                     log.warn("event_name=customer_lookup_failed customer_id={} reason=not_found_or_deleted", id);
//                     return new CustomerNotFoundException(id);
//                 });
//     }

//     @Transactional(readOnly = true)
//     public CustomerResponse findById(Long id) {
//         CustomerEntity customer = getById(id);

//         log.info("event_name=customer_detail_retrieved customer_id={}", id);

//         return toResponse(customer);
//     }

//     @Transactional(readOnly = true)
//     public List<CustomerResponse> findCustomer(String name) {
//         boolean searchByName = name != null && !name.isBlank();

//         log.debug("event_name=customer_search_requested search_by_name={}", searchByName);

//         List<CustomerEntity> customers = searchByName
//                 ? customerRepository.findByFullNameContainingIgnoreCaseAndDeletedAtIsNull(name)
//                 : customerRepository.findByDeletedAtIsNull();

//         if (customers.isEmpty()) {
//             log.warn("event_name=customer_search_completed result_count=0 search_by_name={}", searchByName);
//         } else {
//             log.info("event_name=customer_search_completed result_count={} search_by_name={}",
//                     customers.size(),
//                     searchByName);
//         }

//         return customers.stream()
//                 .map(this::toResponse)
//                 .toList();
//     }

//     @Transactional(readOnly = true)
//     public List<LoanApplicationResponse> findLoanByCustomer(Long customerId) {
//         log.debug("event_name=customer_loans_lookup_requested customer_id={}", customerId);

//         getById(customerId);

//         List<LoanApplicationEntity> loans =
//                 loanApplicationRepository.findLoansByCustomerId(customerId);

//         if (loans.isEmpty()) {
//             log.warn("event_name=customer_loans_lookup_completed customer_id={} loan_count=0", customerId);
//         } else {
//             log.info("event_name=customer_loans_lookup_completed customer_id={} loan_count={}",
//                     customerId,
//                     loans.size());
//         }

//         return loans.stream()
//                 .map(this::toLoanResponse)
//                 .toList();
//     }

//     @Transactional(readOnly = true)
//     public OutstandingAmountResponse getOutstandingAmountByCustomer(Long customerId) {
//         log.debug("event_name=customer_outstanding_amount_requested customer_id={}", customerId);

//         getById(customerId);

//         BigDecimal outstandingAmount =
//                 repaymentScheduleRepository.calculateOutstandingAmountByCustomerId(customerId);

//         if (outstandingAmount == null) {
//             log.warn("event_name=customer_outstanding_amount_calculated customer_id={} outstanding_amount=null reason=no_repayment_schedule_found",
//                     customerId);

//             outstandingAmount = BigDecimal.ZERO;
//         } else if (outstandingAmount.compareTo(BigDecimal.ZERO) == 0) {
//             log.info("event_name=customer_outstanding_amount_calculated customer_id={} outstanding_amount=0 status=no_outstanding_balance",
//                     customerId);
//         } else {
//             log.info("event_name=customer_outstanding_amount_calculated customer_id={} outstanding_amount={}",
//                     customerId,
//                     outstandingAmount);
//         }

//         return OutstandingAmountResponse.builder()
//                 .customerId(customerId)
//                 .outstandingAmount(outstandingAmount)
//                 .build();
//     }

//     @Transactional
//     public CustomerResponse softDeleteCustomer(Long id) {
//         log.debug("event_name=customer_soft_delete_requested customer_id={}", id);

//         CustomerEntity customer = getById(id);

//         customer.setDeletedAt(LocalDateTime.now());

//         try {
//             CustomerEntity updated = customerRepository.save(customer);

//             log.info("event_name=customer_soft_deleted customer_id={} deleted_at={}",
//                     updated.getId(),
//                     updated.getDeletedAt());

//             return toResponse(updated);
//         } catch (DataAccessException ex) {
//             log.error("event_name=customer_soft_delete_failed customer_id={} reason=database_error", id, ex);
//             throw ex;
//         }
//     }

//     private LoanApplicationResponse toLoanResponse(LoanApplicationEntity loan) {
//         log.debug("event_name=loan_application_mapping_started loan_amount={} tenor_month={} status={}",
//                 loan.getLoanAmount(),
//                 loan.getTenorMonth(),
//                 loan.getStatus());

//         return LoanApplicationResponse.builder()
//                 .loanAmount(loan.getLoanAmount())
//                 .tenorMonth(loan.getTenorMonth())
//                 .purpose(loan.getPurpose())
//                 .status(Status.valueOf(loan.getStatus()))
//                 .customer(toResponse(loan.getCustomer()))
//                 .build();
//     }

//     public CustomerResponse toResponse(CustomerEntity entity) {
//         log.debug("event_name=customer_mapping_started customer_id={}", entity.getId());

//         return CustomerResponse.builder()
//                 .id(entity.getId())
//                 .fullName(entity.getFullName())
//                 .nik(entity.getNik())
//                 .email(entity.getEmail())
//                 .phoneNumber(entity.getPhoneNumber())
//                 .build();
//     }
// }
    
// }
