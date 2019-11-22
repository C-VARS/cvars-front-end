package com.cvars.ScotiaTracker.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.model.pojo.Invoice;

public class InvoiceFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.individual_invoices, container, false);
        return view;
    }

    @Override
    public void onDestroy() {
        view = null;
        super.onDestroy();
    }

    public void updateFields(Invoice invoice){

        ((TextView) view.findViewById(R.id.customerName)).setText(invoice.getCustomerName());
        ((TextView) view.findViewById(R.id.customerAddress)).setText(invoice.getCustomerAddress());
        ((TextView) view.findViewById(R.id.customerContact)).setText(invoice.getCustomerContact());
        ((TextView) view.findViewById(R.id.driverName)).setText(invoice.getDriverName());
        ((TextView) view.findViewById(R.id.driverContact)).setText(invoice.getDriverContact());
        ((TextView) view.findViewById(R.id.supplierName)).setText(invoice.getSupplierName());
        ((TextView) view.findViewById(R.id.supplierContact)).setText(invoice.getSupplierContact());
        ((TextView) view.findViewById(R.id.completionDate)).setText(invoice.getCompletionDate());
        ((TextView) view.findViewById(R.id.invoiceNum)).setText(Integer.toString(invoice.getInvoiceId()));
        ((TextView) view.findViewById(R.id.invoiceStatus)).setText(invoice.getOrderStatus().toString());

    }
}
