package com.cvars.ScotiaTracker.networkAPI;

import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.model.pojo.User;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * API Wrapper for the Login API Calls. Used by RetrofitNetwork to create asynchronous API calls.
 */
public interface UserAPI {

    @GET("users")
    Call<User> getUserInfo(@Query("username") String username);

}



