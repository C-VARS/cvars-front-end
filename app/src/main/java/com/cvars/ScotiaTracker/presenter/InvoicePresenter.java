package com.cvars.ScotiaTracker.presenter;

import com.cvars.ScotiaTracker.model.InvoiceModel;
import com.cvars.ScotiaTracker.responseListeners.LoginResponseListener;
import com.cvars.ScotiaTracker.view.InvoiceView;

/**
 * The InvoicePresenter for the  functionality. Implements LoginResponseListener to respond to HTTP
 * results from the model.
 */
public class InvoicePresenter implements LoginResponseListener {

    /**
     * A reference to the data model and connection object for the Login functionality
     */
    private InvoiceModel invoiceModel;
    private InvoiceView invoiceView;

    /**
     * Constructor for a InvoicePresenter that interacts with both the UI view and the Model data
     *
     * @param view reference injection of a UI view that this presenter will manipulate
     */
    public InvoicePresenter(InvoiceView view, InvoiceModel invoiceModel) {
        this.invoiceView = view;
        this.invoiceModel = invoiceModel;
    }


    public void getInvoices(int invoiceID) {

    }

    /**
     * Updates the view based on the information in the  invoiceModel
     */
    @Override
    public void notifyResponse() {
            System.out.println("invoice received.");   
    }

}
