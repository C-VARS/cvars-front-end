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
        int[] widgets= {R.drawable.ic_home, R.drawable.ic_clipboard_notes, R.drawable.ic_search_alt, R.drawable.ic_cog};

        for (int i = 0; i<this.menu.getTabCount(); i++){
            this.menu.getTabAt(i).setIcon(widgets[i]);
        }
    }
}
