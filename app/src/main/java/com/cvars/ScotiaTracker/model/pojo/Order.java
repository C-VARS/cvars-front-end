package com.cvars.ScotiaTracker.model.pojo;

import com.google.gson.annotations.SerializedName;

public class Order {
    // Represents a specific Order and quantity ordered by a SmallBusinessOwner from Supplier
    @SerializedName("item")
    private String name;

    @SerializedName("price")
    private double singleItemPrice;

    @SerializedName("amount")
    private int quantity;

    public Order(String name, double price, int quantity){
        this.name = name;
        this.singleItemPrice = price;
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return singleItemPrice * quantity;
    }

    public String getName() {
        return name;
    }

    public double singleItemPrice() {
        return singleItemPrice;
    }

    public int getQuantity() {
        return quantity;
    }
}
