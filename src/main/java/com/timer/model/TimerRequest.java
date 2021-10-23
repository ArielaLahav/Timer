package com.timer.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.NAME, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = UrlTimerRequest.class, name = "UrlTimerRequest") })
public abstract class TimerRequest {

  private int hours;
  private int minutes;
  private int seconds;
  private TimerRequestType type;

  public TimerRequest() { }

  protected TimerRequest(int hours, int minutes, int seconds, TimerRequestType type) {
    this.hours = hours;
    this.minutes = minutes;
    this.seconds = seconds;
    this.type = type;
  }

  public int getHours() {
    return hours;
  }

  public int getMinutes() {
    return minutes;
  }

  public int getSeconds() {
    return seconds;
  }

  public TimerRequestType getType() {
    return type;
  }

  public abstract String getData();
}
