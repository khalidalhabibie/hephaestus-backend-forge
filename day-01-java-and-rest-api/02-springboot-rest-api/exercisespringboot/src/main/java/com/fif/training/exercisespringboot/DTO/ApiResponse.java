package com.fif.training.exercisespringboot.DTO;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL) 
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiResponse<T> {

    private String code;
    private String message;
    private List<String> errors;
    private T data;

    public ApiResponse(T response) {
        this.data = response;
    }

    public ApiResponse(
            String code,
            String message,
            List<String> errors) {

        this.code = code;
        this.message = message;
        this.errors = errors;
    }
}