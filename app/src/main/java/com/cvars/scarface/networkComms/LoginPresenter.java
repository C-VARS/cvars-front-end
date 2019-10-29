package com.cvars.scarface.networkComms;

import android.content.Context;
import android.util.Log;

import com.cvars.scarface.model.Driver;
import com.cvars.scarface.model.SmallBusinessOwner;
import com.cvars.scarface.model.Supplier;
import com.cvars.scarface.model.User;
import com.google.gson.JsonObject;


import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.cvars.scarface.networkComms.NetworkClient.BASE_URL;

public class LoginPresenter {

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


    public NetworkClient createService(String baseUrl){
        // create an HTTP Logger for logging API calls
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);


        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        NetworkClient service = retrofit.create(NetworkClient.class);

        return service;
    }

    public void loginAsUser(final String username, String password) {
        /*Attempts to login with username and password. Updates MainActivity with a Toast*/

        // create an instance of NetworkClient and a Call to loginAttempt in the API
        NetworkClient service = createService(BASE_URL);
        Call<JsonObject> call = service.loginAttempt(username, password);

        // create LoginCallback object which gets API response and updates MainActivity
        LoginCallback<JsonObject> loginCallback = new LoginCallback<>(username);

        call.enqueue(loginCallback);
    }


}
