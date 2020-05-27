package com.petpal.petpaltravel.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShelterManageDemandActivity extends AppCompatActivity {
    //Atributes
    TextView nameLabel, lbtypePet, lborigin, lbdestination;
    EditText namePet, comments, otherType, dateFrom, dateUntill;
    String nameUser, phoneUser, animalType;
    RadioGroup typePet;
    RadioButton cat, dog, other;
    Spinner originCity, destinationCity;
    String[] locations;
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
        myDemand = myModel.recoverDemandById(idDemand);
        locations = myModel.getCities();
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
        idDemand = bun.getInt("idDemand", 0);
    }

    /**
     * Method for create elements of activity
     */
    private void initElements() {
        nameLabel = (TextView) findViewById(R.id.etNombrePersona);
        namePet = (EditText) findViewById(R.id.etNombreMascota);
        lborigin = (TextView) findViewById(R.id.tvViajaDesde);
        lbdestination = (TextView) findViewById(R.id.tvViajeDestino);
        lbtypePet = (TextView) findViewById(R.id.tvTipoMascota);
        typePet = (RadioGroup) findViewById(R.id.radioTipo);
        cat = (RadioButton) findViewById(R.id.rdGato);
        dog = (RadioButton) findViewById(R.id.rdPerro);
        other = (RadioButton) findViewById(R.id.radioOtro);
        otherType = (EditText) findViewById(R.id.etQueTipo);
        originCity = (Spinner) findViewById(R.id.spViajaDesde);
        destinationCity = (Spinner) findViewById(R.id.spViajeDestino);
        ArrayAdapter<String> questionsAdapter = new ArrayAdapter<String>(ShelterManageDemandActivity.this, android.R.layout.simple_spinner_item, locations);
        questionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        originCity.setAdapter(questionsAdapter);
        destinationCity.setAdapter(questionsAdapter);
        dateFrom = (EditText) findViewById(R.id.etDisponibleDesde);
        dateUntill = (EditText) findViewById(R.id.etDisponibleHasta);
        comments = (EditText) findViewById(R.id.etComentarios);

        btModify = (Button) findViewById(R.id.btModificar);
        btSeeInterested = (Button) findViewById(R.id.btVerPersonasInteresadas);
        btDelete = (Button) findViewById(R.id.btCancelarPeticion);
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
            phoneUser = shared.getString("userPhone", null);
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
                    AlertDialog.Builder myAlert = new AlertDialog.Builder(ShelterManageDemandActivity.this);
                    myAlert.setMessage("¿Seguro que quieres borrarla? Esta acción no se puede deshacer")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    boolean result = myModel.cancelDemand(myDemand);
                                    if (result) {
                                        Intent intent1 = new Intent(ShelterManageDemandActivity.this, UserSearchDemandsActivity.class);
                                        startActivity(intent1);
                                    } else {
                                        btDelete.setText("Error. Prueba más tarde");
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
                    AlertDialog title = myAlert.create();
                    title.setTitle("Borrar petición");
                    title.show();
                } else if (view.getId() == R.id.btModificar) {
                    //recover data from form and save it in variables
                    String petName = null;
                    if (namePet.getText() != null) {
                        petName = namePet.getText().toString();
                    }
                    String dateFromString = null;
                    if (dateFrom.getText() != null) {
                        dateFromString = dateFrom.getText().toString();
                    }
                    String dateUntillString = null;
                    if (dateUntill.getText() != null) {
                        dateUntillString = dateUntill.getText().toString();
                    }
                    GregorianCalendar dateCalFrom = null;
                    GregorianCalendar dateCalUntil = null;
                    String cityOrigin = originCity.getSelectedItem().toString();
                    String cityDestiny = destinationCity.getSelectedItem().toString();
                    int typeSelected = typePet.getCheckedRadioButtonId();
                    String commentString = comments.getText().toString();
                    CompanionForPet newDataDemand = myDemand;
                    if (!"".equals(petName)) {
                        namePet.setHintTextColor(Color.BLACK);
                        if (!"".equals(dateFromString)) {//check empty date avaliable
                            dateFrom.setHintTextColor(Color.BLACK);
                            dateCalFrom = validateDate(dateFromString);
                            if (dateCalFrom != null) { //check valid date avaliable
                                dateFrom.setHintTextColor(Color.BLACK);
                                if (!"".equals(dateUntillString)) { //if deadline is written: check valid date
                                    dateCalUntil = validateDate(dateUntillString);
                                }
                                if (cityOrigin != null) {
                                    lborigin.setTextColor(Color.BLACK);
                                    if (cityDestiny != null) {
                                        lbdestination.setTextColor(Color.BLACK);
                                        if (!cityDestiny.equals(cityOrigin)) {
                                            lborigin.setTextColor(Color.BLACK);
                                            lbdestination.setTextColor(Color.BLACK);
                                            Boolean noChooseType = false;
                                            if (!cat.isChecked() & !dog.isChecked() & !other.isChecked()) {
                                                noChooseType = true;
                                            } else {
                                                if (other.isChecked()) {
                                                    if (otherType.getText() == null & "".equals(otherType.getText().toString())) {
                                                        noChooseType = true;
                                                    } else {
                                                        animalType = otherType.getText().toString();
                                                        noChooseType = false;
                                                    }
                                                }
                                            }
                                            if (!noChooseType) {
                                                lbtypePet.setTextColor(Color.BLACK);
                                                otherType.setHintTextColor(Color.BLACK);
                                                newDataDemand.setNamePet(petName);
                                                newDataDemand.setAvailableFrom(dateCalFrom);
                                                if (dateCalUntil != null) {
                                                    newDataDemand.setDeadline(dateCalUntil);
                                                } else {
                                                    newDataDemand.setDeadline(null);
                                                }
                                                newDataDemand.setOriginCity(cityOrigin);
                                                newDataDemand.setDestinyCity(cityDestiny);
                                                newDataDemand.setTypePet(animalType);
                                                newDataDemand.setComments(commentString);
                                                Boolean control = myModel.modifyDemand(newDataDemand);
                                                if (control) {
                                                    Intent intent1 = new Intent(ShelterManageDemandActivity.this, UserSearchDemandsActivity.class);
                                                    //Create a bundle object
                                                    Bundle bundle = new Bundle();
                                                    //set interesting data
                                                    bundle.putInt("idDemand", idDemand);
                                                    intent1.putExtras(bundle);
                                                    startActivity(intent1);
                                                } else {
                                                    btModify.setText("Error. Prueba más tarde");
                                                    btModify.setTextColor(Color.RED);
                                                }
                                            } else {
                                                lbtypePet.setTextColor(Color.RED);
                                                otherType.setHintTextColor(Color.RED);
                                            }
                                        } else {
                                            lborigin.setTextColor(Color.RED);
                                            lbdestination.setTextColor(Color.RED);
                                        }
                                    } else {
                                        lbdestination.setTextColor(Color.RED);
                                    }
                                } else {
                                    lborigin.setTextColor(Color.RED);
                                }

                            } else {
                                dateFrom.setHintTextColor(Color.RED);
                                dateFrom.setText(null);
                            }

                        } else {
                            dateFrom.setHintTextColor(Color.RED);
                            dateFrom.setText(null);
                        }
                    } else {
                        namePet.setHintTextColor(Color.RED);
                        namePet.setText(null);
                    }
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
     * Auxiliar Method for validating a string as a date after today as dd-mm-yyyy
     * @param dateString String to validate
     * @return the date in Gregorian Calendar format
     */
    private GregorianCalendar validateDate(String dateString) {
        GregorianCalendar result = null;
        int yearDate = -1;
        int monthDate = -1;
        int dayDate = -1;

        if (dateString != null) {
            Boolean control = false;
            Pattern pattern = Pattern.compile("\\d{1,2}-\\d{1,2}-\\d{4}");
            Matcher mather = pattern.matcher(dateString);
            control = mather.find();
            if (control) {
                String[] datePieces = dateString.split("-");
                try {
                    yearDate = Integer.parseInt(datePieces[2]);
                    if (yearDate >= Calendar.getInstance().get(Calendar.YEAR)) {
                        try {
                            monthDate = Integer.parseInt(datePieces[1]);
                            try {
                                dayDate = Integer.parseInt(datePieces[0]);
                                switch (monthDate) {
                                    case 1:
                                    case 3:
                                    case 5:
                                    case 7:
                                    case 8:
                                    case 10:
                                    case 12:
                                        if (0 < dayDate & dayDate <= 31) {
                                            result = new GregorianCalendar(yearDate, monthDate-1, dayDate);
                                        } else {
                                            result = null;
                                        }
                                        break;
                                    case 4:
                                    case 6:
                                    case 9:
                                    case 11:
                                        if (0 < dayDate & dayDate <= 30) {
                                            result = new GregorianCalendar(yearDate, monthDate-1, dayDate);
                                        } else {
                                            result = null;
                                        }
                                        break;
                                    case 2:
                                        //if year is bisiesto
                                        if ((yearDate % 4 == 0 && yearDate % 100 != 0) || (yearDate % 100 == 0 && yearDate % 400 == 0)) {
                                            if (0 < dayDate & dayDate <= 29) {
                                                result = new GregorianCalendar(yearDate, monthDate-1, dayDate);
                                                ;
                                            } else {
                                                result = null;
                                            }
                                        } else {
                                            if (0 < dayDate & dayDate <= 28) {
                                                result = new GregorianCalendar(yearDate, monthDate-1, dayDate);
                                            } else {
                                                result = null;
                                            }
                                        }
                                        break;
                                    default:
                                        result = null;
                                        break;
                                }
                            } catch (Exception e) {
                                result = null;
                            }
                        } catch (Exception exe) {
                            result = null;
                        }

                    } else {
                        result = null;
                    }
                } catch (Exception exem) {
                    result= null;
                }
            } else {
                result=null;
            }
        } else {
            result=null;
        }
        if (result != null) {
            if (result.before(Calendar.getInstance())) {
                result = null;
            }
        }
        return result;
    }

    /**
     * Method for adding listener to the elements
     */
    private void addElementsToListener() {
        btModify.setOnClickListener(listener);
        btSeeInterested.setOnClickListener(listener);
        btDelete.setOnClickListener(listener);
        typePet.setOnCheckedChangeListener(listener2);
    }


    /**
     * Method for loading demand data in the view
     */
    private void loadData() {
        //set value to name of the user field
        nameLabel.setText(nameUser);
        namePet.setText(myDemand.getNamePet());
        animalType = myDemand.getTypePet();
        if (animalType.equals("Gato/a")) {
            cat.setChecked(true);
            otherType.setVisibility(View.GONE);
        } else if (animalType.equals("Perro/a")) {
            dog.setChecked(true);
            otherType.setVisibility(View.GONE);
        } else {
            other.setChecked(true);
            otherType.setVisibility(View.VISIBLE);
            otherType.setText(animalType);
        }
        dateFrom.setText(new SimpleDateFormat("dd-MM-yyyy").format(myDemand.getAvailableFrom().getTime()));
        if (myDemand.getDeadline() != null) {
            dateUntill.setText(new SimpleDateFormat("dd-MM-yyyy").format(myDemand.getDeadline().getTime()));
        } else {
            dateUntill.setText("Sin límite");
        }

        String orcity = myDemand.getOriginCity();
        for (int i = 0; i < locations.length; i++) {
            if (locations[i].equals(orcity)) {
                originCity.setSelection(i);
            }
        }
        String descity = myDemand.getDestinyCity();
        for (int i = 0; i < locations.length; i++) {
            if (locations[i].equals(descity)) {
                destinationCity.setSelection(i);
            }
        }
        comments.setText(myDemand.getComments());
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
