package com.example.demoSpringbootDatabase.exception;

import com.example.demoSpringbootDatabase.dto.ErrorResponse;
import com.example.demoSpringbootDatabase.dto.FieldErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFound(CustomerNotFoundException ex) {
        String correlationId = MDC.get("correlation_id");
        log.error("{\"event\":\"UNEXPECTED_ERROR\", \"message\":\"{}\", \"correlation_id\":\"{}\"}", ex.getMessage(), correlationId);
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ErrorResponse.builder().success(false).code("CUSTOMER_NOT_FOUND").message(ex.getMessage()).correlationId(correlationId).errors(new ArrayList<>()).build()
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        String correlationId = MDC.get("correlation_id");
        log.error("{\"event\":\"VALIDATION_ERROR\", \"message\":\"{}\", \"correlation_id\":\"{}\"}", ex.getMessage(), correlationId);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponse.builder().success(false).code("BAD_REQUEST").message(ex.getMessage()).correlationId(correlationId).errors(new ArrayList<>()).build()
        );
    }
    
    @ExceptionHandler(LoanApplicationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLoanNotFound(LoanApplicationNotFoundException ex) {
        String correlationId = MDC.get("correlation_id");
        log.error("{\"event\":\"UNEXPECTED_ERROR\", \"message\":\"{}\", \"correlation_id\":\"{}\"}", ex.getMessage(), correlationId);
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ErrorResponse.builder().success(false).code("LOAN_APPLICATION_NOT_FOUND").message(ex.getMessage()).correlationId(correlationId).errors(new ArrayList<>()).build()
        );
    }

    @ExceptionHandler(RepaymentScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleScheduleNotFound(RepaymentScheduleNotFoundException ex) {
        String correlationId = MDC.get("correlation_id");
        log.error("{\"event\":\"UNEXPECTED_ERROR\", \"message\":\"{}\", \"correlation_id\":\"{}\"}", ex.getMessage(), correlationId);
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ErrorResponse.builder().success(false).code("REPAYMENT_SCHEDULE_NOT_FOUND").message(ex.getMessage()).correlationId(correlationId).errors(new ArrayList<>()).build()
        );
    }

    @ExceptionHandler(DuplicateCustomerException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(DuplicateCustomerException ex) {
        String correlationId = MDC.get("correlation_id");
        log.error("{\"event\":\"VALIDATION_ERROR\", \"message\":\"{}\", \"correlation_id\":\"{}\"}", ex.getMessage(), correlationId);
        
        // Sesuai standarmu, duplicate mengembalikan status BAD_REQUEST / CONFLICT
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            ErrorResponse.builder().success(false).code("DUPLICATE_DATA").message(ex.getMessage()).correlationId(correlationId).errors(new ArrayList<>()).build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String correlationId = MDC.get("correlation_id");
        List<FieldErrorResponse> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> new FieldErrorResponse(err.getField(), err.getDefaultMessage()))
                .toList();

        log.error("{\"event\":\"VALIDATION_ERROR\", \"message\":\"Invalid request body parameters\", \"correlation_id\":\"{}\"}", correlationId);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponse.builder().success(false).code("VALIDATION_ERROR").message("Invalid request").correlationId(correlationId).errors(errors).build()
        );
    }
}