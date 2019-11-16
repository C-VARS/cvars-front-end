package com.cvars.ScotiaTracker.model;

import com.google.gson.annotations.SerializedName;

public class OrderStatus {
    @SerializedName("onTheWay")
    public boolean onTheWay;
    @SerializedName("arrived")
    public boolean arrived;
    @SerializedName("payment")
    public boolean paymentProcessed;
}