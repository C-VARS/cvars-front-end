package com.cvars.ScotiaTracker.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Invoice implements Comparable<Invoice> {
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


    @Override
    public int compareTo(Invoice o)
    {
       Invoice other = (Invoice) o;
       if (this.orderStatus.paymentProcessed && !o.orderStatus.paymentProcessed) {
           return 1;
       }
        if (this.orderStatus.arrived && !o.orderStatus.arrived) {
            return 1;
        }
        if (this.orderStatus.onTheWay && !o.orderStatus.onTheWay) {
            return 1;
        }
        if (this.orderStatus.paymentProcessed && o.orderStatus.paymentProcessed) {
            return 0;
        }
        if (this.orderStatus.arrived && !o.orderStatus.arrived) {
            return 0;
        }
        if (this.orderStatus.onTheWay && !o.orderStatus.onTheWay) {
            return 0;
        }

        return -1;

    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public String getDriverContact() {
        return driverContact;
    }

    public String getSupplierContact() {
        return supplierContact;
    }

    public String getIssuedDate() {
        return issuedDate;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}


