package com.example.jpabackend.dto.common;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldErrorResponse {
    private String field;
    private String message;
}