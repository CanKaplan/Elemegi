package com.Elemegi.Elemegi.Model;

public class Comment {

    private String customerName;
    private String comment;

    public Comment(String customerName, String comment) {
        this.customerName = customerName;
        this.comment = comment;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
