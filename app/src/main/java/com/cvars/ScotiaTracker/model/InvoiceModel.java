package com.cvars.ScotiaTracker.model;

import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.networkAPI.InvoiceAPI;
import com.cvars.ScotiaTracker.networkAPI.RetrofitNetwork;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    interface InvoiceActionListener {
        void notifyInvoiceAction(InvoiceAction action);
    }

    private List<Invoice> invoices;
    private boolean updateStatus;
    private Map<Integer, Invoice> invoiceMap = new HashMap<>();

    private InvoiceAPI invoiceAPI = RetrofitNetwork.retrofit.create(InvoiceAPI.class);
    private InvoiceActionListener listener;
    private RequestInvoiceCallback requestCallback = new RequestInvoiceCallback();
    private UpdateStatusCallback updateStatusCallback = new UpdateStatusCallback();
    private boolean actionSuccess;

    /**
     * Starts an Asynchronous Call using Retrofit to get Invoices
     *
     * @param username username of the user
     */
    public void requestAllInvoices(String username) {
        Call<List<Invoice>> call = invoiceAPI.getInvoices(username);
        call.enqueue(this.requestCallback);
    }

    public void updateStatus(int invoiceID, String status) {
        Call<JsonObject> call = invoiceAPI.updateStatus(invoiceID, status);
        call.enqueue(this.updateStatusCallback);
    }

    private class RequestInvoiceCallback implements Callback<List<Invoice>> {
        @Override
        public void onResponse(Call<List<Invoice>> call, Response<List<Invoice>> response) {
            actionSuccess = true;
            List<Invoice> invoices = response.body();
            for (Invoice inv: invoices){
                invoiceMap.put(inv.getInvoiceId(), inv);
            }
            listener.notifyInvoiceAction(InvoiceAction.REQUEST);
        }

        @Override
        public void onFailure(Call<List<Invoice>> call, Throwable t) {
            actionSuccess = false;
            listener.notifyInvoiceAction(InvoiceAction.REQUEST);
        }
    }

    private class UpdateStatusCallback implements Callback<JsonObject> {
        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
            // TODO: What to do if updateStatus returns false.
            updateStatus = response.body().get("updateStatus").getAsBoolean();
        }

        @Override
        public void onFailure(Call<JsonObject> call, Throwable t) {
            System.out.println("The Request Failed :(");
        }
    }


    List<Invoice> getInvoices() {
        List<Invoice> list = new ArrayList<>(invoiceMap.values());
        return list;
    }

    Invoice getInvoice(int invoiceID){
        return invoiceMap.get(invoiceID);
    }

    void setListener(InvoiceActionListener listener) {
        this.listener = listener;
    }

    boolean getActionSuccess() {
        return actionSuccess;
    }
}