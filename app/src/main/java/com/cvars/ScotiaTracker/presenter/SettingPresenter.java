package com.cvars.ScotiaTracker.presenter;

import com.cvars.ScotiaTracker.model.DataModelFacade;
import com.cvars.ScotiaTracker.responseListeners.SettingResponseListener;
import com.cvars.ScotiaTracker.view.SettingView;

public class SettingPresenter extends FragmentPresenter implements SettingResponseListener {

    private DataModelFacade modelFacade;
    private SettingView settingView;

    public SettingPresenter(DataModelFacade modelFacade, SettingView settingview) {
        this.modelFacade = modelFacade;
        this.settingView = settingview;
        modelFacade.setSettingResponseListener(this);
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
