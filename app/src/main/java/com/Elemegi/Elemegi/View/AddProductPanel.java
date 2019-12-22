package com.Elemegi.Elemegi.View;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class AddProductPanel extends ViewManager implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private static int imageCount  = 0;

    private boolean check1 = false;
    private boolean check2 = false;
    private boolean check3 = false;

    private AppCompatActivity act;
    private ImageView image2;
    private EditText newName;
    private EditText newDescription;
    private EditText newDeliveryTime;
    private EditText newPrice;
    private List<String> labels;
    private ConstraintLayout layout;
    private AnimationDrawable anim;

    private String nameString;
    private String imageString;
    private String descriptionString;
    private String deliveryTimeString;
    private String priceString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product_page);
        layout=findViewById(R.id.layout);
        anim=(AnimationDrawable)layout.getBackground();
        anim.setEnterFadeDuration(10);
        anim.setExitFadeDuration(1000);
        anim.start();
        BottomNavigationView navView2 = findViewById(R.id.nav_view_bottom);

        myApp.setCurrentActivity(this);
        act = myApp.getCurrentActivity();
        image2 = (ImageView) findViewById(R.id.newImage2);

        newName = (EditText) findViewById(R.id.editProdName);
        newDescription = (EditText) findViewById(R.id.editProdDes);
        newDeliveryTime = (EditText) findViewById(R.id.editProdDeliveryTime);
        newPrice = (EditText) findViewById(R.id.editProdPrice);

        FrameLayout frame2 = (FrameLayout) findViewById(R.id.newFrame2);
        FrameLayout frame4 = (FrameLayout) findViewById(R.id.addButton);



        frame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        frame4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             nameString = newName.getText().toString();
             descriptionString = newDescription.getText().toString();
             deliveryTimeString = newDeliveryTime.getText().toString();
             priceString = newPrice.getText().toString();

                int counter=0;
                if(ViewManager.getInstance().controlNameProduct(nameString).length() == 0){
                    counter++;
                }else{
                    newName.setError(ViewManager.getInstance().controlNameProduct(nameString));
                }
                if(ViewManager.getInstance().controlDescriptionProduct(descriptionString).length() == 0){
                    counter++;
                }
                else{
                    newDescription.setError(ViewManager.getInstance().controlDescriptionProduct(descriptionString));
                }
                if(ViewManager.getInstance().controlDeliveryTimeProduct(deliveryTimeString).length() == 0){
                    counter++;
                }
                else{
                    newDeliveryTime.setError(ViewManager.getInstance().controlDeliveryTimeProduct(deliveryTimeString));
                }
                if(ViewManager.getInstance().controlPriceProduct(priceString).length() == 0){
                    counter++;
                }
                else{
                    newPrice.setError(ViewManager.getInstance().controlPriceProduct(priceString));
                }

                if(counter == 4 && imageCount > 0){
                    labels = ViewManager.getInstance().getLabels(imageString);
                    Long tempResult = ViewManager.getInstance().addProduct(imageString,nameString,descriptionString,deliveryTimeString,priceString,labels);
                    if( tempResult!= 0) {
                        final CharSequence[] options = { "Yayy!" };
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddProductPanel.this);
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
                        changeActivity(ViewManager.getInstance().openProductPagePanel(),tempResult);
                    }
                    else {
                        final CharSequence[] options = { "Ok!" };
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddProductPanel.this);
                        builder.setTitle("Please Upload or Take a Photo!");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int item) {
                                if (options[item].equals("Ok"))
                                {
                                    dialog.dismiss();
                                }
                            }
                        });
                        builder.show();
                    }
                }
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
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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



    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(AddProductPanel.this);
        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1 );
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 0);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
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
                changeActivity(ViewManager.getInstance().openMyOrdersPanel());
                break;
            case R.id.nav_logout:
                changeActivity(ViewManager.getInstance().openLoginPanel1());
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode ==  1){
                Bitmap capturedImage = (Bitmap) data.getExtras().get("data");
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(capturedImage,200,200,true);
                image2.setVisibility(View.INVISIBLE);
                image2.setImageBitmap(scaledBitmap);
                image2.setVisibility(View.VISIBLE);
                if(!check1){
                    check1 = true;
                    imageCount++;
                    imageString = bitmapToBase64(scaledBitmap);
                }
            }
            else{
                Uri selectedImage = data.getData();
                Bitmap tempBitmap = URIToBitmap(selectedImage);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(tempBitmap,200,200,true);
                image2.setVisibility(View.INVISIBLE);
                image2.setImageBitmap(scaledBitmap);
                image2.setVisibility(View.VISIBLE);
                if(!check1){
                    check1 = true;
                    imageCount++;
                    imageString = bitmapToBase64(scaledBitmap);
                }
            }
        }
    }

    public String bitmapToBase64(Bitmap image){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        String encoded = Base64.encodeToString(byteArray, Base64.NO_WRAP);
        return encoded;
    }

    public Bitmap URIToBitmap(Uri uriImage){
        InputStream imageStream = null;
        try {
            imageStream = getContentResolver().openInputStream(uriImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
        return selectedImage;
    }
}
