package com.timer.services;

import com.timer.persistence.ScheduledTask;
import com.timer.persistence.TaskRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public abstract class ExecutionTask {

  private final TaskSchedulingService taskSchedulingService;
  private final TaskRepository taskRepository;

  public ExecutionTask(TaskSchedulingService taskSchedulingService, TaskRepository taskRepository) {
    this.taskSchedulingService = taskSchedulingService;
    this.taskRepository = taskRepository;
  }

  protected void markTaskAsCompleted(String taskId) {
    taskSchedulingService.removeScheduleTask(taskId);
    Optional<ScheduledTask> taskDocumentOptional = taskRepository.findById(taskId);
    if(taskDocumentOptional.isPresent()){
      ScheduledTask scheduledTask = taskDocumentOptional.get();
      scheduledTask.setCompleted();
      taskRepository.save(scheduledTask);
    }
  }
}
