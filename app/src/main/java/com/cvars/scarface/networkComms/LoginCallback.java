package com.cvars.scarface.networkComms;


import com.cvars.scarface.activity.MainActivity;
import com.cvars.scarface.model.Driver;
import com.cvars.scarface.model.SmallBusinessOwner;
import com.cvars.scarface.model.Supplier;
import com.cvars.scarface.model.User;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginCallback<T> implements Callback<T>{
    public static Map<String, LoginPresenter.userTypes> userTypesMap;
    static {
        userTypesMap = new HashMap<>();
        userTypesMap.put("Driver", LoginPresenter.userTypes.DRIVER);
        userTypesMap.put("Customer", LoginPresenter.userTypes.SMALL_BUSINESS_OWNER);
        userTypesMap.put("Supplier", LoginPresenter.userTypes.SUPPLIER);
    }

    // userType information is stored in these variables. If successful, this.success() = True,
    // if error occurred, errormsg() returns error message
    private boolean wasSuccessful = false;
    private LoginPresenter.userTypes loggedInUserType;
    private String errorMessage = "No Errors";
    private String username;

    private User createUser(LoginPresenter.userTypes type, String username){
        // factory method for creating a User based on userType
        User user;

        switch (type){
            case DRIVER:
                user = new Driver(username);
                break;

            case SMALL_BUSINESS_OWNER:
                user = new SmallBusinessOwner(username);
                break;

            case SUPPLIER:
                user = new Supplier(username);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return user;
    }

    public LoginCallback(String username){
        this.username = username;
        System.out.println("LoginCallback created");
    }

    public boolean success(){
        return wasSuccessful;
    }
    public LoginPresenter.userTypes getLoggedInUserType(){
        return loggedInUserType;
    }

    public String errormsg(){
        // returns error message
        return errorMessage;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        System.out.print("Got Callback Response");
        // get the usertype param from JSON response
        if (response.isSuccessful()){


            JsonObject json = (JsonObject) response.body();
            boolean loginSucceeded = json.get("loginStatus").getAsBoolean();
            if (loginSucceeded) {
                String userType = json.get("usertype").getAsString();

                this.loggedInUserType = userTypesMap.get(userType);

                User user = createUser(userTypesMap.get(userType), username);


                System.out.println(" User logged in as a " + getLoggedInUserType());
            } else {
                System.out.println(" Incorrect username or password");
            }

        }

    }
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        // wasSuccessful is set to false
        System.out.println("LoginCallback onFailure() was triggered");
    }

}



