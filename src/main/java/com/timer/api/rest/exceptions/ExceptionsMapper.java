package com.timer.api.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionsMapper {

  @ResponseBody
  @ExceptionHandler(TimerNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String timerNotFoundHandler(TimerNotFoundException ex) {
    return ex.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(InvalidTimerRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String InvalidTimerRequestHandler(InvalidTimerRequestException ex) {
    return ex.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  String InvalidTimerRequestHandler(RuntimeException ex) {
    return ex.getMessage();
  }
}
