package com.petpal.petpaltravel.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.petpal.petpaltravel.R;
import com.petpal.petpaltravel.model.CompanionForPet;
import com.petpal.petpaltravel.model.PPTModel;

import java.text.SimpleDateFormat;

public class ShelterManageDemandActivity extends AppCompatActivity {
    //Atributes
    TextView namePet, OriginCity, Destination, typePet, dateFrom, dateUntill, comments, nameLabel;
    String nameUser;
    String phoneUser;
    Button btModify;
    PPTModel myModel;
    CompanionForPet myDemand;
    int idDemand;
    Boolean isShelter;
    int userId;
    View.OnClickListener listener;
    int situationFlag=0; // 0= normal, -1= missing phone, -2= applied -3=no more application accepted

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //recover urgent data
        recoverShared();
        setContentView(R.layout.shelterviewdemanddetails_layout);
        //instantiate model
        myModel = new PPTModel();
        //recover needed data
        recoverDemandId();
        myDemand= myModel.recoverDemandById(idDemand);
        //Create view elements in activity
        initElements();
        //create a listener
        createListener();
        //load Data in view
        loadData();
        //add listener to elements
        addElementsToListener();
    }

    /**
     * Method for recovering data needed by bundle
     */
    private void recoverDemandId() {
        Bundle bun = this.getIntent().getExtras();
        idDemand= bun.getInt("idDemand",0);
    }

    /**
     * Method for create elements of activity
     */
    private void initElements() {
        namePet= (EditText) findViewById(R.id.etNombreMascota);
        OriginCity= (TextView) findViewById(R.id.etCiudadOrigenMascota);
        Destination= (TextView) findViewById(R.id.etCiudadDestinoMascota);
        typePet= (TextView) findViewById(R.id.etTipoMascota);
        dateFrom= (TextView) findViewById(R.id.etDisponibleDesde);
        dateUntill= (TextView) findViewById(R.id.etDisponibleHasta);
        comments= (TextView) findViewById(R.id.etComentarios);
        nameLabel= (TextView) findViewById(R.id.etNombrePersona);
        //set value to name of the user field
        nameLabel.setText(nameUser);
        btModify = (Button) findViewById(R.id.btModificar);
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
            nameUser = shared.getString("userName", "");
            userId= shared.getInt("id", 0);
            isShelter = shared.getBoolean("isShelter", false);
            phoneUser= shared.getString("userPhone", null);
        }
    }

    /**
     * Method for creating a listener and identify what to do
     */
    private void createListener() {
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //BOTON MODIFICAR --> MODIFICAR
                    //BOTON VER PERSONAS
                    //BOTON CANCELAR OFERTA
                }
        };
    }

    /**
     * Method for adding listener to the elements
     */
    private void addElementsToListener() {
        btModify.setOnClickListener (listener);
    }



    /**
     * Method for loading demand data in the view
     */
    private void loadData() {
        //if the demand is null (does not arrived to this activity), notify
        if(myDemand==null){
            namePet.setText("No encontrado");
            OriginCity.setText("No encontrado");
            Destination.setText("No encontrado");
            typePet.setText("No encontrado");
            dateFrom.setText("No encontrado");
            dateUntill.setText("No encontrado");
            comments.setText("No encontrado");
            btModify.setText("No se ha encontrado");
            btModify.setEnabled(false);
        //if there is a demand
        } else {
            namePet.setText(myDemand.getNamePet());
            OriginCity.setText(myDemand.getOriginCity());
            Destination.setText(myDemand.getDestinyCity());
            typePet.setText(myDemand.getTypePet());
            dateFrom.setText(new SimpleDateFormat("dd-MM-yyyy").format(myDemand.getAvailableFrom().getTime()));
            if (myDemand.getDeadline()!=null) {
                dateUntill.setText(new SimpleDateFormat("dd-MM-yyyy").format(myDemand.getDeadline().getTime()));
            } else {
                dateUntill.setText("Sin fecha límite");
            }
            comments.setText(myDemand.getComments());
        }
    }

    /**
     * Method for creating items of menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
            menu.add(0, 1, 0, "Mi perfil");
            menu.add(0, 2, 1, "Ver mis peticiones");
            menu.add(0, 3, 2, "Publicar petición");
            menu.add(0, 4, 3, "Salir");
        return true;
    }

    // Handles item selections from Option MENU
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case 1:
                    //Go to view account activity
                    Intent intent1 = new Intent(ShelterManageDemandActivity.this, UserViewAccountActivity.class);
                    startActivity(intent1);
                    break;
                case 2:
                        Intent intent2 = new Intent(ShelterManageDemandActivity.this, UserSearchDemandsActivity.class);
                        startActivity(intent2);
                    break;
                case 3:
                        Intent intent3 = new Intent(ShelterManageDemandActivity.this, ShelterPostDemandActivity.class);
                        startActivity(intent3);
                    break;
                case 4://Exit
                    finish();
                    break;
            }
        return true;
    }
}
