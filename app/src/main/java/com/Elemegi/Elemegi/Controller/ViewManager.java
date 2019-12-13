package com.Elemegi.Elemegi.Controller;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.Elemegi.Elemegi.View.AddProductPanel;
import com.Elemegi.Elemegi.View.ChangePasswordPanel;
import com.Elemegi.Elemegi.View.CommentsPanel;
import com.Elemegi.Elemegi.View.EditProfilePanel;
import com.Elemegi.Elemegi.View.ForgotPasswordPanel;
import com.Elemegi.Elemegi.View.HomePagePanel;
import com.Elemegi.Elemegi.View.LoginPanel;
import com.Elemegi.Elemegi.View.MyOrdersPanel;
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
    public boolean checkUserFromDatabase(String email, String password){
        if (MainManager.getInstance().checkUser(email,password)){
            return true;
        }
        else
            return false;
    }
    public Class openEditProfilePanel() { return EditProfilePanel.class;
    }

    public Class openMyOrdersPanel() { return MyOrdersPanel.class;
    }

    public void createNewUser(String name, String surname, String type, String email, String password) {
        MainManager.getInstance().registerUser(name, surname, type, email, password);
    }

    public boolean isExist(String email) {
        if(MainManager.getInstance().checkUserEmail(email)){
            return true;
        }
        else{
            return false;
        }
    }

    public Class openAddProductPanel() {
        return AddProductPanel.class;
    }

    public String controlNameProduct(String newName) {
        if(newName.length() == 0){
            return "Please enter your product name!";
        }
        for(int i = 0; i < newName.length(); i++){
            if((int)(newName.toUpperCase().charAt(i)) > 90 || (int)(newName.toUpperCase().charAt(i)) < 65){
                return "Name have to consist of only alphabetic characters!";
            }
        }
        return "";
    }

    public String controlDescriptionProduct(String newDescription) {

        if(newDescription.length() == 0){
            return "Please enter your name!";
        }
        for(int i = 0; i < newDescription.length(); i++){
            if((int)(newDescription.toUpperCase().charAt(i)) > 90 || (int)(newDescription.toUpperCase().charAt(i)) < 65){
                return "Name have to consist of only alphabetic characters!";
            }
        }
        return "";

    }

    public String controlPriceProduct(String newPrice) {
        if(newPrice.length() == 0){
            return "Please enter the price!";
        }

        for(int i = 0; i < newPrice.length(); i++){
            if(!(newPrice.matches("[0-9]*\\,?[0-9]*"))){
                return "Name have to consist of only numerical characters and ',' !";
            }
        }
        return "";
    }

    public String controlDeliveryTimeProduct(String deliveryTime) {
        if(deliveryTime.length() == 0){
            return "Please enter the delivery time!";
        }

        for(int i = 0; i < deliveryTime.length(); i++){
            if(!(deliveryTime.matches("[0-9]+"))){
                return "Name have to consist of only numerical characters";
            }
        }
        return "";
    }
}
