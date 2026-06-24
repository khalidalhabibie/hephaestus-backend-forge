package com.example.jpabackend.exception;

import com.example.jpabackend.dto.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

        // VALIDATION ERROR (400)
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiResponse<?>> handleValidation(
                        MethodArgumentNotValidException ex) {

                List<FieldErrorResponse> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(e -> new FieldErrorResponse(
                                                e.getField(),
                                                e.getDefaultMessage()))
                                .toList();

                return ResponseEntity.badRequest().body(
                                ApiResponse.error("VALIDATION_ERROR", "Invalid request"));
        }

        // CUSTOMER NOT FOUND
        @ExceptionHandler(CustomerNotFoundException.class)
        public ResponseEntity<ApiResponse<?>> handleCustomerNotFound(
                        CustomerNotFoundException ex) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                ApiResponse.error("CUSTOMER_NOT_FOUND", ex.getMessage()));
        }

        // LOAN NOT FOUND
        @ExceptionHandler(LoanApplicationNotFoundException.class)
        public ResponseEntity<ApiResponse<?>> handleLoanNotFound(
                        LoanApplicationNotFoundException ex) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                ApiResponse.error("LOAN_APPLICATION_NOT_FOUND", ex.getMessage()));
        }

        // REPAYMENT NOT FOUND
        @ExceptionHandler(RepaymentScheduleNotFoundException.class)
        public ResponseEntity<ApiResponse<?>> handleRepaymentNotFound(
                        RepaymentScheduleNotFoundException ex) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                ApiResponse.error("REPAYMENT_SCHEDULE_NOT_FOUND", ex.getMessage()));
        }

        // DUPLICATE
        @ExceptionHandler(DuplicateCustomerException.class)
        public ResponseEntity<ApiResponse<?>> handleDuplicate(
                        DuplicateCustomerException ex) {

                return ResponseEntity.badRequest().body(
                                ApiResponse.error("DUPLICATE_CUSTOMER", ex.getMessage()));
        }

        // GENERIC ERROR
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiResponse<?>> handleGeneral(Exception ex) {

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                                ApiResponse.error("INTERNAL_SERVER_ERROR", ex.getMessage()));
        }
}