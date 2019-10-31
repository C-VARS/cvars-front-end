package com.cvars.ScotiaTracker.presenter;

import com.cvars.ScotiaTracker.model.LoginModel;
import com.cvars.ScotiaTracker.responseHandlers.LoginResponseHandler;
import com.cvars.ScotiaTracker.view.LoginView;

public class LoginPresenter implements LoginResponseHandler {

    private LoginView view;
    private LoginModel loginModel;


    public LoginPresenter(LoginView view) {
        this.view = view;
        loginModel = new LoginModel(this);
    }


    public void attemptLogin(String username, String password) {
        loginModel.attemptLogin(username, password);
    }

    @Override
    public void notifyLoginResponse() {
        if (loginModel.isLoginSuccess()) {
            view.changeToInvoiceActivity(loginModel.getUserType(), loginModel.getUsername());
        } else {
            view.displayMessage(loginModel.getErrorMessage());
        }
    }

}
