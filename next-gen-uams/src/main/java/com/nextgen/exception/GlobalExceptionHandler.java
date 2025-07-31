package com.nextgen.exception;

import com.nextgen.dto.ApiResponse;
import com.nextgen.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserExists(UserRegistrationException ex,HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now().toString()
        );
        return ResponseEntity.badRequest().body(ApiResponse.error(error));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneric(Exception ex, HttpServletRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now().toString()
        );
        return ResponseEntity.internalServerError().body(ApiResponse.error(errorDetails));
    }
}
