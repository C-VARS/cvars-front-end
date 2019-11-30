package com.cvars.ScotiaTracker.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.model.pojo.Order;
import com.cvars.ScotiaTracker.presenter.FragmentPresenter;
import com.cvars.ScotiaTracker.view.FullInvoiceView;

import java.util.List;

public class FullInvoiceFragment extends Fragment implements FullInvoiceView {

    private View view;
    private Invoice invoice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("Invoice", "You created new invoice page");
        this.view = inflater.inflate(R.layout.single_invoice, container, false);
        return view;
    }

    @Override
    public void onDestroy() {
        view = null;
        super.onDestroy();
    }

    @Override
    public void setPresenter(FragmentPresenter presenter)
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
        ((TextView) view.findViewById(R.id.invoiceID)).setText(Integer.toString(invoice.getInvoiceId()));
        ((TextView) view.findViewById(R.id.issuedDate)).setText(invoice.getIssuedDate());
        ((TextView) view.findViewById(R.id.customerName)).setText(invoice.getCustomerName());
        ((TextView) view.findViewById(R.id.customerAddress)).setText(invoice.getCustomerAddress());
        ((TextView) view.findViewById(R.id.customerContact)).setText(invoice.getCustomerContact());
        ((TextView) view.findViewById(R.id.supplierName)).setText(invoice.getSupplierName());
        ((TextView) view.findViewById(R.id.supplierContact)).setText(invoice.getSupplierContact());

        // Calculate Total
        double subtotal = invoice.getTotalCost();
        ((TextView) view.findViewById(R.id.subtotalText)).setText(Double.toString(subtotal));
        ((TextView) view.findViewById(R.id.totalText)).setText(Double.toString(subtotal * 1.13));


    }
}
