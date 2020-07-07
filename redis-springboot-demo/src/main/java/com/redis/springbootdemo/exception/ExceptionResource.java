package com.redis.springbootdemo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ExceptionResource {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<String> customException(CustomException e) {
    return new ResponseEntity<>(e.getMessage(), e.getHttpStatus());
  }

}
