package com.cvars.ScotiaTracker.activity;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.fragment.HomeFragment;
import com.cvars.ScotiaTracker.fragment.IndividualInvoiceFragment;
import com.cvars.ScotiaTracker.fragment.InvoiceFragment;
import com.cvars.ScotiaTracker.fragment.AccountFragment;
import com.cvars.ScotiaTracker.model.DataModelFacade;
import com.cvars.ScotiaTracker.model.pojo.UserType;
import com.cvars.ScotiaTracker.networkAPI.FirebaseLocationSender;
import com.cvars.ScotiaTracker.presenter.HomePresenter;
import com.cvars.ScotiaTracker.presenter.InvoicePresenter;
import com.cvars.ScotiaTracker.presenter.AccountPresenter;
import com.cvars.ScotiaTracker.presenter.IndividualInvoicePresenter;
import com.cvars.ScotiaTracker.responseListeners.InvoiceBoxListener;
import com.cvars.ScotiaTracker.view.IndividualInvoiceView;
import com.cvars.ScotiaTracker.view.HomeView;
import com.cvars.ScotiaTracker.view.InvoiceView;
import com.cvars.ScotiaTracker.view.SettingView;
import com.cvars.ScotiaTracker.view.ActivityMessageInterface;
import com.cvars.ScotiaTracker.view.ViewType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserActivity extends AppCompatActivity implements ActivityMessageInterface {

    private Map<ViewType, Fragment> fragmentMap;
    private ViewType currentFragment;
    private ViewType switchedOutFragment;

    private TabSwitchListener tabListener;
    private InvoiceBoxListener invoiceListener;
    private IndividualInvoicePresenter individualInvoicePresenter;

    private boolean loading;
    private boolean doubleBackToExitPressedOnce = false;

    private String CHANNEL_ID;

    private FirebaseLocationSender locationSender;

    private DataModelFacade dataFacade;

    private BroadcastReceiver mMessageReceiver = new NotificationBroadcastReceiver();

    /**
     * Set up the basic templates that every screen will use within the app using the savedInstanceState.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        initializeNotificationChannel();
        initializeFragmentMap();
        initializeTab();
        initializeModelPresenter();
        initializeToolBar();
        initializeFirebaseConnection();

        // Initiate GPS tracking service
        if (dataFacade.getUserType() == UserType.DRIVER) {
            requestLocationPermission();
        }

        // Set up notifications
        LocalBroadcastManager.getInstance(this).registerReceiver((mMessageReceiver),
                new IntentFilter("Message")
        );
    }

    @Override
    protected void onDestroy() {
        fragmentMap = null;
        dataFacade.onDestroy();
        dataFacade = null;
        invoiceListener.onDestroy();
        invoiceListener = null;

        if (locationSender != null) {
            locationSender.onDestroy();
            locationSender = null;
        }

        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    /**
     * Set up actions for when the back button is pressed
     */
    @Override
    public void onBackPressed() {
        if (currentFragment == ViewType.INDIVIDUAL_INVOICE) {
            switchFragment(switchedOutFragment);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            showToast("Click BACK Again to Exit");

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    /**
     * Initialize notification channel to notify all relevant users about the changes in invoices
     */
    private void initializeNotificationChannel() {
        CHANNEL_ID = getResources().getString(R.string.channel_name);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getResources().getString(R.string.channel_name);
            String description = getResources().getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /**
     * Display the notification <message> when it is received
     * @param message
     */
    @Override
    public void showPushNotification(String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_logo_scotiabank)
                .setContentTitle("Scotia Tracker Notification")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(123, builder.build());
    }

    private class NotificationBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            showPushNotification(intent.getStringExtra("message"));
            dataFacade.requestAllInvoices();
        }
    }

    /**
     * Initialize the tool bar that is shown on every screen of the app
     */
    private void initializeToolBar() {
        Toolbar bar = findViewById(R.id.toolBar);
        bar.inflateMenu(R.menu.tool_bar_menu);
        bar.setOnMenuItemClickListener(new ToolBarListener());
    }

    /**
     * Set up a listener that refreshes invoices when the refresh button on the toolbar is clicked
     */
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

    /**
     * Set up the model, view, presenters bundles to interact with for any activities
     */
    private void initializeModelPresenter() {
        //Initialize data facade
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        UserType userType = UserType.valueOf(getIntent().getStringExtra("userType"));
        dataFacade = new DataModelFacade(username, password, userType);

        //Have the activity observe data facade for top-level functions
        dataFacade.setActivityMessageInterface(this);

        invoiceListener = new InvoiceBoxListener(this);

        ((InvoiceFragment) fragmentMap.get(ViewType.INVOICES)).setInvoiceListeners(invoiceListener);
        ((HomeFragment) fragmentMap.get(ViewType.HOME)).setInvoiceListener(invoiceListener);

        dataFacade.requestAllInvoices();
        dataFacade.requestUserInfo();

        //Create the setting presenter and inject dependencies
        SettingView settingView = (SettingView) fragmentMap.get(ViewType.SETTING);
        AccountPresenter accountPresenter = new AccountPresenter(dataFacade, settingView);
        settingView.setPresenter(accountPresenter);

        // Create the search presenter
        InvoiceView searchView = (InvoiceView) fragmentMap.get(ViewType.INVOICES);
        InvoicePresenter invoicePresenter = new InvoicePresenter(dataFacade, searchView);
        searchView.setPresenter(invoicePresenter);

        // Create the status presenter
        IndividualInvoiceView individualInvoiceView = (IndividualInvoiceView) fragmentMap.get(ViewType.INDIVIDUAL_INVOICE);
        individualInvoicePresenter = new IndividualInvoicePresenter(dataFacade, individualInvoiceView);
        individualInvoiceView.setPresenter(individualInvoicePresenter);

        // Create the home presenter
        HomeView homeView = (HomeView) fragmentMap.get(ViewType.HOME);
        HomePresenter homePresenter = new HomePresenter(dataFacade, homeView);
        homeView.setPresenter(homePresenter);


    }

    /**
     * Initialize the tabs that display the fragment options
     */
    private void initializeTab() {
        TabLayout tab = findViewById(R.id.tab);
        tabListener = new TabSwitchListener();
        tab.addOnTabSelectedListener(tabListener);
    }

    /**
     * A class that allows you to switch between fragments by clicking on the tab buttons
     */
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
            if (viewType == ViewType.INVOICES || viewType == ViewType.HOME) {
                switchFragment(viewType);
            }
        }
    }

    /**
     * Set up each fragment with their corresponding presenters
     */
    private void initializeFragmentMap() {
        fragmentMap = new HashMap<>();
        fragmentMap.put(ViewType.HOME, new HomeFragment());
        fragmentMap.put(ViewType.INVOICES, new InvoiceFragment());
        fragmentMap.put(ViewType.SETTING, new AccountFragment());
        fragmentMap.put(ViewType.INDIVIDUAL_INVOICE, new IndividualInvoiceFragment());

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (ViewType type : fragmentMap.keySet()) {
            ft.add(R.id.registerContainer, fragmentMap.get(type), type.name());
            ft.hide(fragmentMap.get((type)));
        }
        ft.show(fragmentMap.get(ViewType.HOME));
        ft.commit();
        currentFragment = ViewType.HOME;
        switchedOutFragment = ViewType.HOME;
    }

    /**
     * Initialize the firebase connection needed for push notifications
     */
    private void initializeFirebaseConnection() {
        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.i("UserActivity", "Firebase Login Complete");
                } else {
                    Log.e("UserActivity", "Firebase Anonymous Login Failure");
                }
            }
        });
    }

    /**
     * Request location permission for the orders being delivered
     */
    private void requestLocationPermission() {
        List<String> requestList = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (!requestList.isEmpty()) {
            ActivityCompat.requestPermissions(this, requestList.toArray(new String[requestList.size()]), 100);
        } else {
            initializeLocationSending();
        }
    }

    /**
     * Initialize the GPS tracking so that the other users involved in the order can view the order's location
     */
    private void initializeLocationSending() {
        this.locationSender = new FirebaseLocationSender(
                this,
                dataFacade.getUsername());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                showToast("You need to allow access!");
            } else {
                initializeLocationSending();
            }
        }
    }

    /**
     * Switch to the <fragmentType> that the user clicked.
     * @param fragmentType
     */
    public void switchFragment(ViewType fragmentType) {

        getSupportFragmentManager().beginTransaction()
                .hide(fragmentMap.get(currentFragment))
                .show(fragmentMap.get(fragmentType))
                .commit();

        currentFragment = fragmentType;

        if (fragmentType == ViewType.HOME || fragmentType == ViewType.INVOICES) {
            switchedOutFragment = fragmentType;
        }

        Toolbar bar = findViewById(R.id.toolBar);
        switch (fragmentType) {
            case HOME:
                bar.setTitle("Home");
                break;
            case INVOICES:
                bar.setTitle("Invoices");
                break;
            case SETTING:
                bar.setTitle("Account");
                break;
            case INDIVIDUAL_INVOICE:
                bar.setTitle("Invoice");
                break;
        }
    }

    /**
     * Create a toast to display certain <message>s
     * @param message
     */
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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

    /**
     * Populate the individual invoice page with information of the invoice with <invoiceID>
     * @param invoiceID
     */
    @Override
    public void displayInvoice(int invoiceID) {
        switchFragment(ViewType.INDIVIDUAL_INVOICE);
        individualInvoicePresenter.updateInvoice(invoiceID);
    }

    /**
     * Display <message>
     * @param message
     */
    @Override
    public void displayMessage(String message) {
        showToast(message);
    }

    /**
     * Log out the user
     */
    @Override
    public void logOut() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}