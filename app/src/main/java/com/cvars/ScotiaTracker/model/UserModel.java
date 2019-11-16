package com.cvars.ScotiaTracker.model;

import android.app.DownloadManager;

import com.cvars.ScotiaTracker.model.pojo.User;
import com.cvars.ScotiaTracker.networkAPI.LoginAPI;
import com.cvars.ScotiaTracker.networkAPI.RetrofitNetwork;
import com.cvars.ScotiaTracker.networkAPI.UserAPI;

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
    private UserAPI userAPI;
    private User user;
    private UserResponseListener listener;

    private boolean actionSuccess;

    enum UserAction{
        REQUEST, UPDATE
    }

    interface UserResponseListener{
        void notifyUserAction(UserAction action);
    }

    /**
     * Constructs a userModel
     */
    UserModel() {
        userAPI = RetrofitNetwork.retrofit.create(UserAPI.class);
    }

    /**
     * Starts an Asynchronous Call using Retrofit to get a users's information
     * @param username
     */
    void requestUser(String username) {
        Call<User> call = userAPI.getUserInfo(username);
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
        this.user = response.body();
       listener.notifyUserAction(UserAction.REQUEST);
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

    public User getUser(){
        return this.user;
    }

    public void setListener(UserResponseListener listener) {
        this.listener = listener;
    }
}
