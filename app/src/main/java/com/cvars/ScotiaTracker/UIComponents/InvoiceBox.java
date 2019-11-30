package com.cvars.ScotiaTracker.UIComponents;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.model.pojo.UserType;

public class InvoiceBox extends CardView {

    private View boxView;

    private String statusText;
    private int invoiceNum;
    private String orderTitle;

    private ProgressBar progressBar;

    private View.OnClickListener listener;

    public InvoiceBox(Context context, Invoice inv, View.OnClickListener listener) {
        super(context);

        initView();

        this.invoiceNum = inv.getInvoiceId();
        this.statusText = inv.getOrderStatus().toString();
        this.orderTitle = "";
        this.progressBar = boxView.findViewById(R.id.simpleProgressBar);

        this.listener = listener;

        setContent();
    }

    public String setTitle(UserType type, Invoice inv) {
        // Generates a title String for the invoice
        String driverBase = "Delivery for ";
        String supplierBase = "Order for ";
        String customerBase = "Order from ";

        switch (type){
            case DRIVER:
                return driverBase + inv.getCustomerName();

            case CUSTOMER:
                return customerBase + inv.getSupplierName();

            case SUPPLIER:
                return supplierBase + inv.getCustomerName();
        }
        return customerBase + inv.getSupplierName();

    }

    private void initView() {
        boxView = View.inflate(getContext(), R.layout.component_invoice_box, null);
        this.addView(boxView);

        setCardBackgroundColor(Color.GRAY);
        setRadius(30);
        setClickable(true);
        setOnClickListener(listener);
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 7);
        setLayoutParams(params);
    }

    public void setContent(){
        // Grab View objects from InvoiceBox
        TextView titleView = boxView.findViewById(R.id.orderTitle);
        TextView statusView = boxView.findViewById(R.id.statusText);
        TextView invoiceNumView = boxView.findViewById(R.id.invoiceNum);

        // set content of invoice box
        invoiceNumView.setText(Integer.toString(invoiceNum));
        statusView.setText(statusText);
        progressBar.setProgress(getProgress());
        titleView.setText(orderTitle);

    }

    private int getProgress() {
        // returns 1-4 representing stage of delivery. 1 = pending, 2 = on the way, 3 = arrived,
        // 4 = payment processed
        if (statusText.equals("Pending")) {
            return 1;
        }
        else if (statusText.equals("On The Way")) {
            return 2;
        }
        else if (statusText.equals("Arrived")) {
            return 3;
        }
        else if (statusText.equals("Payment Processed")) {
            return 4;
        }
        else {
            System.out.println("Incorrect statusText");
            return 0;
        }
    }

    public int getInvoiceId(){
        return this.invoiceNum;
    }

}
