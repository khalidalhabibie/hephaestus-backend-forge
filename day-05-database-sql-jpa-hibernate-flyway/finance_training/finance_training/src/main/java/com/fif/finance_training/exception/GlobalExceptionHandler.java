package com.fif.finance_training.exception;

import com.fif.finance_training.dto.ErrorResponse;
import com.fif.finance_training.dto.FieldErrorResponse;
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

    // 1. Handler untuk Validasi DTO (@NotBlank, @Min, dll) -> HTTP 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<FieldErrorResponse> details = new ArrayList<>();

        for (FieldError error : fieldErrors) {
            FieldErrorResponse response = FieldErrorResponse.builder()
                    .field(error.getField())
                    .message(error.getDefaultMessage())
                    .build();
            details.add(response);
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .success(false)
                .code("VALIDATION_ERROR")
                .message("Invalid request")
                .errors(details)
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    // 2. Handler jika Customer tidak ditemukan -> HTTP 404
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFound(CustomerNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .success(false)
                .code("CUSTOMER_NOT_FOUND")
                .message(ex.getMessage())
                .errors(new ArrayList<>()) // Menghasilkan "errors": [] di JSON
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // 3. Handler jika Loan tidak ditemukan -> HTTP 404
    @ExceptionHandler(LoanApplicationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLoanNotFound(LoanApplicationNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .success(false)
                .code("LOAN_APPLICATION_NOT_FOUND")
                .message(ex.getMessage())
                .errors(new ArrayList<>())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // 4. Handler jika Jadwal Cicilan tidak ditemukan -> HTTP 404
    @ExceptionHandler(RepaymentScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRepaymentScheduleNotFound(RepaymentScheduleNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .success(false)
                .code("REPAYMENT_SCHEDULE_NOT_FOUND")
                .message(ex.getMessage())
                .errors(new ArrayList<>())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // 5. Handler untuk Duplikasi NIK / Email -> HTTP 400
    @ExceptionHandler(DuplicateCustomerException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateCustomer(DuplicateCustomerException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .success(false)
                .code("DUPLICATE_CUSTOMER")
                .message(ex.getMessage())
                .errors(new ArrayList<>())
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    // 6. Handler untuk pelanggaran alur bisnis/status pinjaman (IllegalArgumentException) -> HTTP 400
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .success(false)
                .code("BAD_REQUEST")
                .message(ex.getMessage())
                .errors(new ArrayList<>())
                .build();

        return ResponseEntity.badRequest().body(errorResponse);
    }

    // 7. Global Catch-All untuk runtime error tidak terduga -> HTTP 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .success(false)
                .code("INTERNAL_SERVER_ERROR")
                .message("Unexpected error occurred: " + ex.getMessage())
                .errors(new ArrayList<>())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}