package com.cvars.ScotiaTracker.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.activity.UserActivity;
import com.cvars.ScotiaTracker.model.pojo.User;
import com.cvars.ScotiaTracker.model.pojo.UserType;
import com.cvars.ScotiaTracker.presenter.FragmentPresenter;
import com.cvars.ScotiaTracker.presenter.SettingPresenter;
import com.cvars.ScotiaTracker.view.SettingView;
import com.cvars.ScotiaTracker.view.ViewType;
import com.google.android.material.tabs.TabLayout;

public class SettingFragment extends Fragment implements SettingView {

    private SettingPresenter settingPresenter;

    private View rootView;
    private ViewGroup settingFrame;
    private View account;
    private View setting;
    private View currentView;

    private SettingTabSwitchListener listener = new SettingTabSwitchListener();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        // Set up tab display and containers for individual layouts
        rootView = view;
        settingFrame = view.findViewById(R.id.settingFrameContainer);

        // Set up the layouts to view for each tab
        account = inflater.inflate(R.layout.component_account, settingFrame, false);
        setting = inflater.inflate(R.layout.component_setting, settingFrame, false);

        // Set up initial tab to display when you enter SettingFragment
        settingFrame.addView(account);
        currentView = account;

        initializeTabListener();
        initializeLogOutListener();

        return view;
    }

    /**
     * Initialize a listener for log out button
     */
    private void initializeLogOutListener() {
        Button logOutButton = account.findViewById(R.id.logOut);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((UserActivity)getActivity()).logOut();
            }
        });
    }

    /**
     * Initialize a listener for tab buttons
     */
    private void initializeTabListener(){
        TabLayout tab = rootView.findViewById(R.id.settingTabs);
        tab.addOnTabSelectedListener(listener);
    }

    @Override
    public void onDestroy() {
        settingPresenter.onDestroy();
        super.onDestroy();

        TabLayout tab = rootView.findViewById(R.id.settingTabs);
        tab.removeOnTabSelectedListener(listener);
    }

    /**
     * Allow to switch between tabs
     */
    private void switchComponent(){
        if (currentView == account){
            currentView = setting;
            settingFrame.removeView(account);
            settingFrame.addView(setting);
        } else{
            currentView = account;
            settingFrame.removeView(setting);
            settingFrame.addView((account));
        }
    }

    /**
     * Populate account page with information about <user> with <username> and <userType>
     * @param user
     * @param username
     * @param userType
     */
    @Override
    public void updateUserInformation(User user, String username, UserType userType) {
        TextView usernameView = account.findViewById(R.id.username);
        TextView userTypeView = account.findViewById(R.id.userType);
        TextView name = account.findViewById(R.id.name);
        TextView address = account.findViewById(R.id.address);
        TextView bankInformation = account.findViewById(R.id.bankInformation);
        TextView contact = account.findViewById(R.id.contact);

        usernameView.setText(username);
        userTypeView.setText(userType.name());
        name.setText(user.getName());
        address.setText(user.getAddress());
        bankInformation.setText(user.getBankInformation());
        contact.setText(user.getContact());
    }

    @Override
    public void setPresenter(FragmentPresenter presenter) {
        settingPresenter = (SettingPresenter) presenter;
    }

    private class SettingTabSwitchListener implements TabLayout.OnTabSelectedListener {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switchComponent();
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
}
