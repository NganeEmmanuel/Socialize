package com.socialize.exception.handler;


import com.socialize.exception.exceptions.*;
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

    //todo brian add UserNotFound exception handler implementation here
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        logger.error("user not found: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles PostNotFoundException and returns a meaningful response.
     *
     * @param ex The PostNotFoundException. This parameter allows access to the exception's message and stack trace.
     * @param request The WebRequest that led to the exception. This parameter can provide additional context about the request.
     * @return A ResponseEntity containing the error message and HTTP status.
     */
    @ExceptionHandler(NoMatchingUserFoundException.class)
    public ResponseEntity<String> handleNoMatchingUserFoundException(NoMatchingUserFoundException ex, WebRequest request) {
        logger.error("No matching user found while looking through search keyword: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles PostNotFoundException and returns a meaningful response.
     *
     * @param ex The PostNotFoundException. This parameter allows access to the exception's message and stack trace.
     * @param request The WebRequest that led to the exception. This parameter can provide additional context about the request.
     * @return A ResponseEntity containing the error message and HTTP status.
     */
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<String> handleCommentNotFoundException(CommentNotFoundException ex, WebRequest request) {
        logger.error("No matching comments found while looking for your post {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles NoChildCommentFoundException and returns a meaningful response.
     *
     * @param ex The NoChildCommentFoundException. This parameter allows access to the exception's message and stack trace.
     * @param request The WebRequest that led to the exception. This parameter can provide additional context about the request.
     * @return A ResponseEntity containing the error message and HTTP status.
     */
    @ExceptionHandler(NoChildCommentFoundException.class)
    public ResponseEntity<String> handleNoChildCommentFoundException(NoChildCommentFoundException ex, WebRequest request) {
        logger.error("No matching child comments found while looking for them: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles NoChildCommentFoundException and returns a meaningful response.
     *
     * @param ex The NoChildCommentFoundException. This parameter allows access to the exception's message and stack trace.
     * @param request The WebRequest that led to the exception. This parameter can provide additional context about the request.
     * @return A ResponseEntity containing the error message and HTTP status.
     */
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<String> handleTokenExpiredException(TokenExpiredException ex, WebRequest request) {
        logger.error("The provided token is expired: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<String> handleProfileNotFoundException(ProfileNotFoundException ex, WebRequest request) {
        logger.error(" User profile not found: {}", ex.getMessage());
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

