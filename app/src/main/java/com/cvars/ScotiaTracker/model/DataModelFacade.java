package com.cvars.ScotiaTracker.model;

import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.model.pojo.User;
import com.cvars.ScotiaTracker.model.pojo.UserType;
import com.cvars.ScotiaTracker.view.UserActivityView;

public class DataModelFacade implements InvoiceModel.InvoiceResponseListener,
                                        UserModel.UserResponseListener {
    private String username;
    private String password;
    private UserType userType;

    private InvoiceModel invoiceModel;
    private UserModel userModel;

    private UserActivityView userActivityView;

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

    public void requestInvoices() {
        userActivityView.showLoading();
        invoiceModel.requestInvoices(username);
    }

    public void requestUserInfo() {
        userActivityView.showLoading();
        userModel.requestUser(username);
    }

    @Override
    public void notifyInvoiceResponse(InvoiceModel.InvoiceAction action) {
        userActivityView.finishLoading();
    }

    @Override
    public void notifyUserAction(UserModel.UserAction action) {
        userActivityView.finishLoading();
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
}
