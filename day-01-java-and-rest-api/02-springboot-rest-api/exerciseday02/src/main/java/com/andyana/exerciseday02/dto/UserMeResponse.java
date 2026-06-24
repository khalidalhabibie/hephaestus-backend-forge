package com.andyana.exerciseday02.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserMeResponse {
    private String username;
    private String role;
}
