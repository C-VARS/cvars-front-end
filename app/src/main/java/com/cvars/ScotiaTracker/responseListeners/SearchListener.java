package com.cvars.ScotiaTracker.responseListeners;

import android.view.View;

import com.cvars.ScotiaTracker.view.UserActivityView;

public class SearchListener implements View.OnClickListener {

    private UserActivityView view;

    public SearchListener(UserActivityView view ) {this.view = view;}

    @Override
    public void onClick(View v) {

    }

    public void onDestroy() {this.view = null;}
}
