package com.Elemegi.Elemegi.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.Elemegi.Elemegi.Controller.ViewManager;
import com.Elemegi.Elemegi.Model.Comment;
import com.Elemegi.Elemegi.Model.Product;
import com.Elemegi.Elemegi.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class ProductPagePanel extends ViewManager implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    private AppCompatActivity act;
    BottomNavigationView navView2;
    NavigationView navigationView;
    ViewFlipper v_flipper;
    LinearLayout sliderDotspanel;
    private ConstraintLayout layout;
    private AnimationDrawable anim;
    private int dotscount;
    private ImageView[] dots;
    private float startX;
    private ImageView starImage;
    private ImageView commentsIcon;
    private ImageView profImage;
    private TextView description;
    private TextView price;
    private TextView rate;
    private TextView durationTime;
    private TextView commentsText;
    private TextView prodName;
    private TextView producerName;

    private boolean isFaved = false;
    private long productID;


    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        layout=findViewById(R.id.layout);
        anim=(AnimationDrawable)layout.getBackground();
        anim.setEnterFadeDuration(10);
        anim.setExitFadeDuration(1000);
        anim.start();*/

        setContentView(R.layout.product_page_page);
        navView2 = findViewById(R.id.nav_view_bottom);
        v_flipper = findViewById(R.id.v_flipper);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        starImage = (ImageView) findViewById(R.id.starImage);
        description = (TextView) findViewById(R.id.descriptionText);
        price = (TextView) findViewById(R.id.price);
        rate = (TextView) findViewById(R.id.rating);
        durationTime = (TextView) findViewById(R.id.prepTime);
        commentsIcon = (ImageView) findViewById(R.id.commentsIcon);
        commentsText = (TextView) findViewById(R.id.commentsText);
        profImage = (ImageView) findViewById(R.id.profImage);
        prodName = (TextView) findViewById(R.id.product_name_flipper);
        producerName = (TextView) findViewById(R.id.producer_name);
        navView2.setSelectedItemId(R.id.navigation_logo);
        navView2.getMenu().getItem(0).setCheckable(false);
        navView2.getMenu().getItem(1).setCheckable(false);
        navView2.getMenu().getItem(3).setCheckable(false);
        navView2.getMenu().getItem(4).setCheckable(false);

        Intent intent = getIntent();
        productID = intent.getLongExtra("id",0);
        final Product thisProduct = ViewManager.getInstance().getProductInfo(productID);

        boolean checkStar = ViewManager.getInstance().checkFav(thisProduct.getProductID(),ViewManager.getInstance().getCurrentUser().getID());
        if(checkStar){
            starImage.setImageResource(R.drawable.star_full);

        }
        else{
            starImage.setImageResource(R.drawable.star_empty);
        }

        description.setText(thisProduct.getDescription());
        price.setText(Double.toString(thisProduct.getPrice()));
        durationTime.setText(Integer.toString(thisProduct.getDeliverTime()));
        rate.setText(Double.toString(thisProduct.getOverallRating()));
        prodName.setText(thisProduct.getName());
        producerName.setText(thisProduct.getProducerName());
        Bitmap[] tempProductImages = convertToBitmap(thisProduct.getImage());
        dots = new ImageView[tempProductImages.length];
        for(int i = 0; i < tempProductImages.length; i++){

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        for (int i = 0; i < tempProductImages.length; i++){
            flipperImages(tempProductImages[i]);
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
                        break;
                    case MotionEvent.ACTION_UP:
                        float endX = event.getX();

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
        profImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(ViewManager.getInstance().openProfile());
            }
        });
        starImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFaved){
                    isFaved = false;
                    starImage.setImageResource(R.drawable.star_empty);
                    ViewManager.getInstance().updateFav(thisProduct.getProductID(),ViewManager.getInstance().getCurrentUser().getID());
                }
                else{
                    isFaved = true;
                    starImage.setImageResource(R.drawable.star_full);
                    ViewManager.getInstance().updateFav(thisProduct.getProductID(),ViewManager.getInstance().getCurrentUser().getID());
                }
            }
        });

        commentsIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(ViewManager.getInstance().openCommentsPanel(),thisProduct.getComments());
            }
        });
        commentsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(ViewManager.getInstance().openCommentsPanel(),thisProduct.getComments());
            }
        });

    }

    public void changeActivity(Class className) {
        startActivity(new Intent(act, className));
    }

    public void changeActivity(Class className, List<Comment> comments) {
        Intent myIntent = new Intent(act, className);
        myIntent.putExtra("id",productID);
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
    public void flipperImages(Bitmap image){
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(Bitmap.createScaledBitmap(image,600,580,true));
        v_flipper.addView(imageView);
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
                changeActivity(ViewManager.getInstance().openLoginPanel1());
                break;
        }
        return true;
    }

    private Bitmap[] convertToBitmap(List<String> images) {
        Bitmap[] newBitmap = new Bitmap[images.size()];
        for (int i = 0; i < images.size(); i++) {
            byte[] decodedString = Base64.decode(images.get(i),Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            newBitmap[i] = decodedByte;
        }
        return newBitmap;
    }
}
