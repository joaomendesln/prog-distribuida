package com.authentication.exception;

public class GoodNotFoundException extends RuntimeException {

    public GoodNotFoundException(Long id) {
        super("Could not find good " + id);
    }
}