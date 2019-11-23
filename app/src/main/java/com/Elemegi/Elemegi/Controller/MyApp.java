package com.Elemegi.Elemegi.Controller;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;

public class MyApp extends Application {


    private boolean firstTime = false;
    public void onCreate() {
        super.onCreate();
    }

    private AppCompatActivity mCurrentActivity = null;
    public AppCompatActivity getCurrentActivity(){
        return mCurrentActivity;
    }
    public void setCurrentActivity(AppCompatActivity mCurrentActivity){
        this.mCurrentActivity = mCurrentActivity;
    }
    public void setFirstTime(boolean flag){
        firstTime = flag;
    }
    public boolean isFirstTime() {
        return firstTime;
    }
}