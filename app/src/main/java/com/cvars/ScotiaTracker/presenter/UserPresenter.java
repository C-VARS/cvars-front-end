package com.cvars.ScotiaTracker.presenter;

import com.cvars.ScotiaTracker.model.UserModel;
import com.cvars.ScotiaTracker.responseListeners.LoginResponseListener;

/**
 * The LoginPresenter for the login functionality. Implements LoginResponderHandler to respond to HTTP
 * results from the model.
 */
public class UserPresenter implements LoginResponseListener {


    /**
     * A reference to the data model and connection object for the Login functionality
     */
    private UserModel userModel;


    /**
     * Updates the view based on the information in the LoginModel
     */
    @Override
    public void notifyResponse() {
//        if (loginModel.isLoginSuccess()) {
//            view.changeToHomeActivity(loginModel.getUserType(), loginModel.getUsername(),
//                    loginModel.getPassword());
//        } else {
//            view.displayErrorMessage(loginModel.getErrorMessage());
//        }
        System.out.println("We made a user");

    }

    /**
     * Life cycle handling methods to prevent data leak
     */
    public void onStop(){
        userModel = null;
    }

}
