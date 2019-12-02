package com.cvars.ScotiaTracker.UIComponents;

import android.content.Context;
import android.view.View.OnClickListener;

import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.model.pojo.UserType;

public class InvoiceBoxFactory {

    public InvoiceBox createInvoiceBox(Context context, Invoice inv, UserType userType, OnClickListener listener) {

        InvoiceBox box = new InvoiceBox(context, inv, listener);
        box.setTitle(userType, inv);

        return box;
    }
}
