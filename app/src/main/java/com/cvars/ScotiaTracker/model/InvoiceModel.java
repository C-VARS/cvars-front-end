package com.cvars.ScotiaTracker.model;

import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.networkAPI.EndpointAPI;
import com.cvars.ScotiaTracker.networkAPI.RetrofitNetwork;
import com.cvars.ScotiaTracker.responseHandlers.ResponseHandler;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A Model object for the use case and information storage related to Invoices. Implements the Retrofit
 * Callback interface to handle asynchronous HTTP response.
 */
public class InvoiceModel implements Callback<List<Invoice>> {

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
        Call<List<Invoice>> call = endpointAPI.getInvoices(username);
        call.enqueue(this);
    }


    @Override
    public void onResponse(Call<List<Invoice>> call, Response<List<Invoice>> response) {

    }

    @Override
    public void onFailure(Call<List<Invoice>> call, Throwable t) {

    }
}
