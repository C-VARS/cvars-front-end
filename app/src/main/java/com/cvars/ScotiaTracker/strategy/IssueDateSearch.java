package com.cvars.ScotiaTracker.strategy;

import com.cvars.ScotiaTracker.model.pojo.Invoice;

import java.util.ArrayList;
import java.util.List;

public class IssueDateSearch implements SearchStrategy {
    @Override
    public List<Invoice> search(List<Invoice> invoices, String searchAttribute) {
        List<Invoice> li = new ArrayList<>();
        for (Invoice i: invoices) {
            String sid = i.getIssuedDate();
            if (sid.contains(searchAttribute)){
                li.add(i);
            }
        }
        return li;
    }
}
