package com.timer.services;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

@Service
public class TaskSchedulingService {

  private final ThreadPoolTaskScheduler taskScheduler;
  private final Map<String, ScheduledFuture<?>> tasksMap = new HashMap<>();

  @Autowired
  public TaskSchedulingService(ThreadPoolTaskScheduler taskScheduler) {
    this.taskScheduler = taskScheduler;
    this.taskScheduler.setPoolSize(4);
  }

  public void scheduleTask(String taskId, Runnable task, Instant startTime) {
    long delay = startTime.getEpochSecond() - Instant.now().getEpochSecond();
    System.out.println(
        "Scheduling task with job id: " + taskId + " and delay: " + (delay < 0 ? 0 : delay));

    ScheduledFuture<?> scheduledTask = taskScheduler.schedule(task, startTime);
    tasksMap.put(taskId, scheduledTask);
  }

  public Long getTaskTimeLeft(String taskId) {
    ScheduledFuture<?> scheduledFuture = tasksMap.get(taskId);
    return scheduledFuture != null ? scheduledFuture.getDelay(TimeUnit.SECONDS) : null;
  }

  public void removeScheduleTask(String taskId) {
    tasksMap.remove(taskId);
  }
}
