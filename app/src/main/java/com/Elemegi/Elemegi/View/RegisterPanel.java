package com.Elemegi.Elemegi.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.Elemegi.Elemegi.Controller.MainManager;
import com.Elemegi.Elemegi.R;
import com.Elemegi.Elemegi.Controller.ViewManager;

public class RegisterPanel extends ViewManager {

    private Button registerButton;
    private TextView loginButton;
    private String name;
    private String surname;
    private String email;
    private String password;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private Activity act;
    private EditText nameEdit;
    private EditText surnameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        myApp.setCurrentActivity(this);
        act = myApp.getCurrentActivity();

        registerButton = (Button) findViewById(R.id.registerButton);
        loginButton = (TextView) findViewById(R.id.loginButton);
        nameEdit = (EditText) findViewById(R.id.name);
        surnameEdit = (EditText) findViewById(R.id.surname);
        email = (((EditText) findViewById(R.id.email)).getText().toString());
        password = ((EditText) findViewById(R.id.password)).getText().toString();

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);

        /*loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(ViewManager.getInstance().openLoginPanel1());
            }
        });*/
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEdit.getText().toString();
                surname = surnameEdit.getText().toString();
                Log.d("123123123123123123","asdasfsadg" + name) ;
                int counter = 0;
                //name Check
                if(ViewManager.getInstance().controlNameRegister(name).length() == 0){
                    counter++;
                }
                else{
                    nameEdit.setError(ViewManager.getInstance().controlNameRegister(name));
                }
                //surname Check
                if(ViewManager.getInstance().controlSurnameRegister(surname).length() == 0){
                    counter++;
                }
                else{
                    surnameEdit.setError(ViewManager.getInstance().controlNameRegister(surname));
                }
                //last Check
                if(counter == 5){
                    changeActivity(ViewManager.getInstance().openLoginPanel1());
                }
            }
        });
    }
    public void changeActivity(Class className) {
        startActivity(new Intent(act, className));
    }

}
