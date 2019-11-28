package com.cvars.ScotiaTracker.UIComponents;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.model.pojo.Invoice;

public class InvoiceBox extends CardView{

    private View boxView;

    private int invoiceId;
    private String orderStatus;
    private String issuedDate;
    private String completedDate;

    private View.OnClickListener listener;

    public InvoiceBox(Context context, Invoice inv, View.OnClickListener listener) {
        super(context);
        invoiceId = inv.getInvoiceId();
        orderStatus = inv.getOrderStatus().toString();
        issuedDate = inv.getIssuedDate();
        completedDate = inv.getCompletionDate();

        this.listener = listener;
        initView();
        setContent();
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
        TextView idText = (boxView.findViewById(R.id.idinfo)).findViewById(R.id.idText);
        TextView statusText = boxView.findViewById(R.id.otherInfo).findViewById(R.id.statusText);
        TextView issuedDate = boxView.findViewById(R.id.otherInfo).findViewById(R.id.issuedDate);
        TextView completedDate = boxView.findViewById(R.id.otherInfo).findViewById(R.id.completionDate);
        idText.setText(Integer.toString(invoiceId));
        // using the OrderStatus toString method
        statusText.setText(orderStatus);
        statusText.setTextSize(20);

        // set either completion or issued date
        if (this.completedDate != null && !this.completedDate.equals("")) {
            completedDate.setText("Finished: " + this.completedDate);
            completedDate.setTextSize(15);
        } else {
            issuedDate.setText("Issued: " + this.issuedDate);
            issuedDate.setTextSize(15);
        }
    }

    public int getInvoiceId(){
        return this.invoiceId;
    }

}
