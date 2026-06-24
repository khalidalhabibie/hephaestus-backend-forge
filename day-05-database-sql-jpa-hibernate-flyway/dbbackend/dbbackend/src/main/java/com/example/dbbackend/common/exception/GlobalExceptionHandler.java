package com.example.dbbackend.common.exception;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.dbbackend.common.dto.ErrorResponse;
import com.example.dbbackend.common.dto.FieldErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Customer not found
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFound(CustomerNotFoundException ex) {

        ErrorResponse error = new ErrorResponse();
        error.setSuccess(false);
        error.setCode("CUSTOMER_NOT_FOUND");
        error.setMessage(ex.getMessage());
        error.setErrors(List.of());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Loan not found
    @ExceptionHandler(LoanApplicationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLoanNotFound(LoanApplicationNotFoundException ex) {

        ErrorResponse error = new ErrorResponse();
        error.setSuccess(false);
        error.setCode("LOAN_APPLICATION_NOT_FOUND");
        error.setMessage(ex.getMessage());
        error.setErrors(List.of());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // repayment not found
    @ExceptionHandler(RepaymentScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleScheduleNotFound(RepaymentScheduleNotFoundException ex) {

        ErrorResponse error = new ErrorResponse();
        error.setSuccess(false);
        error.setCode("REPAYMENT_SCHEDULE_NOT_FOUND");
        error.setMessage(ex.getMessage());
        error.setErrors(List.of());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // duplicate customer
    @ExceptionHandler(DuplicateCustomerException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(DuplicateCustomerException ex) {

        ErrorResponse error = new ErrorResponse();
        error.setSuccess(false);
        error.setCode("DUPLICATE_CUSTOMER");
        error.setMessage(ex.getMessage());
        error.setErrors(List.of());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {

        List<FieldErrorResponse> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.add(new FieldErrorResponse(err.getField(), err.getDefaultMessage())));

        ErrorResponse response = new ErrorResponse();
        response.setSuccess(false);
        response.setCode("VALIDATION_ERROR");
        response.setMessage("Invalid request");
        response.setErrors(errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
