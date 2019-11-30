package com.cvars.ScotiaTracker.view;

import android.location.LocationManager;

import com.cvars.ScotiaTracker.model.pojo.Invoice;

import java.util.List;
import java.util.Map;

public interface InvoiceView extends FragmentView {
       void updateScroller(List<Invoice> invoices);
}
