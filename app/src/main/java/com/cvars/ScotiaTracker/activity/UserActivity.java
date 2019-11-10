package com.cvars.ScotiaTracker.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.fragment.InvoiceFragment;

public class UserActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //To switch fragment, use the same code, but change test to any other type of fragment
        Fragment test = new InvoiceFragment();
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameContainer, test);
        ft.commit();
    }


}