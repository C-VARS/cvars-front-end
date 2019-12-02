package com.cvars.ScotiaTracker.strategy.search;

import com.cvars.ScotiaTracker.model.pojo.Invoice;

import java.util.ArrayList;
import java.util.List;

public class StatusSearch implements SearchStrategy {
    @Override
    public List<Invoice> search(List<Invoice> invoices, String searchAttribute) {
        List<Invoice> li = new ArrayList<>();
        String lowerCase = searchAttribute.toLowerCase();
        for (Invoice i: invoices) {
            String sid = i.getOrderStatus().toString().toLowerCase();
            if (sid.contains(lowerCase)){
                li.add(i);
            }
        }
        return li;
    }
}
