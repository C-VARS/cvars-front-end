package com.cvars.ScotiaTracker.UIComponents;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.cvars.ScotiaTracker.R;

public class InvoiceBox extends FrameLayout {

    private View boxView;
    private int invoiceID;
    private String orderStatus;

    public InvoiceBox(Context context, int invoiceID, String orderStatus) {
        super(context);
        this.invoiceID = invoiceID;
        this.orderStatus = orderStatus;

        initView();
        setContent(invoiceID, orderStatus);
    }

    private void initView() {

        setBackgroundColor(Color.GRAY);
        boxView = View.inflate(getContext(), R.layout.component_invoice_box, null);

        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 6, 6);
        params.gravity = Gravity.TOP;
        boxView.setLayoutParams(params);

        addView(boxView);
    }

    public void setContent(int invoiceID, String orderStatus){
        this.invoiceID = invoiceID;
        this.orderStatus = orderStatus;
        TextView idText = boxView.findViewById(R.id.idText);
        TextView statusText = boxView.findViewById(R.id.statusText);
        idText.setText("Invoice ID: " + invoiceID);
        statusText.setText("Status: " + orderStatus);
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}
