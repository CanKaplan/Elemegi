package com.Elemegi.Elemegi.Controller;


import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.Elemegi.Elemegi.R;
import com.Elemegi.Elemegi.View.LoginPanel;
import com.Elemegi.Elemegi.View.RegisterPanel;

import static androidx.core.content.ContextCompat.startActivity;

public class ViewManager {
    private final static ViewManager instance = new ViewManager();
    public ViewManager() {
       //setContentView(R.layout.login_page);
    }

    public static ViewManager getInstance() {
        return instance;
    }



    public void openHomePagePanel() {

    }

    public void openRegisterPanel() {
        //setContentView(R.layout.login_page);
        RegisterPanel RegisterPanel = new RegisterPanel();
    }

    public void openLoginPanel() {
       // startActivity(new Intent(this, LoginPanel.class));

    }
}
