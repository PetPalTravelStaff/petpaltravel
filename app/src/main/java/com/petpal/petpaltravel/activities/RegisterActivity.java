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
    //Atributes
    private EditText nameBox, mailBox, passBox, repePassBox;
    private CheckBox cbIsShelter;
    private Button btSave;
    View.OnClickListener listener;
    PPTModel myModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        //instantiate model
        myModel= new PPTModel();
        //Create view elements in activity
        initElements();
        //create a listener
        createListener();
        //add elements to listener
        addElementsToListener();
    }

    /**
     *  Method for creating elements of activity
     */
    private void initElements() {
        nameBox = (EditText) findViewById(R.id.etNombre);
        mailBox = (EditText) findViewById(R.id.etEmail);
        passBox = (EditText) findViewById(R.id.etPassword1);
        repePassBox = (EditText) findViewById(R.id.etpassword2);
        btSave = (Button) findViewById(R.id.btDameAlta);
        cbIsShelter = (CheckBox) findViewById(R.id.cbSoyProtectora);
    }

    /**
     * Method for create a listener
     */
    private void createListener() {
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If register button is clicked
                if (view.getId() == R.id.btDameAlta) {
                    //recover data from EditText
                    String userName = nameBox.getText().toString();
                    String userMail = mailBox.getText().toString();
                    String userPass = passBox.getText().toString();
                    String userPass2 = repePassBox.getText().toString();
                    Boolean userShelter = cbIsShelter.isChecked();
                    //Create an user
                    User provUser= new User();
                    //Verify each field, if its right add to the user, and if doesn't notify user
                    if (!"".equals(userName)) {//not empty
                        nameBox.setBackgroundColor(Color.TRANSPARENT);
                        provUser.setName(userName);
                        if (!"".equals(userMail)) {//not empty
                            if(validaMail(userMail)) {//valid mail format
                                mailBox.setBackgroundColor(Color.TRANSPARENT);
                                provUser.setEmail(userMail);
                                if (!"".equals(userPass)) {//not empty
                                    passBox.setBackgroundColor(Color.TRANSPARENT);
                                    if (!"".equals(userPass2)) {//not empty
                                        if (userPass.equals(userPass2)) { //pass and repeat pass matches
                                            repePassBox.setBackgroundColor(Color.TRANSPARENT);
                                            provUser.setPassword(userPass);
                                            provUser.setShelter(userShelter);
                                            int result=0;
                                            //try to add User to BBDD
                                            result = myModel.insertUser(provUser);
                                            //if everything goes right, back to login, saving mail
                                            if (result==1) {
                                                saveOnShared();
                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                             //if there is a problem (email already in DB), notify
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

    /**
     * Method for saving interesting data by Shared Preferences
     */
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

    /**
     * Method for verifying than a mail is well created
     * @param userMail to verify
     * @return true if it is, false, otherwise
     */
    private boolean validaMail(String userMail) {
        Boolean result = false;
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(userMail);
        result= mather.find();
        return  result;
    }

    /**
     * Method for adding a listener to the elements
     */
    private void addElementsToListener() {
        btSave.setOnClickListener(listener);
        cbIsShelter.setOnClickListener(listener);
    }
}
