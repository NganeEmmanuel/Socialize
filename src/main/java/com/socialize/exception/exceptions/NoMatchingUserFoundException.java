package com.socialize.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoMatchingUserFoundException extends RuntimeException{

    public NoMatchingUserFoundException(String username) {
        super("No user found with username and/or name matching {} " + username);
    }

}
