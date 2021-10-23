package com.timer.services;

import com.timer.model.TimerRequest;
import com.timer.model.TimerStatus;

public interface TimerService {

  String setTimer(TimerRequest timerRequest);
  TimerStatus getTimerStatus(String id);

}
