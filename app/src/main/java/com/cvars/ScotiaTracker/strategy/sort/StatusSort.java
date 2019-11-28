package com.cvars.ScotiaTracker.strategy.sort;

import com.cvars.ScotiaTracker.model.pojo.Invoice;

import java.util.ArrayList;
import java.util.List;

public class StatusSort implements SortStrategy{

    @Override
    public List<Invoice> sort(List<Invoice> invoices) {
        List<Invoice> arrivedInvs = new ArrayList<>();
        List<Invoice> otwInvs = new ArrayList<>();
        List<Invoice> pendingInvs = new ArrayList<>();
        List<Invoice> paidInvs = new ArrayList<>();
        for (Invoice inv: invoices) {
            String status = inv.getOrderStatus().toString();
            if (status.equals("Arrived") ) {
                arrivedInvs.add(inv);
            } else if (status.equals("On The Way")) {
                otwInvs.add(inv);
            } else if (status.equals("Pending")) {
                pendingInvs.add(inv);
            } else {
                paidInvs.add(inv);
            }
        }
        arrivedInvs.addAll(otwInvs);
        arrivedInvs.addAll(pendingInvs);
        arrivedInvs.addAll(paidInvs);
        return arrivedInvs;
    }
}
