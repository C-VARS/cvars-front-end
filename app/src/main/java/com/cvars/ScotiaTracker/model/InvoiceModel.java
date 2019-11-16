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
public class InvoiceModel {

    public enum InvoiceAction {
        REQUEST, UPDATE
    }

    interface InvoiceResponseListener {
        void notifyInvoiceResponse(InvoiceAction action);
    }

    private List<Invoice> invoices;

    private InvoiceAPI invoiceAPI = RetrofitNetwork.retrofit.create(InvoiceAPI.class);
    private InvoiceResponseListener listener;
    private RequestInvoiceCallback requestCallback = new RequestInvoiceCallback();
    private boolean actionSuccess;

    /**
     * Starts an Asynchronous Call using Retrofit to get Invoices
     *
     * @param username username of the user
     */
    void requestInvoices(String username) {
        Call<List<Invoice>> call = invoiceAPI.getInvoices(username);
        call.enqueue(this.requestCallback);
    }

    private class RequestInvoiceCallback implements Callback<List<Invoice>> {
        @Override
        public void onResponse(Call<List<Invoice>> call, Response<List<Invoice>> response) {
            actionSuccess = true;
            invoices = response.body();
            listener.notifyInvoiceResponse(InvoiceAction.REQUEST);
        }

        @Override
        public void onFailure(Call<List<Invoice>> call, Throwable t) {
            actionSuccess = false;
            listener.notifyInvoiceResponse(InvoiceAction.REQUEST);
        }
    }


    public List<Invoice> getInvoices() {
        return invoices;
    }

    public Invoice getInvoice(int invoiceID) {
        return null;
    }

    public void setListener(InvoiceResponseListener listener) {
        this.listener = listener;
    }

    public boolean getActionSuccess() {
        return actionSuccess;
    }
}