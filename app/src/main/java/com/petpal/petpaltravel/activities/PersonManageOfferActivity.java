package com.petpal.petpaltravel.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.petpal.petpaltravel.R;
import com.petpal.petpaltravel.model.CompanionOfPet;
import com.petpal.petpaltravel.model.PPTModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonManageOfferActivity extends AppCompatActivity {
    //Attributes
    CheckBox cat, dog, other;
    String nameUser;
    Spinner transportType, origin, destination;
    EditText comments, dateTravel;
    TextView typePet, nameLabel;
    Button btModify, btInterested, btDelete;
    PPTModel myModel;
    String[] locations;
    String[] transportOptions;
    CompanionOfPet myOffer;
    int idOffer;
    Boolean isShelter;
    int userId;
    View.OnClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personviewofferdetails_layout);
        //recover urgent data
        recoverShared();
        //instantiate model
        myModel = new PPTModel();
        //recover needed data
        recoverOfferId();
        myOffer = myModel.recoverOfferById(idOffer);
        locations = myModel.getCities();
        transportOptions = myModel.getTransport();
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
     * Method for recovering interesting data by Shared Preferences
     */
    private void recoverShared() {
        //Create shared prefereces object of a Shared preferences created
        SharedPreferences shared = getSharedPreferences("dades", MODE_PRIVATE);
        //if exist
        if (shared != null) {
            //Use the editor to catch the couples of dates
            nameUser = shared.getString("userName", "");
            userId = shared.getInt("id", 0);
            isShelter = shared.getBoolean("isShelter", false);
        }
    }

    /**
     * Method for recovering data needed by bundle
     */
    private void recoverOfferId() {
        Bundle bun = this.getIntent().getExtras();
        idOffer = bun.getInt("idOffer", 0);
    }

    /**
     * Method for create elements of activity
     */
    private void initElements() {
        nameLabel = (TextView) findViewById(R.id.etNombreProtectora);
        cat = (CheckBox) findViewById(R.id.cbGatos);
        dog = (CheckBox) findViewById(R.id.cbPerros);
        other = (CheckBox) findViewById(R.id.cbOtros);
        dateTravel = (EditText) findViewById(R.id.etDiaViaje);
        origin = (Spinner) findViewById(R.id.spDesde);
        destination = (Spinner) findViewById(R.id.spHasta);
        ArrayAdapter<String> questionsAdapter = new ArrayAdapter<String>(PersonManageOfferActivity.this, android.R.layout.simple_spinner_item, locations);
        questionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        origin.setAdapter(questionsAdapter);
        destination.setAdapter(questionsAdapter);
        transportType = (Spinner) findViewById(R.id.spMeTrasladoEn);
        ArrayAdapter<String> questionsAdapter2 = new ArrayAdapter<String>(PersonManageOfferActivity.this, android.R.layout.simple_spinner_item, transportOptions);
        questionsAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transportType.setAdapter(questionsAdapter2);
        comments = (EditText) findViewById(R.id.etComentarios);

        btModify = (Button) findViewById(R.id.btModificar);
        btInterested = (Button) findViewById(R.id.btVerProtectorasInteres);
        btDelete = (Button) findViewById(R.id.btCancelarOferta);
    }

    /**
     * Method for creating a listener and identify what to do
     */
    private void createListener() {
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btVerProtectorasInteres) {
                    Intent intent1 = new Intent(PersonManageOfferActivity.this, PersonCheckSheltersInterested.class);
                    //Create a bundle object
                    Bundle bundle = new Bundle();
                    //set interesting data
                    bundle.putInt("idOffer", idOffer);
                    intent1.putExtras(bundle);
                    startActivity(intent1);
                } else if (view.getId() == R.id.btCancelarOferta) {
                    //TODO
                } else if (view.getId() == R.id.btModificar) {
                    //TODO
                }
            }
        };
    }




    /**
     * Method for adding a listener to the elements
     */
    private void addElementsToListener() {
        btModify.setOnClickListener(listener);
        btInterested.setOnClickListener(listener);
        btDelete.setOnClickListener(listener);
    }

    /**
     * Method for loading demand data in the view
     */
    private void loadData() {
        //set value to name of the user field
        nameLabel.setText(nameUser);
        String animalType = myOffer.getPetType();
        String[] typePieces = animalType.split(",");
        for (int i=0; i<typePieces.length;i++) {
            if (typePieces[i].trim().equals("Perro/a")) {
                dog.setChecked(true);
            }
            if (typePieces[i].trim().equals("Gato/a")) {
                cat.setChecked(true);
            }
            if (typePieces[i].trim().equals("Otros")) {
                other.setChecked(true);
            }
        }
        dateTravel.setText(new SimpleDateFormat("dd-MM-yyyy").format(myOffer.getDateTravel().getTime()));
        String orcity = myOffer.getOriginCity();
        for (int i = 0; i < locations.length; i++) {
            if (locations[i].equals(orcity)) {
                origin.setSelection(i);
            }
        }
        String descity = myOffer.getDestinyCity();
        for (int i = 0; i < locations.length; i++) {
            if (locations[i].equals(descity)) {
                destination.setSelection(i);
            }
        }
        comments.setText(myOffer.getComments());
        String transportRecovered = myOffer.getTransport();
        if (transportRecovered.equals(transportOptions[0])) {
            transportType.setSelection(0);
        } else if (transportRecovered.equals(transportOptions[1])) {
            transportType.setSelection(1);
        } else if (transportRecovered.equals(transportOptions[2])) {
            transportType.setSelection(2);
        } else if (transportRecovered.equals(transportOptions[3])) {
            transportType.setSelection(3);
        }
    }

        /**
         * Method for creating items of menu
         * @param menu
         * @return
         */
        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            super.onCreateOptionsMenu(menu);
            menu.add(0, 1, 0, "Mi perfil");
            menu.add(0, 2, 1, "Ver mis ofertas");
            menu.add(0, 3, 2, "Buscar peticiones");
            menu.add(0, 4, 3, "Publicar una oferta");
            menu.add(0, 5, 4, "Salir");
            return true;
        }

        // Handles item selections from Option MENU
        @Override
        public boolean onOptionsItemSelected (MenuItem item){
            switch (item.getItemId()) {
                case 1:
                    //Go to view account activity
                    Intent intent1 = new Intent(PersonManageOfferActivity.this, UserViewAccountActivity.class);
                    startActivity(intent1);
                    break;
                case 2:
                    Intent intent2 = new Intent(PersonManageOfferActivity.this, UserSearchOffersActivity.class);
                    startActivity(intent2);
                    break;
                case 3:
                    Intent intent3 = new Intent(PersonManageOfferActivity.this, UserSearchDemandsActivity.class);
                    startActivity(intent3);
                    break;
                case 4:
                    Intent intent4 = new Intent(PersonManageOfferActivity.this, PersonPostOfferActivity.class);
                    startActivity(intent4);
                    break;
                case 5://Exit
                    finishAffinity();
                    break;
            }
            return true;
        }
    }