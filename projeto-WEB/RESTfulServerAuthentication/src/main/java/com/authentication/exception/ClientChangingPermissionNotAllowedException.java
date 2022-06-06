package com.authentication.exception;

public class ClientChangingPermissionNotAllowedException extends RuntimeException{
    public ClientChangingPermissionNotAllowedException(Long permission) {
        super("You are not allowed to change your permission to " + permission);
    }
}
