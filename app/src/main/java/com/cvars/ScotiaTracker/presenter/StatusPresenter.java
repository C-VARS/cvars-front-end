package com.cvars.ScotiaTracker.presenter;

import com.cvars.ScotiaTracker.model.DataModelFacade;
import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.responseListeners.InvoiceResponseListener;
import com.cvars.ScotiaTracker.view.IndividualInvoiceView;
import com.cvars.ScotiaTracker.model.pojo.UserType;

public class StatusPresenter extends FragmentPresenter implements InvoiceResponseListener {

    private DataModelFacade modelFacade;
    private IndividualInvoiceView view;

    public StatusPresenter(DataModelFacade modelFacade, IndividualInvoiceView view) {
        this.modelFacade = modelFacade;
        this.view = view;
        modelFacade.addInvoiceResponseListener(this);
    }

    public void updateStatus(int invoiceID, String status){
        modelFacade.updateStatus(invoiceID, status);
    }

    @Override
    public void onDestroy() {
        modelFacade = null;
    }

    @Override
    public void notifyInvoiceResponse() {
        int id = view.getCurrentInvoiceNum();

        if (id != -1){
            Invoice inv = modelFacade.getInvoice(id);
            view.updateFields(inv);
        }
    }

    public UserType getUserType(){return modelFacade.getUserType();}
}

