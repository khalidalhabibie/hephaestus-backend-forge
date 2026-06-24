package com.example.training.dto;

import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private String error;
    private List<String> details;
 
    public static <T> WebResponse<T> success(T data) {
        return WebResponse.<T>builder().success(true).message("Success").data(data).build();
    }
 
    public static WebResponse<Void> error(String error, String message, List<String> details) {
        return WebResponse.<Void>builder()
                .success(false)
                .error(error)
                .message(message)
                .details(details == null ? Collections.emptyList() : details)
                .build();
    }
}