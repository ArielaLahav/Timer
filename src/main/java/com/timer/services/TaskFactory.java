package com.timer.services;

import com.timer.api.rest.exceptions.InvalidTimerRequestException;
import com.timer.model.TimerRequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskFactory {

  private final UrlExecutionTask urlExecutionTask;

  @Autowired
  public TaskFactory(UrlExecutionTask urlExecutionTask) {
    this.urlExecutionTask = urlExecutionTask;
  }

  public Runnable create(String taskId, TimerRequestType type, String data) {
    if (type == TimerRequestType.URL) {
      return urlExecutionTask.initRunnable(data, taskId);
    } else {
      throw new InvalidTimerRequestException("invalid request type" + type);
    }
  }
}
