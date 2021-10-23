package com.timer.persistence;

import com.timer.model.TimerRequestType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tasks")
public class ScheduledTask {

  @Transient
  public static final String SEQUENCE_NAME = "tasks_sequence";

  @Id
  private final String id;
  private final TimerRequestType type;
  private final String data;
  private final long endDate;
  private boolean isCompleted = false;

  public ScheduledTask(String id, TimerRequestType type, long endDate, String data) {
    this.id = id;
    this.type = type;
    this.endDate = endDate;
    this.data = data;
  }


  public String getId() {
    return id;
  }

  public TimerRequestType getType() {
    return type;
  }

  public String getData() {
    return data;
  }

  public long getEndDate() {
    return endDate;
  }

  public void setCompleted() {
    isCompleted = true;
  }

  public boolean isCompleted() {
    return isCompleted;
  }
}
