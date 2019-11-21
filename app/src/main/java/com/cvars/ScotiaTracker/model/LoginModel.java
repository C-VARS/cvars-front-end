package com.cvars.ScotiaTracker.model;

import com.cvars.ScotiaTracker.model.pojo.UserType;
import com.cvars.ScotiaTracker.networkAPI.LoginAPI;
import com.cvars.ScotiaTracker.networkAPI.RetrofitNetwork;
import com.cvars.ScotiaTracker.responseListeners.LoginResponseListener;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A Model object for the use case and information storage related to Login. Implements the Retrofit
 * Callback interface to handle asynchronous HTTP response.
 */
public class LoginModel implements Callback<JsonObject> {
    /**
     * A Retrofit API connection object for login
     */
    private LoginAPI loginAPI;
    private Boolean loginSuccess;
    private String username;
    private String password;
    private UserType userType;
    /**
     * A reference to a LoginResponseListener interface that will be called whenever an HTTP response
     */
    private LoginResponseListener loginResponseListener;
    private String errorMessage;
    /**
     * Constructs a loginModel with an injected LoginResponseListener reference
     * which interacts with the backend and has some information
     * about the status of the login
     * @param loginResponseListener
     */
    public LoginModel(LoginResponseListener loginResponseListener) {
        this.loginResponseListener = loginResponseListener;
        loginAPI = RetrofitNetwork.retrofit.create(LoginAPI.class);
    }

    /**
     * Starts an Asynchronous Call using Retrofit to attempt a login
     * @param username
     * @param password
     */
    public void attemptLogin(String username, String password) {
        Call<JsonObject> call = loginAPI.attemptLogin(username, password);
        // Asynchronous Call occurs, passing in this loginModel
        call.enqueue(this);
        this.username = username;
        this.password = password;
    }

    /**
     * On response from our HTTP request, notify a login response
     * @param call retrofit Call object
     * @param response retrofit Response object
     */
    @Override
    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
        JsonObject json = response.body();
        loginSuccess = json.get("loginStatus").getAsBoolean();
        if (loginSuccess) {
            loginSuccess = true;
            userType = convertUserToEnum(json.get("userType").getAsString());
        } else {
            this.errorMessage = "Incorrect username or password";
        }

        loginResponseListener.notifyResponse();
    }

    /**
     * On a failed HTTP request, change the errorMessage and notify a login response
     * @param call Call object
     * @param t error object
     */
    @Override
    public void onFailure(Call<JsonObject> call, Throwable t) {
        errorMessage = "Connection failure";
        this.loginSuccess = false;
        loginResponseListener.notifyResponse();
    }

    /**
     * Getter for the potential error message
     * @return a String represents the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Getter for the login success status
     * @return boolean of whether the last login succeeeded
     */
    public Boolean isLoginSuccess(){
        return loginSuccess;
    }

    /**
     * Getter for username
     * @return username
     */
    public String getUsername(){return username;}

    /**
     * Getter for user type
     * @return user type
     */
    public UserType getUserType(){return userType;}

    /**
     * Getter for password
     * @return password
     */
    public String getPassword(){return password;}

    /**
     * A method that converts the JSON response of the user's type into the corresponding Enum
     * @param userType a string that represents a user type
     * @return a constant Enum UserType that corresponds with the string
     */
    private UserType convertUserToEnum(String userType) {
       return UserType.valueOf(userType.toUpperCase());
    }
}
