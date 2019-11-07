package com.cvars.ScotiaTracker.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.model.UserType;
import com.cvars.ScotiaTracker.view.MenuView;

public class SearchActivity extends AppCompatActivity implements MenuView {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    @Override
    public void changeToHomeActivity(UserType user, String username){
        Intent myIntent = new Intent(this, HomeActivity.class);
        startActivity(myIntent);
    }

    @Override
    public void changeToInvoiceActivity(UserType user, String username){
        Intent myIntent = new Intent(this, SearchActivity.class);
        startActivity(myIntent);
    }

    @Override
    public void changeToSearchActivity(UserType user, String username){
        Intent myIntent = new Intent(this, SearchActivity.class);
        startActivity(myIntent);
    }

    @Override
    public void changeToSettingsActivity(UserType user, String username){
        Intent myIntent = new Intent(this, SettingsActivity.class);
        startActivity(myIntent);
    }
}
