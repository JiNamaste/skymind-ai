package com.skymind.backend.exception;

import com.skymind.backend.dto.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<String> handle(ExternalApiException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> fieldErrors.put(err.getField(), err.getDefaultMessage()));

        ApiErrorResponse response = ApiErrorResponse.builder().status(400)
                .error("Bad Request")
                .message("Validation failed")
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .fieldErrors(fieldErrors).build();
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NoFlightsFoundException.class)
    public ResponseEntity<ApiErrorResponse> noFlightFound(NoFlightsFoundException ex, HttpServletRequest request) {
        ApiErrorResponse response =  ApiErrorResponse.builder().status(404)
                .error("Not Found")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .fieldErrors(null).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
