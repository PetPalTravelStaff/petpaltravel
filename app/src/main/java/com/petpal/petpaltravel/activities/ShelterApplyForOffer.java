package com.petpal.petpaltravel.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.petpal.petpaltravel.R;
import com.petpal.petpaltravel.model.ApplicationForOffer;
import com.petpal.petpaltravel.model.CompanionOfPet;
import com.petpal.petpaltravel.model.PPTModel;

public class ShelterApplyForOffer extends AppCompatActivity {
    //Attributes
    private TextView phoneBox, mailBox, labType;
    private EditText commentsBox, namePetBox, otherType;
    private Button apply;
    private Button btcancelar;
    private RadioGroup petType;
    private RadioButton cat, dog, other;
    private int offerId, idUser, provIdApply;
    private ApplicationForOffer myApplicationRecovered;
    private ApplicationForOffer myApplicationToSend;
    private ApplicationForOffer myApplicationModified;
    private String animalType;
    private String nameUser, mailUser, phoneUser;
    private Boolean isShelter;
    //0= user can apply , 1= user already has applies, so can modify or delete,
    // 2= updated data, -1= user can not apply or modify -2= user can not delete apply
    int situationFlag= 0;
    private PPTModel myModel;
    private CompanionOfPet myOffer;
    private View.OnClickListener listener;
    private RadioGroup.OnCheckedChangeListener listener2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelterapplytooffer_layout);
        //instantiate model
        myModel = new PPTModel();
        //Recover needed data
        recoverOfferId();
        recoverShared();
        myOffer = myModel.recoverOfferById(offerId);

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
    private void recoverOfferId() {
        Bundle bun = this.getIntent().getExtras();
        offerId = bun.getInt("idOffer", 0);
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
            mailUser = shared.getString("userMail", "");
            phoneUser = shared.getString("userPhone", "");
            isShelter = shared.getBoolean("isShelter", false);
            idUser = shared.getInt("id", 0);
        }
    }

    /**
     * Method for creating the elements in activity related to layout
     */
    private void initElements() {
        namePetBox= (EditText) findViewById(R.id.etNombreMascota);
        labType= (TextView)findViewById(R.id.tvTipoMascota);
        cat = (RadioButton) findViewById(R.id.rdGato);
        dog = (RadioButton) findViewById(R.id.rdPerro);
        other = (RadioButton) findViewById(R.id.radioOtro);
        otherType= (EditText) findViewById(R.id.etOtro);
        otherType.setVisibility(View.GONE);
        petType = (RadioGroup) findViewById(R.id.radioTipo);
        phoneBox = (TextView) findViewById(R.id.etMiTelefono);
        mailBox = (TextView) findViewById(R.id.etMiEmail);
        commentsBox = (EditText) findViewById(R.id.etComentarios);
        apply = (Button) findViewById(R.id.btSolicitalo);
        btcancelar = (Button) findViewById(R.id.btBorrar);
    }

    /**
     * Method for loading data from recovered or depending on the situation
     */
    private void loadData() {
        //set situation flag depending on the case
        if (myOffer.getIdShelterIntPosition(0) == idUser | myOffer.getIdShelterIntPosition(1) == idUser |
                myOffer.getIdShelterIntPosition(2) == idUser) {
            myApplicationRecovered= myModel.searchApplicationForOffer(offerId, idUser);
            situationFlag = 1;
        } else {
            situationFlag = 0;
        }
        //set values of text in buttons
        setButtonsValues();
        phoneBox.setText(phoneUser);
        mailBox.setText(mailUser);
        if (situationFlag==1){
            switch (myApplicationRecovered.getTypePet()){
                case "Gato/a":
                    cat.setChecked(true);
                    animalType="Gato/a";
                    break;
                case "Perro/a":
                    dog.setChecked(true);
                    animalType="Gato/a";
                    break;
                default:
                    other.setChecked(true);
                    otherType.setVisibility(View.VISIBLE);
                    otherType.setText(myOffer.getPetType());
                    animalType= myOffer.getPetType();
                    break;
            }
            commentsBox.setText(myApplicationRecovered.getComments());
            namePetBox.setText(myApplicationRecovered.getNamePet());
        } else {
            cat.setChecked(true);
            animalType="Gato/a";
        }
    }

    /**
     * Auxiliar method
     */
    private void setButtonsValues() {
        switch (situationFlag) {
            case 0: //normal case
                apply.setText("¡Nos interesa!");
                apply.setEnabled(true);
                apply.setTextColor(Color.WHITE);
                apply.setVisibility(View.VISIBLE);
                btcancelar.setVisibility(View.GONE);
                break;
            case 1: //person has applied already
                apply.setText("Guarda cambios");
                apply.setEnabled(true);
                apply.setTextColor(Color.WHITE);
                apply.setVisibility(View.VISIBLE);
                btcancelar.setText("Cancelamos");
                btcancelar.setEnabled(true);
                btcancelar.setTextColor(Color.WHITE);
                btcancelar.setVisibility(View.VISIBLE);
                break;
            case 2: //person has update data
                apply.setText("¡Guardados!");
                apply.setEnabled(false);
                apply.setTextColor(Color.BLACK);
                apply.setVisibility(View.VISIBLE);
                btcancelar.setText("Cancelamos");
                btcancelar.setTextColor(Color.WHITE);
                btcancelar.setVisibility(View.VISIBLE);
                break;
            case -1: //there is some trouble in appling or updating
                apply.setText("Prueba más tarde");
                apply.setTextColor(Color.RED);
                apply.setVisibility(View.VISIBLE);
                btcancelar.setVisibility(View.GONE);
                break;
            case -2: //there is some trouble in cancelling
                btcancelar.setText("Prueba más tarde");
                btcancelar.setTextColor(Color.RED);
                btcancelar.setVisibility(View.VISIBLE);
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
                if (view.getId() == R.id.btSolicitalo) {
                    switch (situationFlag) {
                        case 0: //normal case: shelter apply to the offer
                            myApplicationToSend = new ApplicationForOffer();
                            myApplicationToSend.setIdOffer(offerId);
                            myApplicationToSend.setIdShelterApplying(idUser);
                            myApplicationToSend.setNameShelter(nameUser);
                            myApplicationToSend.setMail(mailUser);
                            myApplicationToSend.setPhone(phoneUser);
                            String namePet= namePetBox.getText().toString();
                            if (!"".equals(namePet)) {
                                namePetBox.setHintTextColor(Color.BLACK);
                                myApplicationToSend.setNamePet(namePet);
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
                                    labType.setTextColor(Color.BLACK);
                                    otherType.setHintTextColor(Color.BLACK);
                                    myApplicationToSend.setTypePet(animalType);
                                    String comments = commentsBox.getText().toString();
                                    myApplicationToSend.setComments(comments);
                                    provIdApply = myModel.addApplicationToOffer(myApplicationToSend);
                                    if (provIdApply!=0) {
                                        //if can apply suscessfully
                                        situationFlag = 1;
                                    } else {
                                        //if not
                                        situationFlag = -1;
                                    }
                                    //set text to apply button
                                    setButtonsValues();
                                } else {
                                    labType.setTextColor(Color.RED);
                                    otherType.setHintTextColor(Color.RED);
                                }
                            } else {
                                namePetBox.setHintTextColor(Color.RED);
                                namePetBox.setText(null);
                            }
                            break;
                        case 1: //shelter has applied already: person modify the demand
                            Boolean control2= false;
                            if (myApplicationRecovered!=null) {
                                myApplicationModified = myApplicationRecovered;
                            }else{
                                myApplicationModified= myApplicationToSend;
                                myApplicationModified.setIdOffer(provIdApply);
                            }
                            myApplicationToSend.setIdOffer(offerId);
                            myApplicationToSend.setIdShelterApplying(idUser);
                            myApplicationToSend.setNameShelter(nameUser);
                            myApplicationToSend.setMail(mailUser);
                            myApplicationToSend.setPhone(phoneUser);
                            String namePet2= namePetBox.getText().toString();
                            if (!"".equals(namePet2)) {
                                namePetBox.setHintTextColor(Color.BLACK);
                                myApplicationToSend.setNamePet(namePet2);
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
                                    labType.setTextColor(Color.BLACK);
                                    otherType.setHintTextColor(Color.BLACK);
                                    myApplicationToSend.setTypePet(animalType);
                                    String comments = commentsBox.getText().toString();
                                    myApplicationToSend.setComments(comments);
                                    control2 = myModel.modifyApplicationToOffer(myApplicationToSend);
                                    if (control2) {
                                        //if can apply suscessfully
                                        situationFlag = 2;
                                    } else {
                                        //if not
                                        situationFlag = -1;
                                    }
                                    //set text to apply button
                                    setButtonsValues();
                                } else {
                                    labType.setTextColor(Color.RED);
                                    otherType.setHintTextColor(Color.RED);
                                }
                            } else {
                                namePetBox.setHintTextColor(Color.RED);
                            }
                    }
                } else if (view.getId() == R.id.btBorrar) {
                    Boolean control2= false;
                    if (situationFlag==1){
                        if (myApplicationRecovered!=null) {
                            control2= myModel.deleteApplicationToOffer(myApplicationRecovered);
                        } else {
                            myApplicationToSend.setIdAppliForOf(provIdApply);
                            control2 = myModel.deleteApplicationToOffer(myApplicationToSend);
                        }
                    } else if (situationFlag==2){
                        myApplicationModified= myModel.searchOfferApplyById(provIdApply);
                        control2 = myModel.deleteApplicationToOffer(myApplicationModified);
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
        apply.setOnClickListener(listener);
        btcancelar.setOnClickListener(listener);
        petType.setOnCheckedChangeListener(listener2);
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
                Intent intent1 = new Intent(ShelterApplyForOffer.this, UserViewAccountActivity.class);
                startActivity(intent1);
                break;
            case 2:
                    Intent intent2 = new Intent(ShelterApplyForOffer.this, UserSearchDemandsActivity.class);
                    startActivity(intent2);
                break;
            case 3:
                    Intent intent3 = new Intent(ShelterApplyForOffer.this, ShelterPostDemandActivity.class);
                    startActivity(intent3);
                break;
            case 4:
                Intent intent4 = new Intent(ShelterApplyForOffer.this, UserSearchOffersActivity.class);
                startActivity(intent4);
                break;
            case 5://Exit
                finishAffinity();
                break;
        }
        return true;
    }
}
