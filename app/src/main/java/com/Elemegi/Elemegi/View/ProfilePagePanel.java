package com.Elemegi.Elemegi.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.Elemegi.Elemegi.Controller.ViewManager;
import com.Elemegi.Elemegi.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class ProfilePagePanel extends ViewManager implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private AppCompatActivity act;
    BottomNavigationView navView3;
    NavigationView navigationView;
    LinearLayout editButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page_page);
        navView3 = findViewById(R.id.nav_view_bottom);
        navView3.setSelectedItemId(R.id.navigation_profile);
        editButton = findViewById(R.id.layout3);

        myApp.setCurrentActivity(this);
        act = myApp.getCurrentActivity();
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(ViewManager.getInstance().openEditProfilePanel());
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view3);
        navigationView.setNavigationItemSelectedListener(this);
        navView3.setOnNavigationItemSelectedListener(this);
    }

    public void changeActivity(Class className) {
        startActivity(new Intent(act, className));
        finish();
    }

    public void changeActivity(Class className, int id) {
        Intent myIntent = new Intent(act, className);
        myIntent.putExtra("id", id);
        startActivity(myIntent);
        finish();
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.navigation_home:
                changeActivity(ViewManager.getInstance().openHomePagePanel());
                break;
            case R.id.navigation_profile:
                break;
            case R.id.navigation_logo:
                Log.d("aaa","nbr");
                break;
            case R.id.navigation_search:
                //changeActivity(ViewManager.getInstance().openSearchPanel());
                break;
            case R.id.navigation_settings:
                //changeActivity(ViewManager.getInstance().openSettingsPanel());
                break;
            case R.id.nav_categories:
                //changeActivity(ViewManager.getInstance().openSettingsPanel());
                changeActivity(ViewManager.getInstance().openLoginPanel1());
                break;
            case R.id.nav_favourites:
                //changeActivity(ViewManager.getInstance().openSettingsPanel());
                changeActivity(ViewManager.getInstance().openLoginPanel1());
                break;
            case R.id.nav_my_orders:
                //changeActivity(ViewManager.getInstance().openSettingsPanel());
                changeActivity(ViewManager.getInstance().openMyOrdersPanel());
                break;
            case R.id.nav_help:
                //changeActivity(ViewManager.getInstance().openSettingsPanel());
                changeActivity(ViewManager.getInstance().openLoginPanel1());
                break;
            case R.id.nav_logout:
                //changeActivity(ViewManager.getInstance().openSettingsPanel());
                changeActivity(ViewManager.getInstance().openLoginPanel1());
                break;
        }
        return true;
    }
}
