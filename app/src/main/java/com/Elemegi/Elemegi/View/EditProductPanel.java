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
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
import java.util.List;

public class EditProductPanel extends ViewManager {

    private boolean check1 = false;

    private AppCompatActivity act;
    private BottomNavigationView navView2;
    private NavigationView navigationView;
    private ImageView image2;
    private EditText newName;
    private EditText newDescription;
    private EditText newDeliveryTime;
    private EditText newPrice;
    private List<String> labels;
    private ConstraintLayout layout;
    private AnimationDrawable anim;

    private String imageString;
    private String nameString;
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
        image2 = (ImageView) findViewById(R.id.newImage2);

        saveButton = (FrameLayout) findViewById(R.id.saveButton);
        newName = (EditText) findViewById(R.id.editProdName);
        newDescription = (EditText) findViewById(R.id.editProdDes);
        newDeliveryTime = (EditText) findViewById(R.id.editProdDeliveryTime);
        newPrice = (EditText) findViewById(R.id.editProdPrice);

        Intent intent = getIntent();
        productID = intent.getLongExtra("id",0);

        Product currentProduct = ViewManager.getInstance().getProductInfo(productID);
        imageString = currentProduct.getImage();
        Bitmap tempProductImages = convertToBitmap(imageString);

        image2.setImageBitmap(tempProductImages);

        newName.setHint(currentProduct.getName());
        newDescription.setHint(currentProduct.getDescription());
        newDeliveryTime.setHint(String.valueOf(currentProduct.getDeliverTime()));
        newPrice.setHint(String.valueOf(currentProduct.getPrice()));
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
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
                ViewManager.getInstance().updateProduct(productID,nameString,descriptionString,Double.parseDouble(priceString),Integer.parseInt(deliveryTimeString),imageString);
                changeActivity(ViewManager.getInstance().openProductPagePanel(),productID);


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

    private Bitmap convertToBitmap(String images) {
        Bitmap newBitmap = null;
        byte[] decodedString = Base64.decode(images,Base64.NO_WRAP);
        newBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return newBitmap;
    }

    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProductPanel.this);
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

                    imageString = bitmapToBase64(scaledBitmap);
                }
            }
        }
    }

    public String bitmapToBase64(Bitmap image){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
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
        return BitmapFactory.decodeStream(imageStream);
    }

}

