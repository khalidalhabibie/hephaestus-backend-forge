package com.example.dbbackend.repaymentschedule.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dbbackend.common.exception.LoanApplicationNotFoundException;
import com.example.dbbackend.common.exception.RepaymentScheduleNotFoundException;
import com.example.dbbackend.loanapplication.repository.LoanApplicationRepository;
import com.example.dbbackend.repaymentschedule.dto.RepaymentScheduleResponse;
import com.example.dbbackend.repaymentschedule.entity.RepaymentScheduleEntity;
import com.example.dbbackend.repaymentschedule.entity.RepaymentStatus;
import com.example.dbbackend.repaymentschedule.repository.RepaymentScheduleRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class RepaymentScheduleService {

    private final RepaymentScheduleRepository repository;
    private final LoanApplicationRepository loanRepository;

    public RepaymentScheduleService(RepaymentScheduleRepository repository,
            LoanApplicationRepository loanRepository) {
        this.repository = repository;
        this.loanRepository = loanRepository;
    }

    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponse> getByLoanId(Long loanId) {

        loanRepository.findById(loanId)
                .orElseThrow(() -> {
                    log.warn("event=RepaymentFetchFailed reason=LoanNotFound loanId={}", loanId);
                    return new LoanApplicationNotFoundException("Loan not found with id: " + loanId);
                });

        log.info("RepaymentListRequested | loanId={}", loanId);

        return repository.findByLoanApplicationId(loanId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private RepaymentScheduleResponse mapToResponse(RepaymentScheduleEntity entity) {

        RepaymentScheduleResponse res = new RepaymentScheduleResponse();
        res.setId(entity.getId());
        res.setInstallmentNumber(entity.getInstallmentNumber());
        res.setDueDate(entity.getDueDate());
        res.setPrincipalAmount(entity.getPrincipalAmount());
        res.setInterestAmount(entity.getInterestAmount());
        res.setTotalAmount(entity.getTotalAmount());
        res.setStatus(RepaymentStatus.valueOf(entity.getStatus()));

        return res;
    }

    @Transactional(readOnly = true)
    public RepaymentScheduleResponse getById(Long id) {

        RepaymentScheduleEntity entity = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("event=RepaymentNotFound scheduleId={}", id);
                    return new RepaymentScheduleNotFoundException("Schedule not found with id: " + id);
                });

        log.info("RepaymentFetched | scheduleId={}", id);

        return mapToResponse(entity);
    }

}
