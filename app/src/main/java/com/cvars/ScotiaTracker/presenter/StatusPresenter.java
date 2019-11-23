package com.cvars.ScotiaTracker.presenter;

import com.cvars.ScotiaTracker.model.DataModelFacade;

public class StatusPresenter extends FragmentPresenter{

    private DataModelFacade modelFacade;

    public StatusPresenter(DataModelFacade modelFacade) {
        this.modelFacade = modelFacade;
    }

    public void updateStatus(int invoiceID, String status){
        modelFacade.updateStatus(invoiceID, status);
    }

    @Override
    public void onDestroy() {
        modelFacade = null;
    }
}