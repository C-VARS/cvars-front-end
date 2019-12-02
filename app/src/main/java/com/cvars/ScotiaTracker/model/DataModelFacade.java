package com.cvars.ScotiaTracker.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.model.pojo.User;
import com.cvars.ScotiaTracker.model.pojo.UserType;
import com.cvars.ScotiaTracker.networkAPI.FirebaseService;
import com.cvars.ScotiaTracker.responseListeners.InvoiceResponseListener;
import com.cvars.ScotiaTracker.responseListeners.RegisterResponseListener;
import com.cvars.ScotiaTracker.responseListeners.SettingResponseListener;
import com.cvars.ScotiaTracker.strategy.search.CustomerSearch;
import com.cvars.ScotiaTracker.strategy.search.DriverSearch;
import com.cvars.ScotiaTracker.strategy.search.IdSearch;
import com.cvars.ScotiaTracker.strategy.search.IssueDateSearch;
import com.cvars.ScotiaTracker.strategy.search.SearchType;
import com.cvars.ScotiaTracker.strategy.search.StatusSearch;
import com.cvars.ScotiaTracker.strategy.search.SupplierSearch;
import com.cvars.ScotiaTracker.strategy.sort.NewestSort;
import com.cvars.ScotiaTracker.strategy.sort.OldestSort;
import com.cvars.ScotiaTracker.strategy.sort.SortType;
import com.cvars.ScotiaTracker.strategy.sort.StatusSort;
import com.cvars.ScotiaTracker.view.UserActivityView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DataModelFacade implements InvoiceModel.InvoiceActionListener,
        UserModel.UserResponseListener {
    private String username;
    private String password;
    private UserType userType;

    private InvoiceModel invoiceModel;
    private RegisterModel registerModel;
    private UserModel userModel;
    private SearchModel searchModel;

    private FirebaseService firebaseService;

    private UserActivityView userActivityView;

    private List<InvoiceResponseListener> invoiceResponseListeners;
    private List<SettingResponseListener> settingResponseListeners;
    private RegisterResponseListener registerResponseListener;

    public DataModelFacade(String username, String password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;

        invoiceResponseListeners = new LinkedList<>();
        settingResponseListeners = new LinkedList<>();

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

    public void register(Map<String, String> registerData) {
        registerModel.register(registerData);
    }

    public void requestUserInfo() {
        userActivityView.showLoading();
        userModel.requestUser(username);
    }

    private void subscribeToTopic(List<Integer> invoiceList) {
        for (Integer i : invoiceList) {
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

    private void unsubscribeToTopic(List<Integer> invoiceList) {
        for (Integer i : invoiceList) {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(i.toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            String msg = "success!";
                            if (!task.isSuccessful()) {
                                msg = "failed!";
                            }
                            Log.d("Topic Unsubscribed", msg);
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
                    for (InvoiceResponseListener listener : invoiceResponseListeners) {
                        listener.notifyInvoiceResponse();
                    }
                    subscribeToTopic(invoiceModel.getInvoiceID());
                }
                break;
            case UPDATE:
                userActivityView.displayMessage("Update Success!");
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
                    for (SettingResponseListener listener : settingResponseListeners) {
                        listener.notifySettingResponse();
                    }
                }
                break;
            case UPDATE:
                break;
        }
    }

    //TODO: Add in type of search as a parameter (dependency injection?)
    public List<Invoice> executeSearch(String searchAttribute) {
        return searchModel.executeSearch(this.getInvoices(), searchAttribute);
    }

    public void setSortStrategy(SortType type) {
        switch (type) {
            case NEWEST:
                searchModel.setSortStrategy(new NewestSort());
                break;
            case OLDEST:
                searchModel.setSortStrategy(new OldestSort());
                break;
            case STATUS:
                searchModel.setSortStrategy(new StatusSort());
                break;
        }
    }

    public void setSearchStrategy(SearchType type) {
        switch (type) {
            case DRIVER:
                searchModel.setSearchStrategy(new DriverSearch());
                break;
            case CUSTOMER:
                searchModel.setSearchStrategy(new CustomerSearch());
                break;
            case SUPPLIER:
                searchModel.setSearchStrategy(new SupplierSearch());
                break;
            case ID:
                searchModel.setSearchStrategy(new IdSearch());
                break;
            case ISSUE_DATE:
                searchModel.setSearchStrategy(new IssueDateSearch());
                break;
            case STATUS:
                searchModel.setSearchStrategy(new StatusSearch());
                break;
        }
    }

    public void onDestroy() {
        userActivityView = null;
        settingResponseListeners = null;
        invoiceResponseListeners = null;
        unsubscribeToTopic(invoiceModel.getInvoiceID());
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

    public void addInvoiceResponseListener(InvoiceResponseListener listener) {
        invoiceResponseListeners.add(listener);
    }

    public void addRegisterResponseListener(RegisterResponseListener listener) {
        this.registerResponseListener = listener;
    }

    public void addSettingResponseListener(SettingResponseListener listener) {
        settingResponseListeners.add(listener);
    }
}
