package com.timer.api.rest;

import com.timer.api.rest.exceptions.InvalidTimerRequestException;
import com.timer.api.rest.exceptions.TimerNotFoundException;
import com.timer.model.Timer;
import com.timer.model.TimerRequest;
import com.timer.model.TimerStatus;
import com.timer.services.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/timers")
public class TimerController {

  private final TimerService timerService;

  @Autowired
  public TimerController(TimerService timerService) {
    this.timerService = timerService;
  }

  /**
   * Create a new task that will be executed by a given execution delay.
   *
   * @param timerRequest defines the task delay and action.
   * @return the created time unique id
   * @throws InvalidTimerRequestException in case of invalid input.
   */
  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  public Timer setTimer(@RequestBody TimerRequest timerRequest) {
    validateTimerRequest(timerRequest);
    String timerId = timerService.setTimer(timerRequest);
    return new Timer(timerId);
  }

  /**
   * Returns a timer task remaining delay, if exist.
   *
   * @param id the timer task unique id
   * @return the remaining delay of the task. If the task already expired 0 will be returned.
   * @throws TimerNotFoundException in case timer was not found by the given id.
   */
  @GetMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      value = "/{id}")
  public TimerStatus getTimerStatus(@PathVariable String id) {
    return timerService.getTimerStatus(id);
  }

  private void validateTimerRequest(TimerRequest timerRequest) {
    if (timerRequest.getHours() < 0 || timerRequest.getMinutes() < 0
        || timerRequest.getSeconds() < 0) {
      throw new InvalidTimerRequestException(
          "given hours, minutes and second should be positive numbers.");
    }
    if (timerRequest.getData().isEmpty()) {
      throw new InvalidTimerRequestException("data must be given.");
    }
  }
}
