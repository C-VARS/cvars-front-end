package com.cvars.ScotiaTracker.view;
import com.cvars.ScotiaTracker.model.UserType;

public interface MenuView {

    void changeToHomeActivity(UserType user, String username);

    void changeToInvoiceActivity(UserType user, String username);

    void changeToSearchActivity(UserType user, String username);

    void changeToSettingsActivity(UserType user, String username);


}
