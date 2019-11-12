package com.cvars.ScotiaTracker.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cvars.ScotiaTracker.R;

public class InvoiceFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("Invoice", "You created new invoice page");
        return inflater.inflate(R.layout.fragment_invoice, container, false);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("Invoice", "you stopped the invoice");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("Invoice", "You destroyed invoice view");
    }
}
