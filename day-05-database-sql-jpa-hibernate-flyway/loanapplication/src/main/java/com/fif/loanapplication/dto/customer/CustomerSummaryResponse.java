package com.fif.loanapplication.dto.customer;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@JsonPropertyOrder({
        "uid",
        "nik",
        "full_name",
        "email", })

public class CustomerSummaryResponse {

    private UUID uid;
    private String nik;
    @JsonProperty("full_name")
    private String fullName;
    private String email;

}
