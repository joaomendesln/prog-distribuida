package com.authentication.exception;

public class ClientInvalidCredentials  extends RuntimeException{
    public ClientInvalidCredentials() {
        super("Invalid credentials");
    }
}
