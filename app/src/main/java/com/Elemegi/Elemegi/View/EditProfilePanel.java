package com.Elemegi.Elemegi.View;

import android.os.Bundle;

import com.Elemegi.Elemegi.Controller.ViewManager;
import com.Elemegi.Elemegi.R;

public class EditProfilePanel extends ViewManager {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_page);
    }
}