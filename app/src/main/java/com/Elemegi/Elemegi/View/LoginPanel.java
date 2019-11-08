package com.Elemegi.Elemegi.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.Elemegi.Elemegi.Controller.MainManager;
import com.Elemegi.Elemegi.MainActivity;
import com.Elemegi.Elemegi.R;
import com.Elemegi.Elemegi.Controller.ViewManager;

public class LoginPanel extends MainActivity {

    private Button loginButton;
    private Button registerButton;
    private TextView forgotPassword;
    private String email;
    private String password;
    private boolean rememberMe;

    public LoginPanel() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        loginButton=(Button)findViewById(R.id.loginButton);
        registerButton=(Button)findViewById(R.id.registerButton);
        forgotPassword=(TextView) findViewById(R.id.forgotPasswordButton);
        email = (((EditText) findViewById(R.id.email)).toString());
        password = ((EditText) findViewById(R.id.password)).toString();
        rememberMe = ((CheckBox) findViewById(R.id.rememberMe)).isChecked();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tara doğruysa
                    MainManager.getInstance().setUserProperties(email);
                    ViewManager.getInstance().openHomePagePanel();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("aasfdgfhgkjkşli","sadasdfghgj");
                ViewManager.getInstance().openRegisterPanel();
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });


    }
    public void openRegister(){
        Intent intent=new Intent(this, RegisterPanel.class);
        startActivity(intent);
    }

}
