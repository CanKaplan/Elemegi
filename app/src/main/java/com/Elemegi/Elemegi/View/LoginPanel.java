package com.Elemegi.Elemegi.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.Elemegi.Elemegi.R;
import com.Elemegi.Elemegi.Controller.ViewManager;

public class LoginPanel extends ViewManager {

    private Button loginButton;
    private Button registerButton;
    private TextView forgotPassword;
    private String email;
    private String password;
    private boolean rememberMe;
    private AppCompatActivity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        myApp.setCurrentActivity(this);
        act = myApp.getCurrentActivity();

        loginButton = (Button) findViewById(R.id.loginButton);
        registerButton = (Button) findViewById(R.id.registerButton);
        forgotPassword = (TextView) findViewById(R.id.forgotPasswordButton);
        email = (((EditText) findViewById(R.id.email)).getText().toString());
        password = ((EditText) findViewById(R.id.password)).getText().toString();
        rememberMe = ((CheckBox) findViewById(R.id.rememberMe)).isChecked();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tara doğruysa
                //MainManager.getInstance().setUserProperties(email);
                changeActivity(ViewManager.getInstance().openHomePagePanel());
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(ViewManager.getInstance().openRegisterPanel());
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(ViewManager.getInstance().openForgotPasswordPanel());
                //changeActivity(ViewManager.getInstance().openChangePasswordPanel());
            }
        });


    }

    public void changeActivity(Class className) {
        startActivity(new Intent(act, className));
        finish();
    }

}
