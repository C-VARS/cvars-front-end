package com.cvars.ScotiaTracker.model;

import com.cvars.ScotiaTracker.networkAPI.LoginAPI;
import com.cvars.ScotiaTracker.networkAPI.RetrofitNetwork;
import com.cvars.ScotiaTracker.presenter.LoginPresenter;
import com.cvars.ScotiaTracker.responseHandlers.LoginResponseHandler;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginModel implements Callback<JsonObject> {

    private String username;
    private UserType userType;
    private boolean loginSuccess;
    private String errorMessage;
    private LoginAPI loginAPI;

    private LoginResponseHandler loginResponseHandler;

    public LoginModel(LoginResponseHandler loginResponseHandler) {
        this.loginResponseHandler = loginResponseHandler;
        username = "";
        loginSuccess = false;
        errorMessage = "";
        userType = null;
        loginAPI = RetrofitNetwork.retrofit.create(LoginAPI.class);
    }

    public void attemptLogin(String username, String password) {
        Call<JsonObject> call = loginAPI.attemptLogin(username, password);
        this.username = username;
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
        JsonObject json = response.body();
        loginSuccess = json.get("loginStatus").getAsBoolean();
        if (loginSuccess) {
            String userType = json.get("userType").getAsString();
            this.userType = convertUserToEnum(userType);
        } else {
            this.errorMessage = "Incorrect username or password";
        }

        loginResponseHandler.notifyLoginResponse();
    }

    @Override
    public void onFailure(Call<JsonObject> call, Throwable t) {
        loginSuccess = false;
        errorMessage = "Connection failure";

        loginResponseHandler.notifyLoginResponse();
    }

    public String getUsername() {
        return this.username;
    }

    public UserType getUserType() {
        return userType;
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    private UserType convertUserToEnum(String userType) {

        if (userType.equals("Driver")) {
            return UserType.DRIVER;
        } else if (userType.equals("Customer")) {
            return UserType.CUSTOMER;
        } else if (userType.equals("Supplier")) {
            return UserType.SUPPLIER;
        }

        return null;
    }
}
