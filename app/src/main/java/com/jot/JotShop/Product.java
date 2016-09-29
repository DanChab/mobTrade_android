package com.jot.JotShop;

/**
 * Created by D4n on 9/25/2016.
 */

public class Product {
    private int mPid;
    private float  mPrice;
    private String mName;
    private int mThumbnail;

   public  Product(String name, int id, int thumbnail,float price){
       mPid = id;
       mPrice = price;
       mName = name;
       mThumbnail = thumbnail;
   }

    public int getId(){
        return mPid;
    }
    public float getPrice(){
        return mPrice;
    }
    public String getName(){
        return mName;
    }

    public int getThumbnail() {
        return mThumbnail;
    }
}
