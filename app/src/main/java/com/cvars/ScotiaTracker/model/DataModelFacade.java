package com.cvars.ScotiaTracker.model;

import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.model.pojo.UserType;

public class DataModelFacade{
    private String username;
    private String password;
    private UserType userType;

    private InvoiceModel invoiceModel;
    private UserModel userModel;

    public DataModelFacade(String username, String password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;

        invoiceModel = new InvoiceModel();
        userModel = new UserModel();
        invoiceModel.requestInvoices(username);
        userModel.requestUser(username);
    }


}
