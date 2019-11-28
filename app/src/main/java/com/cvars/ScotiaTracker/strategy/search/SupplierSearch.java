package com.cvars.ScotiaTracker.strategy.search;

import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.strategy.search.SearchStrategy;

import java.util.ArrayList;
import java.util.List;

public class SupplierSearch implements SearchStrategy {
    @Override
    public List<Invoice> search(List<Invoice> invoices, String searchAttribute) {
        List<Invoice> li = new ArrayList<>();
        for (Invoice i: invoices) {
            String sid = i.getSupplierName();
            if (sid.contains(searchAttribute)){
                li.add(i);
            }
        }
        return li;
    }
}
