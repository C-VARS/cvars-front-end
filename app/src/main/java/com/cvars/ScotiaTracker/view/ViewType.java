package com.cvars.ScotiaTracker.view;

import android.util.SparseArray;

public enum ViewType {
    HOME (0), SEARCH(1), SETTING(2), INVOICE(3);

    private int tabNum;
    private static SparseArray<ViewType> map;

    ViewType(int tabNum){
        this.tabNum = tabNum;
    }

    static{
        map = new SparseArray<>();
        for (ViewType vt: ViewType.values()){
            map.put(vt.tabNum, vt);
        }
    }

    public static ViewType valueOf(int tabNum){
        return map.get(tabNum);
    }
}
