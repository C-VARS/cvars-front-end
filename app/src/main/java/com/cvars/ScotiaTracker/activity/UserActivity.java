package com.cvars.ScotiaTracker.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.fragment.HomeFragment;
import com.cvars.ScotiaTracker.fragment.InvoiceFragment;
import com.cvars.ScotiaTracker.fragment.SearchFragment;
import com.cvars.ScotiaTracker.fragment.SettingFragment;

public class UserActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        switchFragment(1);
    }

    public void switchFragment(int fragmentNum) {

        /*
        TODO: figure out how to make fragments persist in the background to avoid the need to remake
        TODO: avoid the situation where you are already in a fragment and then click to restart the
        same one
         */


        Fragment fragment;
        switch (fragmentNum) {
            case 1:
                fragment = new HomeFragment();
                break;
            case 2:
                fragment = new InvoiceFragment();
                break;
            case 3:
                fragment = new SearchFragment();
                break;
            case 4:
                fragment = new SettingFragment();
                break;
            default:
                return;
        }

        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment);
        ft.commit();
    }


}