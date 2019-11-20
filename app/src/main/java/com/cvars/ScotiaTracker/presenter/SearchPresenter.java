package com.cvars.ScotiaTracker.presenter;

import com.cvars.ScotiaTracker.model.DataModelFacade;
import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.responseListeners.SearchResponseListener;
import com.cvars.ScotiaTracker.view.SearchView;

import java.util.List;
import java.util.Map;

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

    @Override
    public void notifyInvoiceResponse() {
        searchView.updateScroller(modelFacade.getInvoices());
    }
}
