package com.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class ClientAdvice {

    @ResponseBody
    @ExceptionHandler(ClientNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String clientNotFoundHandler(ClientNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ClientLoginAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String clientLoginAlreadyExistsHandler(ClientLoginAlreadyExistsException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ClientChangingPermissionNotAllowedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String clientChangingPermissionNotAllowedHandler(ClientChangingPermissionNotAllowedException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ClientInvalidCredentials.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String clientInvalidCredentialsHandler(ClientInvalidCredentials ex) {
        return ex.getMessage();
    }
}