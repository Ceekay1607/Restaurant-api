package com.tma.restaurantapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;
import java.util.Map;

/**
 * Global exception handler for API requests.
 * This class provides centralized handling of {@link ApiException} instances
 * and generates appropriate responses with error details.
 */
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(Map.of("message",e.getMessage(),"httpStatus",e.getHttpStatus(),"time",ZonedDateTime.now()));
    }

}
