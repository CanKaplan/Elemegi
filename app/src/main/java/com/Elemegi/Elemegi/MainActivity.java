package com.Elemegi.Elemegi;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import com.Elemegi.Elemegi.Controller.MainManager;
import com.Elemegi.Elemegi.Controller.ViewManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewManager.getInstance().openLoginPanel();
    }
}
