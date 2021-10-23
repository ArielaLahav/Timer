package com.timer.api.rest;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface RestClient {

  @POST
  @Headers({"Content-Type: application/json",
      "Accept: application/json"})
  Call<Void> post(@Url String url);

}
