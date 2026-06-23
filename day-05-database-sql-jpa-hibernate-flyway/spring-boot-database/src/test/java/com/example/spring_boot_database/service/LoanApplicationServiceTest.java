package com.example.spring_boot_database.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Path;

import com.example.spring_boot_database.dto.*;
import com.example.spring_boot_database.entity.*;
import com.example.spring_boot_database.exception.*;
import com.example.spring_boot_database.repository.*;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

import jakarta.persistence.criteria.Predicate;

@ExtendWith(MockitoExtension.class)
class LoanApplicationServiceTest {

    @Mock
    private LoanApplicationRepository loanRepo;

    @Mock
    private CustomerService customerService;

    @Mock
    private RepaymentScheduleRepository scheduleRepo;

    @Mock
    private RepaymentScheduleService scheduleService;

    @InjectMocks
    private LoanApplicationService service;

    private CustomerEntity customer;
    private LoanApplicationEntity loan;

    @BeforeEach
    void setUp() {
        customer = new CustomerEntity();
        customer.setId(1L);

        loan = new LoanApplicationEntity();
        loan.setId(1L);
        loan.setCustomer(customer);
        loan.setLoanAmount(BigDecimal.valueOf(1000));
        loan.setTenorMonth(12);
        loan.setPurpose("Test");
        loan.setStatus(Status.SUBMITTED.name());
    }

    @Test
    void testCreateLoanApplication_exception() {
        CreateLoanApplicationRequest req = new CreateLoanApplicationRequest();
        req.setCustomerId(1L);

        when(customerService.getById(1L))
                .thenThrow(new RuntimeException("DB error"));

        assertThrows(RuntimeException.class,
                () -> service.createLoanApplication(req));

        verify(loanRepo, never()).save(any());
    }


    @Test
    void testFindLoan_emptyResult() {
        when(loanRepo.findAll(any(Specification.class)))
                .thenReturn(List.of());

        List<LoanApplicationResponse> res =
                service.findLoan(null, null, null);

        assertTrue(res.isEmpty());
    }


