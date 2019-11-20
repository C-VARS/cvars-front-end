package com.cvars.ScotiaTracker.responseListeners;

import android.util.Log;
import android.view.View;

import com.cvars.ScotiaTracker.UIComponents.InvoiceBox;
import com.cvars.ScotiaTracker.view.UserActivityView;

public class InvoiceBoxListener implements View.OnClickListener {

    private UserActivityView view;

    public InvoiceBoxListener(UserActivityView view) {
        this.view = view;
    }

    @Override
    public void onClick(View v) {
        InvoiceBox box = (InvoiceBox)v;
        view.displayInvoice(box.getInvoiceId());
    }

    public void onDestroy(){
        view = null;
    }
}
