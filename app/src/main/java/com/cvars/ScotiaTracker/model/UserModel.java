package com.cvars.ScotiaTracker.model;

import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.model.pojo.User;
import com.cvars.ScotiaTracker.networkAPI.EndpointAPI;
import com.cvars.ScotiaTracker.networkAPI.RetrofitNetwork;
import com.cvars.ScotiaTracker.responseHandlers.ResponseHandler;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A Model object for the use case and information storage related to a User. Implements the Retrofit
 * Callback interface to handle asynchronous HTTP response.
 */
public class UserModel {
    /**
     * A Retrofit API connection object for getting User Information
     */
    private EndpointAPI endpointAPI;
    private Boolean loginSuccess;
    private User user;
    private List<Invoice> invoices;
    // TODO: Remove hard dependency & use dependency injection
    private UserCall userCall = new UserCall();
    private InvoiceCall invoiceCall = new InvoiceCall();
    /**
     * A reference to a ResponseHandler interface that will be called whenever an HTTP response
     */
    private ResponseHandler responseHandler;
    private String errorMessage;

    /**
     * Constructs a userModel with an injected ResponseHandler reference
     * which interacts with the backend
     *
     * @param responseHandler
     */
    public UserModel(ResponseHandler responseHandler) {
        this.responseHandler = responseHandler;
        endpointAPI = RetrofitNetwork.retrofit.create(EndpointAPI.class);
    }

    /**
     * Starts an Asynchronous Call using Retrofit to get a users's information
     *
     * @param username
     */
    public void createUser(String username) {
        Call<User> call = endpointAPI.getUser(username);
        // Asynchronous Call occurs, passing in this
        call.enqueue(this.userCall);
    }

    /**
     * Starts an Asynchronous Call using Retrofit to get a users's Invoices
     *
     * @param username
     */
    public void createInvoices(String username) {
        Call<List<Invoice>> call = endpointAPI.getInvoices(username);
        // Asynchronous Call occurs, passing in this
        call.enqueue(this.invoiceCall);
    }

    /**
     * Getter for username
     *
     * @return username
     */
    public String getUsername() {
        return this.user.getName();
    }

    class UserCall implements Callback<User> {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            // TODO: add response, checking
            user = response.body();
            System.out.println(user);
            responseHandler.notifyResponse();
        }

        /**
         * On a failed HTTP request, change the errorMessage and notify a login response
         *
         * @param call Call object
         * @param t    error object
         */
        @Override
        public void onFailure(Call<User> call, Throwable t) {
            errorMessage = "Connection failure";
            loginSuccess = false;
            responseHandler.notifyResponse();
        }

    }

    class InvoiceCall implements Callback<List<Invoice>> {
        @Override
        public void onResponse(Call<List<Invoice>> call, Response<List<Invoice>> response) {
            // TODO: add response, checking
            invoices = response.body();
            System.out.println(invoices);
            responseHandler.notifyResponse();
        }

        /**
         * On a failed HTTP request, change the errorMessage and notify a login response
         *
         * @param call Call object
         * @param t    error object
         */
        @Override
        public void onFailure(Call<List<Invoice>> call, Throwable t) {
            errorMessage = "Connection failure";
            loginSuccess = false;
            responseHandler.notifyResponse();
        }

    }
}
