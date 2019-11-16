package com.cvars.ScotiaTracker.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.fragment.HomeFragment;
import com.cvars.ScotiaTracker.fragment.InvoiceFragment;
import com.cvars.ScotiaTracker.fragment.SearchFragment;
import com.cvars.ScotiaTracker.fragment.SettingFragment;
import com.cvars.ScotiaTracker.model.DataModelFacade;
import com.cvars.ScotiaTracker.model.UserModel;
import com.cvars.ScotiaTracker.model.pojo.UserType;
import com.cvars.ScotiaTracker.presenter.UserPresenter;
import com.cvars.ScotiaTracker.view.UserActivityView;
import com.cvars.ScotiaTracker.model.InvoiceModel;
import com.cvars.ScotiaTracker.view.ViewType;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.Map;

public class UserActivity extends AppCompatActivity implements UserActivityView {

    private Map<ViewType, Fragment> fragmentMap;
    private ViewType currentFragment;
    private TabSwitchListener tabListener;

    private boolean loading;

    private DataModelFacade dataFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initializeFragmentMap();
        initializeTab();
        initializeModelPresenter();
    }

    private void initializeModelPresenter() {
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        UserType userType = UserType.valueOf(getIntent().getStringExtra("userType"));
        dataFacade = new DataModelFacade(username, password, userType);

        dataFacade.setUserActivityView(this);

        dataFacade.requestInvoices();
        dataFacade.requestUserInfo();
    }

    @Override
    protected void onDestroy() {
        fragmentMap = null;
        dataFacade.onDestroy();
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

    public void switchFragment(ViewType fragmentType) {

        getSupportFragmentManager().beginTransaction()
                .hide(fragmentMap.get(currentFragment))
                .show(fragmentMap.get(fragmentType))
                .commit();

        currentFragment = fragmentType;
    }

    /**
     * Displays a Toast text to notify the user of some information
     *
     * @param message The string that is meant to be displayed
     */
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        if (loading){
            return;
        }

        loading = true;

        ProgressBar bar = findViewById(R.id.progressBar);
        bar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void finishLoading() {
        loading = false;

        ProgressBar bar = findViewById(R.id.progressBar);
        bar.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void displayInvoice(int invoiceID){

    }

    public void displayMessage(String message){
        showToast(message);
    }

}