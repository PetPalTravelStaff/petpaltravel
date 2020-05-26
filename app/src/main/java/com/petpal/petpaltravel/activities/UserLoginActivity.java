package com.petpal.petpaltravel.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class UserLoginActivity extends AppCompatActivity {
    //Atributes
    EditText mailBox, passBox;
    String mailSaved;
    Button login, register, btInfo;
    CheckBox rememberMe;
    View.OnClickListener listener;
    User client;
    PPTModel myModel;
    private boolean rememberCheked;
    private String passSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlogin_layout);
        //instanciate model and user
        myModel = new PPTModel();
        client = new User();
        //recover Shared Preferences
        recoverSharedPref();
        //Create view elements in activity
        initElements();
        //create a listener
        createListener();
        //add elements to listener
        addElementsToListener();
    }

    /**
     * Method for creating menu options
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, "Cerrar");
        return true;
    }

    /**
     * Method for indicate what to do when item of menu is selected
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    /**
     * Method for init elements of activity (and values, if needed)
     */
    private void initElements() {
        rememberMe = (CheckBox) findViewById(R.id.cbRecuerdame);
        //In case user ask to remember her/his data
        if (rememberCheked) {
            rememberMe.setChecked(true);
        } else {
            //else set empty values
            mailSaved = null;
            passSaved = null;
        }
        mailBox = (EditText) findViewById(R.id.etUsuario);
        if (mailSaved != null) {
            mailBox.setText(mailSaved);
        }
        passBox = (EditText) findViewById(R.id.etPassword);
        if (passSaved != null) {
            passBox.setText(passSaved);
        }
        login = (Button) findViewById(R.id.btEntrar);
        register = (Button) findViewById(R.id.btAlta);
        btInfo = (Button) findViewById(R.id.btInfo);

    }

    /**
     * Method for create a listener for de buttons of the activity
     */
    private void createListener() {
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if press button login
                if (view.getId() == R.id.btEntrar) {
                    //recover data from EditText
                    String userMail = mailBox.getText().toString();
                    if (userMail != null & !"".equals(userMail)) {
                        String userPass = passBox.getText().toString();
                        if (userPass != null & !"".equals((userPass))) {
                            //try to recover User from servlet with this data
                            client = myModel.validatePassword(userMail, userPass);
                            //if a user is recovered
                            if (client != null) {
                                //save interesting data
                                saveSharedPref();
                                //if is shelter, show search offers
                                if (client.isShelter()) {
                                    Intent intent = new Intent(UserLoginActivity.this, UserSearchOffersActivity.class);
                                    startActivity(intent);
                                    //if is person, show search demands
                                } else {
                                    Intent intent = new Intent(UserLoginActivity.this, UserSearchDemandsActivity.class);
                                    startActivity(intent);
                                }
                                //if not recovered an user, notify
                            } else {
                                passBox.setText(null);
                                passBox.setHintTextColor(Color.RED);
                                passBox.setHint("Datos incorrectos");
                                mailBox.setText(null);
                                mailBox.setHintTextColor(Color.RED);
                                mailBox.setHint("Datos incorrectos");


                            }
                        } else {
                            passBox.setHintTextColor(Color.RED);
                            passBox.setHint("Falta password");
                        }
                    } else {
                        mailBox.setHintTextColor(Color.RED);
                        mailBox.setHint("Falta email");
                    }
                    //if button register is pressed
                } else if (view.getId() == R.id.btAlta) {
                    openRegisterActivity();
                    //if button remember me passowrd is pressed
                } else if (view.getId() == R.id.btInfo) {
                    AlertDialog.Builder myAlert = new AlertDialog.Builder(UserLoginActivity.this);
                    myAlert.setMessage("PetPatTravel\nVersion 1.0 2002\nCreated by:\nMarta Garcia & Roser Vargas")
                            .setCancelable(false)
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent1 = new Intent(UserLoginActivity.this, UserLoginActivity.class);
                                    startActivity(intent1);
                                }
                            });
                    AlertDialog title = myAlert.create();
                    title.setTitle("Información de la aplicación");
                    title.show();
                }
            }
        };
    }

    /**
     * Method for adding listeners to elements
     */
    private void addElementsToListener() {
        login.setOnClickListener(listener);
        register.setOnClickListener(listener);
        btInfo.setOnClickListener(listener);
    }

    /**
     * Method for opening remember password activity
     */
    private void openSendPassActivity() {
        btInfo.setText("Temporalmente fuera de servicio");
    }

    /**
     * Method for opening register activity
     */
    private void openRegisterActivity() {
        Intent intent = new Intent(UserLoginActivity.this, UserRegisterActivity.class);
        startActivity(intent);
    }


    /**
     * Method for saving interesting data by shared preferences
     */
    private void saveSharedPref() {
        //Get params form
        String userName = client.getName();
        int idUser = client.getId();
        Boolean isShelter = client.isShelter();
        Boolean rememberCheck = rememberMe.isChecked();
        String userMail = client.getEmail();
        String userPass = client.getPassword();
        String userPhone = client.getPhone();

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
        myEditor.putString("userPhone", userPhone);
        myEditor.putInt("id", idUser);

        //Push the editor to write them
        myEditor.commit();
    }

    /**
     * Method for recovergin interesting data by shared preferences
     */
    private void recoverSharedPref() {
        System.out.println("Entra aqui");
        //Create shared prefereces object of a Shared preferences created
        SharedPreferences shared = getSharedPreferences("dades", MODE_PRIVATE);
        //if exist
        if (shared != null) {
            //Use the editor to catch the couples of dates
            mailSaved = shared.getString("userMail", null);
            passSaved = shared.getString("userPass", null);
            rememberCheked = shared.getBoolean("rememberMe", false);
        }
    }
}
