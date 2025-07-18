package org.example.hotelmanagementbackend.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private String message;
    private int code;
    private LocalDateTime timestamp;

    public ApiError(String message, int code, LocalDateTime timestamp) {
        this.message = message;
        this.code = code;
        this.timestamp = timestamp;
    }
}
