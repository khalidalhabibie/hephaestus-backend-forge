package com.fif.loanapplication.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fif.loanapplication.dto.exception.ErrorResponse;
import com.fif.loanapplication.dto.exception.FieldErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Field Error Validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> ValidationException(MethodArgumentNotValidException exception) {
        List<FieldErrorResponse> errors = new ArrayList<>();

        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            FieldErrorResponse error = new FieldErrorResponse(
                    fieldError.getField(),
                    fieldError.getDefaultMessage());
            errors.add(error);
        }

        ErrorResponse response = new ErrorResponse("VALIDATION_EROR", "INVALID REQUEST!", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Customer Not Found Exception
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> customerNotFoundException(CustomerNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .code("CUSTOMER_NOT_FOUND")
                        .message(exception.getMessage())
                        .errors(List.of())
                        .build());
    }

    // Loan Not Found Exception
    @ExceptionHandler(LoanApplicationNotFoundException.class)
    public ResponseEntity<ErrorResponse> loanApplicationNotFoundException(LoanApplicationNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .code("LOAN_APPLICATION_NOT_FOUND")
                        .message(exception.getMessage())
                        .errors(List.of())
                        .build());
    }

    // Loan Not Found Exception
    @ExceptionHandler(RepaymentScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponse> repaymentScheduleNotFoundException(
            RepaymentScheduleNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .code("REPAYMENT_SCHEDULE_NOT_FOUND")
                        .message(exception.getMessage())
                        .errors(List.of())
                        .build());
    }

    // Duplicated Customer Data Exception
    @ExceptionHandler(DuplicateCustomerException.class)
    public ResponseEntity<ErrorResponse> duplicatedCustomerException(DuplicateCustomerException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ErrorResponse.builder()
                        .code("DUPLICATED_CUSTOMER_DATA")
                        .message(exception.getMessage())
                        .errors(List.of())
                        .build());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(BadRequestException exception) {
        return new ErrorResponse(
                "BAD_REQUEST",
                exception.getMessage(),
                List.of());
    }

}