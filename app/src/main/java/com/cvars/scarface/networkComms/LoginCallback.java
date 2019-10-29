package com.cvars.scarface.networkComms;


import java.io.IOException;
import java.io.PipedOutputStream;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginCallback<JsonObject> implements Callback<JsonObject>{
    private PipedOutputStream pos;
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


    public LoginCallback(PipedOutputStream pos){
        System.out.println("LoginCallback created");
        this.pos = pos;
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
    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
        System.out.print("Got Callback Response");
        // get the usertype param from JSON response
        if (response.isSuccessful()){
            wasSuccessful = true;
            try {
                JsonObject json = response.body();
                String userType = json.toString();
                pos.write(userType.getBytes());

                System.out.println("User logged in as a " + userType);

            } catch (IOException e) {
                errorMessage = "Failed to write into pipe from LoginCallback";
                System.out.println(errorMessage);
                e.printStackTrace();
            }
            ;

        }

    }
    @Override
    public void onFailure(Call<JsonObject> call, Throwable t) {
        // wasSuccessful is set to false
        System.out.println("LoginCallback onFailure() was triggered");
    }

}



