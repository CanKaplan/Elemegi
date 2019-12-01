package com.Elemegi.Elemegi.Controller;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.Elemegi.Elemegi.View.ChangePasswordPanel;
import com.Elemegi.Elemegi.View.CommentsPanel;
import com.Elemegi.Elemegi.View.ForgotPasswordPanel;
import com.Elemegi.Elemegi.View.HomePagePanel;
import com.Elemegi.Elemegi.View.LoginPanel;
import com.Elemegi.Elemegi.View.ProductPagePanel;
import com.Elemegi.Elemegi.View.ProfilePagePanel;
import com.Elemegi.Elemegi.View.RegisterPanel;



public class ViewManager extends AppCompatActivity {

    private final static ViewManager instance = new ViewManager();
    protected MyApp myApp;
    private AppCompatActivity currentAct;
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

    public Class openCommentsPanel() {
        return CommentsPanel.class;
    }
    public void openLoginPanel() {
        currentAct = myApp.getCurrentActivity();
        startActivity(new Intent(currentAct, LoginPanel.class));
        finish();

    }

    public Class openForgotPasswordPanel() {
        return ForgotPasswordPanel.class;
    }
    public Class openChangePasswordPanel() {
        return ChangePasswordPanel.class;
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

    public String controlEmailRegister(String email) {
        if(email.length() == 0){
            return "Please enter your e-mail!";
        }
        boolean statement1 = false;
        boolean statement2 = false;
        int index1 = 0;
        int index2 = 0;
        for(int i = 1; i < email.length(); i++){
            if(email.charAt(i) == '@'){
                statement1 = true;
                index1 = i + 2;
                break;
            }
            if(email.charAt(i) == '.'){
                break;
            }
        }
        for(int i = index1; i < email.length(); i++){
            if(email.charAt(i) == '@'){
                break;
            }
            if(email.charAt(i) == '.'){
                statement2 = true;
                index2 = i + 2;
                break;
            }
        }
        for(int i = index2; i < email.length(); i++){
            if(email.charAt(i) == '@'){
                statement1 = false;
                break;
            }
            if(email.charAt(i) == '.'){
                statement1 = false;
                break;
            }
        }
        if(!statement1 || !statement2){
            // db check
            return "Please enter a valid e-mail !";
        }
        else{
            return "";
        }
    }

    public String controlPasswordRegister(String password) {
        if(password.length() == 0){
            return "Please enter your password!";
        }
        if(password.length() < 6){
            return "Please enter at least 6 character!";
        }
        return "";
    }

    public String controlPasswordRegister(String password1, String password2) {
        if(password2.length() == 0){
            return "Please enter your password!";
        }
        if(password2.length() < 6){
            return "Please enter at least 6 character!";
        }
        if(!password2.equals(password1)){
            return "Passwords don't match!";
        }
        return "";
    }

    public Class openProductPagePanel(){
        return ProductPagePanel.class;
    }

    public Class openProfile() { return ProfilePagePanel.class;
    }
}
