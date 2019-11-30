package com.cvars.ScotiaTracker.view;

import android.view.View;

import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.model.pojo.LocationTime;

public interface IndividualInvoiceView extends FragmentView {
    void updateOnTheWay();

    void updateArrived();

    void updatePay();

    void updateFields(Invoice invoice);

    int getCurrentInvoiceNum();

    void updateMap(LocationTime lt);
}
