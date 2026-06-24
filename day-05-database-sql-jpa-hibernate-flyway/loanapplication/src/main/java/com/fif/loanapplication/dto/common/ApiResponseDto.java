package com.fif.loanapplication.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDto<T> {

    Boolean success;
    String message;
    T data;

}
