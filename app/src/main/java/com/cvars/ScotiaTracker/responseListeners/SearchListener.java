package com.cvars.ScotiaTracker.responseListeners;

import android.view.View;
import android.widget.SearchView;

import com.cvars.ScotiaTracker.view.UserActivityView;

public class SearchListener implements SearchView.OnQueryTextListener {

    private UserActivityView view;
    private String oldText;

    public SearchListener(UserActivityView view ) {this.view = view;}

    @Override
    public boolean onQueryTextSubmit(String newText) {
        view.executeSearch(newText);
        return true;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        view.executeSearch(newText);
        return true;
    }

    public void onDestroy() {this.view = null;}
}
