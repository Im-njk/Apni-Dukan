package com.example.productpage.model;

public class Category {
    String C_name;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    int img;

    public Category() {
    }

    public void setC_name(String c_name) {
        C_name = c_name;
    }

    public String getC_name() {
        return C_name;
    }

    public Category(String c_name, int img) {
        C_name = c_name;
        this.img = img;
    }
}
