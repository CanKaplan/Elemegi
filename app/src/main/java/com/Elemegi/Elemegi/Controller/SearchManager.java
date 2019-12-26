package com.Elemegi.Elemegi.Controller;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.Elemegi.Elemegi.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class SearchManager extends MainManager {
    public SearchManager(){

    }







//TODO this part is necessary to use search on map feature for Search Manager
    //TODO reference : https://ingenuity.ph/blog/android-maps-with-google-places-api-web-service/

    MapsActivity mapsActivity=null; //initialization
    LatLng latLng=null;
    int globalRadius=10000;

    public void initMaps(long latitude, long longitude){
            mapsActivity  = new MapsActivity();
        latLng = new LatLng(latitude, longitude); //coordinates
    }

    public void addMarker(GoogleMap map, String myTitle ) {
        map.addMarker(new MarkerOptions()
                .position(latLng)
                .title(myTitle));
    }

    public void addDetailedMarker(GoogleMap map, String population) {
        map.addMarker(new MarkerOptions()
                .position(latLng)
                .title("")
                .snippet(population)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        );

    }

    public void addCircles(GoogleMap map) {
        Circle circle = map.addCircle(new CircleOptions()
                .center(latLng)
                .radius(globalRadius)
                .strokeWidth(10)
                .strokeColor(Color.GREEN)
                .fillColor(Color.argb(128,255,0,0))
                .clickable(true));

    }

    public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
        Location currentLocation;
        FusedLocationProviderClient fusedLocationProviderClient;
        private static final int REQUEST_CODE = 101;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            fetchLocation();
        }
        private void fetchLocation() {
            if (ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
                return;
            }
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        currentLocation = location;
                        Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.myMap);
                        assert supportMapFragment != null;
                        supportMapFragment.getMapAsync(MainActivity.this);
                    }
                }
            });
        }
        @Override
        public void onMapReady(GoogleMap googleMap) {
            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
            googleMap.addMarker(markerOptions);
        }
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            switch (requestCode) {
                case REQUEST_CODE:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        fetchLocation();
                    }
                    break;
            }
        }
    }







}