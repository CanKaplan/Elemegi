package com.Elemegi.Elemegi.Model;

public class Comment {
    private long commentID;
    private long ProductID;
    private String customerName;
    private String comment;

    public Comment(long commentID, long productID, String customerName, String comment) {
        this.commentID = commentID;
        ProductID = productID;
        customerName = customerName;
        this.comment = comment;
    }

    public long getCommentID() {
        return commentID;
    }

    public void setCommentID(long commentID) {
        this.commentID = commentID;
    }

    public long getProductID() {
        return ProductID;
    }

    public void setProductID(long productID) {
        ProductID = productID;
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
