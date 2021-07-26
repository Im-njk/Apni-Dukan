package com.example.productpage.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class product {
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String title;
    private String price;
    private String description;
    private String category;
    private String image;

    public product() {
    }

    public product( String title, String price, String description, String category, String image) {

        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.image = image;
    }








    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
