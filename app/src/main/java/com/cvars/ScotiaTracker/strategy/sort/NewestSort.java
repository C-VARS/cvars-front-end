package com.cvars.ScotiaTracker.strategy.sort;

import com.cvars.ScotiaTracker.model.pojo.Invoice;

import java.util.Collections;
import java.util.List;

public class NewestSort implements SortStrategy {
    @Override
    public List<Invoice> sort(List<Invoice> invoice) {
        OldestSort oldSort = new OldestSort();
        List<Invoice> invoicesCopy = oldSort.sort(invoice);
        Collections.reverse(invoicesCopy);
        return invoicesCopy;
    }
}
