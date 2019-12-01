package com.cvars.ScotiaTracker.presenter;

import com.cvars.ScotiaTracker.model.DataModelFacade;
import com.cvars.ScotiaTracker.model.RegisterModel;
import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.responseListeners.InvoiceResponseListener;
import com.cvars.ScotiaTracker.responseListeners.RegisterResponseListener;
import com.cvars.ScotiaTracker.strategy.sort.SortType;
import com.cvars.ScotiaTracker.view.InvoiceView;
import com.cvars.ScotiaTracker.view.RegisterView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterPresenter extends FragmentPresenter implements RegisterResponseListener {

    private RegisterModel registerModel;

    public RegisterPresenter(RegisterModel registerModel) {
        this.registerModel = registerModel;
    }

    @Override
    public void onDestroy() {
        registerModel = null;
    }

    public void register(Map<String, String> registerData) {
        registerModel.register(registerData);
    }



}
