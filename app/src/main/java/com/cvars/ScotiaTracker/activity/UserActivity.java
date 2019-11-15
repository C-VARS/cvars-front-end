package com.cvars.ScotiaTracker.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.fragment.HomeFragment;
import com.cvars.ScotiaTracker.fragment.InvoiceFragment;
import com.cvars.ScotiaTracker.fragment.SearchFragment;
import com.cvars.ScotiaTracker.fragment.SettingFragment;
import com.cvars.ScotiaTracker.model.InvoiceModel;
import com.cvars.ScotiaTracker.view.ViewType;

import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity {

    private Map<ViewType, Fragment> fragmentList;
    private ViewType currentFragment;

    private void initializeFragmentMap(){
        fragmentList = new HashMap<>();
        fragmentList.put(ViewType.HOME, new HomeFragment());
        fragmentList.put(ViewType.INVOICE, new InvoiceFragment());
        fragmentList.put(ViewType.SEARCH, new SearchFragment());
        fragmentList.put(ViewType.SETTING, new SettingFragment());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initializeFragmentMap();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (ViewType type: fragmentList.keySet()){
            ft.add(R.id.fragmentContainer, fragmentList.get(type), type.name());
            ft.hide(fragmentList.get((type)));
        }
        ft.show(fragmentList.get(ViewType.HOME));
        ft.commit();
        currentFragment = ViewType.HOME;
    }

    @Override
    protected void onDestroy() {
        fragmentList = null;
        super.onDestroy();
    }

    public void switchFragment(ViewType fragmentType) {

        getSupportFragmentManager().beginTransaction()
                .hide(fragmentList.get(currentFragment))
                .show(fragmentList.get(fragmentType))
                .commit();

        currentFragment = fragmentType;
    }

}