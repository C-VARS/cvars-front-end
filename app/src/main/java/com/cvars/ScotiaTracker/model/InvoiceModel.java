package com.cvars.ScotiaTracker.model;

import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.networkAPI.InvoiceAPI;
import com.cvars.ScotiaTracker.networkAPI.LoginAPI;
import com.cvars.ScotiaTracker.networkAPI.RetrofitNetwork;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A Model object for the use case and information storage related to Invoices. Implements the Retrofit
 * Callback interface to handle asynchronous HTTP response.
 */
public class InvoiceModel implements Callback<List<Invoice>> {

    private InvoiceAPI invoiceAPI;
    private List<Invoice> invoices;
    private InvoiceResponseListener listener;

    private boolean actionSuccess;

    public enum InvoiceAction{
        REQUEST, UPDATE
    }

    interface InvoiceResponseListener{
        void notifyInvoiceResponse(InvoiceAction action);
    }

    /**
     * Constructs an invoiceModel
     */
    InvoiceModel() {
        invoiceAPI = RetrofitNetwork.retrofit.create(InvoiceAPI.class);
    }

    /**
     * Starts an Asynchronous Call using Retrofit to get Invoices
     * @param username username of the user
     */
    void requestInvoices(String username) {
        Call<List<Invoice>> call = invoiceAPI.getInvoices(username);
        call.enqueue(this);
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public Invoice getInvoice(int invoiceID){
        return null;
    }

    @Override
    public void onResponse(Call<List<Invoice>> call, Response<List<Invoice>> response) {
        invoices = response.body();
        listener.notifyInvoiceResponse(InvoiceAction.REQUEST);
    }

    @Override
    public void onFailure(Call<List<Invoice>> call, Throwable t) {

    }

    public void setListener(InvoiceResponseListener listener) {
        this.listener = listener;
    }
}