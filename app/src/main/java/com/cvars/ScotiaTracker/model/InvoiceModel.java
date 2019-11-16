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
    private List<Invoice> invoices;


    /**
     * Constructs an invoiceModel
     */
    public InvoiceModel() {
        endpointAPI = RetrofitNetwork.retrofit.create(EndpointAPI.class);
    }

    /**
     * Starts an Asynchronous Call using Retrofit to get Invoices
     * @param username
     */
    public void requestInvoices(String username) {
        Call<List<Invoice>> call = endpointAPI.getInvoices(username);
        call.enqueue(this);
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    @Override
    public void onResponse(Call<List<Invoice>> call, Response<List<Invoice>> response) {
        invoices = response.body();
    }

    @Override
    public void onFailure(Call<List<Invoice>> call, Throwable t) {

    }
}
