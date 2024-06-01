package com.socialize.exception.exceptions;

public class CommentNotFoundException extends RuntimeException {

    public CommentNotFoundException(Long id) {
        super("No comments found for Post with ID: " + id);
    }

}
