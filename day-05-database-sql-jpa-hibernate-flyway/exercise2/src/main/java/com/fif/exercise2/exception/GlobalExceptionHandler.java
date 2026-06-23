package com.fif.exercise2.exception;

import com.fif.exercise2.dto.ErrorResponse;
import com.fif.exercise2.dto.FieldErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 404 NOT FOUND
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFound(CustomerNotFoundException ex) {
        // WARN: data tidak ditemukan adalah kondisi bisnis yang sudah diprediksi
        log.warn("event=customer_not_found message=\"{}\" correlation_id={}",
                ex.getMessage(), getCorrelationId());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildError("CUSTOMER_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(LoanApplicationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLoanNotFound(LoanApplicationNotFoundException ex) {
        log.warn("event=loan_not_found message=\"{}\" correlation_id={}",
                ex.getMessage(), getCorrelationId());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildError("LOAN_APPLICATION_NOT_FOUND", ex.getMessage()));
    }

    @ExceptionHandler(RepaymentScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRepaymentNotFound(RepaymentScheduleNotFoundException ex) {
        log.warn("event=repayment_not_found message=\"{}\" correlation_id={}",
                ex.getMessage(), getCorrelationId());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildError("REPAYMENT_SCHEDULE_NOT_FOUND", ex.getMessage()));
    }

    // 409 CONFLICT
    @ExceptionHandler(DuplicateCustomerException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateCustomer(DuplicateCustomerException ex) {
        // WARN: duplikat adalah kondisi bisnis yang bisa diprediksi, bukan bug
        log.warn("event=validation_error error_code=DUPLICATE_CUSTOMER message=\"{}\" correlation_id={}",
                ex.getMessage(), getCorrelationId());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(buildError("DUPLICATE_CUSTOMER", ex.getMessage()));
    }


    // 400 BAD REQUEST — Validation (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationError(MethodArgumentNotValidException ex) {
        List<FieldErrorResponse> fieldErrors = new ArrayList<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.add(new FieldErrorResponse(fe.getField(), fe.getDefaultMessage()));
        }

        // WARN: input tidak valid adalah kesalahan client, bukan bug server
        log.warn("event=validation_error field_count={} correlation_id={}",
                fieldErrors.size(), getCorrelationId());

        ErrorResponse response = new ErrorResponse(false, "VALIDATION_ERROR", "Invalid request", fieldErrors);
        response.setCorrelationId(getCorrelationId());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(InvalidLoanStatusException.class)
    public ResponseEntity<ErrorResponse> handleInvalidLoanStatus(InvalidLoanStatusException ex) {
        log.warn("event=validation_error error_code=INVALID_LOAN_STATUS message=\"{}\" correlation_id={}",
                ex.getMessage(), getCorrelationId());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildError("INVALID_LOAN_STATUS", ex.getMessage()));
    }


    // 500 INTERNAL SERVER ERROR — Unexpected
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        // ERROR: tidak terduga — developer perlu investigasi
        // Intentionally tidak log ex.getMessage() ke client untuk menghindari info leakage
        // Stack trace dicatat di log server, tapi TIDAK dikirim ke response
        log.error("event=unexpected_error error_class={} correlation_id={}",
                ex.getClass().getSimpleName(), getCorrelationId(), ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildError("INTERNAL_SERVER_ERROR", "Unexpected error occurred"));
    }


    // Helpers
    // Ambil correlation_id dari MDC yang sudah di-set oleh RequestIdFilter. Jika tidak ada (misal: error sebelum filter jalan), kembalikan "unknown".
    private String getCorrelationId() {
        String id = MDC.get("correlation_id");
        return id != null ? id : "unknown";
    }

    //Builder ErrorResponse yang selalu menyertakan correlation_id.
    private ErrorResponse buildError(String code, String message) {
        ErrorResponse response = new ErrorResponse(false, code, message, new ArrayList<>());
        response.setCorrelationId(getCorrelationId());
        return response;
    }
}