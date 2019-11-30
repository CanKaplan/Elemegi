package com.Elemegi.Elemegi.Controller;

//import class

import java.sql.*;
import java.util.concurrent.TimeUnit;

class SessionManager {
    private Statement myst;
    private ResultSet myrs;

    private Connection conn;
    private String username="", password="", url="";

    //Handles

    SessionManager() {
        //TODO entries
        String url = "jdbc:mysql://remotemysql.com?useSSL=false"; //url address
        // String url = "mongodb://localhost:27017/test"; //url address
        String username = "4AvrqHFO4g"; // username
        String password = "ItDNsJYW6V"; //password

        connectify();
    }

    Connection connectify(){
        //TODO
        try {

            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(url, username, password); //connect to db server
            TimeUnit.SECONDS.sleep(1);


        }catch (Exception ex){
            System.out.println("Error Message: " + ex);


        }

        return conn;
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
}



