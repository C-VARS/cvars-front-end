package com.cvars.scarface;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServerService {
    @GET("users/login?username={userId}&password={password}")
    Call<JSONObject> loginAttempt(@Path("userId") String userId, @Path("password") String password);
}
