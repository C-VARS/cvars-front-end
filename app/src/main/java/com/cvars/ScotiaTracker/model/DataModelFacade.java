package com.cvars.ScotiaTracker.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.model.pojo.User;
import com.cvars.ScotiaTracker.model.pojo.UserType;
import com.cvars.ScotiaTracker.networkAPI.FirebaseService;
import com.cvars.ScotiaTracker.responseListeners.SearchResponseListener;
import com.cvars.ScotiaTracker.responseListeners.SettingResponseListener;
import com.cvars.ScotiaTracker.strategy.IdSearch;
import com.cvars.ScotiaTracker.view.UserActivityView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

public class DataModelFacade implements InvoiceModel.InvoiceActionListener,
                                        UserModel.UserResponseListener {
    private String username;
    private String password;
    private UserType userType;

    private InvoiceModel invoiceModel;
    private UserModel userModel;
    private SearchModel searchModel;

    private UserActivityView userActivityView;

    private SettingResponseListener settingResponseListener;
    private SettingResponseListener homeSettingResponseListener;
    private SearchResponseListener homeResponseListener;
    private SearchResponseListener invoiceResponseListener;

    private FirebaseService firebaseService;

    /**
     * Construct a facade that stores components needed for the model and views held by this
     * facade to interact with each other.
     *
     * @param username
     * @param password
     * @param userType
     */

    public DataModelFacade(String username, String password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;

        invoiceModel = new InvoiceModel();
        invoiceModel.setListener(this);
        userModel = new UserModel();
        userModel.setListener(this);
        searchModel = new SearchModel();


        firebaseService = new FirebaseService();
    }

    public void setUserActivityView(UserActivityView userActivityView) {
        this.userActivityView = userActivityView;
    }

    /**
     * Pull all invoices of the user with username
     */
    public void requestAllInvoices() {
        userActivityView.showLoading();
        invoiceModel.requestAllInvoices(username);
    }

    public List<Invoice> getInvoices() {
        // Returns a List of Invoices - USE ONLY AFTER requestAllInvoices() is called!
        return invoiceModel.getInvoices();
    }

    public Invoice getInvoice(int invoiceID) {
        return invoiceModel.getInvoice(invoiceID);
    }

    public void updateStatus(int invoiceID, String status) {
        invoiceModel.updateStatus(invoiceID, status);
    }

    public void requestUserInfo() {
        userActivityView.showLoading();
        userModel.requestUser(username);
    }

    public void subscribeToTopic(List<Integer> invoiceList) {
        for (Integer i: invoiceList){
            FirebaseMessaging.getInstance().subscribeToTopic(i.toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            String msg = "success!";
                            if (!task.isSuccessful()) {
                                msg = "failed!";
                            }
                            Log.d("Topic Subscription", msg);
                        }
                    });
        }
    }

    @Override
    public void notifyInvoiceAction(InvoiceModel.InvoiceAction action) {
        userActivityView.finishLoading();

        if (!invoiceModel.getActionSuccess()) {
            userActivityView.displayMessage("Network error!");
            return;
        }

        switch (action) {
            case REQUEST:
                if (!invoiceModel.getInvoices().get(0).getInfoRequestStatus()) {
                    userActivityView.displayMessage("Incorrect user information");
                } else {
                    // get returned invoices from invoiceModel
                    homeResponseListener.notifyInvoiceResponse();
                    invoiceResponseListener.notifyInvoiceResponse();
                    subscribeToTopic(invoiceModel.getInvoiceID());
                }
                break;
            case UPDATE:
                //TODO: handle invoice update callback
                break;
        }
    }

    @Override
    public void notifyUserAction(UserModel.UserAction action) {
        userActivityView.finishLoading();

        if (!userModel.getActionSuccess()) {
            userActivityView.displayMessage("Network error!");
            return;
        }

        switch (action) {
            case REQUEST:
                if (!userModel.getUser().getInfoRequestStatus()) {
                    userActivityView.displayMessage("Incorrect user information");
                } else {
                    if (settingResponseListener != null) {
                        settingResponseListener.notifySettingResponse();
                        homeSettingResponseListener.notifySettingResponse();
                    }
                }
                break;
            case UPDATE:
                break;
        }
    }
    //TODO: Add in type of search as a parameter (dependency injection?)
    public List<Invoice> executeSearch(String searchAttribute) {
        searchModel.setSearch(new IdSearch());
        return searchModel.executeSearch(this.getInvoices(), searchAttribute);
    }

    public void onDestroy() {
        userActivityView = null;
    }

    public User getUser() {
        return userModel.getUser();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setSettingResponseListener(SettingResponseListener settingResponseListener) {
        this.settingResponseListener = settingResponseListener;
    }

    public void setHomeSettingResponseListener(SettingResponseListener settingResponseListener){
        this.homeSettingResponseListener = settingResponseListener;
    }

    public void setHomeResponseListener(SearchResponseListener searchResponseListener) {
        this.homeResponseListener = searchResponseListener;
    }

    public void setInvoiceResponseListener(SearchResponseListener searchResponseListener) {
        this.invoiceResponseListener = searchResponseListener;
    }
}
