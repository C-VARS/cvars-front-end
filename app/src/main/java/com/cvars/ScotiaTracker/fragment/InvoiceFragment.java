package com.cvars.ScotiaTracker.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.UIComponents.InvoicesScroller;
import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.presenter.FragmentPresenter;
import com.cvars.ScotiaTracker.presenter.SearchPresenter;
import com.cvars.ScotiaTracker.view.InvoiceView;

import java.util.List;

public class InvoiceFragment extends Fragment implements InvoiceView {

    private SearchPresenter searchPresenter;

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

        // set up search bar to the corresponding listener
        this.searchBar = rootView.findViewById(R.id.searchBar);
        this.searchBar.setOnQueryTextListener(searchListener);

        //set up scoll container to display in-process invoices
        FrameLayout scrollContainer = rootView.findViewById(R.id.scrollerContainer);
        invoicesScroller = new InvoicesScroller(scrollContainer.getContext(), invoiceListener);
        scrollContainer.addView(invoicesScroller);

        return rootView;
    }

    public void setInvoiceListeners(View.OnClickListener invoiceListener, SearchView.OnQueryTextListener searchListener){
        this.invoiceListener = invoiceListener;
        this.searchListener = searchListener;
    }

    @Override
    public void setPresenter(FragmentPresenter presenter) {
        searchPresenter = (SearchPresenter) presenter;
    }

    @Override
    public void updateScroller(List<Invoice> invoices) {
        invoicesScroller.clearInvoiceBoxes();
        invoicesScroller.initializeWithInvoices(invoices);
    }


    @Override
    public boolean searchable() {
        SearchView searchView = rootView.findViewById(R.id.searchBar);
        String search = searchView.toString();
        return ((search != null) && !search.isEmpty());

    }

    @Override
    public String getSearchAttribute() {
        SearchView searchView = rootView.findViewById(R.id.searchBar);
        return searchView.toString();
    }
}
