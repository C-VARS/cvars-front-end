package com.cvars.scarface.networkComms;

import android.util.Log;

import com.cvars.scarface.model.Driver;
import com.cvars.scarface.model.SmallBusinessOwner;
import com.cvars.scarface.model.Supplier;
import com.cvars.scarface.model.User;
import com.google.gson.JsonObject;


import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login {

    public class LoginError extends Exception{
        LoginError(String str) {
            super(str);
        }
    }

    public static enum userTypes {
        DRIVER,
        SMALL_BUSINESS_OWNER,
        SUPPLIER
    }

    public String baseUrl = "http://cvars.herokuapp.com/";

    public ServerService createService(String baseUrl){
        // create an HTTP Logger for logging API calls
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        // create a retrofit around ServerService and return it
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        ServerService service = retrofit.create(ServerService.class);

        return service;
    }

    public User loginAsUser(final String username, String password) throws LoginError, IOException {
        /*Attempts to login with username and password. On success, returns User object, on failure,
         * throws Exception*/

        // create an instance of ServerService and a Call to loginAttempt in the API
        ServerService service = createService(baseUrl);
        Call<JsonObject> call = service.loginAttempt(username, password);

        // TODO figure out how to get response back from asynchronous call.enqueue() call with LoginCallback

        // create LoginCallback object which stores the userType if loginCallback.success() == True
        LoginCallback<JsonObject> loginCallback = new LoginCallback<>();

        call.enqueue(loginCallback);

        // TODO Figure out how to use the asynchronous call to loginCallback to return a User
        // in this method - or a workaround
        return Driver("paul");
    }

    private User createUser(userTypes type, String username){
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
}
