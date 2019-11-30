package com.cvars.ScotiaTracker.presenter;

import com.cvars.ScotiaTracker.model.DataModelFacade;
import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.responseListeners.InvoiceResponseListener;
import com.cvars.ScotiaTracker.responseListeners.RegisterResponseListener;
import com.cvars.ScotiaTracker.strategy.sort.SortType;
import com.cvars.ScotiaTracker.view.InvoiceView;
import com.cvars.ScotiaTracker.view.RegisterView;

import java.util.HashMap;
import java.util.List;

public class RegisterPresenter extends FragmentPresenter implements RegisterResponseListener {

    private DataModelFacade modelFacade;
    private RegisterView registerView;

    public RegisterPresenter(DataModelFacade modelFacade, RegisterView registerView) {
        this.modelFacade = modelFacade;
        this.registerView = registerView;
        modelFacade.addRegisterResponseListener(this);
    }

    @Override
    public void onDestroy() {
        registerView = null;
        modelFacade = null;
    }

    public void register(HashMap<String, String> registerData) {
        modelFacade.register(registerData);
    }



}
