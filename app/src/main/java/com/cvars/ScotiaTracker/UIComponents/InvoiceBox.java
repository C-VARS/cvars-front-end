package com.cvars.ScotiaTracker.UIComponents;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.model.pojo.Invoice;

public class InvoiceBox extends FrameLayout {

    private View boxView;

    private Invoice inv;

    public InvoiceBox(Context context, Invoice inv) {
        super(context);
        this.inv = inv;

        initView();
        setContent();
    }

    private void initView() {

        setBackgroundColor(Color.GRAY);
        boxView = View.inflate(getContext(), R.layout.component_invoice_box, null);
        this.addView(boxView);
    }

    public void setContent(){
        TextView idText = boxView.findViewById(R.id.idText);
        TextView statusText = boxView.findViewById(R.id.statusText);

        idText.setText("Invoice ID: " + inv.getInvoiceId());
        // using the OrderStatus toString method
        statusText.setText("Status: " + getOrderStatus());
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}
