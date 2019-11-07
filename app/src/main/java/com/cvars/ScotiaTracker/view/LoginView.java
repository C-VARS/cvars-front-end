package com.cvars.ScotiaTracker.view;

import com.cvars.ScotiaTracker.model.UserType;

public interface LoginView {
    void displayErrorMessage(String message);

    void changeToHomeActivity(UserType type, String username);
}