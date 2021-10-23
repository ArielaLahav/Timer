package com.timer.services;

import com.timer.api.rest.exceptions.TimerNotFoundException;
import com.timer.model.TimerRequest;
import com.timer.model.TimerStatus;
import com.timer.persistence.SequenceGeneratorService;
import com.timer.persistence.ScheduledTask;
import com.timer.persistence.TaskRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimerServiceImpl implements TimerService {

  private final TaskRepository taskRepository;
  private final TaskSchedulingService taskSchedulingService;
  private final TaskFactory taskFactory;
  private final SequenceGeneratorService sequenceGeneratorService;

  @Autowired
  public TimerServiceImpl(TaskRepository taskRepository,
      TaskSchedulingService taskSchedulingService,
      TaskFactory taskFactory,
      SequenceGeneratorService sequenceGeneratorService) {
    this.taskRepository = taskRepository;
    this.taskSchedulingService = taskSchedulingService;
    this.taskFactory = taskFactory;
    this.sequenceGeneratorService = sequenceGeneratorService;
  }

  @PostConstruct
  public void initTaskMap() {
    List<ScheduledTask> notCompletedTasks = taskRepository.findNotCompletedTasks();
    notCompletedTasks.forEach(task ->
        taskSchedulingService.scheduleTask(
            String.valueOf(task.getId()),
            taskFactory.create(String.valueOf(task.getId()), task.getType(), task.getData()),
            (task.getEndDate() < Instant.now().getEpochSecond()) ? Instant.now() : Instant.ofEpochSecond(task.getEndDate()))
    );
  }

  @Override
  public String setTimer(TimerRequest timerRequest) {
    Instant delay = Instant.now().plusSeconds(
        (timerRequest.getHours() * 60L * 60L) + (timerRequest.getMinutes() * 60L) + timerRequest
            .getSeconds()
    );

    ScheduledTask scheduledTask = new ScheduledTask(
        sequenceGeneratorService.generateSequence(ScheduledTask.SEQUENCE_NAME),
        timerRequest.getType(), delay.getEpochSecond(), timerRequest.getData());
    String id = String.valueOf(taskRepository.insert(scheduledTask).getId());
    Runnable task = taskFactory.create(id, timerRequest.getType(), timerRequest.getData());
    taskSchedulingService.scheduleTask(id, task, delay);
    return id;
  }

  @Override
  public TimerStatus getTimerStatus(String id) {
    Long taskTimeLeft = taskSchedulingService.getTaskTimeLeft(id);
    if (taskTimeLeft == null) {
      Optional<ScheduledTask> taskDocument = taskRepository.findById(id);
      if(!taskDocument.isPresent()) {
        throw new TimerNotFoundException(id);
      }
      taskTimeLeft = 0L;
    }
    return new TimerStatus(id, String.valueOf(taskTimeLeft));
  }
}
