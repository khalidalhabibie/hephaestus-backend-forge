package com.fif.training.exercisespringboot.DTO;

public record ApiResponseDto<T>(
        String message,
        T data) {

}
