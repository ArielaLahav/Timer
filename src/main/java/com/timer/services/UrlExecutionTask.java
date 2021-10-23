package com.timer.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timer.api.rest.RestClient;
import com.timer.api.rest.RestClientCreator;
import com.timer.persistence.TaskRepository;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service
public class UrlExecutionTask extends ExecutionTask {

  private final RestClient restClient;

  @Autowired
  public UrlExecutionTask(TaskSchedulingService taskSchedulingService,
      TaskRepository taskRepository,
      RestClientCreator restClientCreator) {
    super(taskSchedulingService, taskRepository);
    this.restClient = restClientCreator.createRestClient();
  }

  public Runnable initRunnable(String url, String taskId) {
    return new Runnable() {
      @Override
      public void run() {
        try {
          Response<Void> response = restClient.post(url + "/" + taskId).execute();
          if (!response.isSuccessful()) {
            String errorMessage = getErrorMessage(response.errorBody().string());
            System.out.println("url execution failed with the following message: " + errorMessage);
          } else {
            System.out.println("url executed successfully for job id: " + taskId);
          }
        } catch (IOException e) {
          e.printStackTrace();
        } finally {
          markTaskAsCompleted(taskId);
        }
      }

      private String getErrorMessage(String errorBody) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj;
        try {
          actualObj = mapper.readTree(errorBody);
        } catch (IOException e) {
          return "Can't parse error message from " + errorBody;
        }
        JsonNode jsonNode = actualObj.get("message");
        return jsonNode != null ? jsonNode.textValue() : "";
      }
    };
  }
}
