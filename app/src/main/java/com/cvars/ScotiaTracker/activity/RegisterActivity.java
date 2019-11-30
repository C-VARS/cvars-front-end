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
import com.cvars.ScotiaTracker.model.RegisterModel;
import com.cvars.ScotiaTracker.model.pojo.UserType;
import com.cvars.ScotiaTracker.presenter.InvoicePresenter;
import com.cvars.ScotiaTracker.presenter.LoginPresenter;
import com.cvars.ScotiaTracker.presenter.RegisterPresenter;
import com.cvars.ScotiaTracker.view.InvoiceView;
import com.cvars.ScotiaTracker.view.LoginView;
import com.cvars.ScotiaTracker.view.RegisterView;
import com.cvars.ScotiaTracker.view.ViewType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Register Page of the android app. Implements LoginView for UI manipulation
 * related to register.
 */
public class RegisterActivity extends AppCompatActivity implements RegisterView {

    private RegisterPresenter registerPresenter;

    private RegisterModel registerModel  = new RegisterModel();

    private Map<String, Fragment> fragmentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeFragmentMap();

        registerPresenter = new RegisterPresenter(registerModel);

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


        // TODO: Make a dictionary of each view mapping to it's content
        //
        Map<String, String> registerData = new LinkedHashMap<>();

        ArrayList<EditText> fields = new  ArrayList<>();

//        EditText usernameField = findViewById(R.id.username);
//        EditText nameField = findViewById(R.id.name);
//        EditText contactField = findViewById(R.id.contact);
//        EditText addressField = findViewById(R.id.address);
//        EditText bankInformationField = findViewById(R.id.bankInformation);
//        EditText passwordField = findViewById(R.id.password);

        registerData.put("userType", ((RegisterFragment) fragmentMap.get("Register")).getUserType());

        fields.add((EditText) findViewById(R.id.username));
        fields.add((EditText) findViewById(R.id.password));
        fields.add((EditText) findViewById(R.id.contact));
        fields.add((EditText) findViewById(R.id.name));
        fields.add((EditText) findViewById(R.id.bankInformation));
        fields.add((EditText) findViewById(R.id.address));


         for (EditText et: fields) {
             if (et.isShown()) {
                  registerData.put(et.getHint().toString().toLowerCase().replaceAll("\\s+",""), et.getText().toString());
             }
         }

        registerPresenter.register(registerData);
        changeToLoginActivity();

    }

    // TODO: Add show/finish loading?

    public void supplierChooseType(View view) {
        getSupportFragmentManager().beginTransaction()
                .hide(fragmentMap.get("Type"))
                .show(fragmentMap.get("Register"))
                .commit();
        ((RegisterFragment) fragmentMap.get("Register")).setUserType("Supplier");
    }

    public void customerChooseType(View view) {
        getSupportFragmentManager().beginTransaction()
                .hide(fragmentMap.get("Type"))
                .show(fragmentMap.get("Register"))
                .commit();
        ((RegisterFragment) fragmentMap.get("Register")).setUserType("Customer");
    }

    public void driverChooseType(View view) {
        getSupportFragmentManager().beginTransaction()
                .hide(fragmentMap.get("Type"))
                .show(fragmentMap.get("Register"))
                .commit();
        ((RegisterFragment) fragmentMap.get("Register")).setUserType("Driver");


        findViewById(R.id.address).setVisibility(view.INVISIBLE);
        findViewById(R.id.bankInformation).setVisibility(view.INVISIBLE);

    }

    public void changeToLoginActivity(){
        Intent myIntent = new Intent(this, LoginActivity.class);
        startActivity(myIntent);
        finish();
    }


    @Override
    protected void onDestroy() {
//        loginPresenter.onDestroy();
        super.onDestroy();
    }
}
