package com.cvars.ScotiaTracker.networkAPI;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoginAPI {

    String BASE_URL = "http://cvars.herokuapp.com/";

    @GET("users/login")
    Call<JsonObject> attemptLogin(@Query("username") String username, @Query("password") String password);

}

