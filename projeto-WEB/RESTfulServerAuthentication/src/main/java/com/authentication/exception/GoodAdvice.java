package com.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class GoodAdvice {
   @ResponseBody
   @ExceptionHandler(GoodNotFoundException.class)
   @ResponseStatus(HttpStatus.NOT_FOUND)
   String goodNotFoundHandler(GoodNotFoundException ex) { return ex.getMessage(); }

   @ResponseBody
   @ExceptionHandler(GoodPermissionNotAllowedException.class)
   @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   String goodPermissionNotAllowedFoundHandler(GoodPermissionNotAllowedException ex) { return ex.getMessage(); }
}
