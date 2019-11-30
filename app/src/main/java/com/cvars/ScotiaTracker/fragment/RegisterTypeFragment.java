package com.cvars.ScotiaTracker.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.UIComponents.InvoicesScroller;
import com.google.android.material.tabs.TabLayout;

public class RegisterTypeFragment extends Fragment {

    private View rootView;
    private Button driverButton;
    private Button customerButton;
    private Button supplierButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Set up rootView
        rootView = inflater.inflate(R.layout.fragment_register_type, container, false);
        this.driverButton = rootView.findViewById(R.id.driverButton);
        this.customerButton = rootView.findViewById(R.id.customerButton);
        this.supplierButton = rootView.findViewById(R.id.supplierButton);

        return rootView;
    }

}
