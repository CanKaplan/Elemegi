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
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.Elemegi.Elemegi.Controller.ViewManager;
import com.Elemegi.Elemegi.Model.Product;
import com.Elemegi.Elemegi.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EditProductPanel extends ViewManager implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    private static int imageCount  = 1;
    private boolean check1 = false;
    private boolean check2 = false;
    private boolean check3 = false;

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
    private ConstraintLayout layout;
    private AnimationDrawable anim;

    private String nameString;
    private List<String> images = new ArrayList<>();
    private String descriptionString;
    private String deliveryTimeString;
    private String priceString;
    private FrameLayout saveButton;
    private LinearLayout lin1;
    private long productID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_product_page);
        layout=findViewById(R.id.layout);
        anim=(AnimationDrawable)layout.getBackground();
        anim.setEnterFadeDuration(10);
        anim.setExitFadeDuration(1000);
        anim.start();

        myApp.setCurrentActivity(this);
        act = myApp.getCurrentActivity();
        image1 = (ImageView) findViewById(R.id.newImage1);
        image2 = (ImageView) findViewById(R.id.newImage2);
        image3 = (ImageView) findViewById(R.id.newImage3);

        saveButton = (FrameLayout) findViewById(R.id.saveButton);
        newName = (EditText) findViewById(R.id.editProdName);
        newDescription = (EditText) findViewById(R.id.editProdDes);
        newDeliveryTime = (EditText) findViewById(R.id.editProdDeliveryTime);
        newPrice = (EditText) findViewById(R.id.editProdPrice);

        Intent intent = getIntent();
        productID = intent.getLongExtra("id",0);

        Product currentProduct = ViewManager.getInstance().getProductInfo(productID);
        Bitmap[] tempProductImages = convertToBitmap(currentProduct.getImage());

        image1.setImageBitmap(tempProductImages[0]);

        if(tempProductImages.length > 1){
            image2.setImageBitmap(tempProductImages[1]);
        }
        if(tempProductImages.length > 2){
            image3.setImageBitmap(tempProductImages[2]);
        }

        newName.setHint(currentProduct.getName());
        newDescription.setHint(currentProduct.getDescription());
        newDeliveryTime.setHint(String.valueOf(currentProduct.getDeliverTime()));
        newPrice.setHint(String.valueOf(currentProduct.getPrice()));
        for (int i = 0; i < tempProductImages.length; i++) {
            images.add(i,bitmapToBase64(tempProductImages[i]));
        }
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(1);
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(2);
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(3);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameString = newName.getText().toString();
                descriptionString = newDescription.getText().toString();
                priceString = newPrice.getText().toString();
                deliveryTimeString = newDeliveryTime.getText().toString();

                if(nameString.length() == 0){
                    newName.setText(newName.getHint().toString());
                    nameString = newName.getText().toString();
                }
                if(descriptionString.length() == 0){
                    newDescription.setText(newDescription.getHint().toString());
                    descriptionString = newDescription.getText().toString();
                }
                if(deliveryTimeString.length() == 0){
                    newDeliveryTime.setText(newDeliveryTime.getHint().toString());
                    deliveryTimeString = newDeliveryTime.getText().toString();
                }
                if(priceString.length() == 0){
                    newPrice.setText(newPrice.getHint().toString());
                    priceString = newPrice.getText().toString();
                }

                if(imageCount > 0){
                    ViewManager.getInstance().updateProduct(productID,nameString,descriptionString,Double.parseDouble(priceString),Integer.parseInt(deliveryTimeString),images);
                    changeActivity(ViewManager.getInstance().openProductPagePanel(),productID);
                }


            }
        });

    }

    public void changeActivity(Class className) {
        startActivity(new Intent(act, className));
    }

    public void changeActivity(Class className, long id) {
        Intent myIntent = new Intent(act, className);
        myIntent.putExtra("id", id);
        startActivity(myIntent);
    }

    private Bitmap[] convertToBitmap(List<String> images) {
        Bitmap[] newBitmap = new Bitmap[images.size()];
        for (int i = 0; i < images.size(); i++) {
            byte[] decodedString = Base64.decode(images.get(i),Base64.NO_WRAP);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            newBitmap[i] = decodedByte;
        }
        return newBitmap;
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
                        images.add(0,bitmapToBase64(capturedImage));
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
                        images.add(1,bitmapToBase64(capturedImage));
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
                        images.add(2,bitmapToBase64(capturedImage));
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
                        Bitmap tempBitmap = URIToBitmap(selectedImage);
                        images.add(0, bitmapToBase64(tempBitmap));
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
                        images.add(1,bitmapToBase64(tempBitmap));
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
                        images.add(2, bitmapToBase64(tempBitmap));
                    }
                }
            }
        }
    }

    public String bitmapToBase64(Bitmap image){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 30, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
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
}

