package com.cvars.ScotiaTracker.UIComponents;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.cvars.ScotiaTracker.model.pojo.Invoice;

public class InvoicesScroller extends ScrollView {

    private final Context context;
    private LinearLayout linearLayout;

    public InvoicesScroller(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    private void initView() {
        // initialize the ScrollView with LinearLayout nested inside it
        setBackgroundColor(Color.TRANSPARENT);
        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        


        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.TOP);
        // add linearLayout to scroll view
        linearLayout.addView(new InvoiceBox(context, 1, "Testing 1"));
        linearLayout.addView(new InvoiceBox(context, 2, "Testing 2"));
        linearLayout.addView(new InvoiceBox(context, 3, "Testing 3"));
        this.addView(linearLayout);
    }


    public void pushInvoice(Invoice inv) {
        InvoiceBox invoiceBox = new InvoiceBox(context, inv.getInvoiceId(), "Mutating Genomes");
        linearLayout.addView(invoiceBox);
    }

    public void popInvoice() {
        linearLayout.removeViewAt(0);
    }
}
