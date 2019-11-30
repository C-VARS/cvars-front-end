package com.cvars.ScotiaTracker.UIComponents;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.model.pojo.UserType;
import com.cvars.ScotiaTracker.view.ScrollerView;
import com.cvars.ScotiaTracker.view.UserActivityView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class InvoicesScroller extends ScrollView implements ScrollerView {

    private final Context context;
    private LinearLayout linearLayout;
    private List<InvoiceBox> invoices;
    private View.OnClickListener invoiceListener;

    private UserType userType;

    public InvoicesScroller(Context context, View.OnClickListener invoiceListener, UserType type) {
        super(context);
        this.context = context;
        this.invoiceListener = invoiceListener;
        this.userType = type;
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
        for (Invoice invoice: invs) {
            addInvoiceBox(new InvoiceBox(context, invoice, userType, this.invoiceListener));
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
}