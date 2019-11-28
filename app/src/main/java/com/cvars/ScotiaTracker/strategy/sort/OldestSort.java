package com.cvars.ScotiaTracker.strategy.sort;

import android.view.inputmethod.InputContentInfo;

import com.cvars.ScotiaTracker.model.pojo.Invoice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OldestSort implements SortStrategy {
    @Override
    public List<Invoice> sort(List<Invoice> invoices) {
        List<Invoice> invoicesCopy = new ArrayList<>(invoices);
        quicksort(invoicesCopy, 0, invoices.size() - 1);
        return invoicesCopy;
    }

    public void quicksort(List<Invoice> invoices, int startIndex, int endIndex){
        int partition = partition(invoices, startIndex, endIndex);

        if (partition -1 > startIndex){
            quicksort(invoices, startIndex, partition-1);

        }
        if (partition+1< endIndex){
            quicksort(invoices, partition+1, endIndex);
        }
    }

    public int partition(List<Invoice> invoices, int startIndex, int endIndex){
        Invoice pivot = invoices.get(endIndex);
        for (int i = startIndex; i < endIndex; i++){
            Date invDate = invoices.get(i).getActualDate();
            Date pivDate = pivot.getActualDate();
            if (invDate.before(pivDate)){
                Invoice temp = invoices.get(startIndex);
                invoices.set(startIndex, invoices.get(i));
                invoices.set(i, temp);
                startIndex ++;
            }
        }
        Invoice temp = invoices.get(startIndex);
        invoices.set(startIndex, pivot);
        invoices.set(endIndex, temp);
        return startIndex;
    }
}
