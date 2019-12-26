package com.Elemegi.Elemegi.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.Elemegi.Elemegi.Controller.ViewManager;
import com.Elemegi.Elemegi.R;

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
    private Boolean saveLogin;
    private AppCompatActivity act;
    private RelativeLayout layout;
    private AnimationDrawable anim;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
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

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        Intent myIntent = getIntent();
        boolean result = false;
        result = myIntent.getBooleanExtra("statement",false);
        if(result){
            editor.putBoolean("saveLogin", false); // Storing boolean - true/false
            editor.remove("email");
            editor.remove("password");
            editor.commit();
        }

        saveLogin = pref.getBoolean("saveLogin", false);
        if(saveLogin){
            ViewManager.getInstance().checkUserFromDatabase(pref.getString("email",""),pref.getString("password",""));
            changeActivity(ViewManager.getInstance().openHomePagePanel());
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = emailEdit.getText().toString();
                password = passwordEdit.getText().toString();

                if(ViewManager.getInstance().checkUserFromDatabase(email,password)){
                    if(rememberMeBox.isChecked()){
                        editor.putBoolean("saveLogin", true); // Storing boolean - true/false
                        editor.putString("email", email); // Storing string
                        editor.putString("password", password); // Storing string
                        editor.commit();
                    }
                    changeActivity(ViewManager.getInstance().openHomePagePanel());
                    //changeActivity(ViewManager.getInstance().openFavouritePanel());
                }
                else{
                    emailEdit.setError("Wrong Email or Password");
                    passwordEdit.setError("Wrong Email or Password");
                }
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
            }
        });


    }


    public void changeActivity(Class className) {
        startActivity(new Intent(act, className));
    }

}
