package com.cvars.ScotiaTracker.view;

import com.cvars.ScotiaTracker.model.UserType;
import com.cvars.ScotiaTracker.presenter.LoginPresenter;

public interface LoginView {
    void displayMessage(String message, boolean isError);

    void changeToInvoiceActivity(UserType type, String username);
}