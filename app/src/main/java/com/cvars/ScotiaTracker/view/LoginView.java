package com.cvars.ScotiaTracker.view;

import android.view.View;

import com.cvars.ScotiaTracker.model.pojo.UserType;

public interface LoginView {
    void displayErrorMessage(String message);

    void changeToHomeActivity(UserType type, String username, String password);

    void changeToRegisterActivity(View view);

}