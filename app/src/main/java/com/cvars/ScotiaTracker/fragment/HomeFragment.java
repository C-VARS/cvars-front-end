package com.cvars.ScotiaTracker.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.UIComponents.InvoiceBox;
import com.cvars.ScotiaTracker.UIComponents.InvoicesScroller;
import com.cvars.ScotiaTracker.presenter.HomePresenter;

public class HomeFragment extends Fragment {

    HomePresenter hp;

    public HomeFragment(HomePresenter hp) {
        this.hp = hp;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ConstraintLayout view = (ConstraintLayout) inflater.inflate(R.layout.fragment_home, container, false);


        InvoicesScroller invoicesScroller = new InvoicesScroller(view.getContext());
        // call invoicesScroller.pushInvoice(inv);
        invoicesScroller.setX(100);
        invoicesScroller.setY(100);
        view.addView(invoicesScroller);

        return view;
    }
}
