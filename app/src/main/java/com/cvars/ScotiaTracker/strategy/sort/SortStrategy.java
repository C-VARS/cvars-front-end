package com.cvars.ScotiaTracker.strategy.sort;

import com.cvars.ScotiaTracker.model.pojo.Invoice;

import java.util.List;

public interface SortStrategy{


    List<Invoice> sort(List<Invoice> invoice);
}
