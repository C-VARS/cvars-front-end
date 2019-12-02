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
import com.cvars.ScotiaTracker.presenter.AccountPresenter;
import com.cvars.ScotiaTracker.view.SettingView;

public class AccountFragment extends Fragment implements SettingView {

    private AccountPresenter accountPresenter;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        // Set up tab display and containers for individual layouts
        rootView = view;

        initializeLogOutListener();

        return view;
    }

    /**
     * Initialize a listener for log out button
     */
    private void initializeLogOutListener() {
        Button logOutButton = rootView.findViewById(R.id.logOut);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((UserActivity)getActivity()).logOut();
            }
        });
    }

    @Override
    public void onDestroy() {
        accountPresenter.onDestroy();
        super.onDestroy();
    }

    /**
     * Populate account page with information about <user> with <username> and <userType>
     * @param user
     * @param username
     * @param userType
     */
    @Override
    public void updateUserInformation(User user, String username, UserType userType) {
        TextView usernameView = rootView.findViewById(R.id.username);
        TextView userTypeView = rootView.findViewById(R.id.userType);
        TextView name = rootView.findViewById(R.id.name);
        TextView address = rootView.findViewById(R.id.address);
        TextView bankInformation = rootView.findViewById(R.id.bankInformation);
        TextView contact = rootView.findViewById(R.id.contact);

        usernameView.setText(username);
        userTypeView.setText(userType.name());
        name.setText(user.getName());
        address.setText(user.getAddress());
        bankInformation.setText(user.getBankInformation());
        contact.setText(user.getContact());
    }

    @Override
    public void setPresenter(FragmentPresenter presenter) {
        accountPresenter = (AccountPresenter) presenter;
    }
}
