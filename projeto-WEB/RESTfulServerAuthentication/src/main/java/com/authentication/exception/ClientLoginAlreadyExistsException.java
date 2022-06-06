package com.authentication.exception;

public class ClientLoginAlreadyExistsException extends RuntimeException{
    public ClientLoginAlreadyExistsException(String login) {
        super("Already exists a client with login " + login);
    }
}
