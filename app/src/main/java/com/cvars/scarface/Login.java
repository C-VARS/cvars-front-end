package com.cvars.scarface;

import android.util.Log;

import androidx.core.app.TaskStackBuilder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login {

//    class LoginError extends Exception{
//        LoginError(String str) {
//            super(str);
//        }
//    }

    public static enum userTypes {
        DRIVER,
        SMALL_BUSINESS_OWNER,
        SUPPLIER
    }

    public String baseUrl = "http://10.0.2.2:5000";

    public User loginAsUser(final String username, String password){
        /*Attempts to login with username and password. On success, returns User object, on failure,
        * throws Exception*/

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        ServerService service = retrofit.create(ServerService.class);

        Call<JsonObject> call = service.loginAttempt(username, password);

        final User[] user = new User[1];

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                // get the usertype param from JSON response
                String userType = response.body().get("usertype").getAsString();

                // create and return that type of User
                if (usertype == "driver"){
                    user[0] = new Driver(username);
                } else if (usertype == "small_business_owner"){
                    user[0] = new SmallBusinessOwner(username);
                } else if (usertype == "supplier"){
                    user[0] = new Supplier(username);
                }

            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                //throw new LoginError("Login Attempt Failed");
            }
        });

        return user[0];
    }
}
