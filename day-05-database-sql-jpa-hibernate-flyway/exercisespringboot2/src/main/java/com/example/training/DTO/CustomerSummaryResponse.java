// Format JSON response data customer ringkas (tanpa phone, createdAt). Dipakai di dalam loan response.

package com.example.training.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerSummaryResponse {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("nik")
    private String nik;

    @JsonProperty("email")
    private String email;
}