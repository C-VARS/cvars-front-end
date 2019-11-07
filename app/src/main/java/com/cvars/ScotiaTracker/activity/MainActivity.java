package com.cvars.ScotiaTracker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.model.User;
import com.cvars.ScotiaTracker.model.UserType;
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
    private EditText password;
    private EditText username;

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
            Log.d("Testing", "Denied!");
            return;
        }

        this.username = findViewById(R.id.username);
        this.password = findViewById(R.id.password);
        String username = this.username.getText().toString();
        String password = this.password.getText().toString();

        loginPresenter.attemptLogin(username, password);
        requestProcessed = false;
    }

    /**
     * Displays a Toast text to notify the user of some information
     *
     * @param message The string that is meant to be displayed
     */
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * An overridden interface method for this UI to display a message
     * @param message The string that is meant to be displayed
     */
    @Override
    public void displayErrorMessage(String message) {
        this.password.setError(message);
        requestProcessed = true;
    }

    /**
     * This method is called by LoginPresenter to switch to HomeActivity after a successful
     * Login
     * @param type the type of logged in User
     * @param username username of logged in User
     */
    @Override
    public void changeToHomeActivity(User user) {
        //showToast("Logged in as " + user. + " with username " + username);
        requestProcessed = true;
        Intent myIntent = new Intent(this, HomeActivity.class);
        startActivity(myIntent);
    }
}
