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
import android.widget.TextView;

import com.petpal.petpaltravel.R;
import com.petpal.petpaltravel.model.CompanionForPet;
import com.petpal.petpaltravel.model.PPTModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShowDemandActivity extends AppCompatActivity {

    TextView namePet, OriginCity, Destination, typePet, dateFrom, dateUntill, comments, nameLabel;
    String nameUser;
    String phoneUser;
    Button offerMe;
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
        setContentView(R.layout.viewdetailsdemand_layout);
        myModel = new PPTModel();
        recoverDemandId();
        myDemand= myModel.recoverDemandById(idDemand);
        recoverShared();
        if (myDemand.getIdUserPersonInterested1()==userId | myDemand.getIdUserPersonInterested2()==userId |
                myDemand.getIdUserPersonInterested2()==userId){
            situationFlag=-2;
        } else if (myDemand.getIdUserPersonInterested1()!=0 & myDemand.getIdUserPersonInterested2()!=0 &
                myDemand.getIdUserPersonInterested2()!=0){
            situationFlag=-3;
        } else if (phoneUser==null) {
            situationFlag=-1;
        }

        initElements();
        createListener();
        loadData();
        addElementsToListener();
    }



    private void recoverDemandId() {
        Bundle bun = this.getIntent().getExtras();
        idDemand= bun.getInt("idDemand",0);

    }

    private void initElements() {
        namePet= (TextView) findViewById(R.id.etNombreMascota);
        OriginCity= (TextView) findViewById(R.id.etCiudadOrigenMascota);
        Destination= (TextView) findViewById(R.id.etCiudadDestinoMascota);
        //typePet= (TextView) findViewById(R.id.);
        //dateFrom= (TextView) findViewById(R.id.);
        //dateUntill= (TextView) findViewById(R.id.);
        comments= (TextView) findViewById(R.id.etComentarios);
        //nameLabel= (TextView) findViewById(R.id.);
        //nameLabel.setText(nameUser);
        offerMe= (Button) findViewById(R.id.btApadrinarMascota);

        setButtonOfferMe();

    }

    private void setButtonOfferMe() {
        if (isShelter & userId!=0 & userId==myDemand.getIdeUserShelterOffering()) {
            offerMe.setText("Modificar oferta");
        } else {
            switch (situationFlag) {
                case 0: //normal case
                    offerMe.setText("¡Quiero acompañarle!");
                    break;
                case -1: // missing phone
                    offerMe.setText("Falta tu teléfono para ofrecerte");
                    break;
                case -2: //person has applied already
                    offerMe.setText("Ya no quiero acompañarle");
                    break;
                case -3: //no more appplication accepted
                    offerMe.setText("Estamos a tope de personas voluntarias");
                    break;
                case -4: //no more appplication accepted
                    offerMe.setText("Prueba más tarde");
                    break;
            }
        }
    }

    private void recoverShared() {
        //Creem un objecte amb el shared preferences que li pasem per paràmetre
        SharedPreferences shared = getSharedPreferences("dades", MODE_PRIVATE);
        if (shared!=null) {
            //comencem a llegir
            nameUser = shared.getString("userName", "");
            userId= shared.getInt("id", 0);
            isShelter = shared.getBoolean("isShelter", false);
            phoneUser= shared.getString("userPhone", null);
        }
    }

    private void createListener() {
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShelter & userId!=0 & userId==myDemand.getIdeUserShelterOffering()){
                    //Intent intent  = new Intent(ShowMyDemandsActivity.this, MODIFICAR_DEMANDA.class);
                    //startActivity(intent);
                } else {
                    switch (situationFlag) {
                        case 0: //normal case
                            Boolean control= offerPersonToDemand();
                            if (control){
                                situationFlag=-2;
                            } else {
                                situationFlag=-4;
                            }
                            setButtonOfferMe();
                            break;
                        case -1: // missing phone
                            Intent intent  = new Intent(ShowDemandActivity.this, ViewAccountActivity.class);
                            startActivity(intent);
                            break;
                        case -2: //person has applied already
                            Boolean control2= unOfferPersonToDemand();
                            if (control2){
                                situationFlag=0;
                            } else {
                                situationFlag=-4;
                            }
                            setButtonOfferMe();
                            break;
                    }
                }
            }
        };
    }

    private void addElementsToListener() {
        offerMe.setOnClickListener (listener);
    }


    private Boolean offerPersonToDemand() {
        Boolean result= false;
        result= myModel.addPersonToDemand(userId, nameUser, myDemand.getId());
        return result;

    }

    private Boolean unOfferPersonToDemand() {
        return false;
    }


    private void loadData() {
        if(myDemand==null){
            namePet.setText("No encontrado");
            OriginCity.setText("No encontrado");
            Destination.setText("No encontrado");
            //typePet.setText("No encontrado");
            //dateFrom.setText("No encontrado");
            //dateUntill.setText("No encontrado");
            comments.setText("No encontrado");
            offerMe.setText("No se ha encontrado");
            offerMe.setEnabled(false);
        } else {
            namePet.setText(myDemand.getNamePet());
            OriginCity.setText(myDemand.getOriginCity());
            Destination.setText(myDemand.getDestinyCity());
            //typePet.setText(myDemand.getTypePet());
            //dateFrom.setText(new SimpleDateFormat("dd-MM-yyyy").format(myDemand.getAvailableFrom().getTime()));
            //dateUntill.setText(new SimpleDateFormat("dd-MM-yyyy").format(myDemand.getDeadline().getTime()));
            comments.setText(myDemand.getComments());
        }
    }


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
                    Intent intent1 = new Intent(ShowDemandActivity.this, ViewAccountActivity.class);
                    startActivity(intent1);
                    break;
                case 2:
                    if(isShelter) {
                        Intent intent2 = new Intent(ShowDemandActivity.this, ShowMyDemandsActivity.class);
                        startActivity(intent2);
                    } else {
                        Intent intent2 = new Intent(ShowDemandActivity.this, ShowMyOffersActivity.class);
                        startActivity(intent2);
                    }
                    break;
                case 3:
                    if (isShelter) {
                        Intent intent3 = new Intent(ShowDemandActivity.this, AddDemandActivity.class);
                        startActivity(intent3);
                    } else {
                        Intent intent3 = new Intent(ShowDemandActivity.this, AddOfferActivity.class);
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
