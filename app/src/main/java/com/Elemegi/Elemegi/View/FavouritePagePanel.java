package com.Elemegi.Elemegi.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import androidx.drawerlayout.widget.DrawerLayout;

import com.Elemegi.Elemegi.Controller.MainManager;
import com.Elemegi.Elemegi.Controller.ViewManager;
import com.Elemegi.Elemegi.Model.Product;
import com.Elemegi.Elemegi.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class FavouritePagePanel extends ViewManager implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private AppCompatActivity act;
    BottomNavigationView navView2;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_fav_page);
        navView2 = findViewById(R.id.nav_view_bottom);
        myApp.setCurrentActivity(this);
        act = myApp.getCurrentActivity();
        final List<Product> myProducts = ViewManager.getFavProdList(MainManager.getInstance().getCurrentUser().getID());
        LinearLayout myProductList;
        myProductList = (LinearLayout) findViewById(R.id.my_fav_list);
        Bitmap[] imageBitMap = new Bitmap[myProducts.size()];
        for(int i = 0; i < myProducts.size(); i++){
            imageBitMap[i] = convertToBitmap(myProducts.get(i).getImage());
        }
        for (int i = 0; i < myProducts.size(); i++){
            LinearLayout layoutToAdd = new LinearLayout(act);

            layoutToAdd.setBackgroundColor(Color.parseColor("#FFFFFF"));
            layoutToAdd.setOrientation(LinearLayout.HORIZONTAL);
            layoutToAdd.setClickable(true);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1f);
            if(i == 0){
                params.setMargins(0, 12, 0, 0);
            }
            else{
                params.setMargins(0, 2, 0, 0);
            }
            layoutToAdd.setLayoutParams(params);
            layoutToAdd.setWeightSum(1);

            ImageView tempImage = new ImageView(act); //Product Image
            tempImage.setImageBitmap(imageBitMap[i]);
            TableRow.LayoutParams imageParam = new TableRow.LayoutParams(150,150);
            imageParam.setMargins(10,10,0,10);
            tempImage.setLayoutParams(imageParam);
            layoutToAdd.addView(tempImage);

            TextView tempTitle = new TextView(act); // Product Title
            tempTitle.setText(myProducts.get(i).getName());
            tempTitle.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            tempTitle.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            tempTitle.setTextColor(Color.parseColor("#000F00"));
            layoutToAdd.addView(tempTitle);

            TextView prodDescription = new TextView(act); //Product Description
            prodDescription.setText(myProducts.get(i).getDescription());
            TableRow.LayoutParams paramDescription = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f);
            paramDescription.setMargins(50,30,0,30);
            prodDescription.setLayoutParams(paramDescription);
            prodDescription.setTextColor(Color.parseColor("#000000"));
            prodDescription.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            layoutToAdd.addView(prodDescription);

            TextView prodPrice = new TextView(act); //Product Price
            prodPrice.setText(String.valueOf(myProducts.get(i).getPrice()));
            TableRow.LayoutParams paramPrice = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f);
            paramPrice.setMargins(50,30,40,30);
            prodPrice.setLayoutParams(paramPrice);
            prodPrice.setTextColor(Color.parseColor("#000000"));
            prodPrice.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            layoutToAdd.addView(prodPrice);

            TextView prodDeliverTime = new TextView(act); //Product Deliver Time
            prodDeliverTime.setText(String.valueOf(myProducts.get(i).getDeliverTime()));
            TableRow.LayoutParams paramDeliverTime = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f);
            paramDeliverTime.setMargins(50,30,50,30);
            prodDeliverTime.setLayoutParams(paramDeliverTime);
            prodDeliverTime.setTextColor(Color.parseColor("#000000"));
            prodDeliverTime.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            layoutToAdd.addView(prodDeliverTime);

            myProductList.addView(layoutToAdd);
            final int finalI = i;
            layoutToAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeActivity(ViewManager.getInstance().openProductPagePanel(),myProducts.get(finalI).getProductID());
                }
            });


        }


    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);

    DrawerLayout drawer = findViewById(R.id.drawer_layout);

    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.addDrawerListener(toggle);
    toggle.setDrawerIndicatorEnabled(true);
    toggle.syncState();
    navigationView = (NavigationView) findViewById(R.id.nav_view);
    navigationView.setNavigationItemSelectedListener(this);
    navView2.setOnNavigationItemSelectedListener(this);

    }
    private Bitmap[] convertToBitmap(String[] images) {
        Bitmap[] newBitmap = new Bitmap[images.length];
        for (int i = 0; i < images.length; i++) {
            byte[] decodedString = Base64.decode(String.valueOf(images[i]),Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            newBitmap[i] = decodedByte;
        }
        return newBitmap;
    }

    private Bitmap convertToBitmap(String images) {
        Bitmap newBitmap;
        byte[] decodedString = Base64.decode(images,Base64.DEFAULT);
        newBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
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
                changeActivity(ViewManager.getInstance().openSearchPanel());
                break;
            case R.id.navigation_add:
                changeActivity(ViewManager.getInstance().openAddProductPanel());
                break;
            case R.id.navigation_settings:
                //changeActivity(ViewManager.getInstance().openSettingsPanel());
                break;
            case R.id.nav_categories:
                //changeActivity(ViewManager.getInstance().openCategoriesPanel());
                break;
            case R.id.nav_favourites:
                break;
            case R.id.nav_my_orders:
            case R.id.nav_orders:
                changeActivity(ViewManager.getInstance().openMyOrdersPanel());
                break;
            case R.id.nav_help:
                //changeActivity(ViewManager.getInstance().openHelpPanel());
                break;
            case R.id.nav_logout:
                changeActivity(ViewManager.getInstance().openLoginPanel1());
                break;
        }
        return true;
    }

    public void changeActivity(Class className) {
        startActivity(new Intent(act, className));
    }

    public void changeActivity(Class className, long id) {
        Intent myIntent = new Intent(act, className);
        myIntent.putExtra("id", id);
        startActivity(myIntent);
    }


}
