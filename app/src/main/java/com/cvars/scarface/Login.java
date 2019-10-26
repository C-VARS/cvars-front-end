package com.cvars.scarface;

import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Login {

    public static enum userTypes {
        DRIVER,
        SMALL_BUSINESS_OWNER,
        SUPPLIER
    }
    public static String baseUrl = "localhost";

//    public User login(String user_id, String password) {
//        // sends a login request to REST API and creates a User object if it exists
//
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).build();
//        ServerService service = retrofit.create(ServerService.class);
//
//        try {
//            Call<JsonElement> call = service.loginAttempt(user_id, password);
//            Response<JSONObject> response = call.execute();
//
//            System.out.println(response.body().toString());
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//        return new Driver(user_id="1");
//    }
}
