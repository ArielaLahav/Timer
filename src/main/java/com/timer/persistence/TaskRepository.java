package com.timer.persistence;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface TaskRepository extends MongoRepository<ScheduledTask, String> {

  @Query(value = "{ 'isCompleted' : false}")
  List<ScheduledTask> findNotCompletedTasks();
}
