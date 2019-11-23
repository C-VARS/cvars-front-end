package com.cvars.ScotiaTracker.presenter;

import com.cvars.ScotiaTracker.model.DataModelFacade;
import com.cvars.ScotiaTracker.responseListeners.SearchResponseListener;
import com.cvars.ScotiaTracker.responseListeners.SettingResponseListener;
import com.cvars.ScotiaTracker.view.HomeView;

public class HomePresenter extends FragmentPresenter implements SettingResponseListener {
    private DataModelFacade modelFacade;
    private HomeView homeView;

    public HomePresenter(DataModelFacade modelFacade, HomeView homeView) {
        this.modelFacade = modelFacade;
        this.homeView = homeView;
//        modelFacade.setSearchResponseListener(this);
        modelFacade.setSettingResponseListener(this);
    }

    @Override
    public void onDestroy() {
        homeView = null;
        modelFacade = null;
    }

//    @Override
//    public void notifyInvoiceResponse() {
//        // On the async response of Invoice, update the scroller
//        homeView.updateScroller(modelFacade.getInvoices());
//    }


    public void setWelcomeMessage() {
        // set the welcome message to Welcome Name
        modelFacade.requestUserInfo();
    }

    @Override
    public void notifySettingResponse() {
        homeView.updateMessage("Welcome " + modelFacade.getUser().getName());
    }
}
