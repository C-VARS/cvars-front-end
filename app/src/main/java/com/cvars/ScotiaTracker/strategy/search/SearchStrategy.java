package com.cvars.ScotiaTracker.strategy.search;

import com.cvars.ScotiaTracker.model.pojo.Invoice;

import java.util.List;

public interface SearchStrategy {


    List<Invoice> search(List<Invoice> invoice, String searchAttribute);
}
