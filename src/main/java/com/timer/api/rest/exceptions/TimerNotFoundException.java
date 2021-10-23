package com.timer.api.rest.exceptions;

public class TimerNotFoundException extends RuntimeException {

  public TimerNotFoundException(String id) {
    super("Could not find timer " + id);
  }
}
