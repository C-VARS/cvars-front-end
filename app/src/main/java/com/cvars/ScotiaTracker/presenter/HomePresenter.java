package com.cvars.ScotiaTracker.presenter;

import com.cvars.ScotiaTracker.model.DataModelFacade;
import com.cvars.ScotiaTracker.responseListeners.InvoiceResponseListener;
import com.cvars.ScotiaTracker.responseListeners.SettingResponseListener;
import com.cvars.ScotiaTracker.view.HomeView;

public class HomePresenter extends FragmentPresenter implements SettingResponseListener, InvoiceResponseListener {
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

    public void setWelcomeMessage() {
        // set the welcome message to Welcome Name
        modelFacade.requestUserInfo();
    }

    @Override
    public void notifySettingResponse() {
        homeView.updateMessage("Welcome " + modelFacade.getUser().getName());
    }

    @Override
    public void notifyInvoiceResponse() {
        homeView.updateScroller(modelFacade.getInvoices());
    }
}
