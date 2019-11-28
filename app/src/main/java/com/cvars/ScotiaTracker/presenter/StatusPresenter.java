package com.cvars.ScotiaTracker.presenter;

import com.cvars.ScotiaTracker.model.DataModelFacade;
import com.cvars.ScotiaTracker.view.IndividualInvoiceView;

public class StatusPresenter extends FragmentPresenter{

    private DataModelFacade modelFacade;
    private IndividualInvoiceView view;

    public StatusPresenter(DataModelFacade modelFacade, IndividualInvoiceView view) {
        this.modelFacade = modelFacade;
        this.view = view;
    }

    public void updateStatus(int invoiceID, String status){
        modelFacade.updateStatus(invoiceID, status);
    }

    @Override
    public void onDestroy() {
        modelFacade = null;
    }
}