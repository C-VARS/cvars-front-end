package com.cvars.ScotiaTracker.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cvars.ScotiaTracker.R;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {

    private TabLayout menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.menu = findViewById(R.id.menu);
        int[] widgets= {R.mipmap.scotialogo_round, R.drawable.scotialogo_background, R.drawable.scotialogo_background, R.drawable.scotialogo_background};

        for (int i = 0; i<this.menu.getTabCount(); i++){
            this.menu.getTabAt(i).setIcon(widgets[i]);
        }
    }
}
