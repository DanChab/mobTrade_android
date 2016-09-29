package com.jot.JotShop;


import java.util.List;

public class Snap {

    private int mGravity;
    private String mText;
    private List<Product> mProducts;

    public Snap(int gravity, String text, List<Product> products) {
        mGravity = gravity;
        mText = text;
        mProducts = products;
    }

    public String getText(){
        return mText;
    }

    public int getGravity(){
        return mGravity;
    }

    public List<Product> getProducts(){
        return mProducts;
    }
}
