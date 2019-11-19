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
import com.cvars.ScotiaTracker.UIComponents.InvoicesScroller;
import com.cvars.ScotiaTracker.activity.UserActivity;
import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.presenter.FragmentPresenter;
import com.cvars.ScotiaTracker.presenter.SearchPresenter;
import com.cvars.ScotiaTracker.view.SearchView;
import com.cvars.ScotiaTracker.view.UserActivityView;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements SearchView {

    private SearchPresenter searchPresenter;

    private InvoicesScroller invoicesScroller;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        FrameLayout scrollContainer = view.findViewById(R.id.scrollerContainer);

        invoicesScroller = new InvoicesScroller(scrollContainer.getContext(), (UserActivity) this.getActivity());
        scrollContainer.addView(invoicesScroller);

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

    @Override
    public void updateScroller(List<Invoice> invoices) {
        invoicesScroller.initializeWithInvoices(invoices);
    }
}
