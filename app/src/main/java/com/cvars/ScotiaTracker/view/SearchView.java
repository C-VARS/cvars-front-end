package com.cvars.ScotiaTracker.view;

import com.cvars.ScotiaTracker.model.pojo.Invoice;

import java.util.List;

public interface SearchView extends FragmentView {
    // TODO: What information do we need to update the search
    void updateSearchInformation();

    void updateScroller(List<Invoice> invoices);
}
