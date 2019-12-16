package com.Elemegi.Elemegi.View;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.Elemegi.Elemegi.Controller.ViewManager;
import com.Elemegi.Elemegi.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class SettingsPanel extends ViewManager {
    private AppCompatActivity act;
    BottomNavigationView navView2;
    NavigationView navigationView;
    private Switch s1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page);
        navView2 = findViewById(R.id.nav_view_bottom);
        navView2.setSelectedItemId(R.id.navigation_logo);
        navView2.getMenu().getItem(0).setCheckable(false);
        navView2.getMenu().getItem(1).setCheckable(false);
        navView2.getMenu().getItem(3).setCheckable(false);
        navView2.getMenu().getItem(4).setCheckable(true);
        myApp.setCurrentActivity(this);
        act = myApp.getCurrentActivity();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        s1 = (Switch)findViewById(R.id.s1);
        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                }
            }
        });


    }
}
