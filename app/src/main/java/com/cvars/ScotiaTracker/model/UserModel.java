package com.cvars.ScotiaTracker.model;

import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.model.pojo.User;
import com.cvars.ScotiaTracker.model.pojo.UserType;
import com.cvars.ScotiaTracker.networkAPI.RetrofitNetwork;
import com.cvars.ScotiaTracker.networkAPI.UserAPI;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A Model object for the use case and information storage related to a User. Implements the Retrofit
 * Callback interface to handle asynchronous HTTP response.
 */
class UserModel {

    enum UserAction {
        REQUEST, UPDATE
    }

    interface UserResponseListener {
        void notifyUserAction(UserAction action);
    }

    private User user;

    private UserAPI userAPI = RetrofitNetwork.retrofit.create(UserAPI.class);
    private UserResponseListener listener;
    private RequestUserCallback requestCallback = new RequestUserCallback();
    private boolean actionSuccess;

    /**
     * Starts an Asynchronous Call using Retrofit to get a users's information
     *
     * @param username
     */
    void requestUser(String username) {
        Call<User> call = userAPI.getUserInfo(username);
        call.enqueue(this.requestCallback);
    }

    private class RequestUserCallback implements Callback<User> {
        /**
         * Getter for username
         *
         * @return username
         */
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            user = response.body();
            actionSuccess = true;
            listener.notifyUserAction(UserAction.REQUEST);

        }

        /**
         * On a failed HTTP request, change the errorMessage and notify a login response
         *
         * @param call Call object
         * @param t    error object
         */
        @Override
        public void onFailure(Call<User> call, Throwable t) {
            user = new User();
            System.out.println("Error getting user");
            actionSuccess = false;
            listener.notifyUserAction(UserAction.REQUEST);
        }
    }

    /**
     * Getter for username
     *
     * @return username
     */
    String getUsername() {
        return this.user.getName();
    }

    User getUser() {
        return this.user;
    }

    boolean getActionSuccess() {
        return actionSuccess;
    }


    void setListener(UserResponseListener listener) {
        this.listener = listener;
    }
}