    @Test
    void testFindLoanPaged_empty() {
        Page<LoanApplicationEntity> page =
                new PageImpl<>(List.of());

        when(loanRepo.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(page);

        Page<LoanApplicationResponse> res =
                service.findLoanPaged(null, null, null, PageRequest.of(0, 10));

        assertTrue(res.getContent().isEmpty());
    }


    @Test
    void testUpdateStatus_disbursed_scheduleAlreadyExists() {
        loan.setStatus(Status.APPROVED.name());

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus(Status.DISBURSED);

        RepaymentScheduleEntity schedule = new RepaymentScheduleEntity(); // ✅ FIX

        when(loanRepo.findByIdWithCustomer(1L)).thenReturn(Optional.of(loan));
        when(scheduleRepo.findByLoanApplicationId(1L))
                .thenReturn(List.of(schedule)); // ✅ FIX
        when(loanRepo.save(any())).thenReturn(loan);
        when(customerService.toResponse(any()))
                .thenReturn(CustomerResponse.builder().id(1L).build());

        LoanApplicationResponse res = service.updateStatus(1L, req);

        verify(scheduleRepo, never()).saveAll(any()); 
        assertEquals(Status.DISBURSED, res.getStatus());
    }
    


    @Test
    void testUpdateStatus_finalStateRejected() {
        loan.setStatus(Status.REJECTED.name());

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus(Status.APPROVED);

        when(loanRepo.findByIdWithCustomer(1L)).thenReturn(Optional.of(loan));

        assertThrows(BadRequestException.class,
                () -> service.updateStatus(1L, req));
    }


    @Test
    void testUpdateStatus_close_notFullyPaid() {
        loan.setStatus(Status.DISBURSED.name());

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus(Status.CLOSED);

        // mock repayment belum PAID
        RepaymentScheduleEntity schedule = new RepaymentScheduleEntity();
        schedule.setStatus("UNPAID");

        when(loanRepo.findByIdWithCustomer(1L)).thenReturn(Optional.of(loan));
        when(scheduleRepo.findByLoanApplicationId(1L))
                .thenReturn(List.of(schedule));

        assertThrows(BadRequestException.class,
                () -> service.updateStatus(1L, req));
    }


    @Test
    void testUpdateStatus_close_success() {
        loan.setStatus(Status.DISBURSED.name());

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus(Status.CLOSED);

        RepaymentScheduleEntity schedule = new RepaymentScheduleEntity();
        schedule.setStatus(StatusRepayment.PAID.name());

        when(loanRepo.findByIdWithCustomer(1L)).thenReturn(Optional.of(loan));
        when(scheduleRepo.findByLoanApplicationId(1L))
                .thenReturn(List.of(schedule));
        when(loanRepo.save(any())).thenReturn(loan);
        when(customerService.toResponse(any()))
                .thenReturn(CustomerResponse.builder().id(1L).build());

        LoanApplicationResponse res = service.updateStatus(1L, req);

        assertEquals(Status.CLOSED, res.getStatus());
    }


    @Test
    void testUpdateStatus_unknownStateFallback() {
        loan.setStatus("UNKNOWN"); // trigger default switch

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus(Status.APPROVED);

        when(loanRepo.findByIdWithCustomer(1L)).thenReturn(Optional.of(loan));

        assertThrows(IllegalArgumentException.class,
                () -> service.updateStatus(1L, req));
    }


    @Test
    void testGetSchedules_withData() {
        RepaymentScheduleEntity entity = new RepaymentScheduleEntity();
        RepaymentScheduleResponse dto = new RepaymentScheduleResponse();

        when(scheduleRepo.findByLoanApplicationId(1L))
                .thenReturn(List.of(entity));
        when(scheduleService.toResponse(entity))
                .thenReturn(dto);

        List<RepaymentScheduleResponse> res =
                service.getSchedules(1L);

        assertEquals(1, res.size());
    }


    @Test
    void testGetSummaryByStatus_empty() {
        when(loanRepo.summarizeTotalLoanByStatus())
                .thenReturn(List.of());

        List<LoanSummaryByStatusResponse> res =
                service.getSummaryByStatus();

        assertTrue(res.isEmpty());
    }


    @Test
    void testGetSummaryByStatus_multiple() {
        Object[] row1 = new Object[]{
                Status.SUBMITTED.name(), 1L, BigDecimal.valueOf(1000)
        };

        Object[] row2 = new Object[]{
                Status.APPROVED.name(), 2L, BigDecimal.valueOf(2000)
        };

        when(loanRepo.summarizeTotalLoanByStatus())
                .thenReturn(java.util.Arrays.asList(row1, row2));

        List<LoanSummaryByStatusResponse> res =
                service.getSummaryByStatus();

        assertEquals(2, res.size());
    }


    // ================= FIND LOAN - FULL FILTER =================
    @Test
    void testFindLoan_fullFilter() {
        when(loanRepo.findAll(any(Specification.class)))
                .thenReturn(List.of(loan));

        when(customerService.toResponse(any()))
                .thenReturn(CustomerResponse.builder().id(1L).build());

        List<LoanApplicationResponse> res =
                service.findLoan(
                        Status.SUBMITTED,
                        LocalDate.now().minusDays(1),
                        LocalDate.now()
                );

        assertEquals(1, res.size());
    }

    @Test
    void testUpdateStatus_approved_invalidTransition() {
        loan.setStatus(Status.APPROVED.name());

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus(Status.APPROVED);

        when(loanRepo.findByIdWithCustomer(1L)).thenReturn(Optional.of(loan));

        assertThrows(BadRequestException.class,
                () -> service.updateStatus(1L, req));

        verify(loanRepo, never()).save(any());
    }

    @Test
    void testUpdateStatus_disbursed_invalidTransition() {
        loan.setStatus(Status.DISBURSED.name());

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus(Status.APPROVED); 

        when(loanRepo.findByIdWithCustomer(1L)).thenReturn(Optional.of(loan));

        assertThrows(BadRequestException.class,
                () -> service.updateStatus(1L, req));
    }

    @Test
    void testUpdateStatus_finalState_closed() {
        loan.setStatus(Status.CLOSED.name());

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus(Status.APPROVED); 

        when(loanRepo.findByIdWithCustomer(1L)).thenReturn(Optional.of(loan));

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> service.updateStatus(1L, req));

        assertEquals("Final state cannot be changed", ex.getMessage());
    }

