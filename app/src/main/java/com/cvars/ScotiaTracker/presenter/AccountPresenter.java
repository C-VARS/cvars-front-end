package com.cvars.ScotiaTracker.presenter;

import com.cvars.ScotiaTracker.model.DataModelFacade;
import com.cvars.ScotiaTracker.responseListeners.AccountResponseListener;
import com.cvars.ScotiaTracker.view.SettingView;

public class AccountPresenter extends FragmentPresenter implements AccountResponseListener {

    private DataModelFacade modelFacade;
    private SettingView settingView;

    public AccountPresenter(DataModelFacade modelFacade, SettingView settingview) {
        this.modelFacade = modelFacade;
        this.settingView = settingview;
        modelFacade.addSettingResponseListener(this);
    }

    @Override
    public void onDestroy() {
        settingView = null;
        modelFacade = null;
    }

    @Override
    public void notifySettingResponse() {
        settingView.updateUserInformation(modelFacade.getUser(), modelFacade.getUsername(),
                modelFacade.getUserType());
    }
}
