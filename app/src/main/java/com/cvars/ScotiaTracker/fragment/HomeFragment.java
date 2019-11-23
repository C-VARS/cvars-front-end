package com.cvars.ScotiaTracker.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.UIComponents.InvoiceBox;
import com.cvars.ScotiaTracker.UIComponents.InvoicesScroller;
import com.cvars.ScotiaTracker.activity.UserActivity;
import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.presenter.FragmentPresenter;
import com.cvars.ScotiaTracker.presenter.HomePresenter;
import com.cvars.ScotiaTracker.presenter.SearchPresenter;
import com.cvars.ScotiaTracker.view.HomeView;

import java.util.List;

public class HomeFragment extends Fragment implements HomeView {

    private HomePresenter homePresenter;

    private InvoicesScroller invoicesScroller;
    private View.OnClickListener invoiceListener;

    private TextView welcomeMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.fragment_home, container, false);

        // setup in-progress invoices invoicescroller
        FrameLayout scrollContainer = view.findViewById(R.id.inProgressScroller);
        invoicesScroller = new InvoicesScroller(scrollContainer.getContext(), invoiceListener);

        scrollContainer.addView(invoicesScroller);

        // welcome message
        welcomeMessage = view.findViewById(R.id.welcomeMsg);
        homePresenter.setWelcomeMessage();

        return view;
    }


    public void setInvoiceListener(View.OnClickListener listener){
        this.invoiceListener = listener;
    }

    @Override
    public void setPresenter(FragmentPresenter presenter) {
        homePresenter = (HomePresenter) presenter;
    }


    @Override
    public void updateScroller(List<Invoice> invoices) {
        invoicesScroller.clearInvoiceBoxes();
        invoicesScroller.initializeWithInvoices(invoices);
    }

    @Override
    public void updateMessage(String message) {
        welcomeMessage.setText(message);
    }

}
