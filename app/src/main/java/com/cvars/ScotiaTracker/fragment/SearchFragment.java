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
import com.cvars.ScotiaTracker.UIComponents.InvoicesScroller;
import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.presenter.FragmentPresenter;
import com.cvars.ScotiaTracker.presenter.SearchPresenter;
import com.cvars.ScotiaTracker.view.SearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchView {

    private SearchPresenter searchPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ConstraintLayout view = (ConstraintLayout) inflater.inflate(R.layout.fragment_search, container, false);

        // add Invoices Scroller
        InvoicesScroller invoicesScroller = new InvoicesScroller(view.getContext());


        // get all users invoices
        List<Invoice> invoices = new ArrayList<>();

        invoicesScroller.initializeWithInvoiceBoxes(invoices);

        invoicesScroller.setX(100);
        invoicesScroller.setY(100);

        view.addView(invoicesScroller);
        return view;
    }


    @Override
    public void setPresenter(FragmentPresenter presenter) {
        searchPresenter = (SearchPresenter) presenter;
    }

    @Override
    public void updateSearchInformation() {
        // TODO: Add logic to update the search info
    }
}
