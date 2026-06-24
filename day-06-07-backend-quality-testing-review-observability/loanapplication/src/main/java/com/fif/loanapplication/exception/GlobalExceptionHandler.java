package com.fif.loanapplication.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fif.loanapplication.common.log.LogContext;
import com.fif.loanapplication.dto.exception.ErrorResponse;
import com.fif.loanapplication.dto.exception.FieldErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

        // Field Error Validation
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleValidationException(
                        MethodArgumentNotValidException exception,
                        HttpServletRequest request) {
                List<FieldErrorResponse> errors = new ArrayList<>();

                for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
                        FieldErrorResponse error = new FieldErrorResponse(
                                        fieldError.getField(),
                                        fieldError.getDefaultMessage());
                        errors.add(error);
                }

                log.warn(
                                "event=validation_error status=failed correlation_id={} path={} method={} error_count={}",
                                LogContext.getCorrelationId(),
                                request.getRequestURI(),
                                request.getMethod(),
                                errors.size());

                ErrorResponse response = ErrorResponse.builder()
                                .code("VALIDATION_ERROR")
                                .message("INVALID REQUEST!")
                                .correlationId(LogContext.getCorrelationId())
                                .errors(errors)
                                .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Customer Not Found Exception
        @ExceptionHandler(CustomerNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(
                        CustomerNotFoundException exception,
                        HttpServletRequest request) {
                log.warn(
                                "event=customer_not_found status=failed correlation_id={} path={} method={}",
                                LogContext.getCorrelationId(),
                                request.getRequestURI(),
                                request.getMethod());

                ErrorResponse response = ErrorResponse.builder()
                                .code("CUSTOMER_NOT_FOUND")
                                .message(exception.getMessage())
                                .correlationId(LogContext.getCorrelationId())
                                .errors(List.of())
                                .build();

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Loan Not Found Exception
        @ExceptionHandler(LoanApplicationNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleLoanApplicationNotFoundException(
                        LoanApplicationNotFoundException exception,
                        HttpServletRequest request) {
                log.warn(
                                "event=loan_application_not_found status=failed correlation_id={} path={} method={}",
                                LogContext.getCorrelationId(),
                                request.getRequestURI(),
                                request.getMethod());

                ErrorResponse response = ErrorResponse.builder()
                                .code("LOAN_APPLICATION_NOT_FOUND")
                                .message(exception.getMessage())
                                .correlationId(LogContext.getCorrelationId())
                                .errors(List.of())
                                .build();

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Repayment Schedule Not Found Exception
        @ExceptionHandler(RepaymentScheduleNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleRepaymentScheduleNotFoundException(
                        RepaymentScheduleNotFoundException exception,
                        HttpServletRequest request) {
                log.warn(
                                "event=repayment_schedule_not_found status=failed correlation_id={} path={} method={}",
                                LogContext.getCorrelationId(),
                                request.getRequestURI(),
                                request.getMethod());

                ErrorResponse response = ErrorResponse.builder()
                                .code("REPAYMENT_SCHEDULE_NOT_FOUND")
                                .message(exception.getMessage())
                                .correlationId(LogContext.getCorrelationId())
                                .errors(List.of())
                                .build();

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Duplicated Customer Data Exception
        @ExceptionHandler(DuplicateCustomerException.class)
        public ResponseEntity<ErrorResponse> handleDuplicateCustomerException(
                        DuplicateCustomerException exception,
                        HttpServletRequest request) {
                log.warn(
                                "event=duplicate_customer status=failed correlation_id={} path={} method={}",
                                LogContext.getCorrelationId(),
                                request.getRequestURI(),
                                request.getMethod());

                ErrorResponse response = ErrorResponse.builder()
                                .code("DUPLICATED_CUSTOMER_DATA")
                                .message(exception.getMessage())
                                .correlationId(LogContext.getCorrelationId())
                                .errors(List.of())
                                .build();

                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        // Bad Request Exception
        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<ErrorResponse> handleBadRequestException(
                        BadRequestException exception,
                        HttpServletRequest request) {
                log.warn(
                                "event=bad_request status=failed correlation_id={} path={} method={}",
                                LogContext.getCorrelationId(),
                                request.getRequestURI(),
                                request.getMethod());

                ErrorResponse response = ErrorResponse.builder()
                                .code("BAD_REQUEST")
                                .message(exception.getMessage())
                                .correlationId(LogContext.getCorrelationId())
                                .errors(List.of())
                                .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Forbidden Access
        @ExceptionHandler(ForbiddenAccessException.class)
        public ResponseEntity<ErrorResponse> handleForbiddenAccessException(
                        ForbiddenAccessException exception,
                        HttpServletRequest request) {
                log.warn(
                                "event=forbidden_access status=failed correlation_id={} path={} method={}",
                                LogContext.getCorrelationId(),
                                request.getRequestURI(),
                                request.getMethod());

                ErrorResponse response = ErrorResponse.builder()
                                .code("FORBIDDEN_ACCESS")
                                .message(exception.getMessage())
                                .correlationId(LogContext.getCorrelationId())
                                .errors(List.of())
                                .build();

                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        // Unexpected Error
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handleUnexpectedError(
                        Exception exception,
                        HttpServletRequest request) {
                log.error(
                                "event=unexpected_error status=failed correlation_id={} path={} method={} exception_type={}",
                                LogContext.getCorrelationId(),
                                request.getRequestURI(),
                                request.getMethod(),
                                exception.getClass().getSimpleName());

                ErrorResponse response = ErrorResponse.builder()
                                .code("INTERNAL_SERVER_ERROR")
                                .message("Unexpected error occurred.")
                                .correlationId(LogContext.getCorrelationId())
                                .errors(List.of())
                                .build();

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
}