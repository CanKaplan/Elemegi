package com.Elemegi.Elemegi.View;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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

import java.util.List;

public class MyOrdersPanel extends ViewManager implements BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {
    private AppCompatActivity act;
    BottomNavigationView navView2;
    NavigationView navigationView;
    private ConstraintLayout layout;
    private AnimationDrawable anim;
    private Dialog rankDialog;
    private RatingBar ratingBar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        layout=findViewById(R.id.layout);
        anim=(AnimationDrawable)layout.getBackground();
        anim.setEnterFadeDuration(10);
        anim.setExitFadeDuration(1000);
        anim.start();

         */
        setContentView(R.layout.my_orders_page_page);
        navView2 = findViewById(R.id.nav_view_bottom);
        TextView phoneNumberText = (TextView) findViewById(R.id.priceAndPhoneNumber);
        TextView addressText = (TextView) findViewById(R.id.rateAndAddress);
        TextView note = (TextView) findViewById(R.id.noteVis);
        TextView nameForCustomer = (TextView) findViewById(R.id.nameForCustomer);
        TextView timeForOrders = (TextView) findViewById(R.id.timeForOrders);

        LinearLayout myOrderList;

        String userType = "";
        if(MainManager.getInstance().getCurrentUser() != null) {
            userType = MainManager.getInstance().getCurrentUser().getRoleType(); // User Typeına göre işlem yap customersa home_oage_page otherwise home_page_page_p
        }
        else {
            userType = "";
        }
        List<Order> tempOrders;
        if (userType.equals("Customer")) {
            tempOrders = ViewManager.getMyOrderList(MainManager.getInstance().getCurrentUser().getID(),0);
        }
        else{
            nameForCustomer.setText("Customer");
            phoneNumberText.setText("Phone");
            addressText.setText("Address");
            TableRow.LayoutParams custNameParamText = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            custNameParamText.setMargins(20,0,0,0);
            nameForCustomer.setLayoutParams(custNameParamText);

            TableRow.LayoutParams custPhoneParamText = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            custPhoneParamText.setMargins(25,0,20,0);
            phoneNumberText.setLayoutParams(custPhoneParamText);

            TableRow.LayoutParams custNoteParamText = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            custNoteParamText.setMargins(50,0,70,0);
            note.setLayoutParams(custNoteParamText);

            TableRow.LayoutParams custAddressParamText = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            custAddressParamText.setMargins(120,0,40,0);
            addressText.setLayoutParams(custAddressParamText);

            TableRow.LayoutParams custTimeParamText = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            custTimeParamText.setMargins(10,0,0,0);
            addressText.setLayoutParams(custTimeParamText);

            tempOrders = ViewManager.getMyOrderList(MainManager.getInstance().getCurrentUser().getID(),1);

        }

        final List<Order> myOrders = tempOrders;
        Bitmap[] imageBitMap = new Bitmap[myOrders.size()];
        for(int i = 0; i < myOrders.size(); i++){
            imageBitMap[i] = convertToBitmap(myOrders.get(i).getProductImage());
        }

        myOrderList = (LinearLayout) findViewById(R.id.my_order_list);

        myApp.setCurrentActivity(this);
        act = myApp.getCurrentActivity();

        for (int i = 0; i < myOrders.size(); i++){
            LinearLayout layoutToAdd = new LinearLayout(act);
            boolean isRated = ViewManager.getInstance().isRated(myOrders.get(i).getProductID(),ViewManager.getInstance().getCurrentUser().getID());
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
            layoutToAdd.setWeightSum(2);
            ImageView prodImage = new ImageView(act); //Product Image
            prodImage.setImageBitmap(imageBitMap[i]);
            TableRow.LayoutParams imageParam = new TableRow.LayoutParams(120,120);
            imageParam.setMargins(10,10,0,10);
            prodImage.setLayoutParams(imageParam);
            layoutToAdd.addView(prodImage);


            if(userType.equals("Customer")) {
                TextView prodName = new TextView(act); // Product Title
                prodName.setText(myOrders.get(i).getProductName());
                prodName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                prodName.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                prodName.setWidth(140);
                TableRow.LayoutParams paramName = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f);
                paramName.setMargins(10, 30, 0, 30);
                prodName.setLayoutParams(paramName);
                prodName.setTextColor(Color.parseColor("#000F00"));
                layoutToAdd.addView(prodName);

                TextView prodPrice = new TextView(act); //Product Price
                prodPrice.setText(String.valueOf(myOrders.get(i).getPrice()));
                TableRow.LayoutParams paramPrice = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f);
                paramPrice.setMargins(50, 30, 30, 30);
                prodPrice.setLayoutParams(paramPrice);
                prodPrice.setTextColor(Color.parseColor("#000000"));
                prodPrice.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                layoutToAdd.addView(prodPrice);

