package com.socialize.exception.exceptions;

public class TokenExpiredException extends RuntimeException{
    public TokenExpiredException() {
        super("The provided token is expired");

    }
}
