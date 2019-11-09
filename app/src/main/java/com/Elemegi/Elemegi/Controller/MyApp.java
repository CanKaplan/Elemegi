package com.Elemegi.Elemegi.Controller;

import android.app.Activity;
import android.app.Application;

public class MyApp extends Application {


    private boolean firstTime = false;
    public void onCreate() {
        super.onCreate();
    }

    private Activity mCurrentActivity = null;
    public Activity getCurrentActivity(){
        return mCurrentActivity;
    }
    public void setCurrentActivity(Activity mCurrentActivity){
        this.mCurrentActivity = mCurrentActivity;
    }
    public void setFirstTime(boolean flag){
        firstTime = flag;
    }
    public boolean isFirstTime() {
        return firstTime;
    }
}