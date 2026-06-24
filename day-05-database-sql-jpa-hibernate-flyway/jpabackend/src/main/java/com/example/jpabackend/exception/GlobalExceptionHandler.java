package com.example.jpabackend.exception;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.jpabackend.dto.common.ErrorResponse;
import com.example.jpabackend.dto.common.FieldErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String correlationId = MDC.get("correlation_id");
        log.warn("event=validation_error");
        List<FieldErrorResponse> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(fieldError -> FieldErrorResponse.builder()
            .field(fieldError.getField())
            .message(fieldError.getDefaultMessage())
            .build())
            .toList();
        ErrorResponse response = ErrorResponse.builder()
            .success(false)
            .code("VALIDATION_ERROR")
            .message("Invalid request")
            .correlationId(correlationId)
            .errors(errors)
            .build();
    return ResponseEntity.badRequest()
            .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        String correlationId = MDC.get("correlation_id");
        log.error("event=unexpected_error", ex);
        ErrorResponse response = ErrorResponse.builder()
            .success(false)
            .code("INTERNAL_SERVER_ERROR")
            .message("Unexpected error occurred")
            .correlationId(correlationId)
            .errors(List.of())
            .build();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(response);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFound(CustomerNotFoundException ex) {
        String correlationId = MDC.get("correlation_id");
        log.warn("event=validation_error message=customer_not_found");
        ErrorResponse response = ErrorResponse.builder()
            .success(false)
            .code("CUSTOMER_NOT_FOUND")
            .message(ex.getMessage())
            .correlationId(correlationId)
            .errors(List.of())
            .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(response);
    }

    @ExceptionHandler(DuplicateCustomerException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateCustomer(DuplicateCustomerException ex) {
        String correlationId = MDC.get("correlation_id");
        log.warn("event=validation_error message=duplicate_customer");
        ErrorResponse response = ErrorResponse.builder()
            .success(false)
            .code("DUPLICATE_CUSTOMER")
            .message(ex.getMessage())
            .correlationId(correlationId)
            .errors(List.of())
            .build();
    return ResponseEntity.badRequest()
            .body(response);
    }

    @ExceptionHandler(LoanApplicationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLoanNotFound(LoanApplicationNotFoundException ex) {
        String correlationId = MDC.get("correlation_id");
        log.warn("event=validation_error message=loan_not_found");

        ErrorResponse response = ErrorResponse.builder()
            .success(false)
            .code("LOAN_APPLICATION_NOT_FOUND")
            .message(ex.getMessage())
            .correlationId(correlationId)
            .errors(List.of())
            .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(response);
    }

    @ExceptionHandler(PaymentTransactionNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePaymentNotFound(PaymentTransactionNotFoundException ex) {
        String correlationId = MDC.get("correlation_id");
        log.warn("event=validation_error message=payment_not_found");

        ErrorResponse response = ErrorResponse.builder()
            .success(false)
            .code("PAYMENT_TRANSACTION_NOT_FOUND")
            .message(ex.getMessage())
            .correlationId(correlationId)
            .errors(List.of())
            .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(response);
    }

    @ExceptionHandler(RepaymentScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleScheduleNotFound(RepaymentScheduleNotFoundException ex) {
        String correlationId = MDC.get("correlation_id");
        log.warn("event=validation_error message=schedule_not_found");
        ErrorResponse response = ErrorResponse.builder()
            .success(false)
            .code("REPAYMENT_SCHEDULE_NOT_FOUND")
            .message(ex.getMessage())
            .correlationId(correlationId)
            .errors(List.of())
            .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(response);
    }
}
