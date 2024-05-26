package com.socialize.exception.handler;


import com.socialize.exception.exceptions.PostNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@SuppressWarnings("unused")
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles PostNotFoundException and returns a meaningful response.
     *
     * @param ex The PostNotFoundException. This parameter allows access to the exception's message and stack trace.
     * @param request The WebRequest that led to the exception. This parameter can provide additional context about the request.
     * @return A ResponseEntity containing the error message and HTTP status.
     */
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<String> handlePostNotFoundException(PostNotFoundException ex, WebRequest request) {
        logger.error("Post not found: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles all other exceptions and returns a generic error response.
     *
     * @param ex The Exception. This parameter allows access to the exception's message and stack trace.
     * @param request The WebRequest that led to the exception. This parameter can provide additional context about the request.
     * @return A ResponseEntity containing a generic error message and HTTP status.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

