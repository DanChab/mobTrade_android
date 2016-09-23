package com.jot.JotShop;

/**
 * Created by D4n on 9/4/2016.
 */
public class Items {
    private int mDrawable;
    private String mName;
    private float mRating;

    public Items(String name, int drawable, float rating){
        mName = name;
        mDrawable = drawable;
        mRating = rating;
    }

    public float getRating(){
        return mRating;
    }

    public int getDrawable(){
        return mDrawable;
    }

    public String getName(){
        return mName;
    }
}
