package com.cvars.ScotiaTracker.presenter;

import com.cvars.ScotiaTracker.model.LoginModel;

public class LoginPresenter {

    private LoginView view;
    private LoginModel loginModel;

    public enum UserType {
        DRIVER,
        CUSTOMER,
        SUPPLIER
    }

    public LoginPresenter(LoginView view) {
        this.view = view;
        loginModel = new LoginModel(this);
    }


    public void attemptLogin(String username, String password) {
        loginModel.attemptLogin(username, password);
    }

    public void notifyLoginResponse() {
        if (loginModel.isLoginSuccess()) {
            view.changeToInvoiceActivity(loginModel.getUserType(), loginModel.getUsername());
        } else {
            view.displayMessage(loginModel.getErrorMessage());
        }
    }

    public interface LoginView {
        void displayMessage(String message);

        void changeToInvoiceActivity(UserType type, String username);
    }

}
