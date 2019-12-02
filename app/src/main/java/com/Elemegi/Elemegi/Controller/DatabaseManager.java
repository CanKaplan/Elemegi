package com.Elemegi.Elemegi.Controller;

import android.os.Build;
import android.util.Base64;

import com.Elemegi.Elemegi.Model.Comment;
import com.Elemegi.Elemegi.Model.Product;
import com.Elemegi.Elemegi.Model.User;

import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class DatabaseManager extends MainManager {

    private int userIDCounter;

    private String url = "jdbc:mysql://remotemysql.com?useSSL=false"; //url address
    private String username = "4AvrqHFO4g"; // username
    private String password = "ItDNsJYW6V";
    private Connection conn;

    public DatabaseManager() {

        try {

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(url, username, password); //connect to db server
            TimeUnit.SECONDS.sleep(1);


        }catch (Exception ex){
            System.out.println("Error Message: " + ex);
        }
    }

    void terminateDBConnection() throws InterruptedException {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TimeUnit.MILLISECONDS.sleep(1200);
        System.out.println("Disconnecting from the database...");

    }



    private void connectionHandler() throws InterruptedException, IOException {

        System.out.println("Maria Database System...");

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
                terminateDBConnection();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    //TODO userlist table with parameters
    public void createUserlistTable(){
        if(conn == null) return;

        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(  "create database if not exists MyCluster;\n" +
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
        Statement stmt = null;
        ResultSet rs = null;
        List<User> userlist = null;

        if(conn == null) return null;
        try {
            stmt = conn.createStatement();
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

     private List<Array> getCurrentUser(String email, String password){ //List<String> düzeltilicek*****

        Statement stmt = null;
        ResultSet rs = null;
        List<Array> userlist = null;

        if(conn==null) return null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT userID\n" +
                                        "FROM Userlist\n" +
                                        "WHERE email = '" + email + "' AND password = '" + password + "';");
            Array userarr = rs.getArray("userID");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {  //başka yöntem
                userlist = Collections.singletonList(userarr);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }

        return userlist;


    }
    void insertUser (String name, String email, String password){ // type eklenicek, adress null olarak eklenicek, base64String image null, lang -lon eklicek null olacak, phone number eklenicek null olacak
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        if(conn == null) return;

        try {
            stmt = conn.createStatement();
            int count1 = getCurrentUser(email,password).size();
            pstmt = conn.prepareStatement("INSERT INTO Userlist (name, email, password)\n"
                                            + "VALUES (" + name + "," + email + "," + password + ");");
            int count2 = getCurrentUser(email,password).size();
            if (count2>count1) {
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public boolean checkUser (String email, String password){ //result set in boyutuna bak 0 sa false 1 se true
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        if(conn==null) return false;

        try {
            stmt = conn.createStatement();
            rs = pstmt.executeQuery("SELECT COUNT(*)\n" +
                    "FROM Userlist\n" +
                    "WHERE email = '" + email + "AND password = '" + password + "';");

        } catch (SQLException e) {
            e.printStackTrace();

        }


        return false;
    }

    public boolean changePassword (String email, String password){ // queryi çalıştır doğruysa true döndür
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        if(conn==null) return false;

        try {
            stmt = conn.createStatement();
            pstmt = conn.prepareStatement("UPDATE Userlist\n" +
                                            "SET password = '" + password + "\n" +
                                            "WHERE email = '" + email + "';");

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return false;
    }


    //TODO productlist table with parameters
    public void createProductlistTable(){
        if(conn==null) return;

        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(  "create database if not exists MyCluster;\n" +
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
                                                "image Base64String,\n" + //bunu kontrol et
                                                "primary key (productID), \n" +
                                                "foreign key (userID) references (Userlist);");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //sum of number of product for all users
    private int getProducts(long userID ){ //productları getle

        Statement stmt = null;
        ResultSet rs = null;
        List<Product> productlist = null;

        if(conn==null) return -1;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT *\n" + "FROM Productlist  \n" +
                                        "ON Productlist.userID = Userlist.userID\n" +
                                        "WHERE Productlist.userID IS NOT NULL AND Userlist.userID IS NOT NULL;");
            return rs.getInt("total");

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return -1;


    }


    private List<Product> getFAV(float userID){ // Fav table userID + productID -> productları çek

        Statement stmt = null;
        ResultSet rs = null;
        List<User> favlist = null;

        if(conn==null) return null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT name\n" +
                                        "FROM Userlist\n" +
                                        "WHERE" + " AND userID = " + userID + ";");
            Array favarr = rs.getArray("userID");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                favlist = Collections.singletonList((User) favarr);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }

        return null;


    }

    //TODO here will be updated//
    private void addFav(){ return;} // userID + productID ye göre ekle

    private List<Product> removeFav(){ return null;} // product ID ye göre row sil


    //TODO commentlist table with parameters
    public void createCommentlistTable(){ //description ekle
        if(conn==null) return;

        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(  "create database if not exists MyCluster;\n" +
                                                "use MyCluster;\n" +
                                                "create table if not exists Commentlist;\n" +
                                                "create table Commentlist(\n" +
                                                "productID long,\n" +
                                                "userID long,\n" +
                                                "primary key (commentID),\n" +
                                                "foreign key (productID) references (Productlist),\n" +
                                                "foreign key (userID) references (Userlist);");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public List<Comment> getComments(float productID){// product ide ye göre commment list döndür

        Statement stmt = null;
        ResultSet rs = null;
        List<Comment> commentList = null;

        if(conn==null) return null;
        try {
            stmt = conn.createStatement();
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

    void addComment (Base64 comment){ // userID + productID ekleniicek
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        if(conn==null) return;

        try {
            stmt = conn.createStatement();

            pstmt = conn.prepareStatement("INSERT INTO Commentlist (comment)\n"
                    + "VALUES (" + comment + ");");

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public void createOrderlistTable(){
        if(conn==null) return;

        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(  "create database if not exists MyCluster;\n" +
                    "use MyCluster;\n" +
                    "create table if not exists Orderlist;\n" +
                    "create table Orderlist(\n" +
                    "orderID long,\n" +
                    "productID long,\n" +
                    "userID long,\n" +
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
