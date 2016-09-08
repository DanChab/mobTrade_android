package com.jot.shoprite;

import java.util.List;

/**
 * Created by D4n on 9/4/2016.
 */
public class Snap {
    private int mGravity;
    private String mText;
    private List<App> mApps;

    public Snap(int gravity, String text, List<App> apps){
        mGravity = gravity;
        mText = text;
        mApps = apps;
    }

    public String getText(){
        return mText;
    }

    public int getGravity(){
        return mGravity;
    }

    public List<App> getApps(){
        return mApps;
    }
}
