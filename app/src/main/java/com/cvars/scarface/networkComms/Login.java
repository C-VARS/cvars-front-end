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

import static com.cvars.scarface.networkComms.NetworkClient.BASE_URL;

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


    public User loginAsUser(final String username, String password) throws LoginError, IOException {
        /*Attempts to login with username and password. On success, returns User object, on failure,
         * throws Exception*/
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        NetworkClient service = retrofit.create(NetworkClient.class);

        Call<JsonObject> call = service.loginAttempt(username, password);

        // create pipe for communicating with the callback
        PipedInputStream pis = new PipedInputStream();
        PipedOutputStream pos = new PipedOutputStream();
        pis.connect(pos);

        // create LoginCallback object which stores the userType if loginCallback.success() == True
        LoginCallback<JsonObject> loginCallback = new LoginCallback<>(pos);

        call.enqueue(loginCallback);
        // wait for LoginCallback to receive API call and write into pipe
        int data;
        while ((data = pis.read() )!= -1) {
            System.out.print((char) data);
        }
        pis.close();


        // return the userType of a User if created
        if (loginCallback.success()) {
            // create user of UserType
            User user = createUser(loginCallback.getLoggedInUserType(), username);
            Log.i(null, "Successfully connected to Login and returned a User");
            return user;

        } else {
            Log.e(null, "LOGIN FAILED");
            throw new LoginError("Login Failed");
//        }

        }
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
