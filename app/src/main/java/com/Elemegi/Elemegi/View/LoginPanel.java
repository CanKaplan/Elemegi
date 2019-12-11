package com.Elemegi.Elemegi.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    private EditText emailEdit;
    private String email;
    private EditText passwordEdit;
    private String password;
    private boolean rememberMe;
    private CheckBox rememberMeBox;
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
        emailEdit = (EditText) findViewById(R.id.email);
        passwordEdit = (EditText) findViewById(R.id.password);

        rememberMeBox = (CheckBox) findViewById(R.id.rememberMe);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tara doğruysa

                email = emailEdit.getText().toString();
                password = passwordEdit.getText().toString();

                Log.d("aaaaaaaaaaaa",email);
                Log.d("bbbbbbbbbbbb",password);

                ViewManager.getInstance().checkUserFromDatabase(email,password);
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
    }

}
