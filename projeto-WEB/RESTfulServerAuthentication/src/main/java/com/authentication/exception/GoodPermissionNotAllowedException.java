package com.authentication.exception;

public class GoodPermissionNotAllowedException extends RuntimeException{
    public GoodPermissionNotAllowedException() {
        super("You don't have permission to add a good");
    }
}
