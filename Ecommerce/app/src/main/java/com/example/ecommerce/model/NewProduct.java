package com.example.ecommerce.model;

import java.io.Serializable;

public
class NewProduct implements Serializable {
    private String description;
    private String img_url;
    private String name;
    private String rating;
    private int price;

    public
    NewProduct() {
    }

    public
    NewProduct(String description, String img_url, String name, String rating, int price) {
        this.img_url = img_url;
        this.description = description;
        this.name = name;
        this.rating = rating;
        this.price = price;
    }

    public
    String getImg_url() {
        return img_url;
    }

    public
    void setImg(String img_url) {
        this.img_url = img_url;
    }

    public
    String getDescription() {
        return description;
    }

    public
    void setDescription(String description) {
        this.description = description;
    }

    public
    String getName() {
        return name;
    }

    public
    void setName(String name) {
        this.name = name;
    }

    public
    String getRating() {
        return rating;
    }

    public
    void setRating(String rating) {
        this.rating = rating;
    }

    public
    int getPrice() {
        return price;
    }

    public
    void setPrice(int price) {
        this.price = price;
    }
}
