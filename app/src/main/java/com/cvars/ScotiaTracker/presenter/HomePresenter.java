package com.cvars.ScotiaTracker.presenter;

import com.cvars.ScotiaTracker.model.DataModelFacade;
import com.cvars.ScotiaTracker.responseListeners.SearchResponseListener;
import com.cvars.ScotiaTracker.responseListeners.SettingResponseListener;
import com.cvars.ScotiaTracker.view.HomeView;

public class HomePresenter extends FragmentPresenter implements SearchResponseListener, SettingResponseListener {
    private DataModelFacade modelFacade;
    private HomeView homeView;

    public HomePresenter(DataModelFacade modelFacade, HomeView homeView) {
        this.modelFacade = modelFacade;
        this.homeView = homeView;
        modelFacade.setSearchResponseListener(this);
    }

    @Override
    public void onDestroy() {
        homeView = null;
        modelFacade = null;
    }

    @Override
    public void notifyInvoiceResponse() {
        homeView.updateScroller(modelFacade.getInvoices());
    }


    @Override
    public void notifySettingResponse() {
        System.out.println("Goat's are the worst animal after salamanders " + modelFacade.getUser().getName());
        // Display welcome message this is called after successful modelFacade.requestUserInfo()
        homeView.updateMessage("Welcome " + modelFacade.getUser().getName());
    }
}
