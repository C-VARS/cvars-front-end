package com.cvars.ScotiaTracker.model;

import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.strategy.search.DriverSearch;
import com.cvars.ScotiaTracker.strategy.search.IdSearch;
import com.cvars.ScotiaTracker.strategy.search.SearchStrategy;
import com.cvars.ScotiaTracker.strategy.sort.NewestSort;
import com.cvars.ScotiaTracker.strategy.sort.SortStrategy;

import java.util.List;

class SearchModel {
    private SearchStrategy searchStrategy = new IdSearch();
    private SortStrategy sortStrategy = new NewestSort();

    void setSortStrategy(SortStrategy strategy){
        this.sortStrategy = strategy;
    }

    List<Invoice> executeSearch(List<Invoice> invoices, String searchAttribute) {
        return this.searchStrategy.search(sortStrategy.sort(invoices), searchAttribute);
    }

    void setSearchStrategy(SearchStrategy strategy){
        this.searchStrategy = strategy;
    }

}
