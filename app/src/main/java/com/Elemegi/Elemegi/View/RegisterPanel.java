package com.Elemegi.Elemegi.View;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.Elemegi.Elemegi.Controller.ViewManager;
import com.Elemegi.Elemegi.R;

public class RegisterPanel extends ViewManager {

    private Button registerButton;
    private TextView backButton;
    private String name;
    private String surname;
    private String email;
    private String password;
    private RadioGroup radioGroup;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private AppCompatActivity act;
    private EditText nameEdit;
    private EditText surnameEdit;
    private EditText emailEdit;
    private EditText passwordEdit;
    private RelativeLayout layout;
    private AnimationDrawable anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        layout=findViewById(R.id.layout);
        anim=(AnimationDrawable)layout.getBackground();
        anim.setEnterFadeDuration(10);
        anim.setExitFadeDuration(1000);
        anim.start();
        myApp.setCurrentActivity(this);
        act = myApp.getCurrentActivity();

        registerButton = (Button) findViewById(R.id.registerButton);
        backButton = (TextView) findViewById(R.id.backButton);
        nameEdit = (EditText) findViewById(R.id.name);
        surnameEdit = (EditText) findViewById(R.id.surname);
        emailEdit = (EditText) findViewById(R.id.email);
        passwordEdit = (EditText) findViewById(R.id.password);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivity(ViewManager.getInstance().openLoginPanel1());
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEdit.getText().toString();
                surname = surnameEdit.getText().toString();
                email = emailEdit.getText().toString();
                password = passwordEdit.getText().toString();
                String type = "";
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
                    surnameEdit.setError(ViewManager.getInstance().controlSurnameRegister(surname));
                }
                if(ViewManager.getInstance().controlEmailRegister(email).length() == 0){
                    counter++;
                }
                else{
                    emailEdit.setError(ViewManager.getInstance().controlEmailRegister(email));
                }
                if(ViewManager.getInstance().controlPasswordRegister(password).length() == 0){
                    counter++;
                }
                else{
                    passwordEdit.setError(ViewManager.getInstance().controlPasswordRegister(password));
                }
                int selectedButton = radioGroup.getCheckedRadioButtonId();
                if(selectedButton != -1){
                    RadioButton selected = (RadioButton) findViewById(selectedButton);
                     type = selected.getText().toString();
                    counter++;
                }

                //last Check
                if(counter == 5){
                    if(ViewManager.getInstance().isExist(email)){
                        emailEdit.setError("The email already exist in the system.");
                        counter--;
                    }
                    else {
                        ViewManager.getInstance().createNewUser(name, surname, type, email, password);
                        changeActivity(ViewManager.getInstance().openLoginPanel1());
                    }
                }

            }
        });
    }
    public void changeActivity(Class className) {
        startActivity(new Intent(act, className));
    }

}
