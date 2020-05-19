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
import com.petpal.petpaltravel.model.CompanionForPet;
import com.petpal.petpaltravel.model.PPTModel;

public class ApplyForDemand extends AppCompatActivity {
    TextView nameBox, phoneBox, mailBox;
    EditText commentsBox;
    Button apply;
    Button modify;
    Spinner mySpinner;
    String[] transport;
    int situationFlag= -1; //0= user can apply , 1= user already has applied,
    int situationUpdateFlag=-1; //0= user can update data, 1= user has already updated data, -1= user can not update data,
    View.OnClickListener listener;

    int demandId, idUser;
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

    private void initElements() {
        mySpinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> questionsAdapter = new ArrayAdapter<String>(ApplyForDemand.this,android.R.layout.simple_spinner_item, transport);
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
        modify= (Button) findViewById(R.id.btCancelar);

        //set situation flag depending on the case
        if (myDemand.getIdPersonInterestePosition(1)==idUser | myDemand.getIdPersonInterestePosition(2)==idUser |
                myDemand.getIdPersonInterestePosition(3)==idUser){
            situationFlag=1;
            situationUpdateFlag=0;
        } else  {
            situationFlag=0;
        }

        //set values of text in buttons
        setButtonsValues();
    }

    private void setButtonsValues() {
        switch (situationFlag) {
            case 0: //normal case
                apply.setText("Ofrécete");
                apply.setEnabled(true);
                apply.setTextColor(Color.WHITE);
                break;
            case 1: //person has applied already
                apply.setText("Ya no quiero");
                apply.setEnabled(true);
                apply.setTextColor(Color.RED);
                break;
            case -1: //there is some trouble
                apply.setText("Prueba más tarde");
                apply.setTextColor(Color.RED);
                apply.setEnabled(false);
                break;
        }
        switch (situationUpdateFlag){
            case 0: //normal can update data
                modify.setText("Guarda cambios");
                modify.setEnabled(true);
                modify.setTextColor(Color.WHITE);
                modify.setVisibility(View.VISIBLE);
                break;
            case 1: //person has updated data
                modify.setText("Propuesta actualizada");
                modify.setEnabled(false);
                modify.setTextColor(Color.WHITE);
                modify.setVisibility(View.VISIBLE);
                break;
            case -1: //person can not update data
                modify.setText("");
                modify.setEnabled(false);
                modify.setTextColor(Color.WHITE);
                modify.setVisibility(View.GONE);
                break;
            case -2: //there is some trouble
                modify.setText("Prueba más tarde");
                modify.setTextColor(Color.RED);
                modify.setEnabled(false);
                modify.setVisibility(View.VISIBLE);
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
                            Boolean control = offerPersonToDemand();
                            if (control) {
                                //if can apply suscessfully
                                situationFlag = 1;
                                situationUpdateFlag=0;
                            } else {
                                //if not
                                situationFlag = -1;
                                situationUpdateFlag=-1;
                            }
                            //set text to apply button
                            setButtonsValues();
                            break;
                        case 1: //person has applied already: person un-apply the demand
                            Boolean control2 = unOfferPersonToDemand();
                            if (control2) {
                                //if unapply suscesfully
                                situationFlag = 0;
                                situationUpdateFlag=-1;
                            } else {
                                //if not
                                situationFlag = -1;
                                situationUpdateFlag=0;
                            }
                            //set text to apply button
                            setButtonsValues();
                            break;
                    }
                } else if (view.getId() == R.id.btCancelar) {
                            Boolean control = modifyPersonToDemand();
                            if (control) {
                                //if can update suscessfully
                                situationUpdateFlag = 1;
                            } else {
                                //if not
                                situationUpdateFlag = -2;
                            }
                            //set text to apply button
                            setButtonsValues();
                }
            }
        };
    }



    /**
     * Method for apply for a demand
     * @return true if applied is done, false otherwise
     */
    private Boolean offerPersonToDemand() {
        Boolean result= false;
        result= myModel.addPersonToDemand(idUser, nameUser, myDemand.getId());
        return result;
    }

    /**
     * Method for un-apply for a demand
     * @return true if un-applied is done, false otherwise
     */
    private Boolean unOfferPersonToDemand() {
        return false;
    }

    /**
     * Method for modifying data of application for demand
     * @return true if update is done, false otherwise
     */
    private Boolean modifyPersonToDemand() {
        return false;
    }

    /**
     * Method for adding listener to the elements
     */
    private void addElementsToListener() {
        apply.setOnClickListener (listener);
        modify.setOnClickListener(listener);
    }


    /**
     * Method for creating items of menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (isShelter) {
            menu.add(0, 1, 0, "Mi perfil");
            menu.add(0, 2, 1, "Ver mis peticiones");
            menu.add(0, 3, 2, "Publicar petición");
            menu.add(0, 4, 3, "Salir");
        } else {
            menu.add(0, 1, 0, "Mi perfil");
            menu.add(0, 2, 1, "Ver mis ofertas");
            menu.add(0, 3, 2, "Publicar oferta");
            menu.add(0, 4, 3, "Salir");
        }
        return true;
    }

    // Handles item selections from Option MENU
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                //Go to view account activity
                Intent intent1 = new Intent(ApplyForDemand.this, ViewAccountActivity.class);
                startActivity(intent1);
                break;
            case 2:
                //If is Shelter, go to show my demands activity
                if(isShelter) {
                    Intent intent2 = new Intent(ApplyForDemand.this, SearchDemandsActivity.class);
                    startActivity(intent2);
                    //if is person, go to show my details activity
                } else {
                    Intent intent2 = new Intent(ApplyForDemand.this, SearchOffersActivity.class);
                    startActivity(intent2);
                }
                break;
            case 3:
                //If is Shelter, go to add a demands activity
                if (isShelter) {
                    Intent intent3 = new Intent(ApplyForDemand.this, AddDemandActivity.class);
                    startActivity(intent3);
                    //if is person, go to add an offer activity
                } else {
                    Intent intent3 = new Intent(ApplyForDemand.this, AddOfferActivity.class);
                    startActivity(intent3);
                }
                break;
            case 4://Exit
                finish();
                break;
        }
        return true;
    }
}
