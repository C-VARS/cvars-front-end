package com.cvars.scarface.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cvars.scarface.R;
import com.cvars.scarface.model.User;
import com.cvars.scarface.networkComms.LoginPresenter;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static MainActivity test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test = this;
    }


    public void login(View view) {
        EditText usernameText = (EditText) findViewById(R.id.username);
        EditText passwordText = (EditText) findViewById(R.id.password);
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        LoginPresenter l = new LoginPresenter();

        try {
            l.loginAsUser(username, password);
            displayLoginToast(view, "Login Successful");

        } catch (LoginPresenter.LoginError loginError) {
            displayLoginToast(view, "Login Unsuccessful");
            loginError.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayLoginToast(View view, final String toastText){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    public static MainActivity getTest(){
        return test;
    }


}
