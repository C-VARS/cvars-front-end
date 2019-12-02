package com.cvars.ScotiaTracker.strategy.sort;

import android.view.inputmethod.InputContentInfo;

import com.cvars.ScotiaTracker.model.pojo.Invoice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OldestSort implements SortStrategy {
    private final int RUN = 32;
    @Override
    public List<Invoice> sort(List<Invoice> invoices) {
        List<Invoice> invoicesCopy = new ArrayList<>(invoices);
        timsort(invoicesCopy, invoices.size());
        return invoicesCopy;
    }

    public void insertionSort(List<Invoice> invoices, int startIndex, int endIndex){
        for (int i = startIndex + 1; i <= endIndex; i++){
            Invoice temp = invoices.get(i);
            int j = i - 1;
            while (j >= startIndex && invoices.get(j).getActualDate().after(temp.getActualDate())){
                invoices.set(j+1, invoices.get(j));
                j--;
            }
            invoices.set(j+1, temp);
        }
    }

    public void merge(List<Invoice> invoices, int low, int mid, int high){
        int len1 = mid - low + 1;
        int len2 = high - mid;
        List<Invoice> left = new ArrayList<>(len1);
        List<Invoice> right = new ArrayList<>(len2);
        for (int i = 0; i < len1; i++){
            left.set(i, invoices.get(low + i));
        }
        for (int i = 0; i < len2; i++){
            right.set(i, invoices.get(mid + 1+ i));
        }
        int i = 0;
        int j = 0;
        int k = low;
        while (i < len1 && j < len2){
            if (left.get(i).getActualDate().before(right.get(j).getActualDate())){
                invoices.set(k, left.get(i));
                i++;
            }
            else{
                invoices.set(k, right.get(j));
                j++;
            }
            k++;
        }
        while (i < len1){
            invoices.set(k, left.get(i));
            k++;
            i++;
        }

        while (j < len2){
            invoices.set(k, right.get(j));
            j++;
            k++;
        }
    }

    public void timsort(List<Invoice> invoices, int len){
        for (int i = 0; i < len; i += RUN){
            insertionSort(invoices, i, Math.min((i+31), (len-1)));
        }

        for (int size = RUN; size < len; size = 2*size){
            for (int low = 0; low< len; low+= 2* size){
                int mid = Math.min((low+size-1), (len-1));
                int high = Math.min((low+2*size-1), (len-1));
                merge(invoices, low, mid, high);
            }
        }
    }

//    public void quicksort(List<Invoice> invoices, int startIndex, int endIndex){
//        int partition = partition(invoices, startIndex, endIndex);
//
//        if (partition -1 > startIndex){
//            quicksort(invoices, startIndex, partition-1);
//
//        }
//        if (partition+1< endIndex){
//            quicksort(invoices, partition+1, endIndex);
//        }
//    }
//
//    public int partition(List<Invoice> invoices, int startIndex, int endIndex){
//        Invoice pivot = invoices.get(endIndex);
//        for (int i = startIndex; i < endIndex; i++){
//            Date invDate = invoices.get(i).getActualDate();
//            Date pivDate = pivot.getActualDate();
//            if (invDate.before(pivDate)){
//                Invoice temp = invoices.get(startIndex);
//                invoices.set(startIndex, invoices.get(i));
//                invoices.set(i, temp);
//                startIndex ++;
//            }
//        }
//        Invoice temp = invoices.get(startIndex);
//        invoices.set(startIndex, pivot);
//        invoices.set(endIndex, temp);
//        return startIndex;
//    }
}
