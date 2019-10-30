package com.cvars.ScotiaTracker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.presenter.LoginPresenter;

public class MainActivity extends AppCompatActivity implements LoginPresenter.LoginView {

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginPresenter = new LoginPresenter(this);
    }

    public void login(View view) {
        EditText usernameText = (EditText) findViewById(R.id.username);
        EditText passwordText = (EditText) findViewById(R.id.password);
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        loginPresenter.attemptLogin(username, password);
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayMessage(String message) {
        showToast(message);
    }

    @Override
    public void changeToInvoiceActivity(LoginPresenter.UserType type, String username) {
        showToast("Logged in as " + type + " with username " + username);
    }
}
