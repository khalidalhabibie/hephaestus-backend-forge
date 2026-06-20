package com.example.training.dto;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponse {
    private UUID id;
    
}
