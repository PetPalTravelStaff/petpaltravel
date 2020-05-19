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
import android.widget.Spinner;
import android.widget.TextView;

import com.petpal.petpaltravel.R;
import com.petpal.petpaltravel.model.CompanionOfPet;
import com.petpal.petpaltravel.model.PPTModel;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddOfferActivity extends AppCompatActivity {
    //Attributes
    EditText dateTravel, comments;
    TextView labOrigen, labDestino, labTipo, labTransp;
    TextView nameUserLabel, nameBox;
    Spinner originCity, destinyCity, transport;

    String[] allTransports, allCities;
    CheckBox cat, dog, others;
    private Button btPostOffer;
    View.OnClickListener listener;
    PPTModel myModel;
    int userId;
    String nameUser;
    Boolean isShelter;
    CompanionOfPet myOffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personpostoffer_layout);
        //instantiate model
        myModel = new PPTModel();
        //recover needed data
        recoverShared();
        allTransports= myModel.getTransport();
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
        if (shared!=null) {
            //Use the editor to catch the couples of dates
            nameUser = shared.getString("userName", "");
            userId= shared.getInt("id", 0);
            isShelter = shared.getBoolean("isShelter", false);
        }
    }

    /**
     * Method for create elements of activity
     */
    private void initElements() {
        nameBox= (TextView) findViewById(R.id.etMiNombreEs);
        nameBox.setText(nameUser);
        dateTravel= (EditText) findViewById(R.id.etFechaViajePersona);
        originCity= (Spinner) findViewById(R.id.spCiudadOrigen);
        destinyCity= (Spinner) findViewById(R.id.spCiudadDestino);
        //Create adapter for cities
        ArrayAdapter<String> adapterCity = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, allCities);
        //set the spinners adapter to the previously created one.
        originCity.setAdapter(adapterCity);
        destinyCity.setAdapter(adapterCity);
        cat= (CheckBox) findViewById(R.id.cbGatos);
        dog= (CheckBox) findViewById(R.id.cbPerros);
        others= (CheckBox) findViewById(R.id.cbOtros);
        transport= (Spinner) findViewById(R.id.spViajareEn);
        //Create adapter for transport
        ArrayAdapter<String> adapterTrans = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, allTransports);
        //set the spinners adapter to the previously created one.
        transport.setAdapter(adapterTrans);
        comments= (EditText) findViewById(R.id.etComentarios);
        nameUserLabel= (TextView) findViewById(R.id.etNombrePersona);
        nameUserLabel.setText(nameUser);
        btPostOffer= (Button) findViewById(R.id.btPublicar);
        labOrigen= (TextView) findViewById(R.id.tvCiudadOrigen) ;
        labDestino= (TextView) findViewById(R.id.tvCiudadDestino);
        labTipo= (TextView) findViewById(R.id.tvAcompanyoA);
        labTransp= (TextView) findViewById(R.id.tvTipoTransporte);
    }

    /**
     * Method for creating a listener and identify what to do
     */
    private void createListener() {
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dateString= null;
                if (dateTravel.getText()!=null) {
                    dateString=dateTravel.getText().toString();
                };
                GregorianCalendar dateCal= null;
                String cityOrigin= originCity.getSelectedItem().toString();
                String cityDestiny= destinyCity.getSelectedItem().toString();
                Boolean catChoosed= cat.isChecked();
                Boolean dogChoosed= dog.isChecked();
                Boolean otherChoosed= others.isChecked();
                String transportation= transport.getSelectedItem().toString();
                String commentString= comments.getText().toString();
                myOffer= new CompanionOfPet();
                myOffer.setIdeUserPersonOffering(userId);
                myOffer.setNamePerson(nameUser);
                if (!"".equals(dateString)){//check empty date
                    dateTravel.setHintTextColor(Color.BLACK);
                    dateCal= validateDate(dateString);
                    if (dateCal!=null) {
                        dateTravel.setHintTextColor(Color.BLACK);
                        myOffer.setDateTravel(dateCal);
                        if (cityOrigin!=null){
                            labOrigen.setTextColor(Color.BLACK);
                            myOffer.setOriginCity(cityOrigin);
                            if (cityDestiny!=null){
                                labDestino.setTextColor(Color.BLACK);
                                if (!cityDestiny.equals(cityOrigin)) {
                                    labOrigen.setTextColor(Color.BLACK);
                                    labDestino.setTextColor(Color.BLACK);
                                    myOffer.setDestinyCity(cityDestiny);
                                    Boolean noChooseType= false;
                                    if (catChoosed == false & dogChoosed == false & otherChoosed == false){
                                        noChooseType=true;
                                    } else{
                                        noChooseType=false;
                                    }
                                    if (!noChooseType) {
                                        labTipo.setTextColor(Color.BLACK);
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
                                        myOffer.setPetType(type.toString());

                                        if (transportation != null) {
                                            labTransp.setTextColor(Color.BLACK);
                                            myOffer.setTransport(transportation);
                                            myOffer.setComments(commentString);
                                            int idOffer = myModel.addOfferToBD(myOffer);
                                            System.out.println("Id offer es: "+ idOffer);
                                            if (idOffer != 0) {
                                                Intent intent1 = new Intent(AddOfferActivity.this, ShowOfferActivity.class);
                                                //Create a bundle object
                                                Bundle bundle = new Bundle();
                                                //set interesting data
                                                bundle.putInt("idOffer", idOffer);
                                                intent1.putExtras(bundle);
                                                startActivity(intent1);
                                            } else {
                                                btPostOffer.setText("Error. Prueba más tarde");
                                                btPostOffer.setTextColor(Color.RED);
                                            }
                                        } else {
                                            labTransp.setTextColor(Color.RED);
                                        }
                                    } else {
                                        labTipo.setTextColor(Color.RED);
                                    }
                                }else {
                                    labOrigen.setTextColor(Color.RED);
                                    labDestino.setTextColor(Color.RED);
                                }
                            }else {
                                labDestino.setTextColor(Color.RED);
                            }
                        }else {
                            labOrigen.setTextColor(Color.RED);
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
                                    try {
                                        dayDate = Integer.parseInt(datePieces[0]);
                                    } catch (Exception e) {
                                        dayDate = 0;
                                    }
                                    if (0 < dayDate & dayDate > 31) {
                                        dayDate = dayDate;
                                    } else {
                                        dayDate = 0;
                                    }
                                    break;
                                case 4:
                                case 6:
                                case 9:
                                case 11:
                                    try {
                                        dayDate = Integer.parseInt(datePieces[0]);
                                    } catch (Exception e) {
                                        dayDate = -0;
                                    }
                                    if (0 < dayDate & dayDate > 30) {
                                        dayDate = dayDate;
                                    } else {
                                        dayDate = 0;
                                    }
                                    break;
                                case 2:
                                    try {
                                        dayDate = Integer.parseInt(datePieces[0]);
                                    } catch (Exception e) {
                                        dayDate = 0;
                                    }
                                    //if year is bisiesto
                                    if ((yearDate % 4 == 0 && yearDate % 100 != 0) || (yearDate % 100 == 0 && yearDate % 400 == 0)) {
                                        if (0 < dayDate & dayDate > 29) {
                                            dayDate = dayDate;
                                            ;
                                        } else {
                                            dayDate = 0;
                                        }
                                    } else {
                                        if (0 < dayDate & dayDate > 28) {
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
       return result;
    }

    /**
     * Method for adding a listener to the elements
     */
    private void addElementsToListener() {
        btPostOffer.setOnClickListener(listener);
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
            menu.add(0, 2, 1, "Ver mis ofertas");
            menu.add(0, 3, 2, "Buscar peticiones");
            menu.add(0, 4, 3, "Salir");
        return true;
    }

    // Handles item selections from Option MENU
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                //Go to view account activity
                Intent intent1 = new Intent(AddOfferActivity.this, ViewAccountActivity.class);
                startActivity(intent1);
                break;
            case 2:
                Intent intent2 = new Intent(AddOfferActivity.this, SearchOffersActivity.class);
                startActivity(intent2);
                break;
            case 3:
                Intent intent3 = new Intent(AddOfferActivity.this, SearchDemandsActivity.class);
                startActivity(intent3);
                break;
            case 4://Exit
                finish();
                break;
        }
        return true;
    }
}
