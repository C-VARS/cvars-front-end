package com.cvars.ScotiaTracker.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Invoice {
    // Represents a total order made by a SmallBusinessOwner from Supplier, and delivered by Driver

    private int invoiceId;
    private List<Order> orders;
    private double totalCost;
    private Map<String, Boolean> status;

    public Invoice(int invoiceId, List<Order> orders){
        this.invoiceId = invoiceId;
        this.orders = orders;
        this.totalCost = totalCost(orders);

        // initialize status to empty. Note: order_received is assumed to be True
        this.status = new HashMap<>();
        this.status.put("order_received", true);
        this.status.put("on_the_way", false);
        this.status.put("arrived", false);
        this.status.put("payment_received", true);

    }

    
    public void updateStatus(String key, Boolean value){
        this.status.put(key, value);
    }

    /**
     * Iterates through the list of orders contained in the order and
     * adds their cost to sum.
     * @param orders a List of the orders within this order.
     * @return the total cost of all orders.
     */
    public double totalCost(List<Order> orders) {
        // sums total price of all orders
        double sum = 0.0;
        for (Order order : orders){
            sum += order.getTotalPrice();
        }
        return sum;
    }


    public int getInvoiceId() { return invoiceId;}

    public List<Order> getOrders() {
        return orders;
    }

    public int compareTo(Invoice o)
    {

    }