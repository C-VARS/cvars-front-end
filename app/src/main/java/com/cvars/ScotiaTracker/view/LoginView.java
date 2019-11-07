package com.cvars.ScotiaTracker.view;

import com.cvars.ScotiaTracker.model.UserType;

public interface LoginView {
    void displayErrorMessage(String message);

    void changeToInvoiceActivity(UserType type, String username);
}