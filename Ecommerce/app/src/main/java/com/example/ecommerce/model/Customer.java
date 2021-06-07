package com.example.ecommerce.model;

import java.io.Serializable;

public
class Customer implements Serializable {
    private String customerName, customerPhone, customerAddress;

    public
    Customer() {
    }

    public
    Customer(String customerName, String customerPhone, String customerAddress) {
        //this.idCustomer = idCustomer;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;
    }


    public
    String getCustomerName() {
        return customerName;
    }

    public
    void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public
    String getCustomerPhone() {
        return customerPhone;
    }

    public
    void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public
    String getCustomerAddress() {
        return customerAddress;
    }

    public
    void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }
}
