package com.cvars.ScotiaTracker.model.pojo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    @SerializedName("infoRequestStatus")
    private boolean infoRequestStatus;

    /**
     * Iterates through the list of orders contained in the order and
     * adds their cost to sum.
     * @return the total cost of all orders.
     */
    public double getTotalCost() {
        // sums total price of all orders
        double sum = 0.0;
        for (Order order : this.orders){
            sum += order.getTotalPrice();
        }
        return sum;
    }


    /**
     * Used to sort the invoices by status
     * @param o
     * @return
     */
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

    public Date getActualDate() {
        String clippedDate = issuedDate.substring(0, issuedDate.length() - 4);
        SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
        // TODO: We should definetly make it so it just builds in the date when the invoice is built.
        Date date = new Date();
        try {
           date = formatter.parse(clippedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public String getIssuedDate() {
        return issuedDate != null? issuedDate.substring(0, issuedDate.length() - 4): "";
    }

    public String getCompletionDate() {
        return completionDate != null ? completionDate.substring(0, completionDate.length() - 4): "";
    }

    public List<Order> getOrders() {
        return orders;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public boolean getInfoRequestStatus(){
        return infoRequestStatus;
    }
}


