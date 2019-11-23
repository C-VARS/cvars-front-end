package com.cvars.ScotiaTracker.view;

import com.cvars.ScotiaTracker.model.pojo.Invoice;

import java.util.List;

public interface HomeView extends FragmentView {
    public void updateScroller(List<Invoice> invoices);

    public void updateMessage(String message);
}
