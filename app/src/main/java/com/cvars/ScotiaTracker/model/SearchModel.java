package com.cvars.ScotiaTracker.model;

import com.cvars.ScotiaTracker.model.pojo.Invoice;

import java.util.List;

public class SearchModel {
    private SearchStrategy searchStrategy;
    void setSearch(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    List<Invoice> executeSearch(List<Invoice> invoice, String searchAttribute) {
        return this.searchStrategy.search(invoice, searchAttribute);
    }

}
