package com.cvars.ScotiaTracker.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.model.pojo.Order;
import com.cvars.ScotiaTracker.model.pojo.OrderStatus;
import com.cvars.ScotiaTracker.model.pojo.UserType;
import com.cvars.ScotiaTracker.presenter.FragmentPresenter;
import com.cvars.ScotiaTracker.presenter.StatusPresenter;
import com.cvars.ScotiaTracker.view.IndividualInvoiceView;
import com.google.firebase.firestore.core.OrderBy;

import java.util.List;

public class IndividualInvoiceFragment extends Fragment implements IndividualInvoiceView {

    private View view;
    private Invoice invoice;
    private StatusPresenter statusPresenter;
    private UserType userType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("Invoice", "You created new invoice page");
        this.view = inflater.inflate(R.layout.single_invoice, container, false);
        Button button = view.findViewById(R.id.payNow);

        if(userType == UserType.DRIVER && invoice.getOrderStatus().toString().equals("Pending")){
            button.setText("Notify Delivery");
        }
        else if (userType == UserType.DRIVER){
            button.setText("Confirm Arrival");
        }

        else if (userType == UserType.CUSTOMER){
            button.setText("Pay Now");
        }

        else{
            button.setVisibility(View.GONE);
        }

        // Set up the button to change the status for the selected invoice
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(userType == UserType.DRIVER && invoice.getOrderStatus().toString().equals("Pending")){
                    updateOnTheWay();
                }
                else if (userType == UserType.DRIVER){
                    updateArrived();
                }

                else{
                    updatePay();
                }
            }
        });
        return view;
    }

    @Override
    public void setPresenter(FragmentPresenter presenter){
        statusPresenter = (StatusPresenter) presenter;
        userType = statusPresenter.getUserType();
    }

    @Override
    public void onDestroy() {
        view = null;
        super.onDestroy();
    }

    /**
     * Populate the screen with the necessary information, given by <invoice>
     * @param invoice
     */
    public void updateFields(Invoice invoice){
        // Save this invoice
        this.invoice = invoice;

        // Table to populate
        TableLayout table = view.findViewById(R.id.invoiceTable);
        table.removeAllViews();


        // Populate table with rows of order information
        List<Order> orders = invoice.getOrders();
        for (Order order: orders){
            TableRow row = new TableRow(view.getContext());
            row.setGravity(Gravity.CENTER);
            row.setPadding(0, 50, 0, 50);

            // Set up new cells
            TextView item = new TextView(view.getContext());
            TextView amount = new TextView(view.getContext());
            TextView price = new TextView(view.getContext());
            TextView subtotal = new TextView(view.getContext());

            item.setPadding(0, 0, 0, 5);
            item.setGravity(Gravity.START);
            amount.setPadding(30, 5, 30, 5);
            amount.setGravity(Gravity.CENTER);
            price.setPadding(5, 5, 5, 5);
            price.setGravity(Gravity.CENTER);
            subtotal.setPadding(5, 5, 5, 5);
            subtotal.setGravity(Gravity.RIGHT);

            // Fill in the cells
            item.setText(order.getName());
            amount.setText(Integer.toString(order.getQuantity()));
            price.setText(Double.toString(order.singleItemPrice()));
            subtotal.setText(Double.toString(order.getTotalPrice()));

            // add the cells on to the row
            row.addView(item);
            row.addView(amount);
            row.addView(price);
            row.addView(subtotal);

            table.addView(row);
        }

        // Fill in invoiceID
        ((TextView) view.findViewById(R.id.invoiceNum)).setText(Integer.toString(invoice.getInvoiceId()));
        ((TextView) view.findViewById(R.id.totalPrice)).setText(Double.toString(invoice.getTotalCost()));
        ((TextView) view.findViewById(R.id.status)).setText(invoice.getOrderStatus().toString());
    }

    public int getCurrentInvoiceNum(){
        return Integer.parseInt(((TextView) view.findViewById(R.id.invoiceNum)).getText().toString());
    }

    @Override
    public void updateOnTheWay() {
        int invoiceID = this.invoice.getInvoiceId();
        statusPresenter.updateStatus(invoiceID, "onTheWay");
    }

    @Override
    public void updateArrived() {
        int invoiceID = this.invoice.getInvoiceId();

        statusPresenter.updateStatus(invoiceID, "arrived");
    }

    @Override
    public void updatePay() {
        int invoiceID = this.invoice.getInvoiceId();
        statusPresenter.updateStatus(invoiceID, "payment");
    }
}
