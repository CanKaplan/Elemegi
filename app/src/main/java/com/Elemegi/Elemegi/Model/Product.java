package com.Elemegi.Elemegi.Model;

import android.util.Base64;

import java.util.List;

public class Product {
    private String name;
    private  long productID;
    private long UserID;
    private List<String> labels;
    private Base64[] image;

    private String description;
    private double overallRating;
    private double price;
    private int deliverTime;
    private List<Comment> comments;

    public Product(String name, long productID, long userID, List<String> labels, Base64[] image, double price, int deliverTime) {
        this.name = name;
        this.productID = productID;
        UserID = userID;
        this.labels = labels;
        this.image = image;
        this.price = price;
        this.deliverTime = deliverTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getProductID() {
        return productID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public Base64[] getImage() {
        return image;
    }

    public void setImage(Base64[] image) {
        this.image = image;
    }

    public double getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(double overallRating) {
        this.overallRating = overallRating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(int deliverTime) {
        this.deliverTime = deliverTime;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
