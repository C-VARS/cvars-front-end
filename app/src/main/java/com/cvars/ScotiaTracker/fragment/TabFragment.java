package com.cvars.ScotiaTracker.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cvars.ScotiaTracker.R;
import com.google.android.material.tabs.TabLayout;

public class TabFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        TabLayout tab = view.findViewById(R.id.tab);

        int[] widgets = {R.drawable.ic_home, R.drawable.ic_clipboard_notes, R.drawable.ic_search_alt, R.drawable.ic_cog};

        for (int i = 0; i < tab.getTabCount(); i++) {
            tab.getTabAt(i).setIcon(widgets[i]);
        }

        return view;
    }
}
