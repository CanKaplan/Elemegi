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
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.Elemegi.Elemegi.Controller.ViewManager;
import com.Elemegi.Elemegi.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditProfilePanel extends ViewManager {
    private ConstraintLayout layout;
    private AnimationDrawable anim;
    private EditText profName;
    private EditText profAddress;
    private EditText profPhone;
    private EditText password1;
    private EditText password2;
    private EditText profEmail;
    private String nameString = "";
    private String addressString = "";
    private String phoneString = "";
    private String passwordString = "";
    private String password2String = "";
    private String emailString = "";
    private ImageView profImage;
    private LinearLayout saveButton;
    String imageString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_page);

        profAddress = (EditText) findViewById(R.id.editProfAddress);
        profEmail = (EditText) findViewById(R.id.editProfEmail);
        profName = (EditText) findViewById(R.id.editProfName);
        password1 = (EditText) findViewById(R.id.editProfPassword);
        password2 = (EditText) findViewById(R.id.editProfPassword2);
        profPhone = (EditText) findViewById(R.id.editProfPhone);
        profImage = (ImageView) findViewById(R.id.editProfImg);

        profAddress.setHint(getCurrentUser().getAddress());
        profEmail.setHint(getCurrentUser().getEmail());
        profName.setHint(getCurrentUser().getName());
        password1.setHint(getCurrentUser().getPassword());
        profPhone.setHint(getCurrentUser().getPhoneNumber());
        profImage.setImageBitmap(convertToBitmap(getCurrentUser().getImage()));

        saveButton = (LinearLayout) findViewById(R.id.layout3);


        profImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameString = profName.getText().toString();
                emailString = profEmail.getText().toString();
                passwordString = password1.getText().toString();
                password2String = password2.getText().toString();
                addressString = profAddress.getText().toString();
                phoneString = profPhone.getText().toString();
                boolean statement = ViewManager.getInstance().updateProfile(ViewManager.getInstance().getCurrentUser().getID(),nameString,emailString,passwordString,password2String,addressString,phoneString,imageString);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!statement){
                    password1.setError("Password is wrong!");
                }else {
                    ViewManager.getInstance().checkUserFromDatabase(emailString,password2String);
                    try {
                        Thread.sleep(1900);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    changeActivity(ViewManager.getInstance().openProfile(), ViewManager.getInstance().getCurrentUser().getID());
                }
            }
        });

        profImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    public void changeActivity(Class className, long id) {
        Intent myIntent = new Intent(EditProfilePanel.this, className);
        myIntent.putExtra("id", id);
        startActivity(myIntent);
    }

    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfilePanel.this);
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
                profImage.setVisibility(View.INVISIBLE);
                profImage.setImageBitmap(scaledBitmap);
                profImage.setVisibility(View.VISIBLE);
                imageString = bitmapToBase64(scaledBitmap);
            }
            else{
                Uri selectedImage = data.getData();
                Bitmap tempBitmap = URIToBitmap(selectedImage);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(tempBitmap,200,200,true);
                profImage.setVisibility(View.INVISIBLE);
                profImage.setImageBitmap(scaledBitmap);
                profImage.setVisibility(View.VISIBLE);
                imageString = bitmapToBase64(scaledBitmap);
            }
        }
    }

    public String bitmapToBase64(Bitmap image){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }
    private Bitmap convertToBitmap(String images) {
        Bitmap newBitmap = null;
        byte[] decodedString = Base64.decode(images,Base64.NO_WRAP);
        newBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return newBitmap;
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
