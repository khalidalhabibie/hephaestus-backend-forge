package com.example.spring_boot_database.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ErrorResponse <T> {
    private boolean success;

    private String code;

    private String message;

    private List<FieldErrorResponse> errors;
}

// {
//   "success": false,
//   "code": "CUSTOMER_NOT_FOUND",
//   "message": "Customer not found with id: 999",
//   "errors": []
// }