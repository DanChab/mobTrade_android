package com.jot.JotShop;

import java.util.List;

/**
 * Created by D4n on 9/4/2016.
 */
public class Snap {
    private int mGravity;
    private String mText;
    private List<Items> mApps;

    public Snap(int gravity, String text, List<Items> apps){
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

    public List<Items> getApps(){
        return mApps;
    }
}
