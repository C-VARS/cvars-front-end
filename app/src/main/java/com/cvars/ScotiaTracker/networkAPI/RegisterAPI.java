package com.cvars.ScotiaTracker.networkAPI;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterAPI {
    @POST("users/register")
    Call<JsonObject> register(@Body Map<String, String> body);
}
