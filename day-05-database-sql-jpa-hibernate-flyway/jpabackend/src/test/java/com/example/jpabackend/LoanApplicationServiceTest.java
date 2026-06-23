package com.example.jpabackend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.example.jpabackend.dto.*;
import com.example.jpabackend.repository.*;
import com.example.jpabackend.service.LoanApplicationService;
import com.example.jpabackend.service.RepaymentScheduleService;
import com.example.jpabackend.entity.*;
import com.example.jpabackend.exception.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoanApplicationServiceTest {

    @InjectMocks
    private LoanApplicationService service;

    @Mock
    private LoanApplicationRepository loanRepo;

    @Mock
    private CustomerRepository customerRepo;

    @Mock
    private RepaymentScheduleService repaymentService;

    @Test
    void should_create_loan_application_successfully() {
        // given
        CreateLoanApplicationRequest req = new CreateLoanApplicationRequest();
        req.setCustomerId(1L);
        req.setLoanAmount(BigDecimal.TEN);
        req.setTenorMonth(12);

        CustomerEntity customer = new CustomerEntity();
        customer.setId(1L);

        when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));
        when(loanRepo.save(any())).thenAnswer(i -> i.getArgument(0));

        // when
        LoanApplicationResponse result = service.createLoan(req);

        // then
        assertNotNull(result);
        verify(loanRepo).save(any());
    }

    @Test
    void should_throw_not_found_when_customer_does_not_exist() {
        // given
        CreateLoanApplicationRequest req = new CreateLoanApplicationRequest();
        req.setCustomerId(1L);

        when(customerRepo.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(CustomerNotFoundException.class,
                () -> service.createLoan(req));
    }

    @Test
    void should_get_loan_by_id_successfully() {
        // given
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(1L);
        loan.setCustomer(new CustomerEntity());

        when(loanRepo.findByIdWithCustomer(1L))
                .thenReturn(Optional.of(loan));

        // when
        LoanApplicationResponse result = service.getById(1L);

        // then
        assertEquals(1L, result.getId());
    }

    @Test
    void should_throw_not_found_when_loan_not_exist() {
        // given
        when(loanRepo.findByIdWithCustomer(1L))
                .thenReturn(Optional.empty());

        // when & then
        assertThrows(LoanApplicationNotFoundException.class,
                () -> service.getById(1L));
    }

    @Test
    void should_update_status_to_approved_from_submitted() {
        // given
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(1L);
        loan.setStatus("SUBMITTED");

        CustomerEntity customer = new CustomerEntity();
        customer.setId(10L);
        loan.setCustomer(customer);

        when(loanRepo.findById(1L)).thenReturn(Optional.of(loan));

        // when
        service.updateStatus(1L, "APPROVED");

        // then
        assertEquals("APPROVED", loan.getStatus());
        verify(loanRepo).saveAndFlush(loan);
    }

    @Test
    void should_throw_invalid_status() {
        // given
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setStatus("SUBMITTED");

        when(loanRepo.findById(1L)).thenReturn(Optional.of(loan));

        // when & then
        assertThrows(RuntimeException.class,
                () -> service.updateStatus(1L, "INVALID"));
    }

    @Test
    void should_throw_invalid_transition_from_submitted_to_rejected_directly() {
        // given
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setStatus("SUBMITTED");

        when(loanRepo.findById(1L)).thenReturn(Optional.of(loan));

        // when & then
        assertThrows(RuntimeException.class,
                () -> service.updateStatus(1L, "REJECTED"));
    }

    @Test
    void should_generate_schedule_when_disbursed_first_time() {
        // given
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(1L);
        loan.setStatus("APPROVED");

        CustomerEntity customer = new CustomerEntity();
        customer.setId(10L);
        loan.setCustomer(customer);

        when(loanRepo.findById(1L)).thenReturn(Optional.of(loan));
        when(repaymentService.existsByLoanId(1L)).thenReturn(false);

        // when
        service.updateStatus(1L, "DISBURSED");

        // then
        verify(repaymentService).generateSchedule(1L);
    }

    @Test
    void should_not_generate_schedule_if_already_exists() {
        // given
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(1L);
        loan.setStatus("APPROVED");

        loan.setCustomer(new CustomerEntity());

        when(loanRepo.findById(1L)).thenReturn(Optional.of(loan));
        when(repaymentService.existsByLoanId(1L)).thenReturn(true);

        // when
        service.updateStatus(1L, "DISBURSED");

        // then
        verify(repaymentService, never()).generateSchedule(any());
    }

    @Test
    void should_throw_invalid_transition_from_approved_to_rejected() {
        // given
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setStatus("APPROVED");

        when(loanRepo.findById(1L)).thenReturn(Optional.of(loan));

        // when & then
        assertThrows(RuntimeException.class,
                () -> service.updateStatus(1L, "REJECTED"));
    }

    @Test
    void should_throw_invalid_transition_from_approved_to_submitted() {
        // given
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setStatus("APPROVED");

        when(loanRepo.findById(1L)).thenReturn(Optional.of(loan));

        // when & then
        assertThrows(RuntimeException.class,
                () -> service.updateStatus(1L, "SUBMITTED"));
    }

    @Test
    void should_get_all_loans() {
        // given
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setCustomer(new CustomerEntity());

        when(loanRepo.findAll()).thenReturn(List.of(loan, loan));

        // when
        List<LoanApplicationResponse> result = service.getAll();

        // then
        assertEquals(2, result.size());
    }

    @Test
    void should_get_loans_by_customer_id() {
        // given
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setCustomer(new CustomerEntity());

        when(loanRepo.findByCustomerId(1L))
                .thenReturn(List.of(loan));

        // when
        List<LoanApplicationResponse> result = service.getByCustomerId(1L);

        // then
        assertEquals(1, result.size());
    }

    @Test

    void should_get_loans_by_status() {
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setCustomer(new CustomerEntity());

        when(loanRepo.findByStatus("APPROVED"))
                .thenReturn(List.of(loan));

        // when
        List<LoanApplicationResponse> result = service.getByStatus("APPROVED");

        // then
        assertEquals(1, result.size());
    }

    @Test
    void should_get_loans_with_pagination() {
        // given
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setCustomer(new CustomerEntity());

        Page<LoanApplicationEntity> page = new PageImpl<>(List.of(loan));

        when(loanRepo.findAll(any(PageRequest.class))).thenReturn(page);

        // when
        Page<LoanApplicationResponse> result = service.getAll(0, 10);

        // then
        assertEquals(1, result.getContent().size());
    }

    @Test
    void should_filter_by_date() {
        // given
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setCustomer(new CustomerEntity());

        when(loanRepo.findByCreatedAtBetween(any(), any()))
                .thenReturn(List.of(loan));

        // when
        List<LoanApplicationResponse> result = service.filterByDate(ZonedDateTime.now().minusDays(1),
                ZonedDateTime.now());

        // then
        assertEquals(1, result.size());
    }

    @Test
    void should_get_loan_summary_dto() {
        // given
        LoanSummaryDTO dto = mock(LoanSummaryDTO.class);

        when(loanRepo.getLoanSummaryDTO())
                .thenReturn(List.of(dto));

        // when
        List<LoanSummaryDTO> result = service.getLoanSummaryDTO();

        // then
        assertEquals(1, result.size());
    }

    @Test
    void should_throw_not_found_when_update_status_loan_not_exist() {
        // given
        when(loanRepo.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThrows(LoanApplicationNotFoundException.class,
                () -> service.updateStatus(1L, "APPROVED"));
    }

    @Test
    void should_throw_invalid_transition_from_submitted_to_rejected() {
        // given
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(1L);
        loan.setStatus("SUBMITTED");

        when(loanRepo.findById(1L)).thenReturn(Optional.of(loan));

        // when & then
        assertThrows(RuntimeException.class,
                () -> service.updateStatus(1L, "REJECTED"));

        verify(loanRepo, never()).saveAndFlush(any());
    }

    @Test
    void should_not_trigger_disbursement_logic_if_already_disbursed() {

        // given
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(1L);
        loan.setStatus("DISBURSED");

        loan.setCustomer(new CustomerEntity());

        when(loanRepo.findById(1L)).thenReturn(Optional.of(loan));

        // when
        service.updateStatus(1L, "DISBURSED");

        // then
        verify(repaymentService, never()).generateSchedule(any());
    }

    @Test
    void should_map_all_fields_in_response() {
        // given
        CustomerEntity customer = new CustomerEntity();
        customer.setId(10L);
        customer.setFullName("Budi");
        customer.setNik("123");
        customer.setEmail("budi@mail.com");

        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(1L);
        loan.setCustomer(customer);
        loan.setLoanAmount(BigDecimal.TEN);
        loan.setTenorMonth(12);
        loan.setPurpose("Test");
        loan.setStatus("SUBMITTED");
        loan.setCreatedAt(ZonedDateTime.now());
        loan.setUpdatedAt(ZonedDateTime.now());

        when(loanRepo.findByIdWithCustomer(1L))
                .thenReturn(Optional.of(loan));

        // when
        LoanApplicationResponse result = service.getById(1L);

        // then
        assertEquals(1L, result.getId());
        assertEquals(BigDecimal.TEN, result.getLoanAmount());
        assertEquals(12, result.getTenorMonth());
        assertEquals("SUBMITTED", result.getStatus());
        assertEquals(10L, result.getCustomer().getId());
    }

    @Test
    void should_return_response_after_update_status() {
        // given
        LoanApplicationEntity loan = new LoanApplicationEntity();
        loan.setId(1L);
        loan.setStatus("SUBMITTED");

        loan.setCustomer(new CustomerEntity());

        when(loanRepo.findById(1L)).thenReturn(Optional.of(loan));

        // when
        LoanApplicationResponse result = service.updateStatus(1L, "APPROVED");

        // then
        assertEquals("APPROVED", result.getStatus());
    }

}
