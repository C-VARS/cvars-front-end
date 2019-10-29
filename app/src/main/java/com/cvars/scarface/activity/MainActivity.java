package com.cvars.scarface.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cvars.scarface.R;
import com.cvars.scarface.model.User;
import com.cvars.scarface.networkComms.LoginPresenter;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void login(View view) {
        EditText usernameText = (EditText) findViewById(R.id.username);
        EditText passwordText = (EditText) findViewById(R.id.password);
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        LoginPresenter l = new LoginPresenter();
        l.loginAsUser(username, password);

    }

    public void displayLoginToast(final Context context, final String toastText){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(context, toastText, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }


}
