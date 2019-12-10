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

public class HomePagePanel extends ViewManager implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    private int currentPosition = 1;
    private AppCompatActivity act;
    BottomNavigationView navView2;
    NavigationView navigationView;
    ViewFlipper v_flipper;
    ViewFlipper v_flipper_product;
    private float startX;
    private float startY;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image5;
    private ImageView image6;
    private TextView text1;
    private TextView text2;
    private TextView text3;
    private TextView text4;
    private TextView text5;
    private TextView text6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_page);
        navView2 = findViewById(R.id.nav_view_bottom);
        v_flipper = findViewById(R.id.v_flipper);
        v_flipper_product = findViewById(R.id.v_flipper_big);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image4 = (ImageView) findViewById(R.id.image4);
        image5 = (ImageView) findViewById(R.id.image5);
        image6 = (ImageView) findViewById(R.id.image6);
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        text4 = (TextView) findViewById(R.id.text4);
        text5 = (TextView) findViewById(R.id.text5);
        text6 = (TextView) findViewById(R.id.text6);

        final int productImages[] = {R.drawable.pic3, R.drawable.pic2, R.drawable.pic1,R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic3, R.drawable.pic2, R.drawable.pic1, R.drawable.pic1, R.drawable.pic2, R.drawable.pic3,R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic3, R.drawable.pic2, R.drawable.pic1};
        final String productNames[] = {"aa","ab","ac","ba","bb","bc","ca","cb","cc","da","db","dc","ea","eb","ec","fa","fb","fc"};

        int images[] = {R.drawable.pic1, R.drawable.pic2, R.drawable.pic3};
        dotscount = images.length;
        dots = new ImageView[dotscount];

        image1.setImageResource(productImages[0]);
        image2.setImageResource(productImages[1]);
        image3.setImageResource(productImages[2]);
        image4.setImageResource(productImages[3]);
        image5.setImageResource(productImages[4]);
        image6.setImageResource(productImages[5]);
        text1.setText(productNames[0]);
        text2.setText(productNames[1]);
        text3.setText(productNames[2]);
        text4.setText(productNames[3]);
        text5.setText(productNames[4]);
        text6.setText(productNames[5]);
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

        v_flipper_product.setOnTouchListener(new View.OnTouchListener() {
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
                        startY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        float endX = event.getX();
                        float endY = event.getY();

                        //swipe right
                        if (startX < endX && currentPosition != 0 && Math.abs(startX-endX) >= 10) {
                            v_flipper_product.setOutAnimation(imgAnimationOut);
                            v_flipper_product.showPrevious();
                            currentPosition--;
                            image1.setImageResource(productImages[currentPosition*6 + 0]);
                            image2.setImageResource(productImages[currentPosition*6 + 1]);
                            image3.setImageResource(productImages[currentPosition*6 + 2]);
                            image4.setImageResource(productImages[currentPosition*6 + 3]);
                            image5.setImageResource(productImages[currentPosition*6 + 4]);
                            image6.setImageResource(productImages[currentPosition*6 + 5]);
                            text1.setText(productNames[currentPosition*6 + 0]);
                            text2.setText(productNames[currentPosition*6 + 1]);
                            text3.setText(productNames[currentPosition*6 + 2]);
                            text4.setText(productNames[currentPosition*6 + 3]);
                            text5.setText(productNames[currentPosition*6 + 4]);
                            text6.setText(productNames[currentPosition*6 + 5]);
                        }

                        //swipe left
                        if (startX > endX && currentPosition != 2 && Math.abs(startX-endX) >= 10) {
                            v_flipper_product.setInAnimation(imgAnimationIn);
                            v_flipper_product.showNext();
                            currentPosition++;
                            image1.setImageResource(productImages[currentPosition*6 + 0]);
                            image2.setImageResource(productImages[currentPosition*6 + 1]);
                            image3.setImageResource(productImages[currentPosition*6 + 2]);
                            image4.setImageResource(productImages[currentPosition*6 + 3]);
                            image5.setImageResource(productImages[currentPosition*6 + 4]);
                            image6.setImageResource(productImages[currentPosition*6 + 5]);
                            text1.setText(productNames[currentPosition*6 + 0]);
                            text2.setText(productNames[currentPosition*6 + 1]);
                            text3.setText(productNames[currentPosition*6 + 2]);
                            text4.setText(productNames[currentPosition*6 + 3]);
                            text5.setText(productNames[currentPosition*6 + 4]);
                            text6.setText(productNames[currentPosition*6 + 5]);
                        }
                        if(Math.abs(startX-endX) < 10){

                            int[] locImage = new int[2];
                            image1.getLocationOnScreen(locImage);
                            Log.d("aaaaaaaaaaaaaaaaaaa", String.valueOf(locImage[1]));
                            Log.d("bbbbbbbbbbbbbbbbbbb", String.valueOf(startY));
                            if(startX >= locImage[0] && startX <= (locImage[0] + image1.getWidth()) && startY >= 50 && startY <= 50 + image1.getHeight()){
                                Log.d("aaaaaaaaaaaaaaaaaaa", String.valueOf(locImage[1]));
                                Log.d("bbbbbbbbbbbbbbbbbbb", String.valueOf(startY));
                                changeActivity(ViewManager.getInstance().openProductPagePanel(),currentPosition*6 + 0);
                            }
                            image2.getLocationOnScreen(locImage);
                            if(startX >= locImage[0] && startX <= (locImage[0] + image1.getWidth()) && startY >= 50 && startY <= (50 + image2.getHeight())){
                                Log.d("aaaaaaaaaaaaaaaaaaa","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                                changeActivity(ViewManager.getInstance().openProductPagePanel(),currentPosition*6 + 1);
                            }
                            image3.getLocationOnScreen(locImage);
                            if(startX >= locImage[0] && startX <= (locImage[0] + image1.getWidth()) && startY >= 50 && startY <= (50 + image3.getHeight())){
                                Log.d("aaaaaaaaaaaaaaaaaaa","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                                changeActivity(ViewManager.getInstance().openProductPagePanel(),currentPosition*6 + 2);
                            }
                            image4.getLocationOnScreen(locImage);
                            if(startX >= locImage[0] && startX <= (locImage[0] + image1.getWidth()) && startY >= 360 && startY <= (360 + image4.getHeight())){
                                Log.d("aaaaaaaaaaaaaaaaaaa","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                                changeActivity(ViewManager.getInstance().openProductPagePanel(),currentPosition*6 + 3);
                            }
                            image5.getLocationOnScreen(locImage);
                            if(startX >= locImage[0] && startX <= (locImage[0] + image1.getWidth()) && startY >= 360 && startY <= (360 + image5.getHeight())){
                                Log.d("aaaaaaaaaaaaaaaaaaa","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                                changeActivity(ViewManager.getInstance().openProductPagePanel(),currentPosition*6 + 4);
                            }
                            image6.getLocationOnScreen(locImage);
                            if(startX >= locImage[0] && startX <= (locImage[0] + image6.getWidth()) && startY >= 360 && startY <= (360 + image6.getHeight())){
                                Log.d("aaaaaaaaaaaaaaaaaaa","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                                changeActivity(ViewManager.getInstance().openProductPagePanel(),currentPosition*6 + 5);
                            }
                        }
                        break;
                }

                return true;
            }

        });

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
                        if(Math.abs(startX-endX) < 10){
                            changeActivity(ViewManager.getInstance().openProductPagePanel(),v_flipper.getDisplayedChild());
                        }
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
        v_flipper.setAutoStart(true);
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
                changeActivity(ViewManager.getInstance().openLoginPanel1());
                break;
            case R.id.nav_help:
                //changeActivity(ViewManager.getInstance().openSettingsPanel());
                changeActivity(ViewManager.getInstance().openAddProductPanel());
                break;
            case R.id.nav_logout:
                //changeActivity(ViewManager.getInstance().openSettingsPanel());
                changeActivity(ViewManager.getInstance().openLoginPanel1());
                break;
        }
        return true;
    }
}
