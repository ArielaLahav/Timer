package com.timer.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UrlTimerRequest extends TimerRequest {

  private String url;

  public UrlTimerRequest() {
  }

  @JsonCreator
  public UrlTimerRequest(@JsonProperty("hours") int hours, @JsonProperty("minutes") int minutes,
      @JsonProperty("seconds") int seconds, @JsonProperty("url") String url) {
    super(hours, minutes, seconds, TimerRequestType.URL);
    this.url = url;
  }

  @Override
  public String getData() {
    return url;
  }

}
