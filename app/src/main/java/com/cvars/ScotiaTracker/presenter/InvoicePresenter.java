package com.cvars.ScotiaTracker.presenter;

import com.cvars.ScotiaTracker.model.DataModelFacade;
import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.responseListeners.InvoiceResponseListener;
import com.cvars.ScotiaTracker.strategy.search.SearchType;
import com.cvars.ScotiaTracker.strategy.sort.SortType;
import com.cvars.ScotiaTracker.view.InvoiceView;

import java.util.List;

public class InvoicePresenter extends FragmentPresenter implements InvoiceResponseListener {

    private DataModelFacade modelFacade;
    private InvoiceView invoiceView;

    public InvoicePresenter(DataModelFacade modelFacade, InvoiceView invoiceView) {
        this.modelFacade = modelFacade;
        this.invoiceView = invoiceView;
        modelFacade.addInvoiceResponseListener(this);
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

    public void setSearchStrategy(int index){
        switch(index){
            case 0:
                modelFacade.setSearchStrategy(SearchType.DRIVER);
                break;
            case 1:
                modelFacade.setSearchStrategy(SearchType.ID);
                break;
            case 2:
                modelFacade.setSearchStrategy(SearchType.CUSTOMER);
                break;
            case 3:
                modelFacade.setSearchStrategy(SearchType.SUPPLIER);
                break;
            case 4:
                modelFacade.setSearchStrategy(SearchType.ISSUE_DATE);
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
