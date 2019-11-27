package com.Elemegi.Elemegi.Model;

public class Order {
    private long orderID;
    private long productID;
    private long UserID;
    private int amount;
    private int remainingTime;
    private String productName;
    private String productImage;
    private double price;

    public Order(long orderID, long productID, long userID, int amount, String productName, String productImage, double price) {
        this.orderID = orderID;
        this.productID = productID;
        UserID = userID;
        this.amount = amount;
        this.productName = productName;
        this.productImage = productImage;
        this.price = price;
    }

    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public long getProductID() {
        return productID;
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public long getUserID() {
        return UserID;
    }

    public void setUserID(long userID) {
        UserID = userID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
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

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
