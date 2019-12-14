package com.Elemegi.Elemegi.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.Elemegi.Elemegi.Controller.MainManager;
import com.Elemegi.Elemegi.Controller.ViewManager;
import com.Elemegi.Elemegi.Model.Order;
import com.Elemegi.Elemegi.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MyOrdersPanel extends ViewManager implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    private AppCompatActivity act;
    BottomNavigationView navView2;
    NavigationView navigationView;
    private ConstraintLayout layout;
    private AnimationDrawable anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout=findViewById(R.id.layout);
        anim=(AnimationDrawable)layout.getBackground();
        anim.setEnterFadeDuration(10);
        anim.setExitFadeDuration(1000);
        anim.start();
        setContentView(R.layout.my_orders_page_page);
        navView2 = findViewById(R.id.nav_view_bottom);
        LinearLayout myOrderList;

        Order[] myOrders = ViewManager.getMyOrderList(MainManager.getInstance().getCurrentUser().getID());
        Bitmap[] imageBitMap = new Bitmap[myOrders.length];
        for(int i = 0; i < myOrders.length; i++){
            imageBitMap[i] = convertToBitmap(myOrders[i].getProductImage());
        }

        myOrderList = (LinearLayout) findViewById(R.id.my_order_list);

        myApp.setCurrentActivity(this);
        act = myApp.getCurrentActivity();

        for (int i = 0; i < myOrders.length; i++){
            LinearLayout layoutToAdd = new LinearLayout(act);

            layoutToAdd.setBackgroundColor(Color.parseColor("#FFFFFF"));
            layoutToAdd.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1f);
            if(i == 0){
                params.setMargins(0, 1, 0, 0);
            }
            else{
                params.setMargins(0, 1, 0, 0);
            }
            layoutToAdd.setLayoutParams(params);
            layoutToAdd.setWeightSum(1);
            ImageView prodImage = new ImageView(act); //Product Image
            prodImage.setImageBitmap(imageBitMap[i]);
            TableRow.LayoutParams imageParam = new TableRow.LayoutParams(120,120);
            imageParam.setMargins(10,10,0,10);
            prodImage.setLayoutParams(imageParam);
            layoutToAdd.addView(prodImage);

            TextView prodName = new TextView(act); // Product Title
            prodName.setText(myOrders[i].getProductName());
            prodName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            prodName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            prodName.setTextColor(Color.parseColor("#000F00"));
            layoutToAdd.addView(prodName);

            TextView prodPrice = new TextView(act); //Product Price
            prodPrice.setText(String.valueOf(myOrders[i].getPrice()));
            TableRow.LayoutParams paramPrice = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f);
            paramPrice.setMargins(50,30,0,30);
            prodPrice.setLayoutParams(paramPrice);
            prodPrice.setTextColor(Color.parseColor("#000000"));
            prodPrice.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            layoutToAdd.addView(prodPrice);

            TextView prodRemainingTime = new TextView(act); //Product Remaining Time
            prodRemainingTime.setText(myOrders[i].getRemainingTime());
            TableRow.LayoutParams paramRemainingTime = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f);
            paramRemainingTime.setMargins(50,30,0,30);
            prodRemainingTime.setLayoutParams(paramRemainingTime);
            prodRemainingTime.setTextColor(Color.parseColor("#000000"));
            prodRemainingTime.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            layoutToAdd.addView(prodRemainingTime);

            myOrderList.addView(layoutToAdd);
        }


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

    private Bitmap convertToBitmap(String images) {
        Bitmap newBitmap;
        byte[] decodedString = Base64.decode(images,Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        newBitmap = decodedByte;
        return newBitmap;
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
                //changeActivity(ViewManager.getInstance().openSearchPanel());
                break;
            case R.id.navigation_add:
                changeActivity(ViewManager.getInstance().openAddProductPanel());
                break;
            case R.id.navigation_settings:
                //changeActivity(ViewManager.getInstance().openSettingsPanel());
                break;
            case R.id.nav_categories:
                //changeActivity(ViewManager.getInstance().openSettingsPanel());
                break;
            case R.id.nav_favourites:
                //changeActivity(ViewManager.getInstance().openSettingsPanel());
                break;
            case R.id.nav_my_orders:
                break;
            case R.id.nav_help:
                //changeActivity(ViewManager.getInstance().openSettingsPanel());
                break;
            case R.id.nav_orders:
                //changeActivity(ViewManager.getInstance().openSettingsPanel());
                break;
            case R.id.nav_logout:
                changeActivity(ViewManager.getInstance().openLoginPanel1());
                break;
        }
        return true;
    }

}
