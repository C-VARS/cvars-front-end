package com.cvars.ScotiaTracker.presenter;

import com.cvars.ScotiaTracker.model.DataModelFacade;
import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.responseListeners.SearchResponseListener;
import com.cvars.ScotiaTracker.strategy.sort.SortType;
import com.cvars.ScotiaTracker.view.InvoiceView;

import java.util.List;

public class InvoicePresenter extends FragmentPresenter implements SearchResponseListener {

    private DataModelFacade modelFacade;
    private InvoiceView invoiceView;

    public InvoicePresenter(DataModelFacade modelFacade, InvoiceView invoiceView) {
        this.modelFacade = modelFacade;
        this.invoiceView = invoiceView;
        modelFacade.setInvoiceResponseListener(this);
    }

    @Override
    public void onDestroy() {
        invoiceView = null;
        modelFacade = null;
    }

    public void executeSearch(String search){
        List<Invoice> result = modelFacade.executeSearch(search);
        updateSearch(result);
    }

    public void setSortStrategy(int index){
        switch(index){
            case 0:
                modelFacade.setSortStrategy(SortType.NEWEST);
                break;
            case 1:
                modelFacade.setSortStrategy(SortType.STATUS);
                break;
            case 2:
                modelFacade.setSortStrategy(SortType.OLDEST);
                break;
        }
    }

    private void updateSearch(List<Invoice> invs){
        invoiceView.updateScroller(invs);
    }

    @Override
    public void notifyInvoiceResponse() {
        executeSearch("");
    }
}
