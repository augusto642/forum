package com.alura.forum.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private String errorCode;  // Código de erro adicional

    public ErrorResponse(int status, String message, String errorCode) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
    }
}
