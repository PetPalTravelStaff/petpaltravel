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
import com.petpal.petpaltravel.model.CompanionForPet;
import com.petpal.petpaltravel.model.CompanionOfPet;
import com.petpal.petpaltravel.model.PPTModel;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddDemandActivity extends AppCompatActivity {
    //Attributes
    EditText dateAvaliable, dateDeadLine, comments, otherType, nameBox;
    TextView labOrigen, labDestino, labTipo;
    TextView nameUserLabel;
    Spinner originCity, destinyCity, transport;
    RadioGroup petType;
    RadioButton cat, dog, other;

    String[] allCities;
    private Button btPostDemand;
    View.OnClickListener listener;
    RadioGroup.OnCheckedChangeListener listener2;
    PPTModel myModel;
    int userId;
    String nameUser;
    String animalType = null;
    Boolean isShelter;
    CompanionForPet myDemand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelterdemand_layout);
        //instantiate model
        myModel = new PPTModel();
        //recover needed data
        recoverShared();
        allCities = myModel.getCities();
        //Create view elements in activity
        initElements();
        //create a listener
        createListener();
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
     * Method for create elements of activity
     */
    private void initElements() {
        nameBox = (EditText) findViewById(R.id.etNombre);
        dateAvaliable = (EditText) findViewById(R.id.etFechadisponible);
        dateDeadLine = (EditText) findViewById(R.id.etFechaLimite);
        otherType = (EditText) findViewById(R.id.etOtro);
        otherType.setVisibility(View.GONE);
        originCity = (Spinner) findViewById(R.id.spCiudadOrigen);
        destinyCity = (Spinner) findViewById(R.id.spCiudadDestino);
        //Create adapter for cities
        ArrayAdapter<String> adapterCity = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, allCities);
        //set the spinners adapter to the previously created one.
        originCity.setAdapter(adapterCity);
        destinyCity.setAdapter(adapterCity);
        cat = (RadioButton) findViewById(R.id.rdGato);
        dog = (RadioButton) findViewById(R.id.rdPerro);
        other = (RadioButton) findViewById(R.id.radioOtro);
        petType = (RadioGroup) findViewById(R.id.radioTipo);
        comments = (EditText) findViewById(R.id.etComentarios);
        nameUserLabel = (TextView) findViewById(R.id.etNombreProtectora);
        nameUserLabel.setText(nameUser);
        btPostDemand = (Button) findViewById(R.id.btPublicar);
    }

    /**
     * Method for creating a listener and identify what to do
     */
    private void createListener() {

        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //recover data from form and save it in variables
                String namePet = null;
                if (nameBox.getText() != null) {
                    namePet = nameBox.getText().toString();
                }
                String dateFromString = null;
                if (dateAvaliable.getText() != null) {
                    dateFromString = dateAvaliable.getText().toString();
                System.out.println("Date from String es... " + dateFromString);
                }
                String dateUntillString = null;
                if (dateDeadLine.getText() != null) {
                    dateUntillString = dateDeadLine.getText().toString();
                }
                GregorianCalendar dateCalFrom = null;
                GregorianCalendar dateCalUntil = null;
                String cityOrigin = originCity.getSelectedItem().toString();
                String cityDestiny = destinyCity.getSelectedItem().toString();
                int typeSelected = petType.getCheckedRadioButtonId();
                String commentString = comments.getText().toString();
                myDemand = new CompanionForPet();
                myDemand.setIdeUserShelterOffering(userId);
                myDemand.setNameShelter(nameUser);
                if (!"".equals(namePet)) {
                    nameBox.setHintTextColor(Color.BLACK);
                    myDemand.setNamePet(namePet);
                    if (!"".equals(dateFromString)) {//check empty date avaliable
                        dateAvaliable.setHintTextColor(Color.BLACK);
                        dateCalFrom = validateDate(dateFromString);
                        if (dateCalFrom != null) { //check valid date avaliable
                            dateAvaliable.setHintTextColor(Color.BLACK);
                            myDemand.setAvailableFrom(dateCalFrom);
                            if (!"".equals(dateUntillString)) { //if deadline is written: check valid date
                                dateCalUntil = validateDate(dateUntillString);
                                if (dateCalUntil != null) {
                                    myDemand.setDeadline(dateCalUntil);
                                }
                            } else { //if not writen save null.
                                myDemand.setDeadline(null);
                            }
                            if (cityOrigin != null) {
                                labOrigen.setTextColor(Color.BLACK);
                                myDemand.setOriginCity(cityOrigin);
                                if (cityDestiny != null) {
                                    labDestino.setTextColor(Color.BLACK);
                                    if (!cityDestiny.equals(cityOrigin)) {
                                        labOrigen.setTextColor(Color.BLACK);
                                        labDestino.setTextColor(Color.BLACK);
                                        myDemand.setDestinyCity(cityDestiny);
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
                                            labTipo.setTextColor(Color.BLACK);
                                            otherType.setHintTextColor(Color.BLACK);
                                            myDemand.setTypePet(animalType);
                                            myDemand.setComments(commentString);
                                            int idDemand = myModel.addDemandToBD(myDemand);
                                            if (idDemand != 0) {
                                                Intent intent1 = new Intent(AddDemandActivity.this, ShowDemandActivity.class);
                                                //Create a bundle object
                                                Bundle bundle = new Bundle();
                                                //set interesting data
                                                bundle.putInt("idDemand", idDemand);
                                                intent1.putExtras(bundle);
                                                startActivity(intent1);
                                            } else {
                                                btPostDemand.setText("Error. Prueba más tarde");
                                                btPostDemand.setTextColor(Color.RED);
                                            }
                                        } else {
                                            labTipo.setTextColor(Color.RED);
                                            otherType.setHintTextColor(Color.RED);
                                        }
                                    } else {
                                        labOrigen.setTextColor(Color.RED);
                                        labDestino.setTextColor(Color.RED);
                                    }
                                } else {
                                    labDestino.setTextColor(Color.RED);
                                }
                            } else {
                                labOrigen.setTextColor(Color.RED);
                            }

                        } else {
                            System.out.println("No, salta aqui");
                            dateAvaliable.setHintTextColor(Color.RED);
                            dateAvaliable.setText(null);
                        }

                    } else {
                        System.out.println("Salta aqui porque no tiene fecha");
                        dateAvaliable.setHintTextColor(Color.RED);
                        dateAvaliable.setText(null);
                    }
                } else {
                    nameBox.setHintTextColor(Color.RED);
                    nameBox.setText(null);
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
                        break;
                    case R.id.rdPerro:
                        animalType = "Perro/a";
                        otherType.setVisibility(View.GONE);
                        break;
                    case R.id.radioOtro:
                        otherType.setVisibility(View.VISIBLE);
                        otherType.setHint("¿Cuál?");
                        break;
                }
            }

        };
    }

    private GregorianCalendar validateDate(String dateString) {
        GregorianCalendar result = null;
        int yearDate = 0;
        int monthDate = 0;
        int dayDate = 0;

        if (dateString != null) {
            Boolean control = false;
            Pattern pattern = Pattern.compile("\\d{1,2}-\\d{1,2}-\\d{4}");
            Matcher mather = pattern.matcher(dateString);
            control = mather.find();
            if (control) {
                String[] datePieces = dateString.split("-");
                try {
                    yearDate = Integer.parseInt(datePieces[2]);
                } catch (Exception e) {
                    yearDate = 0;
                }
                if (yearDate >= Calendar.getInstance().get(Calendar.YEAR)) {
                    System.out.println("El año es " +yearDate);
                    try {
                        monthDate = Integer.parseInt(datePieces[1]);
                    } catch (Exception e) {
                        monthDate = 0;
                    }
                    System.out.println("El mes es " +monthDate);
                    int provDay;
                            switch (monthDate) {
                                case 1:
                                case 3:
                                case 5:
                                case 7:
                                case 8:
                                case 10:
                                case 12:
                                    try {
                                        provDay = Integer.parseInt(datePieces[0]);
                                    } catch (Exception e) {
                                        System.out.println("Salta el catch del dia");
                                        provDay = 0;
                                    }
                                    System.out.println("El dia provisional es" + provDay);
                                    if (0 < provDay & provDay >= 31) {
                                        dayDate = provDay;
                                    } else {
                                        dayDate = 0;
                                    }
                                    break;
                                case 4:
                                case 6:
                                case 9:
                                case 11:
                                    try {
                                        provDay = Integer.parseInt(datePieces[0]);
                                    } catch (Exception e) {
                                        provDay = 0;
                                    }
                                    if (0 < provDay & provDay >= 30) {
                                        dayDate = provDay;
                                    } else {
                                        dayDate = 0;
                                    }
                                    break;
                                case 2:
                                    try {
                                        provDay = Integer.parseInt(datePieces[0]);
                                    } catch (Exception e) {
                                        provDay = 0;
                                    }
                                    //if year is bisiesto
                                    if ((yearDate % 4 == 0 && yearDate % 100 != 0) || (yearDate % 100 == 0 && yearDate % 400 == 0)) {
                                        if (0 < provDay & provDay >= 29) {
                                            dayDate = provDay;
                                        } else {
                                            dayDate = 0;
                                        }
                                    } else {
                                        if (0 < provDay & provDay >= 28) {
                                            dayDate = provDay;
                                        } else {
                                            dayDate = 0;
                                        }
                                    }
                                    break;
                                default:
                                    dayDate=0;
                                    System.out.println("Salta el default");
                                    break;
                            }
                    System.out.println("El dia es " +dayDate);

                } else {
                    yearDate=0;
                }
                if (yearDate==0 ||monthDate==0|| dayDate==0) {
                    result=null;
                } else {
                    if (new GregorianCalendar(yearDate, monthDate, dayDate).after(Calendar.getInstance())){
                        result=null;
                    } else {
                        result = new GregorianCalendar(yearDate, monthDate, dayDate);
                    }
                }
            } else {
                result=null;
            }
        }
        return result;
    }

    /**
     * Method for adding a listener to the elements
     */
    private void addElementsToListener() {
        btPostDemand.setOnClickListener(listener);
        petType.setOnCheckedChangeListener(listener2);
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
        menu.add(0, 3, 2, "Buscar ofertas");
        menu.add(0, 4, 3, "Salir");
        return true;
    }

    // Handles item selections from Option MENU
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                //Go to view account activity
                Intent intent1 = new Intent(AddDemandActivity.this, ViewAccountActivity.class);
                startActivity(intent1);
                break;
            case 2:
                Intent intent2 = new Intent(AddDemandActivity.this, SearchDemandsActivity.class);
                startActivity(intent2);
                break;
            case 3:
                Intent intent3 = new Intent(AddDemandActivity.this, SearchOffersActivity.class);
                startActivity(intent3);
                break;
            case 4://Exit
                finish();
                break;
        }
        return true;
    }

}
