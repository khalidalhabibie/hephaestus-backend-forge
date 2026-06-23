package com.fif.exercise2.service;

import com.fif.exercise2.dto.RepaymentScheduleResponse;
import com.fif.exercise2.entity.RepaymentScheduleEntity;
import com.fif.exercise2.exception.RepaymentScheduleNotFoundException;
import com.fif.exercise2.repository.RepaymentScheduleRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RepaymentScheduleService {

    private static final Logger log = LoggerFactory.getLogger(RepaymentScheduleService.class);

    private final RepaymentScheduleRepository repaymentScheduleRepository;

    @Transactional(readOnly = true)
    public List<RepaymentScheduleResponse> getByLoanApplicationId(
            Long loanApplicationId, String status) {
        List<RepaymentScheduleEntity> result = status != null
            ? repaymentScheduleRepository.findByLoanApplicationIdAndStatus(loanApplicationId, status)
            : repaymentScheduleRepository.findByLoanApplicationId(loanApplicationId);

        // INFO: query data — berguna untuk trace apakah data berhasil diambil
        // Tidak ada PII di sini, hanya ID dan jumlah data
        log.info("event=repayment_schedule_fetched loan_application_id={} status_filter={} result_count={}",
                loanApplicationId,
                status != null ? status : "ALL",
                result.size());

        return result.stream()
            .map(this::buildResponse)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RepaymentScheduleResponse getById(Long id) {
        RepaymentScheduleEntity entity = repaymentScheduleRepository.findById(id)
            .orElseThrow(() -> new RepaymentScheduleNotFoundException(id));

        // INFO: level debug sebenarnya lebih tepat untuk get by id,
        // tapi karena tidak ada debug level di requirement, pakai info
        log.info("event=repayment_schedule_fetched schedule_id={} status={}",
                entity.getId(), entity.getStatus());

        return buildResponse(entity);
    }

    private RepaymentScheduleResponse buildResponse(RepaymentScheduleEntity entity) {
        RepaymentScheduleResponse response = new RepaymentScheduleResponse();
        response.setId(entity.getId());
        response.setInstallmentNumber(entity.getInstallmentNumber());
        response.setDueDate(entity.getDueDate());
        response.setPrincipalAmount(entity.getPrincipalAmount());
        response.setInterestAmount(entity.getInterestAmount());
        response.setTotalAmount(entity.getTotalAmount());
        response.setStatus(entity.getStatus());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        return response;
    }
}