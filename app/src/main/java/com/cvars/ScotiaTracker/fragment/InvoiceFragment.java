package com.cvars.ScotiaTracker.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.UIComponents.InvoicesScroller;
import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.presenter.FragmentPresenter;
import com.cvars.ScotiaTracker.presenter.InvoicePresenter;
import com.cvars.ScotiaTracker.view.InvoiceView;
import com.cvars.ScotiaTracker.view.UserActivityView;

import java.util.List;

public class InvoiceFragment extends Fragment implements InvoiceView {

    private InvoicePresenter invoicePresenter;

    private View rootView;
    private SearchView searchBar;
    private InvoicesScroller invoicesScroller;
    private View.OnClickListener invoiceListener;
    private SearchView.OnQueryTextListener searchListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Set up rootView
        rootView = inflater.inflate(R.layout.fragment_search, container, false);

        //set up scoll container to display in-process invoices
        FrameLayout scrollContainer = rootView.findViewById(R.id.scrollerContainer);
        invoicesScroller = new InvoicesScroller(scrollContainer.getContext(), invoiceListener);
        scrollContainer.addView(invoicesScroller);

        initializeSearchListener();

        return rootView;
    }

    private void initializeSearchListener() {
        searchListener = new SearchListener();

        this.searchBar = rootView.findViewById(R.id.searchBar);
        this.searchBar.setOnQueryTextListener(searchListener);
    }

    private class SearchListener implements SearchView.OnQueryTextListener {

        private InvoiceView view;

        @Override
        public boolean onQueryTextSubmit(String newText) {
            if (newText != null) {
                invoicePresenter.executeSearch(newText);
            }
            return true;
        }


        @Override
        public boolean onQueryTextChange(String newText) {
            if (newText != null) {
                invoicePresenter.executeSearch(newText);
            }
            return true;
        }

        public void onDestroy() {
            this.view = null;
        }
    }

    public void setInvoiceListeners(View.OnClickListener invoiceListener) {
        this.invoiceListener = invoiceListener;
    }

    @Override
    public void setPresenter(FragmentPresenter presenter) {
        invoicePresenter = (InvoicePresenter) presenter;
    }

    @Override
    public void updateScroller(List<Invoice> invoices) {
        invoicesScroller.clearInvoiceBoxes();
        invoicesScroller.initializeWithInvoices(invoices);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
