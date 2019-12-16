package com.Elemegi.Elemegi.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.Elemegi.Elemegi.Controller.MainManager;
import com.Elemegi.Elemegi.Controller.ViewManager;
import com.Elemegi.Elemegi.Model.Product;
import com.Elemegi.Elemegi.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomePagePanel extends ViewManager implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    private int currentPosition = 1;
    private String userType = "";
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
    private TextView sliderNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(MainManager.getInstance().getCurrentUser() != null) {
            userType = MainManager.getInstance().getCurrentUser().getRoleType(); // User Typeına göre işlem yap customersa home_oage_page otherwise home_page_page_p
        }
        else {
            userType = "";
        }
        if(userType.equals("Customer")) {
            setContentView(R.layout.home_page_page);
            String[] imagesForSlide = ViewManager.createHomePageSliderContent(MainManager.getInstance().getCurrentUser().getID());
            final String[] namesForSlide = ViewManager.createHomePageSliderNames(MainManager.getInstance().getCurrentUser().getID());
            final String[] imagesForBottom = ViewManager.createHomePageImages(MainManager.getInstance().getCurrentUser().getID());
            final String[] namesForBottom = ViewManager.createHomePageNames(MainManager.getInstance().getCurrentUser().getID());
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
            sliderNames = (TextView) findViewById(R.id.product_name_flipper);

            dotscount = 3;
            dots = new ImageView[dotscount];

            Bitmap[] decodedBytesSlider;
            final Bitmap[] decodedBytesBottom;
            decodedBytesSlider = convertToBitmap(imagesForSlide);
            decodedBytesBottom = convertToBitmap(imagesForBottom);

            image1.setImageBitmap(decodedBytesBottom[0]);
            image2.setImageBitmap(decodedBytesBottom[1]);
            image3.setImageBitmap(decodedBytesBottom[2]);
            image4.setImageBitmap(decodedBytesBottom[3]);
            image5.setImageBitmap(decodedBytesBottom[4]);
            image6.setImageBitmap(decodedBytesBottom[5]);
            text1.setText(namesForBottom[0]);
            text2.setText(namesForBottom[1]);
            text3.setText(namesForBottom[2]);
            text4.setText(namesForBottom[3]);
            text5.setText(namesForBottom[4]);
            text6.setText(namesForBottom[5]);
            for (int i = 0; i < dotscount; i++) {

                dots[i] = new ImageView(this);
                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                params.setMargins(8, 0, 8, 0);

                sliderDotspanel.addView(dots[i], params);

            }

            for (int i = 0; i < 3; i++) {
                flipperImages(decodedBytesSlider[i]);
            }
            for (int i = 0; i < dotscount; i++) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
            }
            sliderNames.setText(namesForSlide[v_flipper.getDisplayedChild()]);
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
                            if (startX < endX && currentPosition != 0 && Math.abs(startX - endX) >= 10) {
                                v_flipper_product.setOutAnimation(imgAnimationOut);
                                v_flipper_product.showPrevious();
                                currentPosition--;
                                image1.setImageBitmap(decodedBytesBottom[currentPosition * 6 + 0]);
                                image2.setImageBitmap(decodedBytesBottom[currentPosition * 6 + 1]);
                                image3.setImageBitmap(decodedBytesBottom[currentPosition * 6 + 2]);
                                image4.setImageBitmap(decodedBytesBottom[currentPosition * 6 + 3]);
                                image5.setImageBitmap(decodedBytesBottom[currentPosition * 6 + 4]);
                                image6.setImageBitmap(decodedBytesBottom[currentPosition * 6 + 5]);
                                text1.setText(namesForBottom[currentPosition * 6 + 0]);
                                text2.setText(namesForBottom[currentPosition * 6 + 1]);
                                text3.setText(namesForBottom[currentPosition * 6 + 2]);
                                text4.setText(namesForBottom[currentPosition * 6 + 3]);
                                text5.setText(namesForBottom[currentPosition * 6 + 4]);
                                text6.setText(namesForBottom[currentPosition * 6 + 5]);
                            }

                            //swipe left
                            if (startX > endX && currentPosition != 2 && Math.abs(startX - endX) >= 10) {
                                v_flipper_product.setInAnimation(imgAnimationIn);
                                v_flipper_product.showNext();
                                currentPosition++;
                                image1.setImageBitmap(decodedBytesBottom[currentPosition * 6 + 0]);
                                image2.setImageBitmap(decodedBytesBottom[currentPosition * 6 + 1]);
                                image3.setImageBitmap(decodedBytesBottom[currentPosition * 6 + 2]);
                                image4.setImageBitmap(decodedBytesBottom[currentPosition * 6 + 3]);
                                image5.setImageBitmap(decodedBytesBottom[currentPosition * 6 + 4]);
                                image6.setImageBitmap(decodedBytesBottom[currentPosition * 6 + 5]);
                                text1.setText(namesForBottom[currentPosition * 6 + 0]);
                                text2.setText(namesForBottom[currentPosition * 6 + 1]);
                                text3.setText(namesForBottom[currentPosition * 6 + 2]);
                                text4.setText(namesForBottom[currentPosition * 6 + 3]);
                                text5.setText(namesForBottom[currentPosition * 6 + 4]);
                                text6.setText(namesForBottom[currentPosition * 6 + 5]);
                            }
                            if (Math.abs(startX - endX) < 10) {

                                int[] locImage = new int[2];
                                image1.getLocationOnScreen(locImage);
                                Log.d("aaaaaaaaaaaaaaaaaaa", String.valueOf(locImage[1]));
                                Log.d("bbbbbbbbbbbbbbbbbbb", String.valueOf(startY));
                                if (startX >= locImage[0] && startX <= (locImage[0] + image1.getWidth()) && startY >= 50 && startY <= 50 + image1.getHeight()) {
                                    Log.d("aaaaaaaaaaaaaaaaaaa", String.valueOf(locImage[1]));
                                    Log.d("bbbbbbbbbbbbbbbbbbb", String.valueOf(startY));
                                    changeActivity(ViewManager.getInstance().openProductPagePanel(), currentPosition * 6 + 0);
                                }
                                image2.getLocationOnScreen(locImage);
                                if (startX >= locImage[0] && startX <= (locImage[0] + image1.getWidth()) && startY >= 50 && startY <= (50 + image2.getHeight())) {
                                    Log.d("aaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                                    changeActivity(ViewManager.getInstance().openProductPagePanel(), currentPosition * 6 + 1);
                                }
                                image3.getLocationOnScreen(locImage);
                                if (startX >= locImage[0] && startX <= (locImage[0] + image1.getWidth()) && startY >= 50 && startY <= (50 + image3.getHeight())) {
                                    Log.d("aaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                                    changeActivity(ViewManager.getInstance().openProductPagePanel(), currentPosition * 6 + 2);
                                }
                                image4.getLocationOnScreen(locImage);
                                if (startX >= locImage[0] && startX <= (locImage[0] + image1.getWidth()) && startY >= 360 && startY <= (360 + image4.getHeight())) {
                                    Log.d("aaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                                    changeActivity(ViewManager.getInstance().openProductPagePanel(), currentPosition * 6 + 3);
                                }
                                image5.getLocationOnScreen(locImage);
                                if (startX >= locImage[0] && startX <= (locImage[0] + image1.getWidth()) && startY >= 360 && startY <= (360 + image5.getHeight())) {
                                    Log.d("aaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                                    changeActivity(ViewManager.getInstance().openProductPagePanel(), currentPosition * 6 + 4);
                                }
                                image6.getLocationOnScreen(locImage);
                                if (startX >= locImage[0] && startX <= (locImage[0] + image6.getWidth()) && startY >= 360 && startY <= (360 + image6.getHeight())) {
                                    Log.d("aaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                                    changeActivity(ViewManager.getInstance().openProductPagePanel(), currentPosition * 6 + 5);
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
                            if (startX < endX && Math.abs(startX - endX) >= 10) {
                                v_flipper.setOutAnimation(imgAnimationOut);
                                v_flipper.showNext();
                            }

                            //swipe left
                            if (startX > endX && Math.abs(startX - endX) >= 10) {
                                v_flipper.setInAnimation(imgAnimationIn);
                                v_flipper.showPrevious();
                            }
                            for (int i = 0; i < dotscount; i++) {
                                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                            }
                            sliderNames.setText(namesForSlide[v_flipper.getDisplayedChild()]);
                            dots[v_flipper.getDisplayedChild()].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                            if (Math.abs(startX - endX) < 10) {
                                changeActivity(ViewManager.getInstance().openProductPagePanel(), v_flipper.getDisplayedChild());
                            }
                            break;
                    }

                    return true;
                }
            });

        }
        else{
            setContentView(R.layout.my_products_page_page);

            navView2 = findViewById(R.id.nav_view_bottom);

            myApp.setCurrentActivity(this);
            act = myApp.getCurrentActivity();

            Product[] myProducts = ViewManager.getMyProdList(MainManager.getInstance().getCurrentUser().getID());


            LinearLayout myProductList;
            myProductList = (LinearLayout) findViewById(R.id.my_order_list);
            Bitmap[] imageBitMap = new Bitmap[myProducts.length];
            for(int i = 0; i < myProducts.length; i++){
                 imageBitMap[i] = convertToBitmap(myProducts[i].getImage())[0];
            }
            for (int i = 0; i < myProducts.length; i++){
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
                ImageView tempImage = new ImageView(act); //Product Image
                tempImage.setImageBitmap(imageBitMap[i]);
                TableRow.LayoutParams imageParam = new TableRow.LayoutParams(120,120);
                imageParam.setMargins(10,10,0,10);
                tempImage.setLayoutParams(imageParam);
                layoutToAdd.addView(tempImage);

                TextView tempTitle = new TextView(act); // Product Title
                tempTitle.setText(myProducts[i].getName());
                tempTitle.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                tempTitle.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                tempTitle.setTextColor(Color.parseColor("#000F00"));
                layoutToAdd.addView(tempTitle);

                TextView prodDescription = new TextView(act); //Product Description
                prodDescription.setText(myProducts[i].getDescription());
                TableRow.LayoutParams paramDescription = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f);
                paramDescription.setMargins(50,30,0,30);
                prodDescription.setLayoutParams(paramDescription);
                prodDescription.setTextColor(Color.parseColor("#000000"));
                prodDescription.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                layoutToAdd.addView(prodDescription);

                TextView prodPrice = new TextView(act); //Product Price
                prodPrice.setText(String.valueOf(myProducts[i].getPrice()));
                TableRow.LayoutParams paramPrice = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f);
                paramPrice.setMargins(50,30,0,30);
                prodPrice.setLayoutParams(paramPrice);
                prodPrice.setTextColor(Color.parseColor("#000000"));
                prodPrice.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                layoutToAdd.addView(prodPrice);

                TextView prodDeliverTime = new TextView(act); //Product Deliver Time
                prodDeliverTime.setText(myProducts[i].getDeliverTime());
                TableRow.LayoutParams paramDeliverTime = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f);
                paramDeliverTime.setMargins(50,30,0,30);
                prodDeliverTime.setLayoutParams(paramDeliverTime);
                prodDeliverTime.setTextColor(Color.parseColor("#000000"));
                prodDeliverTime.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                layoutToAdd.addView(prodDeliverTime);

                myProductList.addView(layoutToAdd);
            }
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
    public void changeActivity(Class className) {
        startActivity(new Intent(act, className));
    }

    public void changeActivity(Class className, long id) {
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
    public void flipperImages(Bitmap image){
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(image);
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
                changeActivity(ViewManager.getInstance().openMyOrdersPanel());
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
