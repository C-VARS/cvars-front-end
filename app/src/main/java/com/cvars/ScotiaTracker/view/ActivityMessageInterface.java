package com.cvars.ScotiaTracker.view;

public interface ActivityMessageInterface {
    void showLoading();

    void finishLoading();

    void displayInvoice(int invoiceID);

    void displayMessage(String message);

    void logOut();

    void showPushNotification(String message);
}