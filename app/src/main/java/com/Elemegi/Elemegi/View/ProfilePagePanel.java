package com.Elemegi.Elemegi.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.Elemegi.Elemegi.Controller.MainManager;
import com.Elemegi.Elemegi.Controller.ViewManager;
import com.Elemegi.Elemegi.Model.User;
import com.Elemegi.Elemegi.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class ProfilePagePanel extends ViewManager implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private AppCompatActivity act;
    BottomNavigationView navView3;
    NavigationView navigationView;
    LinearLayout editButton;
    TextView profileName;
    TextView profileEmail;
    TextView profilePhone;
    TextView profileAddress;
    ImageView profileImage;
    private User currentProfileUser;
    private long producerID = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page_page);
        navView3 = findViewById(R.id.nav_view_bottom);
        navView3.setSelectedItemId(R.id.navigation_profile);
        editButton = findViewById(R.id.layout3);

        profileAddress = (TextView) findViewById(R.id.profileAddress);
        profileName = (TextView) findViewById(R.id.profileName);
        profileEmail = (TextView) findViewById(R.id.profileEmail);
        profilePhone = (TextView) findViewById(R.id.profilePhone);
        profileImage = (ImageView) findViewById(R.id.profileImage);

        Intent intent = getIntent();
        producerID = intent.getLongExtra("id",0L);
        if(producerID != 0L){
            producerID = intent.getLongExtra("id",0);
            currentProfileUser = ViewManager.getInstance().getUserProfile(producerID);
        }
        else
        {
            currentProfileUser = ViewManager.getInstance().getCurrentUser();
        }

        Long currentUserID = ViewManager.getInstance().getCurrentUser().getID();
        profileName.setText(currentProfileUser.getName());
        profileEmail.setText(currentProfileUser.getEmail());
        profilePhone.setText(currentProfileUser.getPhoneNumber());
        profileAddress.setText(currentProfileUser.getAddress());
        profileImage.setImageBitmap(convertToBitmap(currentProfileUser.getImage()));

        myApp.setCurrentActivity(this);
        act = myApp.getCurrentActivity();

        if(currentUserID != currentProfileUser.getID()){
            editButton.setVisibility(View.INVISIBLE);
            ConstraintLayout.LayoutParams editParams = (ConstraintLayout.LayoutParams) editButton.getLayoutParams();
            editParams.topMargin = 5000;
            editParams.leftMargin = 5000;
            editButton.setLayoutParams(editParams);
        }
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
        String userType = "";
        if(MainManager.getInstance().getCurrentUser() != null) {
            userType = MainManager.getInstance().getCurrentUser().getRoleType(); // User Typeına göre işlem yap customersa home_oage_page otherwise home_page_page_p
        }
        else {
            userType = "";
        }
        navigationView = (NavigationView) findViewById(R.id.nav_view3);
        if (userType.equals("Producer")){
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.navigation_menu_p);
            navView3.getMenu().clear();
            navView3.inflateMenu(R.menu.navigation_menu_bottom_p);
        }
        navigationView.setNavigationItemSelectedListener(this);
        navView3.setOnNavigationItemSelectedListener(this);
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
                changeActivity(ViewManager.getInstance().openHomePagePanel());
                break;
            case R.id.navigation_profile:
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
                changeActivity(ViewManager.getInstance().openSettingsPanel());
                break;
            case R.id.nav_favourites:
                changeActivity(ViewManager.getInstance().openFavouritePanel());
                break;
            case R.id.nav_my_orders:
            case R.id.nav_orders:
                changeActivity(ViewManager.getInstance().openMyOrdersPanel());
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

    private Bitmap convertToBitmap(String images) {
        Bitmap newBitmap;
        byte[] decodedString = Base64.decode(images,Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        newBitmap = decodedByte;
        return newBitmap;
    }

}
