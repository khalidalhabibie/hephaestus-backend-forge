package com.example.main.dto.response;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserMeResponse {
    private String username;
    private String role;
}