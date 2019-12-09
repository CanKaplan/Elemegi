package com.Elemegi.Elemegi.Controller;

import android.os.Build;
import android.util.Base64;

import com.Elemegi.Elemegi.Model.Comment;
import com.Elemegi.Elemegi.Model.Product;
import com.Elemegi.Elemegi.Model.User;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

//import class


public class DatabaseManager extends MainManager {

    //contraints and initialized variables
    private int userIDCounter;

    private String url = "jdbc:mysql://remotemysql.com?useSSL=false"; //url address
    private String username = "4AvrqHFO4g"; // username
    private String password = "ItDNsJYW6V"; // password
    private Connection conn;

    private Statement myst;
    private ResultSet myrs;

    //main manager for database
    DatabaseManager() {

        try {
            //use driver manager to connect
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(url, username, password); //connect to db server
            TimeUnit.SECONDS.sleep(1);


        }catch (Exception ex){
            System.out.println("Error Message: " + ex);
        }


    }

    //TODO userlist table with parameters
    void createUserlistTable(){
        //check connection
        if(conn == null) return;

        //create userlist table
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(  "create database if not exists MyCluster;\n" +
                                                "use MyCluster;\n" +
                                                "create table if not exists Userlist;\n" +
                                                "create table Userlist (\n" +
                                                "userID BIGINT NOT NULL,\t\n" +
                                                "name varchar(max),\n" +
                                                "roletype int,\n" +
                                                "password varchar(max),\n" +
                                                "email varchar(max),\n" +
                                                "phoneNumber varchar(max), \n" +
                                                "address varchar(nax),\n" +
                                                "image varchar(max),\n" +
                                                "longtitude BIGINT,\n" +
                                                "latitude BIGINT,\n" +
                                                "primary key (userID);");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //get users from the userlist and return a list
    public List<User> getUsers(){
        //variables
        Statement stmt = null;
        ResultSet rs = null;
        List<User> userlist = null;

        //connection
        if(conn == null) return null;
        try {
            //selection from query
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT userID\n" + "FROM Userlist;");
            Array userarr = rs.getArray("userID");

            //query array to list
            userlist = Collections.singletonList((User) userarr);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }
        //return the list
        return userlist;


    }

    //get a specific user using email and password
    private List<User> getCurrentUser(String email, String password){ //List<String> eklenecek***** --> DONE

        //variables
        Statement stmt;
        ResultSet rs;
        List<String> userlist = new ArrayList<>();

        //connection
        if(conn==null) return null;
        try {
            //get from query
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT *\n" +
                    "FROM Userlist\n" +
                    "WHERE email = '" + email + "' AND password = '" + password + "';");

            //add to the list
            while(rs.next()){
                String str =rs.getString("name");
                userlist.add(str);
            }

            //return the list (both of 2 ways)

            return Collections.singletonList((User) userlist);

            //return Arrays.asList((User) userlist); -> There are two different ways to transform query array to userlist

        } catch (Exception e) {
            e.printStackTrace();

        }

        return null;

    }

