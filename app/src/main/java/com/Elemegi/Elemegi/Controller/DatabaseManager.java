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

    private int userIDCounter;

    private String url = "jdbc:mysql://remotemysql.com?useSSL=false"; //url address
    private String username = "4AvrqHFO4g"; // username
    private String password = "ItDNsJYW6V";
    private Connection conn;

    private Statement myst;
    private ResultSet myrs;


    DatabaseManager() {

        try {

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(url, username, password); //connect to db server
            TimeUnit.SECONDS.sleep(1);


        }catch (Exception ex){
            System.out.println("Error Message: " + ex);
        }


    }

    //TODO userlist table with parameters
    void createUserlistTable(){
        if(conn == null) return;

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

    private List<String> getCurrentUser(String email, String password){ //List<String> eklenecek***** --> DONE

        Statement stmt;
        ResultSet rs;
        List<String> userlist = new ArrayList<>();

        if(conn==null) return null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT *\n" +
                    "FROM Userlist\n" +
                    "WHERE email = '" + email + "' AND password = '" + password + "';");
            while(rs.next()){
                String str =rs.getString("name");
                userlist.add(str);
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }

        return userlist;


    }

    void insertUser (String name, int roleType, String password, String email, String phoneNumber, String address){

        // type eklenicek, adress null olarak eklenicek, base64String image null, lang -lon eklicek null olacak, phone number eklenicek null olacak --> DONE

        address = "";
        Base64 image = null;
        long latitude = 0;
        long longitude = 0;
        phoneNumber = "";

        PreparedStatement pstmt;
        ResultSet rs;

        int count=0;

        if(conn == null) return;

        try {

            pstmt = conn.prepareStatement("INSERT INTO Userlist (name, roleType, password, email, phoneNumber, address)\n"
                    + "VALUES (" + name + "," + roleType + "," + password + ", " + email + ", " + phoneNumber + ", " + address+ ", " + ");");
            pstmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();

        }

    }

    public boolean checkUser (String email, String password){ //result set in boyutuna bak 0 sa false 1 se true --> DONE

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs;
            if(conn==null) return false;

            rs = stmt.executeQuery("SELECT COUNT(*)\n" +
                    "FROM Userlist\n" +
                    "WHERE email = '" + email + "AND password = '" + password + "';");
            if (rs!=null) return true;


        } catch (SQLException e) {
            e.printStackTrace();

        }


        return false;
    }

    boolean changePassword (String email, String password){ // queryi çalıştır doğruysa true döndür -> DONE
        ResultSet rs;
        PreparedStatement pstmt = null;
        if(conn==null) return false;

        try {
            pstmt = conn.prepareStatement("UPDATE Userlist\n" +
                    "SET password = '" + password + "\n" +
                    "WHERE email = '" + email + "';");
            pstmt.execute();

            if (pstmt.executeUpdate()>0) return true;

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return false;
    }


    //TODO productlist table with parameters
    void createProductlistTable(){
        if(conn==null) return;

        PreparedStatement pstmt = null;
        try {
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
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //sum of number of product for all users
    private Object[] getProducts(long userID ){ //productları getle ->DONE

        Statement stmt;
        ResultSet rs = null;
        ResultSetMetaData meta;
        List<Product> productlist = null;

        if(conn==null) return null;

        try {
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

            return table2array(rs);

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return null;


    }


    private Array getFAV(long userID, long productID){ // Fav table userID + productID -> productları çek -> return will be implemented as list of user, Favlist should be creted in Model

        Statement stmt = null;
        ResultSet rs = null;

        List<String> favlist;

        if(conn==null) return null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT prouctID\n" +
                    "FROM Favlist\n" +
                    "WHERE" + " AND userID = " + userID + ";");
            Array favarr = rs.getArray("userID");

            return favarr;
        } catch (SQLException e) {
            e.printStackTrace();


        }

        return null;


    }

    //TODO here will be updated//
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

            if(pstmt.executeUpdate()>0) return true;

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return false;} // userID + productID ye göre ekle->DONE

    private List<Product> removeFav(Product productID) { //return will be fixed
        Statement stmt = null;
        try {
            stmt = conn.createStatement();

            ResultSet rs = null;
            PreparedStatement pstmt;
            if (conn == null) return null;


            stmt = conn.createStatement();

            pstmt = conn.prepareStatement("DELETE FROM Favlist WHERE productID = " + productID + ";");
            pstmt.execute();

            rs = conn.createStatement().executeQuery("SELEC productID FROM Favlist");

            while (true) {
                try {
                    if (!rs.next()) break;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String str = rs.getString("productID");

                return null;


            }
            return null;
        } // product ID ye göre row sil DONE
        catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    //TODO commentlist table with parameters
    public void createCommentlistTable(){ //description ekle
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


    public List<Comment> getComments(long productID){// product ide ye göre commment list döndür DONE

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

            commentList = Collections.singletonList((Comment) commentarr);//ARRAY TO LIST


        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        }

        return commentList;


    }

    void addComment (Base64 comment, long userID, long productID){ // userID + productID ekleniicek DONE
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        if(conn==null) return;

        try {
            stmt = conn.createStatement();

            pstmt = conn.prepareStatement("INSERT INTO Commentlist (comment, userID, productID)\n"
                    + "VALUES (" + comment + ", " + userID + ", " + productID + ";");

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
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public int getColSize(String columnName){
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + columnName);

            if(rs==null) return 0;

            rs.last();
            return rs.getRow();

        } catch (SQLException e) {
            e.printStackTrace();

            return -1;
        }


    }
    private Object[] table2array(ResultSet result){
        int nCol;
        List<String[]> table;
        try {
            nCol = result.getMetaData().getColumnCount();
            table = new ArrayList<>();
            while( result.next()) {
                String[] row = new String[nCol];
                for( int iCol = 1; iCol <= nCol; iCol++ ){
                    Object obj = result.getObject( iCol );
                    row[iCol-1] = (obj == null) ?null:obj.toString();
                }
                table.add( row );
            }
            return table.toArray();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }


}
