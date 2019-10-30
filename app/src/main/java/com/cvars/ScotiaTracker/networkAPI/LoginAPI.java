package com.cvars.ScotiaTracker.networkAPI;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LoginAPI {

    @GET("users/login")
    Call<JsonObject> attemptLogin(@Query("username") String username, @Query("password") String password);

}

