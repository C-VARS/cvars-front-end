package com.cvars.ScotiaTracker.model;

import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.networkAPI.InvoiceAPI;
import com.cvars.ScotiaTracker.networkAPI.RetrofitNetwork;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

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
class InvoiceModel {

    public enum InvoiceAction {
        REQUEST, UPDATE
    }

    interface InvoiceActionListener {
        void notifyInvoiceAction(InvoiceAction action);
    }

    private Map<Integer, Invoice> invoiceMap = new HashMap<>();

    private InvoiceAPI invoiceAPI = RetrofitNetwork.retrofit.create(InvoiceAPI.class);
    private InvoiceActionListener listener;
    private RequestInvoiceCallback requestCallback = new RequestInvoiceCallback();
    private UpdateStatusCallback updateStatusCallback = new UpdateStatusCallback();
    private boolean actionSuccess;
    private boolean infoRequestStatus;

    /**
     * Starts an Asynchronous Call using Retrofit to get Invoices
     *
     * @param username username of the user
     */
    void requestAllInvoices(String username) {
        Call<JsonObject> call = invoiceAPI.getInvoices(username);
        call.enqueue(this.requestCallback);
    }

    void updateStatus(int invoiceID, String status) {
        Call<JsonObject> call = invoiceAPI.updateStatus(invoiceID, status);
        call.enqueue(this.updateStatusCallback);
    }

    private class RequestInvoiceCallback implements Callback<JsonObject> {
        /**
         * Populate the invoiceMap with invoiceIDs and map it to the corresponding invoice instance
         */
        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
            Gson gson = new Gson();
            actionSuccess = true;
            JsonObject body = response.body();
            infoRequestStatus = body.get("infoRequestStatus").getAsBoolean();
            List<Invoice> invoices = gson.fromJson(body.get("invoices"), new TypeToken<List<Invoice>>() {}.getType());
            for (Invoice inv: invoices){
                invoiceMap.put(inv.getInvoiceId(), inv);
            }
            listener.notifyInvoiceAction(InvoiceAction.REQUEST);
        }

        /**
         * Fail silently.
         */
        @Override
        public void onFailure(Call<JsonObject> call, Throwable t) {
            actionSuccess = false;
            listener.notifyInvoiceAction(InvoiceAction.REQUEST);
        }
    }

    private class UpdateStatusCallback implements Callback<JsonObject> {
        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
            JsonObject jsonResponse = response.body();
            if (jsonResponse != null && jsonResponse.has("updateStatus")){
                actionSuccess = response.body().get("updateStatus").getAsBoolean();
            } else{
                actionSuccess = false;
            }
            listener.notifyInvoiceAction(InvoiceAction.UPDATE);
        }

        @Override
        public void onFailure(Call<JsonObject> call, Throwable t) {
            actionSuccess = false;
            listener.notifyInvoiceAction(InvoiceAction.UPDATE);
        }
    }


    List<Invoice> getInvoices() {
        List<Invoice> list = new ArrayList<>(invoiceMap.values());
        return list;
    }

    Invoice getInvoice(int invoiceID){
        return invoiceMap.get(invoiceID);
    }

    List<Integer> getInvoiceID(){
        List<Integer> list = new ArrayList<>(invoiceMap.keySet());
        return list;
    }

    void setListener(InvoiceActionListener listener) {
        this.listener = listener;
    }

    boolean getActionSuccess() {
        return actionSuccess;
    }

    boolean getInfoRequestStatus(){return infoRequestStatus;}
}