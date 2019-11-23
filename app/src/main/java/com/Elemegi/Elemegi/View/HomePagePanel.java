package com.Elemegi.Elemegi.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.Elemegi.Elemegi.Controller.ViewManager;
import com.Elemegi.Elemegi.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomePagePanel extends ViewManager implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private AppCompatActivity act;
    private AppBarConfiguration mAppBarConfiguration;
    BottomNavigationView navView2;
    NavigationView navigationView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_page_p);
        navView2 = findViewById(R.id.nav_view_bottom_p);
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
        navigationView = (NavigationView) findViewById(R.id.nav_view_p);
        navigationView.setNavigationItemSelectedListener(this);
        navView2.setOnNavigationItemSelectedListener(this);

    }

    public void changeActivity(Class className) {
        startActivity(new Intent(act, className));
    }


    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.navigation_home:
                Log.d("aaa","slm");
                break;
            case R.id.navigation_profile:
                changeActivity(ViewManager.getInstance().openLoginPanel1());
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
                changeActivity(ViewManager.getInstance().openLoginPanel1());
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
