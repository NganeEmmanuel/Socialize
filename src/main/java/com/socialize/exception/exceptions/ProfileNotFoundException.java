package com.socialize.exception.exceptions;

public class ProfileNotFoundException extends RuntimeException {
    public ProfileNotFoundException(String userId){
        super("No user profile found with userId: " + userId);
    }
}

