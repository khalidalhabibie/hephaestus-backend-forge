package com.adnan.loanappspringsql.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerSummaryResponse {
  private Long id;

  @JsonProperty("full_name")
  private String fullName;

  private String nik;

  private String email;
}