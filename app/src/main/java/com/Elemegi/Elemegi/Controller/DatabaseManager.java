package com.Elemegi.Elemegi.Controller;

import android.os.Build;
import android.util.Base64;

import com.Elemegi.Elemegi.Model.Comment;
import com.Elemegi.Elemegi.Model.Product;
import com.Elemegi.Elemegi.Model.User;

import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class DatabaseManager extends MainManager {

    private SessionManager mysession;
    private int userIDCounter;

    public static void main(String[] args) throws IOException, InterruptedException {
        DatabaseManager mycollection = new DatabaseManager();
        mycollection.connectionHandler();

        mycollection.askTermination();
    }

    private void connectionHandler() throws InterruptedException, IOException {

        System.out.println("Maria Database System...");

        mysession = new SessionManager();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("Connecting to the database...");

        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println("The process is complete, terminating...");

    }

    private void askTermination(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you wanna disconnect from database?");
        String option = scanner.nextLine();

        if(option.equals("y")) {
            try {
                mysession.terminateDBConnection();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    //TODO userlist table with parameters
    public void createUserlistTable(){
        Connection con = mysession.connectify();
        if(con==null) return;

        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(  "create database if not exists MyCluster;\n" +
                                                "use MyCluster;\n" +
                                                "create table if not exists Userlist;\n" +
                                                "create table Userlist (\n" +
                                                "userID long,\t\n" +
                                                "name varchar(20),\n" +
                                                "roletype int,\n" +
                                                "password varchar(15),\n" +
                                                "email varchar(100),\n" +
                                                "phoneNumber varchar(11), \n" +
                                                "address varchar(100),\n" +
                                                "point double,\n" +
                                                "age int,\n" +
                                                "image varchar(64),\n" +
                                                "longtitude long,\n" +
                                                "latitude long,\n" +
                                                "primary key (userID);");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getUsers(){
        Connection con = mysession.connectify();

        Statement stmt = null;
        ResultSet rs = null;
        List<User> userlist = null;

        if(con==null) return null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT userID\n" + "FROM Userlist;");
            Array userarr = rs.getArray("userID");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                userlist = Collections.singletonList((User) userarr);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }

        return userlist;


    }

     private List<Array> getCurrentUser(String email, String password){
        Connection con = mysession.connectify();

        Statement stmt = null;
        ResultSet rs = null;
        List<Array> userlist = null;

        if(con==null) return null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT userID\n" +
                                        "FROM Userlist\n" +
                                        "WHERE email = '" + email + "' AND password = '" + password + "';");
            Array userarr = rs.getArray("userID");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                userlist = Collections.singletonList(userarr);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }

        return userlist;


    }
    void insertUser (String name, String email, String password){
        Connection con = mysession.connectify();
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        if(con==null) return;

        try {
            stmt = con.createStatement();
            int count1 = getCurrentUser(email,password).size();
            pstmt = con.prepareStatement("INSERT INTO Userlist (name, email, password)\n"
                                            + "VALUES (" + name + "," + email + "," + password + ");");
            int count2 = getCurrentUser(email,password).size();
            if (count2>count1) {
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public boolean getEmail (String email, String password){
        Connection con = mysession.connectify();
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        if(con==null) return false;

        try {
            stmt = con.createStatement();
            pstmt = con.prepareStatement("UPDATE Userlist\n" +
                    "SET password = '" + password + "\n" +
                    "WHERE email = '" + email + "';");

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return false;
    }

    public boolean changePassword (String email, String password){
        Connection con = mysession.connectify();
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        if(con==null) return false;

        try {
            stmt = con.createStatement();
            pstmt = con.prepareStatement("UPDATE Userlist\n" +
                                            "SET password = '" + password + "\n" +
                                            "WHERE email = '" + email + "';");

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return false;
    }

    public boolean setUserID (String email, String password){
        Connection con = mysession.connectify();
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        if(con==null) return false;

        try {
            stmt = con.createStatement();
            pstmt = con.prepareStatement("UPDATE Userlist\n" +
                    "SET userID = '" + userIDGenerator(email,password) + "\n" +
                    "WHERE email = '" + email + "' AND password = '" + password +"';");

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return false;
    }

    //TODO productlist table with parameters
    public void createProductlistTable(){
        Connection con = mysession.connectify();
        if(con==null) return;

        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(  "create database if not exists MyCluster;\n" +
                                                "use MyCluster;\n" +
                                                "create table if not exists Productlist;\n" +
                                                "create table Productlist(\n" +
                                                "name varchar(20),\n" +
                                                "productID long,\n" +
                                                "userID long,\n" +
                                                "overallRating double,\n" +
                                                "description varchar(100),\n" +
                                                "price double,\n" +
                                                "deliverTime int,\n" +
                                                "comments Comment, \n" +
                                                "primary key (productID), \n" +
                                                "foreign key (userID) references (Userlist);");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //sum of number of product for all users
    private int getProductAmount(float userID ){
        Connection con = mysession.connectify();

        Statement stmt = null;
        ResultSet rs = null;
        List<Product> productlist = null;

        if(con==null) return -1;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT COUNT(*) AS total\n" + "FROM Productlist FULL JOIN Userlist \n" +
                                        "ON Productlist.userID = Userlist.userID\n" +
                                        "WHERE Productlist.userID IS NOT NULL AND Userlist.userID IS NOT NULL;");
            return rs.getInt("total");

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return -1;


    }


    private List<User> getFAV(float userID, float productID){
        Connection con = mysession.connectify();

        Statement stmt = null;
        ResultSet rs = null;
        List<User> favlist = null;

        if(con==null) return null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT name\n" +
                                        "FROM Userlist\n" +
                                        "WHERE productID = " + productID + " AND userID = " + userID + ";");
            Array favarr = rs.getArray("userID");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                favlist = Collections.singletonList((User) favarr);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }

        return favlist;


    }

    //TODO here will be updated//
    private List<User> addFav(){ return null;}

    private List<User> removeFav(){ return null;}


    //TODO commentlist table with parameters
    public void createCommentlistTable(){
        Connection con = mysession.connectify();
        if(con==null) return;

        PreparedStatement pstmt;
        try {
            pstmt = con.prepareStatement(  "create database if not exists MyCluster;\n" +
                                                "use MyCluster;\n" +
                                                "create table if not exists Commentlist;\n" +
                                                "create table Commentlist(\n" +
                                                "commentID long,\n" +
                                                "productID long,\n" +
                                                "userID long,\n" +
                                                "image varchar(64),\n" +
                                                "primary key (commentID),\n" +
                                                "foreign key (productID) references (Productlist),\n" +
                                                "foreign key (image) references (Userlist),\n" +
                                                "foreign key (userID) references (Userlist);");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //ID generator
    private String userIDGenerator(String name, String email){
        int generatedID = 0;
        int part1 = 0;
        for(int c = 0; c<name.length(); c++){
            generatedID += name.charAt(c);
        }
        return (generatedID + email.length() - 1) + "";

    }

    public List<Comment> getComments(float productID){
        Connection con = mysession.connectify();

        Statement stmt = null;
        ResultSet rs = null;
        List<Comment> commentList = null;

        if(con==null) return null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT Comments \n" + "" +
                    "                   FROM Productlist, Commentlist\n" +
                                        "ON Productlist.prouctID = Commentlist.productID\n" +
                                        "WHERE Productlist.userID IS NOT NULL AND Commentlist.productID IS NOT NULL;");
            Array commentarr = rs.getArray("comments");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                commentList = Collections.singletonList((Comment) commentarr);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }

        return commentList;


    }

    void addComment (Base64 comment){
        Connection con = mysession.connectify();
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        if(con==null) return;

        try {
            stmt = con.createStatement();

            pstmt = con.prepareStatement("INSERT INTO Commentlist (comment)\n"
                    + "VALUES (" + comment + ");");

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public void createOrderlistTable(){
        Connection con = mysession.connectify();
        if(con==null) return;

        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(  "create database if not exists MyCluster;\n" +
                    "use MyCluster;\n" +
                    "create table if not exists Orderlist;\n" +
                    "create table Orderlist(\n" +
                    "orderID long,\n" +
                    "productID long,\n" +
                    "userID long,\n" +
                    "amount int,\n" +
                    "remainingTime int,\n" +
                    "productName varchar(50),\n" +
                    "productImage varchar,\n" +
                    "price double,\n" +
                    "primary key (orderID),\n" +
                    "foreign key (productID) references (Productlist),\n" +
                    "foreign key (userID) references (Userlist);");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}
