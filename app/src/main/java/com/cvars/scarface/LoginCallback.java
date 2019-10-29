package com.cvars.scarface;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginCallback<JsonObject> implements Callback<JsonObject> {

    public static Map<String, Login.userTypes> userTypesMap;
    static {
        userTypesMap = new HashMap<>();
        userTypesMap.put("driver", Login.userTypes.DRIVER);
        userTypesMap.put("small_business_owner", Login.userTypes.SMALL_BUSINESS_OWNER);
        userTypesMap.put("supplier", Login.userTypes.SUPPLIER);
    }

    // userType information is stored in these variables. If successful, this.success() = True,
    // if error occurred, errormsg() returns error message
    private boolean wasSuccessful = false;
    private Login.userTypes loggedInUserType;

    public boolean success(){
        return wasSuccessful;
    }
    public Login.userTypes getLoggedInUserType(){
        return loggedInUserType;
    }

    public String errormsg(){
        // returns error message
        return "A login error occurred";
    }

    @Override
    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
        wasSuccessful = true;
        // get the usertype param from JSON response
        String userType = response.body().get("usertype").getAsString();
        loggedInUserType = userTypesMap.get(userType);

    }
    @Override
    public void onFailure(Call<JsonObject> call, Throwable t) {
        // wasSuccessful is set to false
    }
}



