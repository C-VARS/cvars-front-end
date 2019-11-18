package com.cvars.ScotiaTracker.model;

import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.model.pojo.User;
import com.cvars.ScotiaTracker.model.pojo.UserType;
import com.cvars.ScotiaTracker.responseListeners.SearchResponseListener;
import com.cvars.ScotiaTracker.responseListeners.SettingResponseListener;
import com.cvars.ScotiaTracker.view.UserActivityView;

public class DataModelFacade implements InvoiceModel.InvoiceActionListener,
                                        UserModel.UserResponseListener {
    private String username;
    private String password;
    private UserType userType;

    private InvoiceModel invoiceModel;
    private UserModel userModel;

    private UserActivityView userActivityView;

    private SettingResponseListener settingResponseListener;
    private SearchResponseListener searchResponseListener;


    public DataModelFacade(String username, String password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;

        invoiceModel = new InvoiceModel();
        invoiceModel.setListener(this);
        userModel = new UserModel();
        userModel.setListener(this);
    }

    public void setUserActivityView(UserActivityView userActivityView) {
        this.userActivityView = userActivityView;
    }

    public void requestAllInvoices() {
        userActivityView.showLoading();
        invoiceModel.requestAllInvoices(username);
    }

    public void requestUserInfo() {
        userActivityView.showLoading();
        userModel.requestUser(username);
    }

    @Override
    public void notifyInvoiceAction(InvoiceModel.InvoiceAction action) {
        userActivityView.finishLoading();

        if (!invoiceModel.getActionSuccess()){
            userActivityView.displayMessage("Network error!");
            return;
        }

        switch(action){
            case REQUEST:
                if (!invoiceModel.getInvoices().get(0).getInfoRequestStatus()) {
                    userActivityView.displayMessage("Incorrect user information");
                }else{
                    System.out.println("Update UI by making calls here");
                }
                break;
            case UPDATE:
                break;
        }
    }

    @Override
    public void notifyUserAction(UserModel.UserAction action) {
        userActivityView.finishLoading();

        if (!userModel.getActionSuccess()){
            userActivityView.displayMessage("Network error!");
            return;
        }

        switch(action){
            case REQUEST:
                if (!userModel.getUser().getInfoRequestStatus()) {
                    userActivityView.displayMessage("Incorrect user information");
                }else{
                    if (settingResponseListener != null){
                        settingResponseListener.notifySettingResponse();
                    }
                }
                break;
            case UPDATE:
                break;
        }
    }

    public void onDestroy() {
        userActivityView = null;
    }

    public Invoice getInvoice(int invoiceID) {
        return invoiceModel.getInvoice(invoiceID);
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

    public void setSearchResponseListener(SearchResponseListener searchResponseListener) {
        this.searchResponseListener = searchResponseListener;
    }
}
