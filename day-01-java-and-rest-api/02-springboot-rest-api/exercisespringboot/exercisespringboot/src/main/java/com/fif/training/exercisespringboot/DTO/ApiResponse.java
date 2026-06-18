package com.fif.training.exercisespringboot.DTO;

public record ApiResponse<T>(
                String message,
                T data) {
}
