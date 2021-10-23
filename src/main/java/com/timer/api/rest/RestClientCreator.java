package com.timer.api.rest;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;

@Component
public class RestClientCreator {

  public RestClient createRestClient() {
    OkHttpClient httpClient = new OkHttpClient.Builder()
        .connectTimeout(300, TimeUnit.SECONDS)
        .readTimeout(300, TimeUnit.SECONDS)
        .build();
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://myhost/mypath/")
        .client(httpClient)
        .build();

    return retrofit.create(RestClient.class);
  }
}
