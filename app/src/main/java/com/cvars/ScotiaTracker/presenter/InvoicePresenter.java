package com.cvars.ScotiaTracker.presenter;

import android.util.Log;

import com.cvars.ScotiaTracker.model.InvoiceModel;
import com.cvars.ScotiaTracker.model.LoginModel;
import com.cvars.ScotiaTracker.responseHandlers.ResponseHandler;
import com.cvars.ScotiaTracker.view.LoginView;

/**
 * The InvoicePresenter for the  functionality. Implements ResponseHandler to respond to HTTP
 * results from the model.
 */
public class InvoicePresenter implements ResponseHandler {

    /**
     * A reference to the UI that implements LoginView interface
     */
    private LoginView view;

    /**
     * A reference to the data model and connection object for the Login functionality
     */
    private InvoiceModel invoiceModel;
    private LoginModel loginModel;

    /**
     * Constructor for a InvoicePresenter that interacts with both the UI view and the Model data
     *
     * @param view reference injection of a UI view that this presenter will manipulate
     */
    public InvoicePresenter(LoginView view, LoginModel loginModel) {
        this.view = view;
        this.invoiceModel = new InvoiceModel(this);
        this.loginModel = loginModel;
    }

    /**
     * Asks the invoiceModel to attempt a login with given username and password
     * @param username username that the user inputted
     */
    public void getInvoices(String username) {
        invoiceModel.getInvoices(username);
    }

    /**
     * Updates the view based on the information in the LoginModel
     */
    @Override
    public void notifyResponse() {
            System.out.println("invoice received.");
            view.changeToHomeActivity(loginModel.getUserType(), loginModel.getUsername());
    }

}
