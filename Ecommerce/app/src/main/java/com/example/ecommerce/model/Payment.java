package com.example.ecommerce.model;

import java.io.Serializable;

public
class Payment implements Serializable {
    String nameProduct, massge, paymentType, time, transport;
    int totalAmount;
    String documentId;

    public
    String getDocumentId() {
        return documentId;
    }

    public
    void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public
    Payment() {
    }

    public
    Payment(String massge, String nameProduct, String paymentType, String time, int totalAmount, String transport,String documentId) {
        this.nameProduct = nameProduct;
        this.massge = massge;
        this.paymentType = paymentType;
        this.time = time;
        this.transport = transport;
        this.totalAmount = totalAmount;
        this.documentId = documentId;
    }

    public
    String getNameProduct() {
        return nameProduct;
    }

    public
    void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public
    String getMassge() {
        return massge;
    }

    public
    void setMassge(String massge) {
        this.massge = massge;
    }

    public
    String getPaymentType() {
        return paymentType;
    }

    public
    void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public
    String getTime() {
        return time;
    }

    public
    void setTime(String time) {
        this.time = time;
    }

    public
    String getTransport() {
        return transport;
    }

    public
    void setTransport(String transport) {
        this.transport = transport;
    }

    public
    int getTotalAmount() {
        return totalAmount;
    }

    public
    void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}
