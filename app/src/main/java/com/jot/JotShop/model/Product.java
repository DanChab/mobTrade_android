package com.jot.JotShop.model;

import java.io.Serializable;

/**
 * Created by D4n on 10/4/2016.
 */
public class Product implements Serializable{
    private String name;
    private String small, medium, large;
    private String price;
    private String description;
    private String timestamp;

    public Product() {
    }



    public Product(String name, String small, String medium, String large, String price, String description ){
        this.name = name;
        this.description = description;
        this.small = small;
        this.medium = medium;
        this.large = large;

        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String setTimestamp) {
        this.timestamp = setTimestamp;
    }
}

