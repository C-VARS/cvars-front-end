package com.cvars.ScotiaTracker.model;

import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.networkAPI.InvoiceAPI;
import com.cvars.ScotiaTracker.networkAPI.RegisterAPI;
import com.cvars.ScotiaTracker.networkAPI.RetrofitNetwork;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterModel {

    private RegisterAPI registerAPI = RetrofitNetwork.retrofit.create(RegisterAPI.class);
    private RegisterModel.RegisterCallback registerCallback = new RegisterCallback();

    public void register(HashMap<String, String> registerData) {
        Call<JsonObject> call = registerAPI.register(registerData);
        call.enqueue(this.registerCallback);
    }

    public class RegisterCallback implements Callback<JsonObject> {

        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
            System.out.println("Successfully registered a user");
        }

        @Override
        public void onFailure(Call<JsonObject> call, Throwable t) {

        }
    }

}
