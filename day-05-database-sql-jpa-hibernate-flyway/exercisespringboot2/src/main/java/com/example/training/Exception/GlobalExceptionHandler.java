// Tangkap semua error dan convert ke ErrorResponse dengan HTTP status yang sesuai (404, 400, 409, 500).

package com.example.training.Exception;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.training.DTO.ErrorResponse;
import com.example.training.DTO.FieldErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j // tugas hari ini
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFound(CustomerNotFoundException ex) {
        log.warn("event=customer_not_found_error, message={}", ex.getMessage()); // tugas hari ini
        return buildErrorResponse(HttpStatus.NOT_FOUND, "CUSTOMER_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(LoanApplicationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLoanNotFound(LoanApplicationNotFoundException ex) {
        log.warn("event=loan_not_found_error, message={}", ex.getMessage()); // tugas hari ini
        return buildErrorResponse(HttpStatus.NOT_FOUND, "LOAN_APPLICATION_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(RepaymentScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRepaymentNotFound(RepaymentScheduleNotFoundException ex) {
        log.warn("event=repayment_schedule_not_found_error, message={}", ex.getMessage()); // tugas hari ini
        return buildErrorResponse(HttpStatus.NOT_FOUND, "REPAYMENT_SCHEDULE_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(DuplicateCustomerException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateCustomer(DuplicateCustomerException ex) {
        log.warn("event=duplicate_customer_error, message={}", ex.getMessage()); // tugas hari ini
        return buildErrorResponse(HttpStatus.CONFLICT, "DUPLICATE_CUSTOMER", ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<FieldErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(this::mapFieldError)
                .collect(Collectors.toList());

        List<String> fieldNames = errors.stream()
                .map(FieldErrorResponse::getField)
                .collect(Collectors.toList());

        log.warn("event=validation_error, fields={}, total_errors={}", fieldNames, errors.size()); // tugas hari ini

        return buildErrorResponse(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", "Invalid request", errors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("event=forbidden_access_or_invalid_argument, message={}", ex.getMessage()); // tugas hari ini
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "INVALID_ARGUMENT", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        log.error("event=unexpected_error, message={}, exception_class={}", // tugas hari ini
                ex.getMessage(), ex.getClass().getSimpleName(), ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "An unexpected error occurred");
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String code, String message) {
        return buildErrorResponse(status, code, message, List.of());
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String code, 
            String message, List<FieldErrorResponse> errors) {
        
        String correlationId = MDC.get("correlation_id");
        
        ErrorResponse response = ErrorResponse.builder()
                .success(false)
                .code(code)
                .message(message)
                .errors(errors)
                .corelationId(correlationId) 
                .build();
        
        return ResponseEntity.status(status).body(response);
    }

    private FieldErrorResponse mapFieldError(FieldError error) {
        return FieldErrorResponse.builder()
                .field(error.getField())
                .message(error.getDefaultMessage())
                .build();
    }
}