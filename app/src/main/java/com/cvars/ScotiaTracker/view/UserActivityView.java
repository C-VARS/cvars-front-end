package com.cvars.ScotiaTracker.view;

public interface UserActivityView {
    void showLoading();

    void finishLoading();

    void displayInvoice(int invoiceID);

    void displayMessage(String message);

    void logOut();

    void showPushNotification(String message);

    void executeSearch(String searchAttribute);
}