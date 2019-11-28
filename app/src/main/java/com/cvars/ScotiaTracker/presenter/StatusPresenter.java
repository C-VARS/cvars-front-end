package com.cvars.ScotiaTracker.presenter;

import com.cvars.ScotiaTracker.model.DataModelFacade;
import com.cvars.ScotiaTracker.model.pojo.UserType;

public class StatusPresenter extends FragmentPresenter{

    private DataModelFacade modelFacade;
    private UserType userType;

    public StatusPresenter(DataModelFacade modelFacade) {
        this.modelFacade = modelFacade;
        userType = modelFacade.getUserType();
    }

    public void updateStatus(int invoiceID, String status){
        modelFacade.updateStatus(invoiceID, status);
    }

    @Override
    public void onDestroy() {
        modelFacade = null;
    }

    public UserType getUserType(){return userType;}
}

