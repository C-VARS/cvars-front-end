package com.cvars.ScotiaTracker.view;

import com.cvars.ScotiaTracker.model.User;
import com.cvars.ScotiaTracker.model.UserType;

public interface LoginView {
    void displayErrorMessage(String message);

    void changeToHomeActivity(User user);
}