package com.cvars.ScotiaTracker.model.pojo;

import com.google.gson.annotations.SerializedName;

public class OrderStatus {
    @SerializedName("onTheWay")
    public boolean onTheWay;
    @SerializedName("arrived")
    public boolean arrived;
    @SerializedName("payment")
    public boolean paymentProcessed;

    @Override
    public String toString() {
        // Return String of most recent order status
        if (paymentProcessed) {
            return "Payment Processed";
        } else if (arrived) {
            return "Arrived";
        } else if (onTheWay) {
            return "On The Way";
        }
        else {
            return "Pending";
        }
    }
}
