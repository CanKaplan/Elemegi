package com.Elemegi.Elemegi.Model;

public class Comment {
    private long commentID;
    private long ProductID;
    private long UserID;
    private String image;
    private String comment;

    public Comment(long commentID, long productID, long userID, String image, String comment) {
        this.commentID = commentID;
        ProductID = productID;
        UserID = userID;
        this.image = image;
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

    public long getUserID() {
        return UserID;
    }

    public void setUserID(long userID) {
        UserID = userID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
