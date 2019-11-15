package com.cvars.ScotiaTracker.presenter;

import com.cvars.ScotiaTracker.model.UserModel;
import com.cvars.ScotiaTracker.responseHandlers.ResponseHandler;
import com.cvars.ScotiaTracker.view.UserView;

/**
 * The LoginPresenter for the login functionality. Implements LoginResponderHandler to respond to HTTP
 * results from the model.
 */
public class UserPresenter implements ResponseHandler {

    /**
     * A reference to the UI that implements LoginView interface
     */
    private UserView view;

    /**
     * A reference to the data model and connection object for the Login functionality
     */
    private UserModel userModel;

    /**
     * Constructor for a LoginPresenter that interacts with both the UI view and the Model data
     *
     * @param view reference injection of a UI view that this presenter will manipulate
     */
    public UserPresenter(UserView view) {
        this.view = view;
        userModel = new UserModel(this);
    }

    /**
     * Asks the loginModel to attempt a login with given username and password
     * @param username username that the user inputted
     */
    public void createUser(String username) {
        userModel.createUser(username);
    }

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
        view = null;
        userModel = null;
    }

}
