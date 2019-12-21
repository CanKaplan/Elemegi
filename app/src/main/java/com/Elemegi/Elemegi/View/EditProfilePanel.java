package com.Elemegi.Elemegi.View;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.Elemegi.Elemegi.Controller.ViewManager;
import com.Elemegi.Elemegi.R;

public class EditProfilePanel extends ViewManager {
    private ConstraintLayout layout;
    private AnimationDrawable anim;
    private EditText profName;
    private EditText profAddress;
    private EditText profPhone;
    private EditText password1;
    private EditText password2;
    private EditText profEmail;
    private String nameString;
    private String addressString;
    private String phoneString;
    private String passwordString;
    private String password2String;
    private String emailString;
    private ImageView profImage;
    private LinearLayout saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout=findViewById(R.id.layout);
        anim=(AnimationDrawable)layout.getBackground();
        anim.setEnterFadeDuration(10);
        anim.setExitFadeDuration(1000);
        anim.start();
        setContentView(R.layout.edit_profile_page);

        profAddress = (EditText) findViewById(R.id.editProfAddress);
        profEmail = (EditText) findViewById(R.id.editProfEmail);
        profName = (EditText) findViewById(R.id.editProfName);
        password1 = (EditText) findViewById(R.id.editProfPassword);
        password2 = (EditText) findViewById(R.id.editProfPassword2);
        profPhone = (EditText) findViewById(R.id.editProfPhone);
        profImage = (ImageView) findViewById(R.id.editProfImg);
        saveButton = (LinearLayout) findViewById(R.id.layout3);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameString = profName.getText().toString();
                emailString = profEmail.getText().toString();
                passwordString = password1.getText().toString();
                password2String = password2.getText().toString();
                addressString = profAddress.getText().toString();
                phoneString = profPhone.getText().toString();
            }
        });

        profImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
/*
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
    }*/
}
