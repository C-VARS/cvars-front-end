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

    private int invoiceId;
    private String orderStatus;
    private String issuedDate;
    private String completedDate;

    public InvoiceBox(Context context, Invoice inv) {
        super(context);
        invoiceId = inv.getInvoiceId();
        orderStatus = inv.getOrderStatus().toString();
        issuedDate = inv.getIssuedDate();
        completedDate = inv.getCompletionDate();

        initView();
        setContent();
    }

    public InvoiceBox(Context context, int id, String status, String issuedDate, String completedDate) {
        super(context);
        invoiceId = id;
        orderStatus = status;
        this.issuedDate = issuedDate;
        this.completedDate = completedDate;

        initView();
        setContent();
    }
    public InvoiceBox(Context context, int id, String status, String issuedDate) {
        super(context);
        invoiceId = id;
        orderStatus = status;
        this.issuedDate = issuedDate;
        this.completedDate = "";

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
        TextView issuedDate = boxView.findViewById(R.id.issuedDate);
        TextView completedDate = boxView.findViewById(R.id.completionDate);

        idText.setText("Invoice ID: " + invoiceId);
        // using the OrderStatus toString method
        statusText.setText("Status: " + orderStatus);


        // set either completion or issued date
        if (this.completedDate != null && !this.completedDate.equals("")) {
            completedDate.setText("Finished: " + this.completedDate);
        } else {
            issuedDate.setText("Issued: " + this.issuedDate);
        }
    }

}
