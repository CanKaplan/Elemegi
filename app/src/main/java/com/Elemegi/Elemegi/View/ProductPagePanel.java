package com.Elemegi.Elemegi.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import com.Elemegi.Elemegi.Controller.ViewManager;
import com.Elemegi.Elemegi.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class ProductPagePanel extends ViewManager implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    private AppCompatActivity act;
    BottomNavigationView navView2;
    NavigationView navigationView;
    ViewFlipper v_flipper;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private float startX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_page_page);
        navView2 = findViewById(R.id.nav_view_bottom);
        v_flipper = findViewById(R.id.v_flipper);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);

        int images[] = {R.drawable.pic1, R.drawable.pic2, R.drawable.pic3};
        dotscount = images.length;
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        for (int i = 0; i < images.length; i++){
            flipperImages(images[i]);
        }
        for(int i = 0; i< dotscount; i++){
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
        }

        dots[v_flipper.getDisplayedChild()].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        myApp.setCurrentActivity(this);
        act = myApp.getCurrentActivity();

        v_flipper.addOnLayoutChangeListener(onLayoutChangeListener_viewFlipper);


        v_flipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                Animation imgAnimationIn = AnimationUtils.
                        loadAnimation(act, android.R.anim.slide_in_left);
                imgAnimationIn.setDuration(700);
                v_flipper.setInAnimation(imgAnimationIn);

                Animation imgAnimationOut = AnimationUtils.
                        loadAnimation(act, android.R.anim.slide_out_right);
                imgAnimationOut.setDuration(700);
                int action = event.getActionMasked();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        Log.d("asa", String.valueOf(startX));
                        break;
                    case MotionEvent.ACTION_UP:
                        float endX = event.getX();
                        Log.d("asa", String.valueOf(endX));

                        float endY = event.getY();

                        //swipe right
                        if (startX < endX && Math.abs(startX-endX) >= 10) {
                            v_flipper.setOutAnimation(imgAnimationOut);
                            v_flipper.showNext();
                        }

                        //swipe left
                        if (startX > endX && Math.abs(startX-endX) >= 10) {
                            v_flipper.setInAnimation(imgAnimationIn);
                            v_flipper.showPrevious();
                        }
                        for(int i = 0; i< dotscount; i++){
                            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                        }

                        dots[v_flipper.getDisplayedChild()].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                        break;
                }

                return true;
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

    View.OnLayoutChangeListener onLayoutChangeListener_viewFlipper = new View.OnLayoutChangeListener() {
        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
            for(int i = 0; i< dotscount; i++){
                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
            }

            dots[v_flipper.getDisplayedChild()].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
        }
    };
    public void flipperImages(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);
        v_flipper.addView(imageView);
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
