package com.timer.api.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.timer.TimerRunner;
import com.timer.model.TimerStatus;
import com.timer.services.TimerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TimerRunner.class)
@AutoConfigureMockMvc
public class TimerControllerTest {

  @MockBean
  private TimerService timerService;

  @Autowired
  private TimerController controller;

  @Autowired
  MockMvc mockMvc;

  private String REST_URL = "/timers";

  @Test
  public void setTimer_validBody_success() throws Exception {
    Mockito.when(timerService.setTimer(Matchers.any())).thenReturn("1");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post(REST_URL)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(createTimerInJson(0, 0, 1, "https://someserver.com"));

    mockMvc.perform(requestBuilder).andDo(
        MockMvcResultHandlers.log()
    ).andExpect(status().is2xxSuccessful())
        .andExpect(content().string("{\"id\":\"1\"}"));
  }

  @Test
  public void setTimer_negativeHours_failure() throws Exception {
    Mockito.when(timerService.setTimer(Matchers.any())).thenReturn("1");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post(REST_URL)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(createTimerInJson(-1, 0, 1, "https://someserver.com"));

    mockMvc.perform(requestBuilder).andExpect(status().isBadRequest())
        .andExpect(content().string(
            "Request is invalid: given hours, minutes and second should be positive numbers."));
  }


  @Test
  public void setTimer_negativeMinutes_failure() throws Exception {
    Mockito.when(timerService.setTimer(Matchers.any())).thenReturn("1");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post(REST_URL)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(createTimerInJson(0, -1, 1, "https://someserver.com"));

    mockMvc.perform(requestBuilder).andExpect(status().isBadRequest())
        .andExpect(content().string(
            "Request is invalid: given hours, minutes and second should be positive numbers."));
  }

  @Test
  public void setTimer_negativeSeconds_failure() throws Exception {
    Mockito.when(timerService.setTimer(Matchers.any())).thenReturn("1");

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .post(REST_URL)
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(createTimerInJson(0, 0, -1, "https://someserver.com"));

    mockMvc.perform(requestBuilder).andExpect(status().isBadRequest())
        .andExpect(content().string(
            "Request is invalid: given hours, minutes and second should be positive numbers."));
  }

  @Test
  public void getTimer_timerStatusReturned() throws Exception {
    TimerStatus timerStatus = new TimerStatus("1", "100");
    Mockito.when(timerService.getTimerStatus("1")).thenReturn(timerStatus);

    RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get(REST_URL + "/1")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);

    mockMvc.perform(requestBuilder).andExpect(status().is2xxSuccessful())
        .andExpect(content().string("{\"timeLeft\":\"100\",\"id\":\"1\"}"));
  }


  private static String createTimerInJson(long hours, long minutes, long seconds, String url) {
    return "{ \"type\" : \"UrlTimerRequest\", \"hours\": \"" + hours + "\", " +
        "\"minutes\":\"" + minutes + "\"," +
        "\"seconds\":\"" + seconds + "\"," +
        "\"url\":\"" + url + "\"}";
  }

}
