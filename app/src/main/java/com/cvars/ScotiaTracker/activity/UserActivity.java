package com.cvars.ScotiaTracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.UIComponents.InvoiceBox;
import com.cvars.ScotiaTracker.fragment.HomeFragment;
import com.cvars.ScotiaTracker.fragment.InvoiceFragment;
import com.cvars.ScotiaTracker.fragment.SearchFragment;
import com.cvars.ScotiaTracker.fragment.SettingFragment;
import com.cvars.ScotiaTracker.model.DataModelFacade;
import com.cvars.ScotiaTracker.model.pojo.Invoice;
import com.cvars.ScotiaTracker.model.pojo.UserType;
import com.cvars.ScotiaTracker.presenter.SearchPresenter;
import com.cvars.ScotiaTracker.presenter.SettingPresenter;
import com.cvars.ScotiaTracker.responseListeners.InvoiceBoxListener;
import com.cvars.ScotiaTracker.view.SearchView;
import com.cvars.ScotiaTracker.view.SettingView;
import com.cvars.ScotiaTracker.view.UserActivityView;
import com.cvars.ScotiaTracker.view.ViewType;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserActivity extends AppCompatActivity implements UserActivityView {

    private Map<ViewType, Fragment> fragmentMap;
    private ViewType currentFragment;
    private TabSwitchListener tabListener;
    private InvoiceBoxListener invoiceListener;

    private boolean loading;

    private DataModelFacade dataFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initializeFragmentMap();
        initializeTab();
        initializeModelPresenter();
        initializeToolBar();
    }

    @Override
    protected void onDestroy() {
        fragmentMap = null;
        dataFacade.onDestroy();
        dataFacade = null;
        invoiceListener.onDestroy();
        invoiceListener = null;
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
    }

    private void initializeToolBar() {
        Toolbar bar = findViewById(R.id.toolBar);
        bar.inflateMenu(R.menu.tool_bar_menu);
        bar.setOnMenuItemClickListener(new ToolBarListener());
    }

    private class ToolBarListener implements Toolbar.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.action_refresh) {
                dataFacade.requestAllInvoices();
                dataFacade.requestUserInfo();
            }
            return false;
        }
    }

    private void initializeModelPresenter() {
        //Initialize data facade
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        UserType userType = UserType.valueOf(getIntent().getStringExtra("userType"));
        dataFacade = new DataModelFacade(username, password, userType);

        //Have the activity observe data facade for top-level functions
        dataFacade.setUserActivityView(this);

        //Create the setting presenter and inject dependencies
        SettingView settingView = (SettingView) fragmentMap.get(ViewType.SETTING);
        SettingPresenter settingPresenter = new SettingPresenter(dataFacade, settingView);
        settingView.setPresenter(settingPresenter);

        // Create the search presenter
        SearchView searchView = (SearchView) fragmentMap.get(ViewType.SEARCH);
        SearchPresenter searchPresenter = new SearchPresenter(dataFacade, searchView);
        searchView.setPresenter(searchPresenter);

        dataFacade.requestAllInvoices();
        dataFacade.requestUserInfo();
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
            int tabNum = tab.getPosition();
            ViewType viewType = ViewType.valueOf(tabNum);
            if (viewType == ViewType.SEARCH){
                switchFragment(viewType);
            }
        }
    }

    private void initializeFragmentMap() {
        fragmentMap = new HashMap<>();
        // TODO: Construct the Fragments passing in their own presenters
        fragmentMap.put(ViewType.HOME, new HomeFragment());
        fragmentMap.put(ViewType.SEARCH, new SearchFragment());
        fragmentMap.put(ViewType.SETTING, new SettingFragment());
        fragmentMap.put(ViewType.INVOICE, new InvoiceFragment());

        initializeInvoiceBoxListener();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (ViewType type : fragmentMap.keySet()) {
            ft.add(R.id.fragmentContainer, fragmentMap.get(type), type.name());
            ft.hide(fragmentMap.get((type)));
        }
        ft.show(fragmentMap.get(ViewType.HOME));
        ft.commit();
        currentFragment = ViewType.HOME;
    }

    private void initializeInvoiceBoxListener() {
        invoiceListener = new InvoiceBoxListener(this);
        ((SearchFragment) fragmentMap.get(ViewType.SEARCH)).setInvoiceListener(invoiceListener);
    }

    public void switchFragment(ViewType fragmentType) {

        getSupportFragmentManager().beginTransaction()
                .hide(fragmentMap.get(currentFragment))
                .show(fragmentMap.get(fragmentType))
                .commit();

        currentFragment = fragmentType;

        Toolbar bar = findViewById(R.id.toolBar);
        switch (fragmentType) {
            case HOME:
                bar.setTitle("Home");
                break;
            case SEARCH:
                bar.setTitle("Invoices");
                break;
            case SETTING:
                bar.setTitle("Setting");
                break;
            case INVOICE:
                bar.setTitle("Invoice");
                break;
        }
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
        if (loading) {
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
    public void displayInvoice(int invoiceID) {
        switchFragment(ViewType.INVOICE);
        Invoice inv = dataFacade.getInvoice(invoiceID);

        ((InvoiceFragment) fragmentMap.get(ViewType.INVOICE)).updateFields(inv);
    }

    @Override
    public void displayMessage(String message) {
        showToast(message);
    }

    @Override
    public void logOut() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void initializeInvoices() {

    }

}