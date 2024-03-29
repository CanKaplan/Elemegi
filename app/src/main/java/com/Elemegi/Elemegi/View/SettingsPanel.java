package com.Elemegi.Elemegi.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.Elemegi.Elemegi.Controller.MainManager;
import com.Elemegi.Elemegi.Controller.ViewManager;
import com.Elemegi.Elemegi.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class SettingsPanel extends ViewManager implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {
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
        navView2 = findViewById(R.id.nav_view_bottom);
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
        String userType = "";
        if(MainManager.getInstance().getCurrentUser() != null) {
            userType = MainManager.getInstance().getCurrentUser().getRoleType(); // User Typeına göre işlem yap customersa home_oage_page otherwise home_page_page_p
        }
        else {
            userType = "";
        }
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (userType.equals("Producer")){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.navigation_menu_p);
            navView2.getMenu().clear();
            navView2.inflateMenu(R.menu.navigation_menu_bottom_p);
        }

        navigationView.setNavigationItemSelectedListener(this);
        navView2.setOnNavigationItemSelectedListener(this);


    }
    public void changeActivity(Class className) {
        startActivity(new Intent(act, className));
    }

    public void changeActivity(Class className, long id) {
        Intent myIntent = new Intent(act, className);
        myIntent.putExtra("id", id);
        startActivity(myIntent);
    }
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.navigation_home:
                changeActivity(ViewManager.getInstance().openHomePagePanel());
                break;
            case R.id.navigation_profile:
                changeActivity(ViewManager.getInstance().openProfile());
                break;
            case R.id.navigation_logo:
                break;
            case R.id.navigation_search:
                changeActivity(ViewManager.getInstance().openSearchPanel());
                break;
            case R.id.navigation_add:
                changeActivity(ViewManager.getInstance().openAddProductPanel());
                break;
            case R.id.navigation_settings:
                break;
            case R.id.nav_favourites:
                changeActivity(ViewManager.getInstance().openFavouritePanel());
                break;
            case R.id.nav_my_orders:
            case R.id.nav_orders:
                break;
            case R.id.nav_help:
                changeActivity(ViewManager.getInstance().openHelpPanel());
                break;
            case R.id.nav_logout:
                changeActivity(ViewManager.getInstance().openLoginPanel1());
                break;
        }
        return true;
    }
}
