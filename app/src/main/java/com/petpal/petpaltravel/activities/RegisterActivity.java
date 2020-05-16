package com.petpal.petpaltravel.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.petpal.petpaltravel.R;
import com.petpal.petpaltravel.model.PPTModel;
import com.petpal.petpaltravel.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameBox, mailBox, passBox, repePassBox;
    private CheckBox cbIsShelter;
    private Button btSave;
    View.OnClickListener listener;
    PPTModel myModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        myModel= new PPTModel();
        //Create view elements in activity
        initElements();
        //create a listener
        createListener();
        //add elements to listener
        addElementsToListener();
 }



    private void initElements() {
        nameBox = (EditText) findViewById(R.id.etNombre);
        mailBox = (EditText) findViewById(R.id.etEmail);
        passBox = (EditText) findViewById(R.id.etPassword1);
        repePassBox = (EditText) findViewById(R.id.etpassword2);
        btSave = (Button) findViewById(R.id.btDameAlta);
        cbIsShelter = (CheckBox) findViewById(R.id.cbSoyProtectora);
    }

    private void createListener() {
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btDameAlta) {
                    //recover data from EditText
                    int result=0;
                    User provUser= new User();
                    String userName = nameBox.getText().toString();
                    String userMail = mailBox.getText().toString();
                    String userPass = passBox.getText().toString();
                    String userPass2 = repePassBox.getText().toString();
                    Boolean userShelter = cbIsShelter.isChecked();
                    if (!"".equals(userName)) {
                        nameBox.setBackgroundColor(Color.TRANSPARENT);
                        provUser.setName(userName);
                        if (!"".equals(userMail)) {
                            if(validaMail(userMail)) {
                                mailBox.setBackgroundColor(Color.TRANSPARENT);
                                provUser.setEmail(userMail);
                                if (!"".equals(userPass)) {
                                    passBox.setBackgroundColor(Color.TRANSPARENT);
                                    if (!"".equals(userPass2)) {
                                        if (userPass.equals(userPass2)) {
                                            repePassBox.setBackgroundColor(Color.TRANSPARENT);
                                            provUser.setPassword(userPass);
                                            provUser.setShelter(userShelter);
                                            //recover User from servlet
                                            result = myModel.insertUser(provUser);
                                            //if a user is recovered
                                            if (result==1) {
                                                saveOnShared();
                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                            } else if (result==0){
                                                mailBox.setText(null);
                                                mailBox.setHintTextColor(Color.RED);
                                                mailBox.setHint("Este mail ya tiene una cuenta");
                                            }
                                        } else {
                                            repePassBox.setText(null);
                                            repePassBox.setHint("Passwords no coinciden");
                                            repePassBox.setHintTextColor(Color.RED);
                                        }
                                    } else {
                                        repePassBox.setHintTextColor(Color.RED);
                                        repePassBox.setHint("Repite password");
                                    }
                                } else {
                                    passBox.setHintTextColor(Color.RED);
                                    passBox.setHint("Falta password");
                                }

                            } else {
                                mailBox.setText(null);
                                mailBox.setHintTextColor(Color.RED);
                                mailBox.setHint("Mail mal formado");
                            }
                        } else {

                            mailBox.setHintTextColor(Color.RED);
                            mailBox.setHint("Mail obligatorio");
                        }
                    } else {
                        nameBox.setHintTextColor(Color.RED);
                        nameBox.setHint("Nombre obligatorio");
                    }
                }
            }
        };
    }

    private void saveOnShared() {
        //Get params form
        String userMail= mailBox.getText().toString();

        //Create shared prefereces object
        SharedPreferences shared = getSharedPreferences("dades", MODE_PRIVATE);

        //Create an editor over the objecte created
        SharedPreferences.Editor myEditor = shared.edit();

        //Use the editor to catch the couples of dates
        myEditor.putString("userMail", userMail);

        //Push the editor to write them
        myEditor.commit();
    }

    private boolean validaMail(String userMail) {
        Boolean result = false;
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(userMail);
        result= mather.find();
        return  result;
    }

    private void addElementsToListener() {
        btSave.setOnClickListener(listener);
        cbIsShelter.setOnClickListener(listener);
    }
}
