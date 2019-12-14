package com.Elemegi.Elemegi.View;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.Elemegi.Elemegi.Controller.ViewManager;
import com.Elemegi.Elemegi.R;

public class ChangePasswordPanel extends ViewManager {
    private Button changeButton;
    private String password1;
    private String password2;
    private AppCompatActivity act;
    private EditText passwordEdit1;
    private EditText passwordEdit2;
    private RelativeLayout layout;
    private AnimationDrawable anim;
    private String email = "";  // Mailin kimden geldiği öğrenilicek
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_page);
        layout=findViewById(R.id.layout);
        anim=(AnimationDrawable)layout.getBackground();
        anim.setEnterFadeDuration(10);
        anim.setExitFadeDuration(1000);
        anim.start();
        myApp.setCurrentActivity(this);
        act = myApp.getCurrentActivity();

        changeButton = (Button) findViewById(R.id.changeButton);
        passwordEdit1 = (EditText) findViewById(R.id.password1);
        passwordEdit2 = (EditText) findViewById(R.id.password2);

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password1 = passwordEdit1.getText().toString();
                password2 = passwordEdit2.getText().toString();
                if(ViewManager.getInstance().controlPasswordRegister(password1,password2).length() == 0){
                    //set password
                    changeActivity(ViewManager.getInstance().openLoginPanel1());
                }
                else{
                    passwordEdit2.setError(ViewManager.getInstance().controlPasswordRegister(password1,password2));
                }
            }
        });
    }

    public void changeActivity(Class className) {
        startActivity(new Intent(act, className));
    }

}