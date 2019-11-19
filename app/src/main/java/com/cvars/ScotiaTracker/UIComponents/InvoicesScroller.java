package com.cvars.ScotiaTracker.UIComponents;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.model.DataModelFacade;
import com.cvars.ScotiaTracker.model.InvoiceModel;
import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.model.pojo.UserType;
import com.cvars.ScotiaTracker.view.ScrollerView;
import com.cvars.ScotiaTracker.view.UserActivityView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class InvoicesScroller extends ScrollView implements ScrollerView {

    private final Context context;
    private LinearLayout linearLayout;
    private List<InvoiceBox> invoices;
    private UserActivityView userActivityView;

    public InvoicesScroller(Context context, UserActivityView userActivityView) {
        super(context);
        this.context = context;
        this.userActivityView = userActivityView;
        initView();
    }

    private void initView() {
        // initialize the ScrollView with LinearLayout nested inside it
        setBackgroundColor(Color.TRANSPARENT);
        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));


        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.TOP);
        // add linearLayout to scroll view
        this.addView(linearLayout);

        invoices = new ArrayList<>();
    }

    public void addInvoiceBox(InvoiceBox inv) {
        // Adds an invoice to linearLayout as a InvoiceBox view
        linearLayout.addView(inv);

        invoices.add(inv);
    }

    public void removeInvoiceBox(InvoiceBox inv) {
        // Removes InvoiceBox instance from view
        linearLayout.removeView(inv);
    }

    public void initializeWithInvoices(List<Invoice> invs) {
        //  initializes with list of invoices - POJO
        for (Invoice inv : invs) {
            addInvoiceBox(new InvoiceBox(context, inv, this));
        }

    }

    public void initializeWithInvoiceBoxes(List<InvoiceBox> invs) {
        //  initializes with list of invoices - View objects
        for (InvoiceBox inv : invs) {
            addInvoiceBox(inv);
        }

    }

    public void clearInvoiceBoxes() {
        // Deletes all invoice boxes
        for (InvoiceBox inv : invoices) {
            removeInvoiceBox(inv);
        }
    }

    public void displayInvoice(int invoiceID){
        this.userActivityView.displayInvoice(invoiceID);
    }
}