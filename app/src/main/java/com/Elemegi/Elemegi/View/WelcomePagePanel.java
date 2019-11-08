package com.Elemegi.Elemegi.View;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.Elemegi.Elemegi.MainActivity;
import com.Elemegi.Elemegi.R;
import android.content.Intent;

public class WelcomePagePanel extends MainActivity {
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);
        login=(Button)findViewById(R.id.button1);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });

    }
    public void openRegister(){
        Intent intent=new Intent(this,Register.class);
        startActivity(intent);
    }



}
