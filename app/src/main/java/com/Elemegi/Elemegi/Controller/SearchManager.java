package com.Elemegi.Elemegi.Controller;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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








}