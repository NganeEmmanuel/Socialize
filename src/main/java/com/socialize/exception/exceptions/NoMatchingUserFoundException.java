package com.socialize.exception.exceptions;

public class NoMatchingUserFoundException extends RuntimeException {

    public NoMatchingUserFoundException(String username) {
        super("No user found with username and/or name matching {} " + username);
    }
}
