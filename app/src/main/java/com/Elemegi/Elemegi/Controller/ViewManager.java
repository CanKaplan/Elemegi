package com.Elemegi.Elemegi.Controller;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.Elemegi.Elemegi.View.ForgotPasswordPanel;
import com.Elemegi.Elemegi.View.HomePagePanel;
import com.Elemegi.Elemegi.View.LoginPanel;
import com.Elemegi.Elemegi.View.RegisterPanel;



public class ViewManager extends Activity {

    private final static ViewManager instance = new ViewManager();
    protected MyApp myApp;
    private Activity currentAct;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApp = (MyApp)this.getApplicationContext();
    }

    protected void onResume() {
        super.onResume();
        if(!myApp.isFirstTime()){

            myApp.setCurrentActivity(this);
            openLoginPanel();
            myApp.setFirstTime(true);
        }
    }


    public static ViewManager getInstance() {
        return instance;
    }



    public Class openHomePagePanel() {
        return HomePagePanel.class;
    }

    public Class openRegisterPanel() {
        return RegisterPanel.class;
    }

    public Class openLoginPanel1() {
        return LoginPanel.class;
    }

    public void openLoginPanel() {
        currentAct = myApp.getCurrentActivity();
        startActivity(new Intent(currentAct, LoginPanel.class));
        finish();

    }

    public Class openForgotPasswordPanel() {
        return ForgotPasswordPanel.class;
    }

    public String controlNameRegister(String name) {
        if(name.length() == 0){
            return "Please enter your name!";
        }
        for(int i = 0; i < name.length(); i++){
            if((int)(name.toUpperCase().charAt(i)) > 90 || (int)(name.toUpperCase().charAt(i)) < 65){
                return "Name have to consist of only alphabetic characters!";
            }
        }
        return "";
    }

    public String controlSurnameRegister(String surname) {
        if(surname.length() == 0){
            return "Please enter your name!";
        }
        for(int i = 0; i < surname.length(); i++){
            if((int)(surname.toUpperCase().charAt(i)) > 90 || (int)(surname.toUpperCase().charAt(i)) < 65){
                return "Name have to consist of only alphabetic characters!";
            }
        }
        return "";
    }
}
