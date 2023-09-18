package com.tma.restaurantapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Custom exception class for API-related errors.
 * Extends the {@link Exception} class to indicate unchecked exceptions.
 */
@Getter
public class ApiException extends Exception {

    /**
     * The HTTP status associated with the exception.
     */
    private HttpStatus httpStatus;

    /**
     * Constructor
     *
     * @param message
     * @param httpStatus
     */
    public ApiException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
