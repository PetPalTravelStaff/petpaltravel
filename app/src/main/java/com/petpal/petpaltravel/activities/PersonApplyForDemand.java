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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.petpal.petpaltravel.R;
import com.petpal.petpaltravel.model.ApplicationForDemand;
import com.petpal.petpaltravel.model.CompanionForPet;
import com.petpal.petpaltravel.model.PPTModel;

import java.sql.SQLOutput;

public class PersonApplyForDemand extends AppCompatActivity {
    //Attributes
    TextView nameBox, phoneBox, mailBox;
    EditText commentsBox;
    Button apply;
    Button btcancel;
    Spinner mySpinner;
    String[] transport;
     //0= user can apply , 1= user already has applies, so can modify or delete,
     // 2= updated data, -1= user can not apply or modify -2= user can not delete apply
     int situationFlag= 0;
    View.OnClickListener listener;
    ApplicationForDemand apliRecovered;
    ApplicationForDemand newApply;
    ApplicationForDemand applyModify;
    int demandId, idUser, provIdApli;
    private String nameUser, mailUser, phoneUser;
    Boolean isShelter;

    PPTModel myModel;
    CompanionForPet myDemand;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personapplytodemand_layout);
        //instantiate model
        myModel = new PPTModel();
        //Recover needed data
        recoverDemandId();
        recoverShared();
        transport= myModel.getTransport();
        myDemand= myModel.recoverDemandById(demandId);
        //Create view elements in activity
        initElements();
        loadData();
        //create a listener
        createListener();
        //load data in view
        addElementsToListener();
    }


    /**
     * Method for recovering data needed by bundle
     */
    private void recoverDemandId() {
        Bundle bun = this.getIntent().getExtras();
        demandId= bun.getInt("idDemand",0);
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
            mailUser = shared.getString("userMail", "");
            phoneUser = shared.getString("userPhone", "");
            isShelter = shared.getBoolean("isShelter", false);
            idUser= shared.getInt("id", 0);
        }
    }

    /**
     * Method for initiating elements related to layout
     */
    private void initElements() {
        mySpinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> questionsAdapter = new ArrayAdapter<String>(PersonApplyForDemand.this,android.R.layout.simple_spinner_item, transport);
        questionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(questionsAdapter);

        nameBox= (TextView) findViewById(R.id.etNombre);
        nameBox.setText(nameUser);
        phoneBox= (TextView) findViewById(R.id.etMiTelefono);
        phoneBox.setText(phoneUser);
        mailBox= (TextView) findViewById(R.id.etMiEmail);
        mailBox.setText(mailUser);
        commentsBox= (EditText) findViewById(R.id.etComentarios);

        apply= (Button) findViewById(R.id.btOfrecete);
        btcancel = (Button) findViewById(R.id.btBorrar);
    }

    /**
     * Method for loading data with recovered data or depending on situation
     */
    private void loadData() {
        //set situation flag depending on the case
        if (myDemand.getIdPersonInterestePosition(0)==idUser | myDemand.getIdPersonInterestePosition(1)==idUser |
                myDemand.getIdPersonInterestePosition(2)==idUser){
            apliRecovered= myModel.searchApplicationForDemand(demandId, idUser);
            commentsBox.setText(apliRecovered.getComments());
            String transportRecovered= apliRecovered.getTransport();
            if (transportRecovered.equals(transport[0])) {
                mySpinner.setSelection(0);
            } else if (transportRecovered.equals(transport[1])) {
                mySpinner.setSelection(1);
            }else if (transportRecovered.equals(transport[2])) {
                mySpinner.setSelection(2);
            }else if (transportRecovered.equals(transport[2])) {
                mySpinner.setSelection(2);
            }
            situationFlag=1;
        } else  {
            situationFlag=0;
        }

        //set values of text in buttons
        setButtonsValues();

    }

    /**
     * Auxiliar method for setting values of the buttons depending on the situation
     */
    private void setButtonsValues() {
        switch (situationFlag) {
            case 0: //normal case
                apply.setText("¡Me ofrezco!");
                apply.setEnabled(true);
                apply.setTextColor(Color.WHITE);
                apply.setVisibility(View.VISIBLE);
                btcancel.setVisibility(View.GONE);
                break;
            case 1: //person has applied already
                apply.setText("Guarda cambios");
                apply.setEnabled(true);
                apply.setTextColor(Color.WHITE);
                apply.setVisibility(View.VISIBLE);
                btcancel.setText("Cancelo");
                btcancel.setEnabled(true);
                btcancel.setTextColor(Color.WHITE);
                btcancel.setVisibility(View.VISIBLE);
                break;
            case 2: //person has update data
                apply.setText("¡Guardados!");
                apply.setEnabled(false);
                apply.setTextColor(Color.BLACK);
                apply.setVisibility(View.VISIBLE);
                btcancel.setText("Cancelo");
                btcancel.setTextColor(Color.WHITE);
                btcancel.setVisibility(View.VISIBLE);
                break;
            case -1: //there is some trouble in appling or updating
                apply.setText("Prueba más tarde");
                apply.setTextColor(Color.RED);
                apply.setVisibility(View.VISIBLE);
                btcancel.setVisibility(View.GONE);
                break;
            case -2: //there is some trouble in cancelling
                btcancel.setText("Prueba más tarde");
                btcancel.setTextColor(Color.RED);
                btcancel.setVisibility(View.VISIBLE);
                apply.setText("Prueba más tarde");
                apply.setTextColor(Color.RED);
                apply.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * Method for creating a listener and identify what to do
     */
    private void createListener() {
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btOfrecete) {
                    switch (situationFlag) {
                        case 0: //normal case: person apply to the demand
                            Boolean control;
                            newApply = new ApplicationForDemand();
                            newApply.setIdDemand(demandId);
                            newApply.setIdPersonApplying(idUser);
                            newApply.setNamePerson(nameUser);
                            newApply.setMail(mailUser);
                            newApply.setPhone(phoneUser);
                            String transport = mySpinner.getSelectedItem().toString();
                            newApply.setTransport(transport);
                            String comments = commentsBox.getText().toString();
                            newApply.setComments(comments);
                            provIdApli = myModel.addApplicationToDemand(newApply);
                            if (provIdApli !=0) {
                                //if can apply suscessfully
                                situationFlag = 1;
                            } else {
                                //if not
                                situationFlag = -1;
                            }
                            //set text to apply button
                            setButtonsValues();
                            break;
                        case 1: //person has applied already: person modify data
                            Boolean control2;
                            if (apliRecovered!=null) {
                                applyModify = apliRecovered;
                            } else {
                                applyModify = newApply;
                                applyModify.setIdDemand(provIdApli);
                            }
                            applyModify.setIdPersonApplying(idUser);
                            applyModify.setNamePerson(nameUser);
                            applyModify.setMail(mailUser);
                            applyModify.setPhone(phoneUser);
                            String transport2 = mySpinner.getSelectedItem().toString();
                            applyModify.setTransport(transport2);
                            String comments2 = commentsBox.getText().toString();
                            applyModify.setComments(comments2);
                            control= myModel.modifyApplicationToDemand(applyModify);
                            if (control){
                                situationFlag = 2;
                            } else {
                                //if not
                                situationFlag = -1;
                            }
                            //set text to apply button
                            setButtonsValues();
                            break;
                    }
                } else if (view.getId() == R.id.btBorrar) {
                    Boolean control2= false;
                    if (situationFlag==1){
                        if (apliRecovered!=null) {
                            control2= myModel.unOfferPersonToDemand(apliRecovered);
                        } else {
                            newApply.setIdApplForDem(provIdApli);
                            control2 = myModel.unOfferPersonToDemand(newApply);
                        }
                    } else if (situationFlag==2){
                        applyModify= myModel.searchDemandApplyById(provIdApli);
                        control2 = myModel.unOfferPersonToDemand(applyModify);
                    }
                    if (control2) {
                        //if unapply suscesfully
                        situationFlag = 0;
                    } else {
                        //if not
                        situationFlag = -2;
                    }
                    //set text to apply button
                    setButtonsValues();
                }
            }
        };
    }

    /**
     * Method for adding listener to the elements
     */
    private void addElementsToListener() {
        apply.setOnClickListener (listener);
        btcancel.setOnClickListener(listener);
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
        menu.add(0, 3, 2, "Publicar oferta");
        menu.add(0, 4, 3, "Buscar peticiones");
        menu.add(0, 5, 4, "Salir");
        return true;
    }

    // Handles item selections from Option MENU
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                //Go to view account activity
                Intent intent1 = new Intent(PersonApplyForDemand.this, UserViewAccountActivity.class);
                startActivity(intent1);
                break;
            case 2:
                    Intent intent2 = new Intent(PersonApplyForDemand.this, UserSearchOffersActivity.class);
                    startActivity(intent2);
                break;
            case 3:
                    Intent intent3 = new Intent(PersonApplyForDemand.this, PersonPostOfferActivity.class);
                    startActivity(intent3);
                break;
            case 4:
                Intent intent4 = new Intent(PersonApplyForDemand.this, UserSearchDemandsActivity.class);
                startActivity(intent4);
                break;
            case 5://Exit
                finishAffinity();
                break;
        }
        return true;
    }
}
