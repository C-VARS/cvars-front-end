package com.cvars.ScotiaTracker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.model.pojo.UserType;
import com.cvars.ScotiaTracker.presenter.LoginPresenter;
import com.cvars.ScotiaTracker.view.LoginView;

/**
 * Main activity of the android app that is a Login screen. Implements LoginView for UI manipulation
 * related to login.
 */
public class MainActivity extends AppCompatActivity implements LoginView {

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
        setContentView(R.layout.activity_main);
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
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        showLoading();
        loginPresenter.attemptLogin(username, password);
    }

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

    @Override
    protected void onStop(){
        super.onStop();
        loginPresenter.onStop();
    }
}
