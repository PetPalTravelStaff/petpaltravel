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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserViewAccountActivity extends AppCompatActivity {
    //Attributes
    private EditText nameBox, mailBox, phoneBox, passBox, repePassBox;
    private CheckBox cbIsShelter;
    private Button btUpdate, btDelete;
    int idUser;
    User myUser;
    View.OnClickListener listener;
    PPTModel myModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userviewaccount_layout);
        //instantiate model
        myModel= new PPTModel();
        //recover interesting data from Shared Preferences
        recoverShared();
        //recover data form this user
        myUser= myModel.searchUserById(idUser);
        //Create view elements in activity
        initElements();
        //Show values in fields
        setValuesToView();
        //create a listener
        createListener();
        //add elements to listener
        addElementsToListener();
    }



    /**
     * Method for recovering interesting data by Shared Preferences
     */
    private void recoverShared() {
        //Create shared prefereces object of a Shared preferences created
        SharedPreferences shared = getSharedPreferences("dades", MODE_PRIVATE);
        //if exist
        if (shared!=null) {
            //Use the editor to catch the couples of dates
            idUser= shared.getInt("id", idUser);
        }
    }

    /**
     *  Method for creating elements of activity
     */
    private void initElements() {
        nameBox = (EditText) findViewById(R.id.etNombre);
        mailBox = (EditText) findViewById(R.id.etEmail);
        phoneBox= (EditText) findViewById(R.id.etTelefono);
        passBox = (EditText) findViewById(R.id.etPassword1);
        repePassBox = (EditText) findViewById(R.id.etPassword2);
        cbIsShelter = (CheckBox) findViewById(R.id.cbSoyProtectora);
        btUpdate = (Button) findViewById(R.id.btModificarDatos);
        btDelete = (Button) findViewById(R.id.btCancelarCuenta);

    }

    /**
     * Method for preparing the data in the view
     */
    private void setValuesToView() {
        nameBox.setText(myUser.getName());
        mailBox.setText(myUser.getEmail());
        phoneBox.setText(myUser.getPhone());
        cbIsShelter.setChecked(myUser.isShelter());
        btUpdate.setTextColor(Color.WHITE);
        btUpdate.setEnabled(true);
    }

    /**
     * Method for create a listener
     */
    private void createListener() {
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If update button is clicked
                if (view.getId() == R.id.btModificarDatos) {
                    //recover data from EditText
                    String userName = nameBox.getText().toString();
                    String userMail = mailBox.getText().toString();
                    String userPhone = phoneBox.getText().toString();
                    String userPass = "";
                    if (null != passBox.getText()) {
                        userPass = passBox.getText().toString();
                    }
                    String userPass2 = "";
                    if (null != repePassBox.getText()) {
                        userPass2 = repePassBox.getText().toString();
                    }
                    Boolean userShelter = cbIsShelter.isChecked();
                    //Create an user and set his/her id
                    User provUser = new User();
                    provUser.setId(myUser.getId());
                    //Verify each field, if its right add to the user, and if doesn't notify user
                    if (!"".equals(userName)) {//not empty
                        provUser.setName(userName);
                        if (!"".equals(userMail)) {//not empty
                            if (validaMail(userMail)) {//valid mail format
                                provUser.setEmail(userMail);
                                //Save new phone, even it is empty,
                                provUser.setPhone(userPhone);
                                if (!"".equals(userPass)) {//not empty
                                    if (!"".equals(userPass2)) {//not empty
                                        if (userPass.equals(userPass2)) { //pass and repeat pass matches
                                            //repePassBox.setBackgroundColor(Color.TRANSPARENT);
                                            provUser.setPassword(userPass);
                                            provUser.setShelter(userShelter);
                                            int result = 0;
                                            //try to add User to BBDD
                                            result = myModel.updateUser(provUser);
                                            //if everything goes right, back to login, saving mail
                                            if (result == 1) {
                                                myUser = provUser;
                                                saveOnShared();
                                                btUpdate.setText("Datos actualizados");
                                                btUpdate.setTextColor(Color.BLACK);
                                                //if there is a problem (email already in DB), notify
                                            } else if (result == 0) {
                                                mailBox.setText(null);
                                                mailBox.setHintTextColor(Color.RED);
                                                mailBox.setHint("Este mail ya tiene una cuenta");
                                            }
                                        } else {
                                            repePassBox.setText(null);
                                            repePassBox.setHint("Contraseñas no coinciden.");
                                            repePassBox.setHintTextColor(Color.RED);
                                        }
                                    } else {
                                        repePassBox.setHintTextColor(Color.RED);
                                        repePassBox.setHint("Repite nueva contraseña");
                                    }
                                    //if pass1 empty (not changing password)
                                } else {
                                    if ("".equals(userPass2)) {// pass2 empty
                                        provUser.setShelter(userShelter);
                                        int result = 0;
                                        //try to add User to BBDD
                                        result = myModel.updateUser(provUser);
                                        //if everything goes right, saving new information and notify
                                        if (result == 1) {
                                            myUser.setName(provUser.getName());
                                            myUser.setEmail(provUser.getEmail());
                                            myUser.setPhone(provUser.getPhone());
                                            myUser.setShelter(provUser.isShelter());
                                            saveOnShared();
                                            btUpdate.setText("Datos actualizados");
                                            btUpdate.setTextColor(Color.BLACK);
                                            //if there is a problem (email already in DB), notify
                                        } else if (result == 0) {
                                            mailBox.setText(null);
                                            mailBox.setHintTextColor(Color.RED);
                                            mailBox.setHint("Este mail ya tiene una cuenta");
                                        } else {
                                            btUpdate.setText("Prueba más tarde");
                                            btUpdate.setTextColor(Color.RED);
                                            btUpdate.setEnabled(true);
                                        }

                                        //if pass2 not empty
                                    } else {
                                        repePassBox.setText(null);
                                        repePassBox.setHint("Dejar en blanco");
                                        repePassBox.setHintTextColor(Color.RED);
                                    }
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
                } else if (view.getId() == R.id.btCancelarCuenta) {
                    AlertDialog.Builder myAlert= new AlertDialog.Builder(UserViewAccountActivity.this);
                    myAlert.setMessage("¿Seguro que quieres darte de baja? Esta acción hará que pierdas todos tus datos en la app")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Boolean control = myModel.deleteUser(idUser);
                                    if (control) {
                                        Intent intent = new Intent(UserViewAccountActivity.this, UserRegisterActivity.class);
                                        //delete all data saved
                                        SharedPreferences.Editor editor = getSharedPreferences("TrackerTrackerGPS", MODE_PRIVATE).edit();
                                        editor.clear().apply();
                                        startActivity(intent);
                                    } else {
                                        btDelete.setText("Prueba más tarde");
                                        btDelete.setTextColor(Color.RED);
                                    }
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog title= myAlert.create();
                    title.setTitle("Darte de baja");
                    title.show();
                }
            }
        };
    }

    /**
     * Method for saving interesting data by Shared Preferences
     */
    private void saveOnShared() {
        //Get parameters form new user
        String userMail= myUser.getEmail();
        String userName= myUser.getName();
        Boolean isShelter= myUser.isShelter();
        String userPass= myUser.getPassword();
        String userPhone= myUser.getPhone();

        //Create shared prefereces object
        SharedPreferences shared = getSharedPreferences("dades", MODE_PRIVATE);

        //Create an editor over the objecte created
        SharedPreferences.Editor myEditor = shared.edit();

        //Use the editor to catch the couples of dates
        myEditor.putString("userMail", userMail);
        myEditor.putString("userName", userName);
        myEditor.putBoolean("isShelter", isShelter);
        myEditor.putString("userMail", userMail);
        myEditor.putString("userPass", userPass);
        myEditor.putString("userPhone", userPhone);

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
        btUpdate.setOnClickListener(listener);
        btDelete.setOnClickListener(listener);
    }

    /**
     * Method for creating items of menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (myUser.isShelter()) {
            menu.add(0, 1, 0, "Ver mis peticiones");
            menu.add(0, 2, 1, "Publicar petición");
            menu.add(0, 3, 2, "Buscar ofertas");
            menu.add(0, 4, 3, "Salir");
        } else {
            menu.add(0, 1, 0, "Ver mis ofertas");
            menu.add(0, 2, 1, "Publicar oferta");
            menu.add(0, 3, 2, "Buscar peticiones");
            menu.add(0, 4, 3, "Salir");
        }

        return true;
    }

    // Handles item selections from Option MENU
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                //If is Shelter, go to show my demands activity
                if(myUser.isShelter()) {
                    Intent intent2 = new Intent(UserViewAccountActivity.this, UserSearchDemandsActivity.class);
                    startActivity(intent2);
                    //if is person, go to show my offers activity
                } else {
                    Intent intent2 = new Intent(UserViewAccountActivity.this, UserSearchOffersActivity.class);
                    startActivity(intent2);
                }
                break;
            case 2:
                //If is Shelter, go to add a demands activity
                if (myUser.isShelter()) {
                    Intent intent3 = new Intent(UserViewAccountActivity.this, ShelterPostDemandActivity.class);
                    startActivity(intent3);
                    //if is person, go to add an offer activity
                } else {
                    Intent intent3 = new Intent(UserViewAccountActivity.this, PersonPostOfferActivity.class);
                    startActivity(intent3);
                }
                break;
            case 3:
                //If is Shelter, go to view offers activity
                if (myUser.isShelter()) {
                    Intent intent4 = new Intent(UserViewAccountActivity.this, UserSearchOffersActivity.class);
                    startActivity(intent4);
                    //if is person, go to view demands activity
                } else {
                    Intent intent4 = new Intent(UserViewAccountActivity.this, UserSearchDemandsActivity.class);
                    startActivity(intent4);
                }
                break;
            case 4://Exit
                finishAffinity();
                break;
        }
        return true;
    }
}
