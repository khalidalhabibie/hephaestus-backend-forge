package com.adnan.exercisespring.dto;

import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse<T> {
  public String code;
  public String message;
  public List<FieldErrorResponse> errors;

  public static ErrorResponse<Void> error(String code, String message, List<FieldErrorResponse> errors) {
    return ErrorResponse.<Void>builder()
        .code(code)
        .message(message)
        .errors(errors == null ? Collections.emptyList() : errors)
        .build();
  }
}
