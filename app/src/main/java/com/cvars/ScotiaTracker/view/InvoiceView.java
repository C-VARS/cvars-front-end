package com.cvars.ScotiaTracker.view;

import com.cvars.ScotiaTracker.model.pojo.Invoice;

import java.util.List;
import java.util.Map;

public interface InvoiceView extends FragmentView {
       void updateScroller(List<Invoice> invoices);
       boolean searchable();
       String getSearchAttribute();
}
