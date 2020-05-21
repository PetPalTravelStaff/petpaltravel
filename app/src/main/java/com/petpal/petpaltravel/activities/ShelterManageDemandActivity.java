package com.petpal.petpaltravel.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.petpal.petpaltravel.R;
import com.petpal.petpaltravel.model.CompanionForPet;
import com.petpal.petpaltravel.model.PPTModel;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class ShelterManageDemandActivity extends AppCompatActivity {
    //Atributes
    TextView nameLabel;
    EditText namePet, comments, otherType, dateFrom, dateUntill;
    String nameUser, phoneUser, animalType;
    RadioGroup typePet;
    RadioButton cat,dog, other;
    Spinner originCity, destination;
    String[]locations;
    Button btModify;
    Button btSeeInterested;
    Button btDelete;
    PPTModel myModel;
    CompanionForPet myDemand;
    int idDemand;
    Boolean isShelter;
    int userId;
    View.OnClickListener listener;
    private RadioGroup.OnCheckedChangeListener listener2;

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
        locations= myModel.getCities();
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
        nameLabel= (TextView) findViewById(R.id.etNombrePersona);
        namePet= (EditText) findViewById(R.id.etNombreMascota);
        typePet= (RadioGroup) findViewById(R.id.radioTipo);
        cat= (RadioButton)findViewById(R.id.rdGato);
        dog= (RadioButton)findViewById(R.id.rdPerro);
        other= (RadioButton)findViewById(R.id.radioOtro);
        otherType= (EditText)findViewById(R.id.etQueTipo);
        originCity = (Spinner) findViewById(R.id.spViajaDesde);
        destination = (Spinner) findViewById(R.id.spViajeDestino);
        ArrayAdapter<String> questionsAdapter = new ArrayAdapter<String>(ShelterManageDemandActivity.this,android.R.layout.simple_spinner_item, locations);
        questionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        originCity.setAdapter(questionsAdapter);
        destination.setAdapter(questionsAdapter);
        dateFrom= (EditText) findViewById(R.id.etDisponibleDesde);
        dateUntill= (EditText) findViewById(R.id.etDisponibleHasta);
        comments= (EditText) findViewById(R.id.etComentarios);

        btModify = (Button) findViewById(R.id.btModificar);
        btSeeInterested= (Button) findViewById(R.id.btVerPersonasInteresadas);
        btDelete= (Button) findViewById(R.id.btCancelarPeticion);
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
                if (view.getId() == R.id.btVerPersonasInteresadas) {
                    Intent intent1 = new Intent(ShelterManageDemandActivity.this, ShelterCheckPersonsInterested.class);
                    //Create a bundle object
                    Bundle bundle = new Bundle();
                    //set interesting data
                    bundle.putInt("idDemand", idDemand);
                    intent1.putExtras(bundle);
                    startActivity(intent1);
                } else if (view.getId() == R.id.btCancelarPeticion) {
                    //TODO
                }
                else if (view.getId() == R.id.btModificar) {
//                //recover data from form and save it in variables
//                String namePet = null;
//                if (nameBox.getText() != null) {
//                    namePet = nameBox.getText().toString();
//                }
//                String dateFromString = null;
//                if (dateAvaliable.getText() != null) {
//                    dateFromString = dateAvaliable.getText().toString();
//                }
//                String dateUntillString = null;
//                if (dateDeadLine.getText() != null) {
//                    dateUntillString = dateDeadLine.getText().toString();
//                }
//                GregorianCalendar dateCalFrom = null;
//                GregorianCalendar dateCalUntil = null;
//                String cityOrigin = originCity.getSelectedItem().toString();
//                String cityDestiny = destinyCity.getSelectedItem().toString();
//                int typeSelected = petType.getCheckedRadioButtonId();
//                String commentString = comments.getText().toString();
//                myDemand = new CompanionForPet();
//                myDemand.setIdeUserShelterOffering(userId);
//                myDemand.setNameShelter(nameUser);
//                if (!"".equals(namePet)) {
//                    nameBox.setHintTextColor(Color.BLACK);
//                    myDemand.setNamePet(namePet);
//                    if (!"".equals(dateFromString)) {//check empty date avaliable
//                        dateAvaliable.setHintTextColor(Color.BLACK);
//                        dateCalFrom = validateDate(dateFromString);
//                        if (dateCalFrom != null) { //check valid date avaliable
//                            dateAvaliable.setHintTextColor(Color.BLACK);
//                            myDemand.setAvailableFrom(dateCalFrom);
//                            if (!"".equals(dateUntillString)) { //if deadline is written: check valid date
//                                dateCalUntil = validateDate(dateUntillString);
//                                if (dateCalUntil != null) {
//                                    myDemand.setDeadline(dateCalUntil);
//                                }
//                            } else { //if not writen save null.
//                                myDemand.setDeadline(null);
//                            }
//                            if (cityOrigin != null) {
//                                labOrigen.setTextColor(Color.BLACK);
//                                myDemand.setOriginCity(cityOrigin);
//                                if (cityDestiny != null) {
//                                    labDestino.setTextColor(Color.BLACK);
//                                    if (!cityDestiny.equals(cityOrigin)) {
//                                        labOrigen.setTextColor(Color.BLACK);
//                                        labDestino.setTextColor(Color.BLACK);
//                                        myDemand.setDestinyCity(cityDestiny);
//                                        Boolean noChooseType = false;
//                                        if (!cat.isChecked() & !dog.isChecked() & !other.isChecked()) {
//                                            noChooseType = true;
//                                        } else {
//                                            if (other.isChecked()) {
//                                                if (otherType.getText() == null & "".equals(otherType.getText().toString())) {
//                                                    noChooseType = true;
//                                                } else {
//                                                    animalType = otherType.getText().toString();
//                                                    noChooseType = false;
//                                                }
//                                            }
//                                        }
//                                        if (!noChooseType) {
//                                            labTipo.setTextColor(Color.BLACK);
//                                            otherType.setHintTextColor(Color.BLACK);
//                                            myDemand.setTypePet(animalType);
//                                            myDemand.setComments(commentString);
//                                            int idDemand = myModel.addDemandToBD(myDemand);
//                                            if (idDemand != 0) {
//                                                Intent intent1 = new Intent(ShelterPostDemandActivity.this, UserSearchDemandsActivity.class);
//                                                //Create a bundle object
//                                                Bundle bundle = new Bundle();
//                                                //set interesting data
//                                                bundle.putInt("idDemand", idDemand);
//                                                intent1.putExtras(bundle);
//                                                startActivity(intent1);
//                                            } else {
//                                                btPostDemand.setText("Error. Prueba más tarde");
//                                                btPostDemand.setTextColor(Color.RED);
//                                            }
//                                        } else {
//                                            labTipo.setTextColor(Color.RED);
//                                            otherType.setHintTextColor(Color.RED);
//                                        }
//                                    } else {
//                                        labOrigen.setTextColor(Color.RED);
//                                        labDestino.setTextColor(Color.RED);
//                                    }
//                                } else {
//                                    labDestino.setTextColor(Color.RED);
//                                }
//                            } else {
//                                labOrigen.setTextColor(Color.RED);
//                            }
//
//                        } else {
//                            dateAvaliable.setHintTextColor(Color.RED);
//                            dateAvaliable.setText(null);
//                        }
//
//                    } else {
//                        dateAvaliable.setHintTextColor(Color.RED);
//                        dateAvaliable.setText(null);
//                    }
//                } else {
//                    nameBox.setHintTextColor(Color.RED);
//                    nameBox.setText(null);
//                }
                }
            }
        };
        listener2 = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rdGato:
                        animalType = "Gato/a";
                        otherType.setVisibility(View.GONE);
                        otherType.setText(null);
                        break;
                    case R.id.rdPerro:
                        animalType = "Perro/a";
                        otherType.setVisibility(View.GONE);
                        otherType.setText(null);
                        break;
                    case R.id.radioOtro:
                        otherType.setVisibility(View.VISIBLE);
                        otherType.setHint("¿Cuál?");
                        break;
                }
            }

        };
    }

    /**
     * Method for adding listener to the elements
     */
    private void addElementsToListener() {
        btModify.setOnClickListener(listener);
        btSeeInterested.setOnClickListener (listener);
        btDelete.setOnClickListener (listener);
        typePet.setOnCheckedChangeListener(listener2);
    }


    /**
     * Method for loading demand data in the view
     */
    private void loadData() {
        //set value to name of the user field
        nameLabel.setText(nameUser);
        namePet.setText(myDemand.getNamePet());
        animalType= myDemand.getTypePet();
        if (animalType.equals("Gato/a")) {
            cat.setChecked(true);
            otherType.setVisibility(View.GONE);
        } else if (animalType.equals("Perro/a")) {
            dog.setChecked(true);
            otherType.setVisibility(View.GONE);
        }else {
            other.setChecked(true);
            otherType.setVisibility(View.VISIBLE);
            otherType.setText(animalType);
        }
        dateFrom.setText(new SimpleDateFormat("dd-MM-yyyy").format(myDemand.getAvailableFrom().getTime()));
        if (myDemand.getDeadline()!=null) {
            dateUntill.setText(new SimpleDateFormat("dd-MM-yyyy").format(myDemand.getDeadline().getTime()));
        } else {
            dateUntill.setText("Sin límite");
        }

         String orcity= myDemand.getOriginCity();
         for (int i=0; i<locations.length; i++){
             if (locations[i].equals(orcity)) {
                 originCity.setSelection(i);
             }
         }
        String descity= myDemand.getDestinyCity();
        for (int i=0; i<locations.length; i++){
            if (locations[i].equals(descity)) {
                destination.setSelection(i);
            }
        }
        comments.setText(myDemand.getComments());
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
                    finishAffinity();
                    break;
            }
        return true;
    }
}
