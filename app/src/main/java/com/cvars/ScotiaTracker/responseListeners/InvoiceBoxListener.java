package com.cvars.ScotiaTracker.responseListeners;

import android.view.View;

import com.cvars.ScotiaTracker.UIComponents.InvoiceBox;
import com.cvars.ScotiaTracker.view.ActivityMessageInterface;

public class InvoiceBoxListener implements View.OnClickListener {

    private ActivityMessageInterface view;

    public InvoiceBoxListener(ActivityMessageInterface view) {
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
