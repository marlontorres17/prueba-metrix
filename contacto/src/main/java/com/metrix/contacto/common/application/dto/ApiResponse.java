package com.metrix.contacto.common.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private final int code;
    private final String message;
    private final T data;
    private final String errorCode; 

    public static <T> ApiResponse<T> success(T data, String message, int code) {
        return new ApiResponse<>(code, message, data, null);
    }

    public static <T> ApiResponse<T> error(String message, int code) {
        return new ApiResponse<>(code, message, null, null);
    }

    public static <T> ApiResponse<T> error(T data, String message, int code) {
        return new ApiResponse<>(code, message, data, null);
    }

    
    public static <T> ApiResponse<T> error(T data, String message, int code, String errorCode) {
        return new ApiResponse<>(code, message, data, errorCode);
    }
}
