package com.example.spring_boot_database.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.spring_boot_database.dto.ApiResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(
            MethodArgumentNotValidException exception
    ) {

        List<String> details = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.toList());

        ApiResponse<Void> response = ApiResponse.error(
                "VALIDATION_ERROR",
                "Invalid request",
                details
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex
    ) {
        ApiResponse<Void> response = ApiResponse.error(
                "MALFORMED_JSON",
                "Request body is invalid or malformed",
                List.of("JSON request body is not readable")
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex
    ) {
        ApiResponse<Void> response = ApiResponse.error(
                "INVALID_PARAMETER",
                "Invalid parameter value",
                List.of(ex.getName() + " has invalid value")
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex
    ) {
        ApiResponse<Void> response = ApiResponse.error(
                "MISSING_PARAMETER",
                "Required request parameter is missing",
                List.of(ex.getParameterName() + " is required")
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex
    ) {
        ApiResponse<Void> response = ApiResponse.error(
                "UNSUPPORTED_MEDIA_TYPE",
                "Content type is not supported",
                List.of(ex.getContentType() == null ? "Unsupported content type" : ex.getContentType().toString())
        );

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(response);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex
    ) {
        ApiResponse<Void> response = ApiResponse.error(
                "METHOD_NOT_ALLOWED",
                "HTTP method is not supported",
                List.of(ex.getMethod() + " method is not allowed for this endpoint")
        );

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleCustomerNotFound(CustomerNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, "CUSTOMER_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(LoanNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleLoanNotFound(LoanNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, "LOAN_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(RepaymentScheduleNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleRepaymentNotFound(RepaymentScheduleNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, "REPAYMENT_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(DuplicateCustomerException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicate(DuplicateCustomerException ex) {
        return build(HttpStatus.BAD_REQUEST, "DUPLICATE_DATA", ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(BadRequestException ex) {
        return build(HttpStatus.BAD_REQUEST, "BAD_REQUEST", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleUnexpected(Exception ex) {

        ApiResponse<Void> response = ApiResponse.error(
                "INTERNAL_SERVER_ERROR",
                "Unexpected error occurred",
                List.of(ex.getClass().getSimpleName())
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private ResponseEntity<ApiResponse<Void>> build(
            HttpStatus status,
            String error,
            String message
    ) {
        ApiResponse<Void> response = ApiResponse.error(
                error,
                message,
                null
        );

        return ResponseEntity.status(status).body(response);
    }
}