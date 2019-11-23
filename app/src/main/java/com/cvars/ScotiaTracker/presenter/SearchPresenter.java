package com.cvars.ScotiaTracker.presenter;

import com.cvars.ScotiaTracker.model.DataModelFacade;
import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.responseListeners.SearchResponseListener;
import com.cvars.ScotiaTracker.view.InvoiceView;

import java.util.List;

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

    public void updateSearch(List<Invoice> invs){
        invoiceView.updateScroller(invs);
    }

    @Override
    public void notifyInvoiceResponse() {
        invoiceView.updateScroller(modelFacade.getInvoices());
    }
}
