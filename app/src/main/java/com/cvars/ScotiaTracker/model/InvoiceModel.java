package com.cvars.ScotiaTracker.model;

import com.cvars.ScotiaTracker.networkAPI.EndpointAPI;
import com.cvars.ScotiaTracker.networkAPI.RetrofitNetwork;
import com.cvars.ScotiaTracker.responseHandlers.ResponseHandler;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A Model object for the use case and information storage related to Invoices. Implements the Retrofit
 * Callback interface to handle asynchronous HTTP response.
 */
public class InvoiceModel implements Callback<JsonObject> {

    private EndpointAPI endpointAPI;

    /**
     * A reference to a ResponseHandler interface that will be called whenever an HTTP response
     */
    private ResponseHandler responseHandler;

    /**
     * Constructs an invoiceModel with an injected ResponseHandler reference
     * which interacts with the backend and has some information
     * about the status of the Invoice
     * @param responseHandler
     */
    public InvoiceModel(ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
        endpointAPI = RetrofitNetwork.retrofit.create(EndpointAPI.class);
    }

    /**
     * Starts an Asynchronous Call using Retrofit to get Invoices
     * @param username
     */
    public void getInvoices(String username) {
        Call<JsonObject> call = endpointAPI.getInvoices(username);
        // Asynchronous Call occurs, passing in this loginModel
        call.enqueue(this);
    }

    /**
     * On response from our HTTP request, notify a invoice response
     * @param call retrofit Call object
     * @param response retrofit Response object
     */
    @Override
    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
        JsonObject json = response.body();
//        loginSuccess = json.get("loginStatus").getAsBoolean();
//        if (loginSuccess) {
//            String userType = json.get("userType").getAsString();
//            this.userType = convertUserToEnum(userType);
//        } else {
//            this.errorMessage = "Incorrect username or password";
//        }

        System.out.println(json);
        responseHandler.notifyResponse();
    }

    /**
     * On a failed HTTP request, change the errorMessage and notify a invoice response
     * @param call Call object
     * @param t error object
     */
    @Override
    public void onFailure(Call<JsonObject> call, Throwable t) {
//        loginSuccess = false;
//        errorMessage = "Connection failure";

        responseHandler.notifyResponse();
    }
}
