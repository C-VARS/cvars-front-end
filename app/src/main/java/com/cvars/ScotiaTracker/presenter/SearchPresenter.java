package com.cvars.ScotiaTracker.presenter;

import com.cvars.ScotiaTracker.model.DataModelFacade;
import com.cvars.ScotiaTracker.responseListeners.SearchResponseListener;
import com.cvars.ScotiaTracker.view.InvoiceView;

public class SearchPresenter extends FragmentPresenter implements SearchResponseListener {

    private DataModelFacade modelFacade;
    private InvoiceView invoiceView;

    public SearchPresenter(DataModelFacade modelFacade, InvoiceView invoiceView) {
        this.modelFacade = modelFacade;
        this.invoiceView = invoiceView;
        modelFacade.setSearchResponseListener(this);
    }

    @Override
    public void onDestroy() {
        invoiceView = null;
        modelFacade = null;
    }

    @Override
    public void notifyInvoiceResponse() {
        invoiceView.updateScroller(modelFacade.executeSearch("1"));
//        invoiceView.updateScroller(modelFacade.getInvoices());
    }
}
