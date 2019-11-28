package com.cvars.ScotiaTracker.model;

import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.strategy.search.SearchStrategy;
import com.cvars.ScotiaTracker.strategy.sort.SortStrategy;

import java.util.List;

public class SortModel {
    private SortStrategy sortStrategy;

    void setSort(SortStrategy SortStrategy) {
        this.sortStrategy = SortStrategy;
    }

    List<Invoice> executeSort(List<Invoice> invoices) {
        return this.sortStrategy.sort(invoices);
    }

}
