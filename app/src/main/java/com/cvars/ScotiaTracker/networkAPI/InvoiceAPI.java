package com.cvars.ScotiaTracker.networkAPI;

import com.cvars.ScotiaTracker.model.pojo.Invoice;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface InvoiceAPI {


    @GET("invoices")
    Call<List<Invoice>> getInvoices(@Query("username") String username);

}
