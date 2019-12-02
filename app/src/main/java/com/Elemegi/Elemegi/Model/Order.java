package com.Elemegi.Elemegi.Model;

import java.util.Date;

public class Order {
    private long orderID;
    private long productID;
    private long UserID;
    private Date givenDate;
    private String productName;
    private String productImage;
    private double price;
    private String description;

    public Order(long orderID, long productID, long userID, Date givenDate, String productName, double price) {
        this.orderID = orderID;
        this.productID = productID;
        this.UserID = userID;
        this.givenDate = givenDate;
        this.productName = productName;
        this.price = price;
    }

    public String getDescription() {
        return description;
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

    public Date getGivenDate() {
        return givenDate;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public double getPrice() {
        return price;
    }

}