    void insertUser (String name, int roleType, String password, String email, String phoneNumber, String address){

        // type eklenicek, adress null olarak eklenicek, base64String image null, lang -lon eklicek null olacak, phone number eklenicek null olacak --> DONE

        //variables
        address = "";
        phoneNumber = "";
        Base64 image = null;
        long latitude = 0, longitude = 0;


        PreparedStatement pstmt;
        ResultSet rs;

        int count=0;

        //connection
        if(conn == null) return;

        try {
            //insert to the query
            pstmt = conn.prepareStatement("INSERT INTO Userlist (name, roleType, password, email, phoneNumber, address)\n"
                    + "VALUES (" + name + "," + roleType + "," + password + ", " + email + ", " + phoneNumber + ", " + address+ ", " + ");");
            pstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    //check whether a user exists in the query
    public boolean checkUser (String email, String password){ //result set in boyutuna bak 0 sa false 1 se true --> DONE

        try {
            //variables
            Statement stmt = conn.createStatement();
            ResultSet rs;

            //connection
            if(conn==null) return false;

            //check whether it is not zero in the query
            rs = stmt.executeQuery("SELECT COUNT(*)\n" +
                    "FROM Userlist\n" +
                    "WHERE email = '" + email + "AND password = '" + password + "';");

            //if exists
            if (rs!=null) return true;


        } catch (SQLException e) {
            e.printStackTrace();

        }

        return false;
    }

    //change password of a user with email and password
    boolean changePassword (String email, String password){ // queryi çalıştır doğruysa true döndür -> DONE
        //variables
        ResultSet rs;
        PreparedStatement pstmt = null;
        if(conn==null) return false;

        try {
            //change the password in the query
            pstmt = conn.prepareStatement("UPDATE Userlist\n" +
                    "SET password = '" + password + "\n" +
                    "WHERE email = '" + email + "';");
            pstmt.execute();

            //check whether it changed
            if (pstmt.executeUpdate()>0) return true;

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return false;
    }


    //productlist table with parameters
    void createProductlistTable(){
        //connection
        if(conn==null) return;

        //variable
        PreparedStatement pstmt = null;
        try {
            //prepare productlist table
            pstmt = conn.prepareStatement(  "create database if not exists MyCluster;\n" +
                    "use MyCluster;\n" +
                    "create table if not exists Productlist;\n" +
                    "create table Productlist(\n" +
                    "name varchar(max),\n" +
                    "productID BIGINT NOT NULL,\n" +
                    "userID BIGINT NOT NULL,\n" +
                    "overallRating double,\n" +
                    "description varchar(max),\n" +
                    "price double,\n" +
                    "deliverTime int,\n" +
                    "image BASE64,\n" + //bunu kontrol et -->DONE
                    "primary key (productID), \n" +
                    "foreign key (userID) references (Userlist);");
            //create table
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //sum of number of product for all users
    private Object[] getProducts(long userID ){ //productları getle ->DONE
        //variables
        Statement stmt;
        ResultSet rs = null;
        ResultSetMetaData meta;
        List<Product> productlist = null;

        //connection
        if(conn==null) return null;

        try {
            //get the list from query
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT **\n" +
                    "FROM Productlist  \n" +
                    "WHERE productID IS NOT NULL AND userID = " + userID + ";");
            meta = rs.getMetaData();

            //number of columns
            int colCnt= meta.getColumnCount();

            ResultSet temp = rs;
            temp.last();
            int rowCnt= temp.getRow();

            //list of column names
            ArrayList<String> collist = new ArrayList<>();
            for (int c = 1; c<colCnt; c++){
                collist.add(meta.getColumnName(c));
            }

            //return the list
            return table2array(rs);

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;
    }

    //get favorite list
    private List<Product> getFAV(long userID, long productID){
        //TODO Fav table userID + productID -> productları çek -> return is implemented as list of products, "Favlist should be created in Model"
        //variables
        Statement stmt = null;
        ResultSet rs = null;

        //connection
        if(conn==null) return null;
        try {
            //get from query
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT productID\n" +
                                        "FROM Favlist\n" +
                                        "WHERE" + productID + " AND userID = " + userID + ";");

            //return the list
            return Collections.singletonList((Product) rs.getArray("productID"));
        } catch (SQLException e) {
            e.printStackTrace();


        }

        return null;


    }

    //add a product to favorites
    private boolean addFav(long userID, long productID){
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        if(conn==null) return false;

        try {
            stmt = conn.createStatement();

            pstmt = conn.prepareStatement("INSERT INTO Favlist (productID, userID)\n"
                    + "VALUES (" + productID + "), " + userID + ";");

            pstmt.execute();

            //check whether added
            if(pstmt.executeUpdate()>0) return true;

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return false;} // userID + productID ye göre ekle->DONE

    //remove a product from list
    private List<Product> removeFav(Product productID) { //return will be fixed -> DONE
        Statement stmt = null;
        try {
            stmt = conn.createStatement();

            ResultSet rs = null;
            PreparedStatement pstmt;
            if (conn == null) return null;


            stmt = conn.createStatement();

            pstmt = conn.prepareStatement("DELETE FROM Favlist WHERE productID = " + productID + ";");
            pstmt.execute();

            rs = conn.createStatement().executeQuery("SELECT productID FROM Favlist");

            while (true) {
                try {
                    if (!rs.next()) break;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String str = rs.getString("productID");

            }
            return Collections.singletonList((Product) rs.getArray("userID"));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    //commentlist table with parameters
    public void createCommentlistTable(){ //description ekle -> will be added
        if(conn==null) return;

        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(  "create database if not exists MyCluster;\n" +
                    "use MyCluster;\n" +
                    "create table if not exists Commentlist;\n" +
                    "create table Commentlist(\n" +
                    "productID BIGINT NOT NULL,\n" +
                    "userID BIGINT NOT NULL,\n" +
                    "primary key (commentID),\n" +
                    "foreign key (productID) references (Productlist),\n" +
                    "foreign key (userID) references (Userlist);");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // get list of comments
    public List<Comment> getComments(long productID){// product ide ye göre commment list döndür DONE
        //variables
        Statement stmt = null;
        ResultSet rs = null;
        List<Comment> commentList = null;

        //connection
        if(conn==null) return null;
        try {
            //get from query
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT Comments \n" + "" +
                                        "FROM Productlist, Commentlist\n" +
                                        "ON Productlist.prouctID = Commentlist.productID\n" +
                                        "WHERE Productlist.userID IS NOT NULL AND Commentlist.productID IS NOT NULL;");
            Array commentarr = rs.getArray("comments");

            //array to list
            commentList = Collections.singletonList((Comment) commentarr);//ARRAY TO LIST


        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }

        return commentList;


    }

    //add comment to query
    void addComment (Base64 comment, long userID, long productID){ // userID + productID eklenicek DONE
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;

        //cconnection
        if(conn==null) return;

        try {
            //add to query
            stmt = conn.createStatement();

            pstmt = conn.prepareStatement("INSERT INTO Commentlist (comment, userID, productID)\n"
                    + "VALUES (" + comment + ", " + userID + ", " + productID + ";");

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }
    //create list of orders
    public void createOrderlistTable(){
        if(conn==null) return;

        PreparedStatement pstmt = null;
        try {
            //create table
            pstmt = conn.prepareStatement(  "create database if not exists MyCluster;\n" +
                    "use MyCluster;\n" +
                    "create table if not exists Orderlist;\n" +
                    "create table Orderlist(\n" +
                    "orderID BIGINT NOT NULL,\n" +
                    "productID BIGINT NOT NULL,\n" +
                    "userID BIGINT NOT NULL,\n" +
                    "remainingTime int,\n" +
                    "productName varchar(max),\n" +
                    "productImage varchar(max),\n" +
                    "price double,\n" +
                    "primary key (orderID),\n" +
                    "foreign key (productID) references (Productlist),\n" +
                    "foreign key (userID) references (Userlist);");
            //execute table
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    //get length of columns
    public int getColSize(String columnName){
        Statement stmt = null;
        try {
            //select all from query
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + columnName);

            //check whether it is zero
            if(rs==null) return 0;

            //go to last row and get its rank
            rs.last();
            return rs.getRow();

        } catch (SQLException e) {
            e.printStackTrace();

            return -1;
        }


    }
    //table to list method
    private Object[] table2array(ResultSet result){
        //variables
        int nCol;
        List<String[]> table;
        try {
            //get column count and create a list
            nCol = result.getMetaData().getColumnCount();
            table = new ArrayList<>();
            //select from query and insert rows to a list
            while( result.next()) {
                String[] row = new String[nCol];
                for( int iCol = 1; iCol <= nCol; iCol++ ){
                    Object obj = result.getObject( iCol );
                    row[iCol-1] = (obj == null) ?null:obj.toString();
                }
                table.add( row );
            }
            //return the list
            return table.toArray();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


}
