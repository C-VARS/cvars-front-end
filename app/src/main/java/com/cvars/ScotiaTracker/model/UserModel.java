package com.cvars.ScotiaTracker.model;

import com.cvars.ScotiaTracker.model.pojo.User;
import com.cvars.ScotiaTracker.model.pojo.UserType;
import com.cvars.ScotiaTracker.networkAPI.EndpointAPI;
import com.cvars.ScotiaTracker.networkAPI.RetrofitNetwork;
import com.cvars.ScotiaTracker.responseHandlers.ResponseHandler;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A Model object for the use case and information storage related to a User. Implements the Retrofit
 * Callback interface to handle asynchronous HTTP response.
 */
public class UserModel implements Callback<User> {
    /**
     * A Retrofit API connection object for getting User Information
     */
    private EndpointAPI endpointAPI;
    private User user;

    /**
     * Constructs a userModel
     */
    public UserModel() {
        endpointAPI = RetrofitNetwork.retrofit.create(EndpointAPI.class);
    }

    /**
     * Starts an Asynchronous Call using Retrofit to get a users's information
     * @param username
     */
    public void requestUser(String username) {
        Call<User> call = endpointAPI.getUserInfo(username);
        // Asynchronous Call occurs, passing in this
        call.enqueue(this);
    }

    /**
     * On response from our HTTP request, notify a user data response
     * @param call retrofit Call object
     * @param response retrofit Response object
     */
    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        // TODO: add response, checking
        this.user = response.body();
    }

    /**
     * On a failed HTTP request, change the errorMessage and notify a login response
     * @param call Call object
     * @param t error object
     */
    @Override
    public void onFailure(Call<User> call, Throwable t) {
    }

    /**
     * Getter for username
     * @return username
     */
    public String getUsername(){return this.user.getName();}



}
