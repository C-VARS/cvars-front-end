package com.cvars.scarface.networkComms;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServerService {
    @GET("users/login")
    Call<JsonObject> loginAttempt(@Query("username") String username, @Query("password") String password);
}

