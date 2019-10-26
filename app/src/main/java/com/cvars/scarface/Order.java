package com.cvars.scarface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Order {
    // Represents a total order made by a SmallBusinessOwner from Supplier, and delivered by Driver

    private String invoiceId;
    private List<Item> items;
    private double totalCost;
    private Map<String, Boolean> status;

    public Order(String invoiceId, List<Item> items){
        this.invoiceId = invoiceId;
        this.items = items;
        this.totalCost = totalCost(items);

        // initialize status to empty. Note: order_received is assumed to be True
        this.status = new HashMap<String, Boolean>();
        this.status.put("order_received", true);
        this.status.put("on_the_way", false);
        this.status.put("arrived", false);
        this.status.put("payment_received", true);

    }

    public void updateStatus(String key, Boolean value){
        this.status.put(key, value);
    }

    public double totalCost(List<Item> items) {
        // sums total price of all items
        double sum = 0.0;
        for (Item item : items){
            sum += item.getTotalPrice();
        }
        return sum;
    }


    public String getInvoiceId() {
        return invoiceId;
    }

    public List<Item> getItems() {
        return items;
    }

}