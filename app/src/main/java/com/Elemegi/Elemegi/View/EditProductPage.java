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
import android.widget.LinearLayout;

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

public class EditProductPanel extends ViewManager implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private AppCompatActivity act;
    private BottomNavigationView navView2;
    private NavigationView navigationView;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private EditText newName;
    private EditText newDescription;
    private EditText newDeliveryTime;
    private EditText newPrice;
    private List<String> labels;
    private FrameLayout frame1;
    private FrameLayout frame2;
    private FrameLayout frame3;
    private FrameLayout frame4;
    private ConstraintLayout layout;
    private AnimationDrawable anim;

    private String nameString;
    private String[] images;
    private String descriptionString;
    private String deliveryTimeString;
    private String priceString;
    private LinearLayout lin1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_product_page);
        layout=findViewById(R.id.layout);
        anim=(AnimationDrawable)layout.getBackground();
        anim.setEnterFadeDuration(10);
        anim.setExitFadeDuration(1000);
        anim.start();
        navView2 = findViewById(R.id.nav_view_bottom);

        myApp.setCurrentActivity(this);
        act = myApp.getCurrentActivity();
        image1 = (ImageView) findViewById(R.id.newImage1);
        image2 = (ImageView) findViewById(R.id.newImage2);
        image3 = (ImageView) findViewById(R.id.newImage3);

        newName = (EditText) findViewById(R.id.editProdName);
        newDescription = (EditText) findViewById(R.id.editProdDes);
        newDeliveryTime = (EditText) findViewById(R.id.editProdDeliveryTime);
        newPrice = (EditText) findViewById(R.id.editProdPrice);

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

    private void selectImage(final int i) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProductPanel.this);
        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1 + (10 * i));
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2 + (10 * i));
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
                changeActivity(ViewManager.getInstance().openMyOrdersPanel());
                break;
            case R.id.nav_help:
                //changeActivity(ViewManager.getInstance().openSettingsPanel());
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
            if(requestCode % 10 ==  1){
                Bitmap capturedImage = (Bitmap) data.getExtras().get("data");
                if(requestCode / 10 == 1){
                    image1.setVisibility(View.INVISIBLE);
                    image1.setImageBitmap(capturedImage);
                    image1.setVisibility(View.VISIBLE);
                    image1.getLayoutParams().width = 330;
                    image1.getLayoutParams().height = 320;
                    if(!check1){
                        check1 = true;
                        imageCount++;
                        images[1] = bitmapToBase64(capturedImage);
                    }
                }
                else if(requestCode / 10 == 2){
                    image2.setVisibility(View.INVISIBLE);
                    image2.setImageBitmap(capturedImage);
                    image2.setVisibility(View.VISIBLE);
                    image2.getLayoutParams().width = 330;
                    image2.getLayoutParams().height = 320;
                    if(!check2){
                        check2 = true;
                        imageCount++;
                        images[2] = bitmapToBase64(capturedImage);
                    }
                }
                else{
                    image3.setVisibility(View.INVISIBLE);
                    image3.setImageBitmap(capturedImage);
                    image3.setVisibility(View.VISIBLE);
                    image3.getLayoutParams().width = 330;
                    image3.getLayoutParams().height = 320;
                    if(!check3){
                        check3 = true;
                        imageCount++;
                        images[3] = bitmapToBase64(capturedImage);
                    }
                }
            }
            else{
                Uri selectedImage = data.getData();

                if(requestCode / 10 == 1){
                    image1.setVisibility(View.INVISIBLE);
                    image1.setImageURI(selectedImage);
                    image1.setVisibility(View.VISIBLE);
                    image1.getLayoutParams().width = 330;
                    image1.getLayoutParams().height = 320;
                    if(!check1){
                        check1 = true;
                        imageCount++;
                        Bitmap tempBitmap = URIToBitmap(selectedImage);
                        images[1] = bitmapToBase64(tempBitmap);
                    }
                }
                else if(requestCode / 10 == 2){
                    image2.setVisibility(View.INVISIBLE);
                    image2.setImageURI(selectedImage);
                    image2.setVisibility(View.VISIBLE);
                    image2.getLayoutParams().width = 330;
                    image2.getLayoutParams().height = 320;
                    if(!check2){
                        check2 = true;
                        imageCount++;
                        Bitmap tempBitmap = URIToBitmap(selectedImage);
                        images[2] = bitmapToBase64(tempBitmap);
                    }
                }
                else{
                    image3.setVisibility(View.INVISIBLE);
                    image3.setImageURI(selectedImage);
                    image3.setVisibility(View.VISIBLE);
                    image3.getLayoutParams().width = 330;
                    image3.getLayoutParams().height = 320;
                    if(!check3){
                        check3 = true;
                        imageCount++;
                        Bitmap tempBitmap = URIToBitmap(selectedImage);
                        images[3] = bitmapToBase64(tempBitmap);
                    }
                }
            }
        }
    }

    public String bitmapToBase64(Bitmap image){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap resizedImage = Bitmap.createScaledBitmap(image,300,300,true);
        resizedImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
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

