package com.cvars.ScotiaTracker.networkAPI;

import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface InvoiceAPI {


    @GET("invoices")
    Call<List<Invoice>> getInvoices(@Query("username") String username);

    @POST("invoices/update")
    Call<JsonObject> updateStatus(@Query("invoiceID") int invoiceID, @Query("status") String newStatus);

}
