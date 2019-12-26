package com.Elemegi.Elemegi.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

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

public class SearchPanel extends ViewManager implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private AppCompatActivity act;
    BottomNavigationView navView2;
    NavigationView navigationView;
    private ImageView searchButton;
    private EditText searchStringEdit;
    private String imageString;
    private String searchString;
    private List<String> labels = new ArrayList<>();
    public List<Product> currentProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seacrh_page);
        navView2 = findViewById(R.id.nav_view_bottom);
        searchButton = (ImageView) findViewById(R.id.searchButtonSearch);
        searchStringEdit = (EditText) findViewById(R.id.searchInput);
        ImageView imageSearch = (ImageView) findViewById(R.id.imageSearchIcon);

        imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchString = searchStringEdit.getText().toString();

                currentProducts = ViewManager.getInstance().getSearchResult(searchString);
                getSearchContent();
            }
        });

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
    private void getSearchContent(){
        LinearLayout myProductList;
        myProductList = (LinearLayout) findViewById(R.id.my_searched_products);
        Bitmap[] imageBitMap = new Bitmap[currentProducts.size()];
        for(int i = 0; i < currentProducts.size(); i++){
            imageBitMap[i] = convertToBitmap(currentProducts.get(i).getImage());
        }
        LinearLayout[] layoutToAdd = new LinearLayout[currentProducts.size()];
        for (int i = 0; i < currentProducts.size(); i++){
             layoutToAdd[i] = new LinearLayout(SearchPanel.this);

            layoutToAdd[i].setBackgroundColor(Color.parseColor("#FFFFFF"));
            layoutToAdd[i].setOrientation(LinearLayout.HORIZONTAL);
            layoutToAdd[i].setClickable(true);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1f);
            if(i == 0){
                params.setMargins(0, 12, 0, 0);
            }
            else{
                params.setMargins(0, 2, 0, 0);
            }
            layoutToAdd[i].setLayoutParams(params);
            layoutToAdd[i].setWeightSum(1);

            ImageView tempImage = new ImageView(SearchPanel.this); //Product Image
            tempImage.setImageBitmap(imageBitMap[i]);
            TableRow.LayoutParams imageParam = new TableRow.LayoutParams(150,150);
            imageParam.setMargins(10,10,0,10);
            tempImage.setLayoutParams(imageParam);
            layoutToAdd[i].addView(tempImage);

            TextView tempTitle = new TextView(SearchPanel.this); // Product Title
            tempTitle.setText(currentProducts.get(i).getName());
            tempTitle.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            tempTitle.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            tempTitle.setTextColor(Color.parseColor("#000F00"));
            layoutToAdd[i].addView(tempTitle);

            TextView prodDescription = new TextView(SearchPanel.this); //Product Description
            prodDescription.setText(currentProducts.get(i).getDescription());
            TableRow.LayoutParams paramDescription = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f);
            paramDescription.setMargins(50,30,0,30);
            prodDescription.setLayoutParams(paramDescription);
            prodDescription.setTextColor(Color.parseColor("#000000"));
            prodDescription.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            layoutToAdd[i].addView(prodDescription);

            TextView prodPrice = new TextView(SearchPanel.this); //Product Price
            prodPrice.setText(String.valueOf(currentProducts.get(i).getPrice()));
            TableRow.LayoutParams paramPrice = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f);
            paramPrice.setMargins(50,30,40,30);
            prodPrice.setLayoutParams(paramPrice);
            prodPrice.setTextColor(Color.parseColor("#000000"));
            prodPrice.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            layoutToAdd[i].addView(prodPrice);

            TextView prodDeliverTime = new TextView(SearchPanel.this); //Product Deliver Time
            prodDeliverTime.setText(String.valueOf(currentProducts.get(i).getDeliverTime()));
            TableRow.LayoutParams paramDeliverTime = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1f);
            paramDeliverTime.setMargins(50,30,50,30);
            prodDeliverTime.setLayoutParams(paramDeliverTime);
            prodDeliverTime.setTextColor(Color.parseColor("#000000"));
            prodDeliverTime.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            layoutToAdd[i].addView(prodDeliverTime);

            myProductList.addView(layoutToAdd[i]);
            final int finalI = i;
            layoutToAdd[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent = new Intent(SearchPanel.this, ViewManager.getInstance().openProductPagePanel());
                    myIntent.putExtra("id", currentProducts.get(finalI).getProductID());
                    startActivity(myIntent);
                }
            });

        }
    }

    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(SearchPanel.this);
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
                imageString = bitmapToBase64(scaledBitmap);
                labels = ViewManager.getInstance().getLabels(imageString);
                for (int i = 0; i < labels.size(); i++) {
                    Log.d("asadasdasdasdsa",labels.get(i));
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currentProducts = ViewManager.getInstance().searchLabelsFromDatabase(labels);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getSearchContent();
            }

            else{
                Uri selectedImage = data.getData();
                Bitmap tempBitmap = URIToBitmap(selectedImage);
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(tempBitmap,200,200,true);
                imageString = bitmapToBase64(scaledBitmap);
                labels = ViewManager.getInstance().getLabels(imageString);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currentProducts = ViewManager.getInstance().searchLabelsFromDatabase(labels);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getSearchContent();
            }
        }
    }

    public String bitmapToBase64(Bitmap image){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();

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

    private Bitmap convertToBitmap(String images) {
        Bitmap newBitmap;
        byte[] decodedString = Base64.decode(images,Base64.DEFAULT);
        newBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
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
                changeActivity(ViewManager.getInstance().openMyOrdersPanel());
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

    public void changeActivity(Class className) {
        Intent myIntent = new Intent(SearchPanel.this, className);
        startActivity(myIntent);
    }

    public void changeActivity(Class className, long id) {
        Intent myIntent = new Intent(SearchPanel.this, className);
        myIntent.putExtra("id", id);
        startActivity(myIntent);
    }


}
