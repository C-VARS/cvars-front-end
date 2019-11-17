package com.cvars.ScotiaTracker.presenter;

import com.cvars.ScotiaTracker.model.DataModelFacade;
import com.cvars.ScotiaTracker.responseListeners.SearchResponseListener;
import com.cvars.ScotiaTracker.view.SearchView;

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
        // TODO: Update information about a an invoice
    }
}
