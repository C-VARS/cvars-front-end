package com.cvars.scarface;

import com.google.gson.JsonElement;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServerService {
    @GET("users/login")
    Call<JsonElement> loginAttempt(@Query("username") String userId, @Query("password") String password);
}
