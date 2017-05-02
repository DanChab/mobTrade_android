package com.Jot.Jot.model;

import java.io.Serializable;

/**
 * Created by Lincoln on 04/04/16.
 */
public class Product implements Serializable{
    private String name;
    private String small, medium, large;
    private String price;

    public Product() {
    }

    public Product(String name, String small, String medium, String large, String price) {
        this.name = name;
        this.small = small;
        this.medium = medium;
        this.large = large;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
