package com.szuse.f4.common.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
import com.szuse.f4.common.ResponseJSON;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseJSON handleBadRequestException(BadRequestException e) {
    return new ResponseJSON(1, e.getMessage());
  }

  @ExceptionHandler(UnauthorizedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ResponseJSON handleUnauthorizedException(UnauthorizedException e) {
    return new ResponseJSON(2, e.getMessage());
  }

  @ExceptionHandler(InternalServerErrorException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseJSON handleInternalServerErrorException(InternalServerErrorException e) {
    return new ResponseJSON(3, e.getMessage());
  }

}
