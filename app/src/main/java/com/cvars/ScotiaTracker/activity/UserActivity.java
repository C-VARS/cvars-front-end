package com.cvars.ScotiaTracker.activity;

import android.app.Activity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.fragment.HomeFragment;
import com.cvars.ScotiaTracker.fragment.InvoiceFragment;
import com.cvars.ScotiaTracker.fragment.SearchFragment;
import com.cvars.ScotiaTracker.fragment.SettingFragment;
import com.cvars.ScotiaTracker.model.UserModel;
import com.cvars.ScotiaTracker.presenter.UserPresenter;
import com.cvars.ScotiaTracker.view.UserView;
import com.cvars.ScotiaTracker.model.InvoiceModel;
import com.cvars.ScotiaTracker.view.ViewType;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity implements UserView {

    private Map<ViewType, Fragment> fragmentMap;
    private ViewType currentFragment;
    private TabSwitchListener tabListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initializeFragmentMap();
        initializeTab();
    }

    @Override
    protected void onDestroy() {
        fragmentMap = null;
        super.onDestroy();
    }

    private void initializeTab() {

        TabLayout tab = findViewById(R.id.tab);

        int[] widgets = {R.drawable.ic_home, R.drawable.ic_clipboard_notes, R.drawable.ic_cog};

        for (int i = 0; i < tab.getTabCount(); i++) {
            tab.getTabAt(i).setIcon(widgets[i]);
        }

        tabListener = new TabSwitchListener();
        tab.addOnTabSelectedListener(tabListener);
    }

    private void initializeFragmentMap(){
        fragmentMap = new HashMap<>();
        fragmentMap.put(ViewType.HOME, new HomeFragment());
        fragmentMap.put(ViewType.SEARCH, new SearchFragment());
        fragmentMap.put(ViewType.SETTING, new SettingFragment());

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (ViewType type: fragmentMap.keySet()){
            ft.add(R.id.fragmentContainer, fragmentMap.get(type), type.name());
            ft.hide(fragmentMap.get((type)));
        }
        ft.show(fragmentMap.get(ViewType.HOME));
        ft.commit();
        currentFragment = ViewType.HOME;
    }

    private class TabSwitchListener implements TabLayout.OnTabSelectedListener {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int tabNum = tab.getPosition();
            ViewType viewType = ViewType.valueOf(tabNum);
            switchFragment(viewType);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
            //unimplemented
        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {
            //unimplemented
        }
    }

    public void switchFragment(ViewType fragmentType) {

        getSupportFragmentManager().beginTransaction()
                .hide(fragmentMap.get(currentFragment))
                .show(fragmentMap.get(fragmentType))
                .commit();

        currentFragment = fragmentType;
    }
    
    public void displayInvoice(int invoiceID){

    }

}