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

public class LoginActivity extends AppCompatActivity {

    EditText mailBox, passBox;
    String mailSaved;
    Button login, register, sendPass;
    CheckBox rememberMe;
    View.OnClickListener listener;
    User client;
    PPTModel myModel;
    private boolean rememberCheked;
    private String passSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        myModel= new PPTModel();
        client= new User();
        recoverSharedPref();
        //Create view elements in activity
        initElements();
        //create a listener
        createListener();
        //add elements to listener
        addElementsToListener();


        // Método para mostrar y ocultar el menú
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, "Cerrar");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    private void initElements() {
        rememberMe= (CheckBox) findViewById(R.id.cbRecuerdame);
        if (rememberCheked) {
            rememberMe.setChecked(true);
        } else {
            mailSaved=null;
            passSaved=null;
        }
        mailBox= (EditText) findViewById(R.id.etUsuario) ;
        if (mailSaved!=null) {
            mailBox.setText(mailSaved);
        }
        passBox= (EditText) findViewById(R.id.etPassword);
        if (passSaved!=null) {
            passBox.setText(passSaved);
        }
        login= (Button) findViewById(R.id.btEntrar);
        register= (Button) findViewById(R.id.btAlta);
        sendPass= (Button) findViewById(R.id.btRecuerdaPassword);
    }

    private void createListener() {
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btEntrar) {
                    //recover data from EditText
                        String userMail = mailBox.getText().toString();
                        String userPass = passBox.getText().toString();
                        //recover User from servlet
                        client = myModel.validatePassword(userMail, userPass);
                    //if a user is recovered
                    if (client!=null){
                        //save interesting data
                        saveSharedPref();
                        //if is shelter, show search offers
                        if (client.isShelter()){
                            Intent intent  = new Intent(LoginActivity.this, SearchOffersActivity.class);
                            startActivity(intent);
                            //if is person, show search demands
                        } else  {
                            Intent intent  = new Intent(LoginActivity.this, SearchDemandsActivity.class);
                            startActivity(intent);
                        }
                        //if not recovered an user
                    } else {
                        passBox.setHintTextColor(Color.RED);
                        passBox.setHint("Password incorrecto");
                        passBox.setText(null);
                    }
                } else if (view.getId() == R.id.btAlta) {
                    openRegisterActivity();
                } else if (view.getId() == R.id.btRecuerdaPassword) {
                    openSendPassActivity();
                }
            }
        };
    }
    private void addElementsToListener() {
        login.setOnClickListener (listener);
        register.setOnClickListener(listener);
        sendPass.setOnClickListener(listener);
    }

    private void openSendPassActivity() {
        //Intent intent  = new Intent(LoginActivity.this, ForgotPassActivity.class);
        //startActivity(intent);
    }

    private void openRegisterActivity() {
        Intent intent  = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);

    }

    private void saveSharedPref() {
        //Get params form
        String userName= client.getName();
        int idUser= client.getId();
        Boolean isShelter= client.isShelter();
        Boolean rememberCheck = rememberMe.isChecked();
        String userMail= client.getEmail();
        String userPass= client.getPassword();

        //Create shared prefereces object
        SharedPreferences shared = getSharedPreferences("dades", MODE_PRIVATE);

        //Create an editor over the objecte created
        SharedPreferences.Editor myEditor = shared.edit();

        //Use the editor to catch the couples of dates
        myEditor.putString("userName", userName);
        myEditor.putBoolean("isShelter", isShelter);
        myEditor.putString("userMail", userMail);
        myEditor.putString("userPass", userPass);
        myEditor.putBoolean("rememberMe", rememberCheck);
        myEditor.putInt("id", idUser);

        //Push the editor to write them
        myEditor.commit();
    }

    private void recoverSharedPref() {
        //Create shared prefereces object
        SharedPreferences shared = getSharedPreferences("dades", MODE_PRIVATE);
        if (shared!=null) {
            //Use the editor to catch the couples of dates
            mailSaved = shared.getString("userMail", null);
            passSaved = shared.getString("userPass", null);
            rememberCheked= shared.getBoolean("rememberMe", false);
        }
    }
}
