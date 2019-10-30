package com.cvars.ScotiaTracker.model;

import com.cvars.ScotiaTracker.networkAPI.LoginAPI;
import com.cvars.ScotiaTracker.networkAPI.RetrofitNetwork;
import com.cvars.ScotiaTracker.presenter.LoginPresenter;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginModel implements Callback<JsonObject> {

    private String username;
    private LoginPresenter.UserType userType;
    private boolean loginSuccess;
    private String errorMessage;
    private LoginAPI loginAPI;

    private LoginPresenter loginPresenter;

    public LoginModel(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
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
            String userType = json.get("usertype").getAsString();
            this.userType = convertUserToEnum(userType);
        } else {
            this.errorMessage = "Incorrect username or password";
        }

        loginPresenter.notifyLoginResponse();
    }

    @Override
    public void onFailure(Call<JsonObject> call, Throwable t) {
        loginSuccess = false;
        errorMessage = "Connection failure";

        loginPresenter.notifyLoginResponse();
    }

    public String getUsername() {
        return this.username;
    }

    public LoginPresenter.UserType getUserType() {
        return userType;
    }

    public boolean isLoginSuccess() {
        return loginSuccess;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    private LoginPresenter.UserType convertUserToEnum(String userType) {

        if (userType.equals("Driver")) {
            return LoginPresenter.UserType.DRIVER;
        } else if (userType.equals("Customer")) {
            return LoginPresenter.UserType.CUSTOMER;
        } else if (userType.equals("Supplier")) {
            return LoginPresenter.UserType.SUPPLIER;
        }

        return null;
    }
}
