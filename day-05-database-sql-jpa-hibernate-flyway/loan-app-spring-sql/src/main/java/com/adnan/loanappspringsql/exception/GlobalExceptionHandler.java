package com.adnan.loanappspringsql.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.adnan.loanappspringsql.dto.api.ApiResponse;
import com.adnan.loanappspringsql.dto.api.FieldErrorResponse;
import com.adnan.loanappspringsql.utils.LogUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiResponse<Void>> errorValidation(MethodArgumentNotValidException exception) {
                List<FieldErrorResponse> errors = exception.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> new FieldErrorResponse(
                                                toSnakeCase(error.getField()),
                                                error.getDefaultMessage()))
                                .toList();
                log.warn(LogUtil.format(
                                "validation_error",
                                "fieldCount", errors.size()));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(ApiResponse.error("VALIDATION_ERROR", "Invalid request", errors));
        }

        @ExceptionHandler(BadRequestException.class)
        public ResponseEntity<ApiResponse<Void>> badRequest(BadRequestException exception) {
                log.warn(LogUtil.format(
                                "bad_request",
                                "message", exception.getMessage()));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(ApiResponse.error("VALIDATION_ERROR",
                                                exception.getMessage() != null ? exception.getMessage()
                                                                : "Invalid request",
                                                null));
        }

        @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<ApiResponse<Void>> notFound(NotFoundException exception) {
                log.warn(LogUtil.format(
                                "resource_not_found",
                                "message", exception.getMessage()));
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(ApiResponse.error("NOT_FOUND",
                                                exception.getMessage() != null ? exception.getMessage()
                                                                : "Resource not found",
                                                null));
        }

        @ExceptionHandler(ForbiddenException.class)
        public ResponseEntity<ApiResponse<Void>> forbidden(ForbiddenException exception) {
                log.warn(LogUtil.format(
                                "forbidden_access"));
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                .body(ApiResponse.error("FORBIDDEN",
                                                exception.getMessage() != null ? exception.getMessage()
                                                                : "Access denied",
                                                null));
        }

        @ExceptionHandler(UnauthorizedException.class)
        public ResponseEntity<ApiResponse<Void>> unauthorized(UnauthorizedException exception) {
                log.warn(LogUtil.format(
                                "unauthorized_access"));
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .body(ApiResponse.error("UNAUTHORIZED",
                                                exception.getMessage() != null ? exception.getMessage()
                                                                : "Authentication is required",
                                                null));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiResponse<Void>> internalServerError(Exception exception) {
                log.error(LogUtil.format(
                                "unexpected_error"),
                                exception);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ApiResponse.error("INTERNAL_SERVER_ERROR", "Unexpected error occurred", null));
        }

        public static String toSnakeCase(String value) {
                return value
                                .replaceAll("([a-z])([A-Z])", "$1_$2")
                                .toLowerCase();
        }
}