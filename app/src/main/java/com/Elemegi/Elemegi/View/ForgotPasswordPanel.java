package com.Elemegi.Elemegi.View;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.Elemegi.Elemegi.Controller.ViewManager;
import com.Elemegi.Elemegi.R;

public class ForgotPasswordPanel extends ViewManager {
    private Button sendEmailButton;
    private TextView backButton;
    private String email;
    private AppCompatActivity act;
    private EditText emailEdit;
    private RelativeLayout layout;
    private AnimationDrawable anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_page);
        layout=findViewById(R.id.layout);
        anim=(AnimationDrawable)layout.getBackground();
        anim.setEnterFadeDuration(10);
        anim.setExitFadeDuration(1000);
        anim.start();
        myApp.setCurrentActivity(this);
        act = myApp.getCurrentActivity();

        sendEmailButton = (Button) findViewById(R.id.sendEmailButton);
        backButton = (TextView) findViewById(R.id.backButton);
        emailEdit = (EditText) findViewById(R.id.email);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(ViewManager.getInstance().openLoginPanel1());
            }
        });
        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailEdit.getText().toString();
                //db check
                emailEdit.setError("Wrong e-mail address! Please enter a valid e-mail address!");
            }
        });
    }

    public void changeActivity(Class className) {
        startActivity(new Intent(act, className));
    }

}
