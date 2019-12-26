package com.Elemegi.Elemegi.Model;

public class Order {
    private long productID;
    private String productName;
    private String productImage;
    private double price;
    private int remainingTime;
    private long customerID;
    private String customerName;
    private String customerAddress;
    private String customerTelephone;
    private String note;

    public Order( long productID, String productImage , String productName, long customerID, String customerName, String customerAddress, String customerTelephone, double price , int remainingTime , String note) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.note = note;
        this.productImage = productImage;
        this.remainingTime = remainingTime;
        this.customerID = customerID;
        this.customerAddress = customerAddress;
        this.customerTelephone = customerTelephone;
        this.customerName = customerName;
    }


    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(long customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerTelephone() {
        return customerTelephone;
    }

    public void setCustomerTelephone(String customerTelephone) {
        this.customerTelephone = customerTelephone;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImages) {
        this.productImage = productImages;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
}
