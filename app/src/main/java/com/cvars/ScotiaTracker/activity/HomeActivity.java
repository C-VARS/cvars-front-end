package com.cvars.ScotiaTracker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.model.UserType;
import com.cvars.ScotiaTracker.view.MenuView;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity implements MenuView {

    private TabLayout menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.menu = findViewById(R.id.menu);
        int[] widgets= {R.drawable.ic_home, R.drawable.ic_clipboard_notes, R.drawable.ic_search_alt, R.drawable.ic_cog};

        for (int i = 0; i<this.menu.getTabCount(); i++){
            this.menu.getTabAt(i).setIcon(widgets[i]);
        }
    }

    @Override
    public void changeToHomeActivity(UserType user, String username){
        Intent myIntent = new Intent(this, HomeActivity.class);
        startActivity(myIntent);
    }

    @Override
    public void changeToInvoiceActivity(UserType user, String username){
        Intent myIntent = new Intent(this, InvoiceActivity.class);
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