package com.cvars.scarface.networkComms;


import android.content.Context;
import android.widget.Toast;

import com.cvars.scarface.activity.MainActivity;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginCallback<T> implements Callback<T>{
    public static Map<String, Login.userTypes> userTypesMap;
    static {
        userTypesMap = new HashMap<>();
        userTypesMap.put("Driver", Login.userTypes.DRIVER);
        userTypesMap.put("Customer", Login.userTypes.SMALL_BUSINESS_OWNER);
        userTypesMap.put("Supplier", Login.userTypes.SUPPLIER);
    }

    // userType information is stored in these variables. If successful, this.success() = True,
    // if error occurred, errormsg() returns error message
    private boolean wasSuccessful = false;
    private Login.userTypes loggedInUserType;
    private String errorMessage = "No Errors";


    public LoginCallback(){
        System.out.println("LoginCallback created");
    }

    public boolean success(){
        return wasSuccessful;
    }
    public Login.userTypes getLoggedInUserType(){
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
            wasSuccessful = true;
            
            createToast("Successfull", MainActivity.getTest().getApplicationContext());

            JsonObject json = (JsonObject) response.body();
            String userType =  json.get("usertype").getAsString();

            this.loggedInUserType = userTypesMap.get(userType);

            System.out.println(" User logged in as a " + getLoggedInUserType());

        }

    }
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        // wasSuccessful is set to false
        System.out.println("LoginCallback onFailure() was triggered");
    }

    public void createToast(String message, Context context){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}



