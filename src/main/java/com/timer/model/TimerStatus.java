package com.timer.model;

public class TimerStatus {

  private final String Id;
  private final String timeLeft;

  public TimerStatus(String id, String timeLeft) {
    Id = id;
    this.timeLeft = timeLeft;
  }

  public String getTimeLeft() {
    return timeLeft;
  }

  public String getId() {
    return Id;
  }
}
