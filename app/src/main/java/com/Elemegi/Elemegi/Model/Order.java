package com.Elemegi.Elemegi.Model;

public class Order {
    private long orderID;
    private long productID;
    private long UserID;
    private String productName;
    private String productImage;
    private double price;
    private int remainingTime;

    public Order(long orderID, long productID, long userID, String productImage , String productName, double price , int remainingTime ) {
        this.orderID = orderID;
        this.productID = productID;
        this.UserID = userID;
        this.productName = productName;
        this.price = price;
        this.productImage = productImage;
        this.remainingTime = remainingTime;
    }

    public long getOrderID() {
        return orderID;
    }

    public long getProductID() {
        return productID;
    }


    public long getUserID() {
        return UserID;
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
