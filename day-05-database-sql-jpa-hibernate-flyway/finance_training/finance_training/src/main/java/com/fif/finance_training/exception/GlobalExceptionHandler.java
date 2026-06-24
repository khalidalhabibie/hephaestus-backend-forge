package com.fif.finance_training.exception;

import com.fif.finance_training.dto.ErrorResponse;
import com.fif.finance_training.dto.FieldErrorResponse;
import com.fif.finance_training.web.CorrelationIdContext;
import com.fif.finance_training.web.StructuredLogger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j // logging
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final StructuredLogger logger;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        String correlationId = CorrelationIdContext.get();

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<FieldErrorResponse> details = new ArrayList<>();

        for (FieldError error : fieldErrors) {
            details.add(FieldErrorResponse.builder()
                    .field(error.getField())
                    .message(error.getDefaultMessage())
                    .build());
        }

        logger.warn("VALIDATION_ERROR", "Request validation failed",
                "errorCount", String.valueOf(details.size()),
                "fields", details.stream().map(FieldErrorResponse::getField).toList().toString());

        return ResponseEntity.badRequest().body(ErrorResponse.builder()
                .success(false)
                .code("VALIDATION_ERROR")
                .message("Invalid request")
                .correlationId(correlationId)
                .errors(details)
                .build());
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFound(CustomerNotFoundException ex) {
        String correlationId = CorrelationIdContext.get();

        logger.warn("CUSTOMER_NOT_FOUND", ex.getMessage(),
                "customerId", extractId(ex.getMessage()));

        return buildError("CUSTOMER_NOT_FOUND", ex.getMessage(), correlationId, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LoanApplicationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLoanNotFound(LoanApplicationNotFoundException ex) {
        String correlationId = CorrelationIdContext.get();

        logger.warn("LOAN_NOT_FOUND", ex.getMessage(),
                "loanId", extractId(ex.getMessage()));

        return buildError("LOAN_APPLICATION_NOT_FOUND", ex.getMessage(), correlationId, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RepaymentScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRepaymentScheduleNotFound(RepaymentScheduleNotFoundException ex) {
        String correlationId = CorrelationIdContext.get();

        return buildError("REPAYMENT_SCHEDULE_NOT_FOUND", ex.getMessage(), correlationId, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateCustomerException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateCustomer(DuplicateCustomerException ex) {
        String correlationId = CorrelationIdContext.get();

        logger.warn("DUPLICATE_CUSTOMER", ex.getMessage());

        return buildError("DUPLICATE_CUSTOMER", ex.getMessage(), correlationId, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        String correlationId = CorrelationIdContext.get();

        logger.warn("BUSINESS_RULE_VIOLATION", ex.getMessage(),
                "violation", ex.getMessage());

        return buildError("BAD_REQUEST", ex.getMessage(), correlationId, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        String correlationId = CorrelationIdContext.get();

        logger.error("UNEXPECTED_ERROR", "Unexpected error occurred", ex,
                "errorType", ex.getClass().getSimpleName());

        return buildError("INTERNAL_SERVER_ERROR",
                "Unexpected error occurred. Reference: " + correlationId,
                correlationId, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> buildError(String code, String message, String correlationId, HttpStatus status) {
        return ResponseEntity.status(status).body(ErrorResponse.builder()
                .success(false)
                .code(code)
                .message(message)
                .correlationId(correlationId)
                .errors(new ArrayList<>())
                .build());
    }

    private String extractId(String message) {
        if (message == null) return "unknown";
        String[] parts = message.split("id: ");
        return parts.length > 1 ? parts[1] : "unknown";
    }
}