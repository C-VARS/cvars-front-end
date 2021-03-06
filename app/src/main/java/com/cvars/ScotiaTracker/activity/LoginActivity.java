package com.cvars.ScotiaTracker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.model.pojo.UserType;
import com.cvars.ScotiaTracker.presenter.LoginPresenter;
import com.cvars.ScotiaTracker.view.LoginView;

/**
 * Main activity of the android app that is a Login screen. Implements LoginView for UI manipulation
 * related to login.
 */
public class LoginActivity extends AppCompatActivity implements LoginView {

    /**
     * A reference to the LoginPresenter that corresponds with the Login functionality
     */
    private LoginPresenter loginPresenter;

    /**
     * A flag variable used for concurrency purposes. Makes sure that the User does not give
     * duplicate input when the last input has not been processed yet.
     */
    private boolean requestProcessed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenter(this);
        requestProcessed = true;
    }

    /**
     * Login a user with input text from username and password field. On success, moves to
     * InvoiceView, on failure, displays a login failed Toast.
     * @param view the button that triggered this method
     */
    public void login(View view) {
        if (!requestProcessed){
            return;
        }

        EditText usernameField = findViewById(R.id.username);
        EditText passwordField = findViewById(R.id.password);
        String usernameFirst = usernameField.getText().toString();
        String username;
        if (usernameFirst.length() > 1){
            username = String.valueOf(usernameFirst.charAt(0)).toUpperCase() + usernameFirst.substring(1);
        }
        else if (usernameFirst.length() == 1){
            username = String.valueOf(usernameFirst.charAt(0)).toUpperCase();
        }
        else{
            username = "";
        }
        String password = passwordField.getText().toString();
        showLoading();
        loginPresenter.attemptLogin(username, password);
    }

    /**
     * Show that the login is in process by showing a login bar
     */
    private void showLoading(){
        requestProcessed = false;

        ProgressBar bar = findViewById(R.id.loginProgressBar);
        bar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void finishLoading(){

        requestProcessed = true;

        ProgressBar bar = findViewById(R.id.loginProgressBar);
        bar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


    /**
     * An overridden interface method for this UI to display a message
     * @param message The string that is meant to be displayed
     */
    @Override
    public void displayErrorMessage(String message) {
        EditText passwordField = findViewById(R.id.password);
        passwordField.setError(message);
        finishLoading();
    }

    /**
     * This method is called by LoginPresenter to switch to UserActivity after a successful
     * Login
     * @param type the type of logged in User
     * @param username username of logged in User
     */
    @Override
    public void changeToHomeActivity(UserType type, String username, String password) {
        Intent myIntent = new Intent(this, UserActivity.class);
        myIntent.putExtra("username", username);
        myIntent.putExtra("password", password);
        myIntent.putExtra("userType", type.name());
        startActivity(myIntent);
        finishLoading();
        finish();
    }

    /**
     * Switch over to <view>, which allows you to register as a new user
     * @param view
     */
    @Override
    public void changeToRegisterActivity(View view) {
        Intent myIntent = new Intent(this, RegisterActivity.class);
        startActivity(myIntent);
        finishLoading();
    }

    @Override
    protected void onDestroy() {
        loginPresenter.onDestroy();
        super.onDestroy();
    }
}
