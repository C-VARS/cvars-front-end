package com.cvars.ScotiaTracker.presenter;

import com.cvars.ScotiaTracker.model.LoginModel;
import com.cvars.ScotiaTracker.responseHandlers.LoginResponseHandler;
import com.cvars.ScotiaTracker.view.LoginView;

/**
 * The Presenter for the login functionality. Implements LoginResponderHandler to respond to HTTP
 * results from the model.
 */
public class LoginPresenter implements LoginResponseHandler {

    /**
     * A reference to the UI that implements LoginView interface
     */
    private LoginView view;

    /**
     * A reference to the data model and connection object for the Login functionality
     */
    private LoginModel loginModel;

    /**
     * Constructor for a LoginPresenter that interacts with both the UI view and the Model data
     *
     * @param view reference injection of a UI view that this presenter will manipulate
     */
    public LoginPresenter(LoginView view) {
        this.view = view;
        loginModel = new LoginModel(this);
    }

    /**
     * Asks the loginModel to attempt a login with given username and password
     * @param username username that the user inputted
     * @param password password that the user inputted
     */
    public void attemptLogin(String username, String password) {
        loginModel.attemptLogin(username, password);
    }

    /**
     * Updates the view based on the information in the LoginModel
     */
    @Override
    public void notifyLoginResponse() {
        if (loginModel.isLoginSuccess()) {
            view.changeToInvoiceActivity(loginModel.getUserType(), loginModel.getUsername());
        } else {
            view.displayErrorMessage(loginModel.getErrorMessage());
        }
    }

}
