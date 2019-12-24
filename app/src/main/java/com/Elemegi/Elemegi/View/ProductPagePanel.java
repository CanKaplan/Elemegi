package com.Elemegi.Elemegi.View;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
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
    private Button editButton;
    private Button orderButton;
    private Button deleteButton;
    private boolean isFaved = false;
    private long productID;
    private ImageView productImage;


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
        navigationView = (NavigationView) findViewById(R.id.nav_view);
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
        deleteButton = findViewById(R.id.deleteButtonProduct);
        editButton = findViewById(R.id.editButtonProduct);
        orderButton = findViewById(R.id.orderButtonProduct);
        navView2.setSelectedItemId(R.id.navigation_logo);
        productImage = (ImageView) findViewById(R.id.productImageID);
        navView2.getMenu().getItem(0).setCheckable(false);
        navView2.getMenu().getItem(1).setCheckable(false);
        navView2.getMenu().getItem(3).setCheckable(false);
        navView2.getMenu().getItem(4).setCheckable(false);

        Intent intent = getIntent();
        productID = intent.getLongExtra("id",0);
        final Product thisProduct = ViewManager.getInstance().getProductInfo(productID);
        String userType = "";
        if(ViewManager.getInstance().getCurrentUser() != null) {
            userType = ViewManager.getInstance().getCurrentUser().getRoleType(); // User Typeına göre işlem yap customersa home_oage_page otherwise home_page_page_p
        }
        else {
            userType = "";
        }
        if(userType.equals("Customer")){
            starImage.setVisibility(View.VISIBLE);
            boolean checkStar = ViewManager.getInstance().checkFav(thisProduct.getProductID(),ViewManager.getInstance().getCurrentUser().getID());
            if(checkStar){
                starImage.setImageResource(R.drawable.star_full);
            }
            else{
                starImage.setImageResource(R.drawable.star_empty);
            }
            orderButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.INVISIBLE);
            editButton.setVisibility(View.INVISIBLE);
            ConstraintLayout.LayoutParams editParams = (ConstraintLayout.LayoutParams) editButton.getLayoutParams();
            editParams.topMargin = 5000;
            editParams.leftMargin = 5000;
            editButton.setLayoutParams(editParams);
            deleteButton.setLayoutParams(editParams);
            ConstraintLayout.LayoutParams orderParams = (ConstraintLayout.LayoutParams) orderButton.getLayoutParams();
            orderParams.topMargin = 1383;
            orderParams.leftMargin = 425;
            orderButton.setLayoutParams(orderParams);
        }
        else{
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.navigation_menu_p);
            navView2.getMenu().clear();
            navView2.inflateMenu(R.menu.navigation_menu_bottom_p);
            starImage.setVisibility(View.INVISIBLE);
            orderButton.setVisibility(View.INVISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.VISIBLE);
            ConstraintLayout.LayoutParams orderParams = (ConstraintLayout.LayoutParams) orderButton.getLayoutParams();
            orderParams.topMargin = 5000;
            orderParams.leftMargin = 5000;
            orderButton.setLayoutParams(orderParams);
            ConstraintLayout.LayoutParams editParams = (ConstraintLayout.LayoutParams) editButton.getLayoutParams();
            editParams.topMargin = 1383;
            editParams.leftMargin = 300;
            editButton.setLayoutParams(editParams);
            ConstraintLayout.LayoutParams deleteParams = (ConstraintLayout.LayoutParams) deleteButton.getLayoutParams();
            deleteParams.topMargin = 1383;
            deleteParams.leftMargin = 550;
            deleteButton.setLayoutParams(deleteParams);
        }

        description.setText(thisProduct.getDescription());
        price.setText(Double.toString(thisProduct.getPrice()));
        durationTime.setText(Integer.toString(thisProduct.getDeliverTime()));
        rate.setText(Double.toString(thisProduct.getOverallRating()));
        prodName.setText(thisProduct.getName());
        producerName.setText(thisProduct.getProducerName());
        Bitmap tempProductImages = convertToBitmap(thisProduct.getImage());
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(tempProductImages,600,600,true);
        productImage.setImageBitmap(scaledBitmap);

        myApp.setCurrentActivity(this);
        act = myApp.getCurrentActivity();



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navView2.setOnNavigationItemSelectedListener(this);
        profImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(ViewManager.getInstance().openProfile(),thisProduct.getProducerId());
            }
        });
        if(userType.equals("Customer")) {
            starImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFaved) {
                        isFaved = false;
                        starImage.setImageResource(R.drawable.star_empty);
                        ViewManager.getInstance().updateFav(thisProduct.getProductID(), ViewManager.getInstance().getCurrentUser().getID());
                    } else {
                        isFaved = true;
                        starImage.setImageResource(R.drawable.star_full);
                        ViewManager.getInstance().updateFav(thisProduct.getProductID(), ViewManager.getInstance().getCurrentUser().getID());
                    }
                }
            });
        }
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
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = { "Delete Product", "Cancel" };
                AlertDialog.Builder builder = new AlertDialog.Builder(ProductPagePanel.this);
                builder.setTitle("Delete Product!");

                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (options[item].equals("Delete Product"))
                        {
                            ViewManager.getInstance().deleteProduct(productID);
                            changeActivity(ViewManager.getInstance().openHomePagePanel());
                        }
                        else if (options[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(ViewManager.getInstance().openEditProductPanel(),productID);
            }
        });
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProductPagePanel.this);
                builder.setTitle("Add Note");

                final EditText input = new EditText(ProductPagePanel.this);
                builder.setView(input);

                builder.setPositiveButton("Order", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String asda = input.getText().toString();
                        ViewManager.getInstance().giveOrder(productID,ViewManager.getInstance().getCurrentUser().getID(),asda);
                        final CharSequence[] options = { "Yayy!" };
                        AlertDialog.Builder builder = new AlertDialog.Builder(ProductPagePanel.this);
                        builder.setTitle("Congratulations! Product Succesfully Added into the System!");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                if (options[item].equals("Yayy"))
                                {
                                    dialog.dismiss();
                                }
                            }
                        });
                        builder.show();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                builder.show();
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

    public void changeActivity(Class className, long id) {
        Intent myIntent = new Intent(act, className);
        myIntent.putExtra("id",id);
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
                //changeActivity(ViewManager.getInstance().openSettingsPanel());
                break;
            case R.id.nav_categories:
                //changeActivity(ViewManager.getInstance().openCategoriesPanel());
                break;
            case R.id.nav_favourites:
                changeActivity(ViewManager.getInstance().openFavouritePanel());
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

    private Bitmap convertToBitmap(String images) {
        Bitmap newBitmap;
        byte[] decodedString = Base64.decode(images,Base64.NO_WRAP);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        newBitmap = decodedByte;
        return newBitmap;
    }
}
