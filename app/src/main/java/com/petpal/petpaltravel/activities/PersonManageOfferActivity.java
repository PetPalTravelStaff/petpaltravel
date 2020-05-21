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
    TextView typePet, nameLabel, lborigin, lbdestination, lbtransport;
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
        typePet= (TextView) findViewById(R.id.tvAcompanyoA);
        cat = (CheckBox) findViewById(R.id.cbGatos);
        dog = (CheckBox) findViewById(R.id.cbPerros);
        other = (CheckBox) findViewById(R.id.cbOtros);
        dateTravel = (EditText) findViewById(R.id.etDiaViaje);
        origin = (Spinner) findViewById(R.id.spDesde);
        lborigin= (TextView) findViewById(R.id.tvViajoDesde);
        destination = (Spinner) findViewById(R.id.spHasta);
        lbdestination= (TextView) findViewById(R.id.tvViajeDestino);
        ArrayAdapter<String> questionsAdapter = new ArrayAdapter<String>(PersonManageOfferActivity.this, android.R.layout.simple_spinner_item, locations);
        questionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        origin.setAdapter(questionsAdapter);
        destination.setAdapter(questionsAdapter);
        lbtransport= (TextView) findViewById(R.id.tvMeTrasladoEn);
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
                    boolean result= myModel.cancelOffer(myOffer);
                    if (result) {
                        Intent intent1 = new Intent(PersonManageOfferActivity.this, UserSearchOffersActivity.class);
                        startActivity(intent1);
                    } else {
                        btDelete.setText("Error. Prueba más tarde");
                        btDelete.setTextColor(Color.RED);
                    }
                } else if (view.getId() == R.id.btModificar) {
                    String dateString= null;
                    if (dateTravel.getText()!=null) {
                        dateString=dateTravel.getText().toString();
                    };
                    GregorianCalendar dateCal= null;
                    String cityOrigin= origin.getSelectedItem().toString();
                    String cityDestiny= destination.getSelectedItem().toString();
                    Boolean catChoosed= cat.isChecked();
                    Boolean dogChoosed= dog.isChecked();
                    Boolean otherChoosed= other.isChecked();
                    String transportation= transportType.getSelectedItem().toString();
                    String commentString= comments.getText().toString();
                    CompanionOfPet newDataOffer= myOffer;
                    if (!"".equals(dateString)){//check empty date
                        dateTravel.setHintTextColor(Color.BLACK);
                        dateCal= validateDate(dateString);
                        if (dateCal!=null) {
                            dateTravel.setHintTextColor(Color.BLACK);
                            newDataOffer.setDateTravel(dateCal);
                            if (cityOrigin!=null){
                                lborigin.setTextColor(Color.BLACK);
                                newDataOffer.setOriginCity(cityOrigin);
                                if (cityDestiny!=null){
                                    lbdestination.setTextColor(Color.BLACK);
                                    if (!cityDestiny.equals(cityOrigin)) {
                                        lborigin.setTextColor(Color.BLACK);
                                        lbdestination.setTextColor(Color.BLACK);
                                        newDataOffer.setDestinyCity(cityDestiny);
                                        Boolean noChooseType= false;
                                        if (catChoosed == false & dogChoosed == false & otherChoosed == false){
                                            noChooseType=true;
                                        } else{
                                            noChooseType=false;
                                        }
                                        if (!noChooseType) {
                                            typePet.setTextColor(Color.BLACK);
                                            Boolean coma1 = false;
                                            Boolean coma2 = false;
                                            if (catChoosed == true & dogChoosed == true) {
                                                coma1 = true;
                                            }
                                            if (dogChoosed == true & otherChoosed == true) {
                                                coma2 = true;
                                            }
                                            StringBuilder type = new StringBuilder();
                                            if (catChoosed) {
                                                type.append("Gato/a");
                                                if (coma1) {
                                                    type.append(", ");
                                                }
                                            }
                                            if (dogChoosed) {
                                                type.append("Perro/a");
                                                if (coma2) {
                                                    type.append(", ");
                                                }
                                            }
                                            if (otherChoosed) {
                                                type.append("Otros");
                                            }
                                            newDataOffer.setPetType(type.toString());

                                            if (transportation != null) {
                                                lbtransport.setTextColor(Color.BLACK);
                                                newDataOffer.setTransport(transportation);
                                                newDataOffer.setComments(commentString);
                                                Boolean control = myModel.modifyOffer(newDataOffer);
                                                if (control) {
                                                    Intent intent1 = new Intent(PersonManageOfferActivity.this, UserSearchOffersActivity.class);
                                                    //Create a bundle object
                                                    Bundle bundle = new Bundle();
                                                    //set interesting data
                                                    bundle.putInt("idOffer", idOffer);
                                                    intent1.putExtras(bundle);
                                                    startActivity(intent1);
                                                } else {
                                                    btModify.setText("Error. Prueba más tarde");
                                                    btModify.setTextColor(Color.RED);
                                                }
                                            } else {
                                                lbtransport.setTextColor(Color.RED);
                                            }
                                        } else {
                                            typePet.setTextColor(Color.RED);
                                        }
                                    }else {
                                        lborigin.setTextColor(Color.RED);
                                        lbdestination.setTextColor(Color.RED);
                                    }
                                }else {
                                    lbdestination.setTextColor(Color.RED);
                                }
                            }else {
                                lborigin.setTextColor(Color.RED);
                            }

                        } else {
                            dateTravel.setHintTextColor(Color.RED);
                            dateTravel.setText(null);
                        }

                    } else {
                        dateTravel.setHintTextColor(Color.RED);
                        dateTravel.setText(null);
                    }
                }
            }
        };
    }

    private GregorianCalendar validateDate(String dateString) {
        GregorianCalendar result= null;
        int yearDate=0;
        int monthDate=0;
        int dayDate=0;

        if (dateString!=null){
            Boolean control= false;
            Pattern pattern = Pattern.compile("\\d{1,2}-\\d{1,2}-\\d{4}");
            Matcher mather = pattern.matcher(dateString);
            control= mather.find();
            if (control){
                String[] datePieces= dateString.split("-");
                try {
                    yearDate = Integer.parseInt(datePieces[2]);
                }catch (Exception e){
                    yearDate=0;
                }
                if (yearDate>= Calendar.getInstance().get(Calendar.YEAR)) {
                    try {
                        monthDate = Integer.parseInt(datePieces[1]);
                    } catch (Exception e) {
                        monthDate = 0;
                    }
                    if (yearDate == Calendar.getInstance().get(Calendar.YEAR) &
                            monthDate < Calendar.getInstance().get(Calendar.MONTH)) {
                        dayDate = 0;
                    } else {
                        try {
                            dayDate = Integer.parseInt(datePieces[0]);
                        } catch (Exception e) {
                            dayDate = 0;
                        }
                        if (yearDate == Calendar.getInstance().get(Calendar.YEAR) &
                                monthDate == Calendar.getInstance().get(Calendar.MONTH) &
                                dayDate <= Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
                            dayDate = 0;
                        } else {
                            switch (monthDate) {
                                case 1:
                                case 3:
                                case 5:
                                case 7:
                                case 8:
                                case 10:
                                case 12:

                                    if (0 < dayDate & dayDate <=31) {
                                        dayDate = dayDate;
                                    } else {
                                        dayDate = 0;
                                    }
                                    break;
                                case 4:
                                case 6:
                                case 9:
                                case 11:
                                    if (0 < dayDate & dayDate <= 30) {
                                        dayDate = dayDate;
                                    } else {
                                        dayDate = 0;
                                    }
                                    break;
                                case 2:
                                    //if year is bisiesto
                                    if ((yearDate % 4 == 0 && yearDate % 100 != 0) || (yearDate % 100 == 0 && yearDate % 400 == 0)) {
                                        if (0 < dayDate & dayDate <= 29) {
                                            dayDate = dayDate;
                                            ;
                                        } else {
                                            dayDate = 0;
                                        }
                                    } else {
                                        if (0 < dayDate & dayDate <= 28) {
                                            dayDate = dayDate;
                                        } else {
                                            dayDate = 0;
                                        }
                                    }
                                    break;
                                default:
                                    dayDate = 0;
                                    break;
                            }
                        }
                    }
                }else {
                    yearDate=0;
                    monthDate=0;
                    dayDate=0;
                }
            } else {
                yearDate=0;
                monthDate=0;
                dayDate=0;
            }
        }
        result= new GregorianCalendar(yearDate, monthDate, dayDate);
        if (result.before(Calendar.getInstance())) {
            result=null;
        }
        return result;
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