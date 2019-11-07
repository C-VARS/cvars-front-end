package com.cvars.ScotiaTracker.networkAPI;

import com.cvars.ScotiaTracker.model.Invoice;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InvoiceAPI {
    @GET("invoices")
    Call<List<Invoice>> getInvoice(@Query("username") String username);
}
