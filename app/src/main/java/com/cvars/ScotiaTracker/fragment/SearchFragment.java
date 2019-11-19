package com.cvars.ScotiaTracker.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.UIComponents.InvoiceBox;
import com.cvars.ScotiaTracker.UIComponents.InvoicesScroller;
import com.cvars.ScotiaTracker.activity.UserActivity;
import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.presenter.FragmentPresenter;
import com.cvars.ScotiaTracker.presenter.SearchPresenter;
import com.cvars.ScotiaTracker.view.SearchView;
import com.cvars.ScotiaTracker.view.UserActivityView;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchView{

    private SearchPresenter searchPresenter;

    private View rootView;
    private InvoicesScroller invoicesScroller;
    private View.OnClickListener invoiceListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        FrameLayout scrollContainer = rootView.findViewById(R.id.scrollerContainer);
        invoicesScroller = new InvoicesScroller(scrollContainer.getContext(), invoiceListener);
        scrollContainer.addView(invoicesScroller);
        return rootView;
    }

    public void setInvoiceListener(View.OnClickListener listener){
        this.invoiceListener = listener;
    }

    @Override
    public void setPresenter(FragmentPresenter presenter) {
        searchPresenter = (SearchPresenter) presenter;
    }

    @Override
    public void updateSearchInformation() {
        // TODO: Add logic to update the search info
    }

    @Override
    public void updateScroller(List<Invoice> invoices) {
        invoicesScroller.initializeWithInvoices(invoices);
    }
}
