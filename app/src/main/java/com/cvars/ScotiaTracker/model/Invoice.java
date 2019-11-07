package com.cvars.ScotiaTracker.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class Invoice {
    // Represents a total order made by a SmallBusinessOwner from Supplier, and delivered by Driver

    @SerializedName("invoiceID")
    private int invoiceId;

    @SerializedName("customerName")
    private String customerName;
    @SerializedName("driverName")
    private String driverName;
    @SerializedName("supplierName")
    private String supplierName;

    @SerializedName("customerAddress")
    private String customerAddress;

    @SerializedName("customerContact")
    private String customerContact;
    @SerializedName("driverContact")
    private String driverContact;
    @SerializedName("supplierContact")
    private String supplierContact;

    @SerializedName("issuedDate")
    private String issuedDate;
    @SerializedName("completionDate")
    private String completionDate;

    @SerializedName("orders")
    private List<Order> orders;

    @SerializedName("orderStatus")
    private OrderStatus orderStatus;

    private double totalCost;

    public Invoice(int invoiceId, List<Order> orders){
        this.invoiceId = invoiceId;
        this.orders = orders;
        this.totalCost = totalCost(orders);
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
        return 1;
    }
    class OrderStatus{
        @SerializedName("onTheWay")
        private boolean onTheWay;
        @SerializedName("arrived")
        private boolean arrived;
        @SerializedName("payment")
        private boolean paymentProcessed;
    }

}