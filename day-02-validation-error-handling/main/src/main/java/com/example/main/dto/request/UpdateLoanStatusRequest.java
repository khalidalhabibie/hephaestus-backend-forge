package com.example.main.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request body untuk memperbarui status pengajuan pinjaman")
public class UpdateLoanStatusRequest {

    @NotBlank(message = "Status cannot be empty")
    @Schema(description = "Status baru untuk loan (e.g. DISBURSED, CLOSED)", example = "DISBURSED")
    private String status;
}