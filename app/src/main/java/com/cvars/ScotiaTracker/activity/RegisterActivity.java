package com.cvars.ScotiaTracker.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cvars.ScotiaTracker.R;
import com.cvars.ScotiaTracker.fragment.RegisterFragment;
import com.cvars.ScotiaTracker.fragment.RegisterTypeFragment;
import com.cvars.ScotiaTracker.model.pojo.UserType;
import com.cvars.ScotiaTracker.presenter.LoginPresenter;
import com.cvars.ScotiaTracker.presenter.RegisterPresenter;
import com.cvars.ScotiaTracker.view.LoginView;
import com.cvars.ScotiaTracker.view.ViewType;

import java.util.HashMap;
import java.util.Map;

/**
 * Register Page of the android app. Implements LoginView for UI manipulation
 * related to register.
 */
public class RegisterActivity extends AppCompatActivity {

    private RegisterPresenter registerPresenter;

    private Map<String, Fragment> fragmentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeFragmentMap();

    }

    private void initializeFragmentMap() {
        fragmentMap = new HashMap<>();
        fragmentMap.put("Type", new RegisterTypeFragment());
        fragmentMap.put("Register", new RegisterFragment());

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        for (String s: fragmentMap.keySet()) {
            ft.add(R.id.registerContainer, fragmentMap.get(s), s);
            ft.hide(fragmentMap.get(s));
        }
        ft.show(fragmentMap.get("Type"));
        ft.commit();
    }



    /**
     * Register a user with input text from username and password field. On success, moves to
     * login page, on failure, displays a login failed Toast.
     * @param view the button that triggered this method
     */
    public void register(View view) {

        EditText usernameField = findViewById(R.id.username);
        EditText passwordField = findViewById(R.id.password);
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        registerPresenter.register()


    }

    // TODO: Add show/finish loading?

    public void  chooseType(View view) {
        getSupportFragmentManager().beginTransaction()
                .hide(fragmentMap.get("Type"))
                .show(fragmentMap.get("Register"))
                .commit();
    }

    @Override
    protected void onDestroy() {
//        loginPresenter.onDestroy();
        super.onDestroy();
    }
}
