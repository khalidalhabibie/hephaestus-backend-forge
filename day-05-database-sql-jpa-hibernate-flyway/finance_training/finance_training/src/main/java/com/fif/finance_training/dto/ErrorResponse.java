package com.fif.finance_training.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private boolean success;
    private String code;
    private String message;
    
    // List ini bisa kosong untuk error 404, tapi akan terisi jika ada validasi error (400)
    private List<FieldErrorResponse> errors;
}
