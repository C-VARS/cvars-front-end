package com.cvars.scarface;

public class Item {
    // Represents a specific Item and quantity ordered by a SmallBusinessOwner from Supplier
    private String name;
    private double singleItemPrice;
    private int quantity;

    public Item(String name, double price, int quantity){
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
