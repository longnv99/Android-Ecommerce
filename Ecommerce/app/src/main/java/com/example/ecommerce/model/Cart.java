package com.example.ecommerce.model;

import java.io.Serializable;

public
class Cart implements Serializable {
    private String productImg;
    private String currentDate;
    private String currentTime;
    private String productName;
    private String productPrice;
    private int totalPrice;
    private String totalQuantity;
    private String documentId;
    public
    Cart() {
    }

    public
    String getDocumentId() {
        return documentId;
    }

    public
    void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public
    Cart(String documentId,String productImg,String currentDate, String currentTime, String productName, String productPrice, int totalPrice, String totalQuantity) {
        this.documentId = documentId;
        this.productImg = productImg;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.productName = productName;
        this.productPrice = productPrice;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
    }

    public
    String getCurrentDate() {
        return currentDate;
    }

    public
    void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public
    String getCurrentTime() {
        return currentTime;
    }

    public
    void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public
    String getProductName() {
        return productName;
    }

    public
    void setProductName(String productName) {
        this.productName = productName;
    }

    public
    String getProductPrice() {
        return productPrice;
    }

    public
    void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public
    int getTotalPrice() {
        return totalPrice;
    }

    public
    void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public
    String getTotalQuantity() {
        return totalQuantity;
    }

    public
    void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public
    String getProductImg() {
        return productImg;
    }

    public
    void setProductImg(String productImg) {
        this.productImg = productImg;
    }
}
