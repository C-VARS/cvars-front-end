package com.cvars.ScotiaTracker.presenter;

import com.cvars.ScotiaTracker.model.DataModelFacade;
import com.cvars.ScotiaTracker.model.pojo.UserType;
import com.cvars.ScotiaTracker.responseListeners.InvoiceResponseListener;
import com.cvars.ScotiaTracker.responseListeners.AccountResponseListener;
import com.cvars.ScotiaTracker.view.HomeView;

public class HomePresenter extends FragmentPresenter implements AccountResponseListener, InvoiceResponseListener {
    private DataModelFacade modelFacade;
    private HomeView homeView;

    public HomePresenter(DataModelFacade modelFacade, HomeView homeView) {
        this.modelFacade = modelFacade;
        this.homeView = homeView;
        modelFacade.addInvoiceResponseListener(this);
        modelFacade.addSettingResponseListener(this);
    }

    @Override
    public void onDestroy() {
        homeView = null;
        modelFacade = null;
    }

    @Override
    public void notifySettingResponse() {
        String fullName = modelFacade.getUser().getName();
        homeView.updateMessage("Welcome " + fullName + ",");
    }

    @Override
    public void notifyInvoiceResponse() {
        homeView.updateScroller(modelFacade.getInvoices());
    }

    public UserType getUserType() {
        return modelFacade.getUserType();
    }
}
