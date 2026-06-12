package com.example.main.template;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response<T> {
    
    @JsonProperty("status_code")
    private int statusCode;
    
    private String message;
    
    private T data;

    private Response(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public static <T> Response<T> ok(T data, String message) {
        return new Response<>(200, message, data);
    }

    public static <T> Response<T> created(T data, String message) {
        return new Response<>(201, message, data);
    }

    public static <T> Response<T> error(int statusCode, String message) {
        return new Response<>(statusCode, message, null);
    }

    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}