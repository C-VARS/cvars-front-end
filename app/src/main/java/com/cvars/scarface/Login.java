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

    class LoginError extends Exception{
        LoginError(String str) {
            super(str);
        }
    }

    public static enum userTypes {
        DRIVER,
        SMALL_BUSINESS_OWNER,
        SUPPLIER
    }

    public String baseUrl = "http://10.0.2.2:5000";

    public User loginAsUser(final String username, String password) throws LoginError {
        /*Attempts to login with username and password. On success, returns User object, on failure,
        * throws Exception*/

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        ServerService service = retrofit.create(ServerService.class);

        Call<JsonObject> call = service.loginAttempt(username, password);

        // create LoginCallback object which stores the userType if loginCallback.success() == True
        LoginCallback loginCallback = new LoginCallback();
        call.enqueue(loginCallback);

        // return the userType of a User if created
        if (loginCallback.success()){
            // create user of UserType
            User user = createUser(loginCallback.getLoggedInUserType(), username);

            return user;

        } else {
            throw new LoginError("Login Failed");
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
