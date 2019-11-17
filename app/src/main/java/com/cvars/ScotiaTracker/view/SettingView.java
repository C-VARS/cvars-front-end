package com.cvars.ScotiaTracker.view;

import com.cvars.ScotiaTracker.model.pojo.User;
import com.cvars.ScotiaTracker.model.pojo.UserType;

public interface SettingView extends FragmentView{
    void updateUserInformation(User user, String username, UserType userType);
}
