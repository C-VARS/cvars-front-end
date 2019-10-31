package com.cvars.ScotiaTracker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.model.UserType;
import com.cvars.ScotiaTracker.presenter.LoginPresenter;
import com.cvars.ScotiaTracker.view.LoginView;

public class MainActivity extends AppCompatActivity implements LoginView {

    private LoginPresenter loginPresenter;

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
     * @param view
     */
    public void login(View view) {
        if (!requestProcessed){
            Log.d("Testing", "Denied!");
            return;
        }

        EditText usernameText = findViewById(R.id.username);
        EditText passwordText = findViewById(R.id.password);
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        loginPresenter.attemptLogin(username, password);
        requestProcessed = false;
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Display a Toast message on the screen
     * @param message
     */
    @Override
    public void displayMessage(String message) {
        showToast(message);
        requestProcessed = true;
    }

    /**
     * This method is called by LoginPresenter to switch to InvoiceActivity after a successful
     * Login
     * @param type the type of logged in User
     * @param username username of logged in User
     */
    @Override
    public void changeToInvoiceActivity(UserType type, String username) {
        showToast("Logged in as " + type + " with username " + username);
        requestProcessed = true;
        Intent myIntent = new Intent(this, InvoiceActivity.class);
        startActivity(myIntent);
    }
}