                TextView prodNote = new TextView(act); //Product Note
                prodNote.setText(myOrders.get(i).getNote());
                TableRow.LayoutParams paramNote = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f);
                paramNote.setMargins(0, 30, 100, 30);
                prodNote.setLayoutParams(paramNote);
                prodNote.setTextColor(Color.parseColor("#000000"));
                prodNote.setWidth(250);
                prodNote.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                layoutToAdd.addView(prodNote);

                final ImageView prodRate = new ImageView(act); //Product rate button
                prodRate.setId(R.id.rateButton);
                prodRate.setImageResource(R.drawable.comment);
                TableRow.LayoutParams imageProdParam = new TableRow.LayoutParams(80,120);
                imageProdParam.setMargins(0,10,30,10);
                prodRate.setLayoutParams(imageProdParam);
                layoutToAdd.addView(prodRate);


                TextView prodRemainingTime = new TextView(act); //Product Remaining Time
                prodRemainingTime.setText(String.valueOf(myOrders.get(i).getRemainingTime()));
                TableRow.LayoutParams paramRemainingTime = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f);
                paramRemainingTime.setMargins(35,30,70,30);
                prodRemainingTime.setLayoutParams(paramRemainingTime);
                prodRemainingTime.setTextColor(Color.parseColor("#000000"));
                prodRemainingTime.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                layoutToAdd.addView(prodRemainingTime);

                if(myOrders.get(i).getRemainingTime() > 0 || !isRated)
                    prodRate.setVisibility(View.INVISIBLE);
                else
                    prodRate.setVisibility(View.VISIBLE);



                final int finalI = i;
                prodRate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rankDialog = new Dialog(MyOrdersPanel.this, R.style.FullHeightDialog);
                        rankDialog.setContentView(R.layout.rank_dialog);
                        rankDialog.setCancelable(true);
                        ratingBar = (RatingBar)rankDialog.findViewById(R.id.dialog_ratingbar);
                        ratingBar.setRating(5);

                        TextView text = (TextView) rankDialog.findViewById(R.id.rank_dialog_text1);
                        text.setText("Give Rate");

                        Button updateButton = (Button) rankDialog.findViewById(R.id.rank_dialog_button);
                        updateButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                float rati2 = ratingBar.getRating();
                                ViewManager.getInstance().giveRate(myOrders.get(finalI).getProductID(),ViewManager.getInstance().getCurrentUser().getID(),(int)rati2);
                                prodRate.setVisibility(View.INVISIBLE);
                                rankDialog.dismiss();
                            }
                        });
                        //now that the dialog is set up, it's time to show it
                        rankDialog.show();
                    }
                });
                prodImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeActivity(ViewManager.getInstance().openProductPagePanel(),myOrders.get(finalI).getProductID());
                    }
                });
            }
            else {
                TextView prodCustName = new TextView(act); //Product Cust Name
                prodCustName.setText(myOrders.get(i).getCustomerName());
                TableRow.LayoutParams prodCustParam = new TableRow.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                prodCustName.setTextColor(Color.parseColor("#000000"));
                prodCustName.setWidth(150);
                prodCustParam.setMargins(30,10,10,10);
                prodCustName.setLayoutParams(prodCustParam);
                layoutToAdd.addView(prodCustName);


                TextView prodNote = new TextView(act); //Product Phone
                prodNote.setText(myOrders.get(i).getCustomerTelephone());
                TableRow.LayoutParams paramNote = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f);
                paramNote.setMargins(0, 30, 50, 30);
                prodNote.setLayoutParams(paramNote);
                prodNote.setTextColor(Color.parseColor("#000000"));
                prodNote.setWidth(60);
                prodNote.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                layoutToAdd.addView(prodNote);

                TextView prodNote1 = new TextView(act); //Product Note
                prodNote1.setText(myOrders.get(i).getNote());
                TableRow.LayoutParams paramNote1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                paramNote1.setMargins(5, 30, 0, 30);
                prodNote1.setLayoutParams(paramNote1);
                prodNote1.setTextColor(Color.parseColor("#000000"));
                prodNote1.setWidth(180);
                prodNote1.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                layoutToAdd.addView(prodNote1);

                TextView prodAdress = new TextView(act); //Product Note
                prodAdress.setText(myOrders.get(i).getNote());
                TableRow.LayoutParams paramAddress = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                paramAddress.setMargins(0, 30, 10, 30);
                prodAdress.setLayoutParams(paramAddress);
                prodAdress.setTextColor(Color.parseColor("#000000"));
                prodAdress.setWidth(180);
                prodAdress.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                layoutToAdd.addView(prodAdress);

                TextView prodRemainingTime = new TextView(act); //Product Remaining Time
                prodRemainingTime.setText(String.valueOf(myOrders.get(i).getRemainingTime()));
                TableRow.LayoutParams paramRemainingTime = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f);
                paramRemainingTime.setMargins(35,30,0,30);
                prodRemainingTime.setLayoutParams(paramRemainingTime);
                prodRemainingTime.setTextColor(Color.parseColor("#000000"));
                prodRemainingTime.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                layoutToAdd.addView(prodRemainingTime);


                final int finalI = i;
                prodImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeActivity(ViewManager.getInstance().openProductPagePanel(),myOrders.get(finalI).getProductID());
                    }
                });
            }
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
