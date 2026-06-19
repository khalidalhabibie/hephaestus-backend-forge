package com.example.training.dto;

import com.example.training.model.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonPropertyOrder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    @JsonProperty("username")
    private String username;
    @JsonProperty("role")
    private Role role;
}
