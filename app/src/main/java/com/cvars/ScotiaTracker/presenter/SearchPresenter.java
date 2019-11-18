package com.cvars.ScotiaTracker.presenter;

import com.cvars.ScotiaTracker.model.DataModelFacade;
import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.responseListeners.SearchResponseListener;
import com.cvars.ScotiaTracker.view.SearchView;

import java.util.List;

public class SearchPresenter extends FragmentPresenter implements SearchResponseListener {

    private DataModelFacade modelFacade;
    private SearchView searchView;

    public SearchPresenter(DataModelFacade modelFacade, SearchView searchView) {
        this.modelFacade = modelFacade;
        this.searchView = searchView;
        modelFacade.setSearchResponseListener(this);
    }

    @Override
    public void onDestroy() {
        searchView = null;
        modelFacade = null;
    }

    public void initializeScroller() {
        // Get users invoices from modelFacade and have notifyInvoiceResponse() populate the scroller
        modelFacade.requestAllInvoices();
    }
    // initializeScroller eventually has a call to notifyInvoiceResponse()
    @Override
    public void notifyInvoiceResponse() {
        // when our SearchResponseListener receives a response - pull the list of returned Invoices
        // from modelFacade
        List<Invoice> invoices = modelFacade.getInvoices();
        searchView.updateScroller(invoices);
    }
}
