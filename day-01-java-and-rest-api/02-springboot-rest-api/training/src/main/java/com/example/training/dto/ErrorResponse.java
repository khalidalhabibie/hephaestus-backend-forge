package com.example.training.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Structure untuk error response")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse<T> {

    @Schema(description = "Kode error", example = "INVALID_REQUEST")
    @JsonProperty
    private String code;

    @Schema(description = "Pesan error", example = "Invalid Request")
    private String message;

    @Schema(description = "List field yang error")
    private List<FieldErrorResponse> fieldErrorResponses;

    public static ErrorResponse<Void> error(String code, String message, List<FieldErrorResponse> responses) {
        return ErrorResponse.<Void>builder()
            .code(code)
            .message(message)
            .fieldErrorResponses(responses)
            .build();
    }
}