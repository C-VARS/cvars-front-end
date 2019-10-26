package com.cvars.scarface;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

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
        loginHelper(username, password);
    }

    private User loginHelper(String username, String password) {


        String baseUrl = "localhost:5000";

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl).build();
        ServerService service = retrofit.create(ServerService.class);

        try {
            Call<JSONObject> call = service.loginAttempt(username, password);
            Response<JSONObject> response = call.execute();
            System.out.println(response.body().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Driver(username);
    }
}
