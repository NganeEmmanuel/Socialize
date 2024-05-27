package com.socialize.exception.exceptions;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long id) {
        super("Post not found with ID: " + id);
    }
}