    @Test
    void testUpdateStatus_submitted_invalid() {
        loan.setStatus(Status.SUBMITTED.name());

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus(Status.CLOSED); 

        when(loanRepo.findByIdWithCustomer(1L)).thenReturn(Optional.of(loan));

        BadRequestException ex = assertThrows(BadRequestException.class,
                () -> service.updateStatus(1L, req));

        assertEquals("Invalid transition", ex.getMessage());
    }

    @Test
    void testUpdateStatus_submitted_validApproved() {
        loan.setStatus(Status.SUBMITTED.name());

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus(Status.APPROVED); 

        when(loanRepo.findByIdWithCustomer(1L)).thenReturn(Optional.of(loan));
        when(loanRepo.save(any())).thenReturn(loan);
        when(customerService.toResponse(any()))
                .thenReturn(CustomerResponse.builder().id(1L).build());

        LoanApplicationResponse res = service.updateStatus(1L, req);

        assertEquals(Status.APPROVED, res.getStatus());
    }

    @Test
    void testUpdateStatus_submitted_validRejected() {
        loan.setStatus(Status.SUBMITTED.name());

        UpdateLoanStatusRequest req = new UpdateLoanStatusRequest();
        req.setStatus(Status.REJECTED); 

        when(loanRepo.findByIdWithCustomer(1L)).thenReturn(Optional.of(loan));
        when(loanRepo.save(any())).thenReturn(loan);
        when(customerService.toResponse(any()))
                .thenReturn(CustomerResponse.builder().id(1L).build());

        LoanApplicationResponse res = service.updateStatus(1L, req);

        assertEquals(Status.REJECTED, res.getStatus());
    }

    @Test
    @SuppressWarnings("unchecked")
    void testBuildLoanSpecification_allBranchesCovered() {

        lenient().when(loanRepo.findAll(any(Specification.class)))
                .thenAnswer(invocation -> {

                    Specification<LoanApplicationEntity> spec =
                            invocation.getArgument(0);

                    Root<LoanApplicationEntity> root = mock(Root.class);
                    CriteriaQuery<?> query = mock(CriteriaQuery.class);
                    CriteriaBuilder cb = mock(CriteriaBuilder.class);

                    Path<?> customerPath = mock(Path.class);
                    Path<?> deletedAtPath = mock(Path.class);
                    Path<?> statusPath = mock(Path.class);
                    Path<?> createdAtPath = mock(Path.class);

                    Predicate predicate = mock(Predicate.class);

                    doReturn(customerPath).when(root).get("customer");
                    doReturn(deletedAtPath).when(customerPath).get("deletedAt");
                    doReturn(statusPath).when(root).get("status");
                    doReturn(createdAtPath).when(root).get("createdAt");

                    lenient().doAnswer(inv -> predicate)
                            .when(cb).isNull(any());

                    lenient().doAnswer(inv -> predicate)
                            .when(cb).equal(any(), any());

                    lenient().doAnswer(inv -> predicate)
                            .when(cb).greaterThanOrEqualTo(
                                    any(jakarta.persistence.criteria.Expression.class),
                                    any(Comparable.class)
                            );

                    lenient().doAnswer(inv -> predicate)
                            .when(cb).lessThan(
                                    any(jakarta.persistence.criteria.Expression.class),
                                    any(Comparable.class)
                            );

                    lenient().doAnswer(inv -> predicate)
                            .when(cb).and(any(Predicate[].class));

                    spec.toPredicate(root, query, cb);

                    return List.of(loan);
                });

        when(customerService.toResponse(any()))
                .thenReturn(CustomerResponse.builder().id(1L).build());

        List<LoanApplicationResponse> result = service.findLoan(
                Status.APPROVED,
                LocalDate.now().minusDays(1),
                LocalDate.now()
        );

        assertEquals(1, result.size());
    }



}
