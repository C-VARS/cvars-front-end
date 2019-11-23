package com.cvars.ScotiaTracker.model;

import com.cvars.ScotiaTracker.model.pojo.Invoice;

import java.util.ArrayList;
import java.util.List;

public class IdSearch implements SearchStrategy {
    @Override
    public List<Invoice> search(List<Invoice> invoices, String searchAttribute) {
        List<Invoice> li = new ArrayList<>();
        for (Invoice i: invoices) {
            String sid = Integer.toString(i.getInvoiceId());
            if (sid.contains(searchAttribute)){
                li.add(i);
            }
        }
        return li;
    }
}
