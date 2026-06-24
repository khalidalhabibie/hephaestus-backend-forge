package com.example.exercise.dto;


import com.example.exercise.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonPropertyOrder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    @JsonProperty("email")
    private String email;
    @JsonProperty("role")
    private Role role;
}