package com.jot.shoprite;

/**
 * Created by D4n on 9/4/2016.
 */
public class App {
    private int mDrawable;
    private String mName;
    private float mRating;

    public App(String name, int drawable, float rating){
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
