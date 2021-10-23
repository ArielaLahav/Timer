package com.timer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TimerRunner {

  public static void main(String[] args) {
    SpringApplication.run(TimerRunner.class, args);
  }

}
