package com.Elemegi.Elemegi.View;


import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.Elemegi.Elemegi.Controller.ViewManager;
import com.Elemegi.Elemegi.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class EditProductPanel extends ViewManager implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private AppCompatActivity act;
    private BottomNavigationView navView2;
    private NavigationView navigationView;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private EditText newName;
    private EditText newDescription;
    private EditText newDeliveryTime;
    private EditText newPrice;
    private List<String> labels;
    private FrameLayout frame1;
    private FrameLayout frame2;
    private FrameLayout frame3;
    private FrameLayout frame4;
    private ConstraintLayout layout;
    private AnimationDrawable anim;

    private String nameString;
    private String[] images;
    private String descriptionString;
    private String deliveryTimeString;
    private String priceString;
    private LinearLayout lin1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_product_page);
        layout=findViewById(R.id.layout);
        anim=(AnimationDrawable)layout.getBackground();
        anim.setEnterFadeDuration(10);
        anim.setExitFadeDuration(1000);
        anim.start();
        navView2 = findViewById(R.id.nav_view_bottom);

        myApp.setCurrentActivity(this);
        act = myApp.getCurrentActivity();
        image1 = (ImageView) findViewById(R.id.newImage1);
        image2 = (ImageView) findViewById(R.id.newImage2);
        image3 = (ImageView) findViewById(R.id.newImage3);

        newName = (EditText) findViewById(R.id.editProdName);
        newDescription = (EditText) findViewById(R.id.editProdDes);
        newDeliveryTime = (EditText) findViewById(R.id.editProdDeliveryTime);
        newPrice = (EditText) findViewById(R.id.editProdPrice);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navView2.setOnNavigationItemSelectedListener(this);
    }

    public void changeActivity(Class className) {
        startActivity(new Intent(act, className));
    }

    public void changeActivity(Class className, int id) {
        Intent myIntent = new Intent(act, className);
        myIntent.putExtra("id", id);
        startActivity(myIntent);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch(menuItem.getItemId()) {
            case R.id.navigation_home:
                break;
            case R.id.navigation_profile:
                changeActivity(ViewManager.getInstance().openProfile());
                break;
            case R.id.navigation_logo:
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
                break;
            case R.id.nav_logout:
                changeActivity(ViewManager.getInstance().openLoginPanel1());
                break;
        }
        return true;
    }
}

