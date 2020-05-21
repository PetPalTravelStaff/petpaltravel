package com.petpal.petpaltravel.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.petpal.petpaltravel.R;
import com.petpal.petpaltravel.model.ApplicationForDemand;
import com.petpal.petpaltravel.model.CompanionForPet;
import com.petpal.petpaltravel.model.PPTModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShelterViewAndConfirmOnePersonInterested extends AppCompatActivity {
    //Attributes
    TextView nameBox, transportBox, mailBox, phoneBox, commentBox, nameLabel;
    Button btChoose;
    ApplicationForDemand myApplication;
    String nameUser, userPhone;
    int idUser, idDemand, idApplication;
    PPTModel myModel;
    private Boolean isShelter, isSelected;
    View.OnClickListener listener;
    private CompanionForPet myDemand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelterviewandconfirmpersonapply_layout);
        //instantiate model
        myModel = new PPTModel();
        //recover interesting data by Shared Preferences and Bundle
        recoverDemandId();
        recoverShared();
        //recover list of all demands from model
        myApplication = myModel.searchDemandApplyById(idApplication);
        myDemand = myModel.recoverDemandById(idDemand);
        isSelected= myApplication.getChoosed();
        //Create view elements in activity
        initElements();
        //create a listener
        createListener();
        //Add elements to listener
        addElementsToListener();
        //load data in view
        loadData();
    }

    /**
     * Method for recovering data needed by bundle
     */
    private void recoverDemandId() {
        Bundle bun = this.getIntent().getExtras();
        idDemand = bun.getInt("idDemand", 0);
        idApplication = bun.getInt("idApplyDem", 0);
    }

    /**
     * Method for recovering interesting data by Shared Preferences
     */
    private void recoverShared() {
        //Create shared prefereces object of a Shared preferences created
        SharedPreferences shared = getSharedPreferences("dades", MODE_PRIVATE);
        //if exist
        if (shared != null) {
            //Use the editor to catch the couples of dates
            nameUser = shared.getString("userName", "");
            idUser = shared.getInt("id", 0);
            isShelter = shared.getBoolean("isShelter", false);
            userPhone= shared.getString("userPhone", null);
        }
    }


    /**
     * Method for creating the elements of the activity
     */
    private void initElements () {
        nameBox = (TextView) findViewById(R.id.etNombre);
        transportBox = (TextView) findViewById(R.id.etViajaEn);
        mailBox = (TextView) findViewById(R.id.etMiEmail);
        phoneBox = (TextView) findViewById(R.id.etMiTelefono);
        commentBox = (TextView) findViewById(R.id.etComentarios);
        //nameLabel= (TextView) findViewById(R.id.etNombrePersona);
        btChoose = (Button) findViewById(R.id.btLoElijo);
    }

    /**
     * Method for creating a listener and identify what to do
     */
    private void createListener() {
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Ha clicado al botón");
                Boolean control= myModel.confirmSelectedPerson(myApplication);
                if (control) {
                    btChoose.setText("Ya no lo elijo");
                    btChoose.setTextColor(Color.BLACK);
                    String text = "PetPatTravel te informa: la protectora " + myDemand.getNameShelter()+" te ha elegido para acompañar a " + myDemand.getNamePet() +
                            "(" + myDemand.getTypePet()+ ") desde " + myDemand.getOriginCity() + " hasta " + myDemand.getDestinyCity() +
                            ". Se pondrán en contacto contigo para ultimas detalles. Gracias y... ¡buen viaje!";
                    SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage(myApplication.getPhone(), null, text , null, null);
                    String text2 = "PetPatTravel te informa: has escogido a " + myApplication.getNamePerson()+" para acompañar a " + myDemand.getNamePet() +
                            "(" + myDemand.getTypePet()+ ") desde " + myDemand.getOriginCity() + " hasta " + myDemand.getDestinyCity() +
                            ". Acuerdate de ponerte en contacto con esta persona para concretar los detalles. Gracias y... ¡buen viaje!";
                    SmsManager sms2 = SmsManager.getDefault();
                    sms2.sendTextMessage(userPhone, null, text2 , null, null);
                } else {
                    btChoose.setText("Prueba más tarde");
                    btChoose.setTextColor(Color.RED);
                }
            }
        };
    }

    /**
     * Method for adding listener to the elements
     */
    private void addElementsToListener() {
        btChoose.setOnClickListener(listener);
    }

    /**
     * Method for loading demand data in the view
     */
    private void loadData() {
        //nameLabel.setText(nameUser);
        nameBox.setText (myApplication.getNamePerson());
        transportBox.setText(myApplication.getTransport());
        mailBox.setText(myApplication.getMail());
        phoneBox.setText(myApplication.getPhone());
        commentBox.setText(myApplication.getComments());
        if (isSelected) {
            btChoose.setText("Ya no lo elijo");
            btChoose.setTextColor(Color.RED);
        } else {
            btChoose.setText("¡La elijo!");
            btChoose.setTextColor(Color.WHITE);
        }
    }

    /**
     * Method for creating items of menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, "Mi perfil");
        menu.add(0, 2, 1, "Ver mis peticiones");
        menu.add(0, 3, 2, "Publicar petición");
        menu.add(0, 4, 3, "Buscar ofertas");
        menu.add(0, 5, 4, "Salir");
        return true;
    }

    // Handles item selections from Option MENU
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                //Go to view account activity
                Intent intent1 = new Intent(ShelterViewAndConfirmOnePersonInterested.this, UserViewAccountActivity.class);
                startActivity(intent1);
                break;
            case 2:
                Intent intent2 = new Intent(ShelterViewAndConfirmOnePersonInterested.this, UserSearchDemandsActivity.class);
                startActivity(intent2);
                break;
            case 3:
                Intent intent3 = new Intent(ShelterViewAndConfirmOnePersonInterested.this, ShelterPostDemandActivity.class);
                startActivity(intent3);
                break;
            case 4:
                Intent intent4 = new Intent(ShelterViewAndConfirmOnePersonInterested.this, UserSearchOffersActivity.class);
                startActivity(intent4);
                break;
            case 5://Exit
                finish();
                break;
        }
        return true;
    }
}

