package com.timer.api.rest.exceptions;

public class InvalidTimerRequestException extends RuntimeException {

  public InvalidTimerRequestException(String cause) {
    super("Request is invalid: " + cause);
  }
}
