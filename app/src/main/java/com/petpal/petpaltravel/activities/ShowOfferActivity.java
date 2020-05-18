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
import com.petpal.petpaltravel.model.CompanionOfPet;
import com.petpal.petpaltravel.model.PPTModel;

import java.text.SimpleDateFormat;

public class ShowOfferActivity extends AppCompatActivity {
    //Atributes
    TextView namePerson, OriginCity, Destination, typePet, date, transport, comments, userLabel;
    String nameUser;
    String phoneUser;
    Button acceptOffer;
    PPTModel myModel;
    CompanionOfPet myOffer;
    int idOffer;
    Boolean isShelter;
    int userId;
    View.OnClickListener listener;
    int situationFlag=0; // 0= normal, -1= missing phone, -2= applied -3=no more application accepted
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewdetailsoffer_layout);
        //instantiate model
        myModel = new PPTModel();
        //recover needed data
        recoverOfferId();
        myOffer= myModel.recoverOfferById(idOffer);
        recoverShared();
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
    private void recoverOfferId() {
        Bundle bun = this.getIntent().getExtras();
        idOffer= bun.getInt("idOffer",0);
    }

    /**
     * Method for create elements of activity
     */
    private void initElements() {
        //namePerson= (TextView) findViewById(R.id.);
        OriginCity= (TextView) findViewById(R.id.etCiudadOrigenpersona);
        Destination= (TextView) findViewById(R.id.etCiudadDestinoPersona);
        typePet= (TextView) findViewById(R.id.etTipoMascota);
        date= (TextView) findViewById(R.id.etDiaViaje);
        transport= (TextView) findViewById(R.id.etTransporte);
        comments= (TextView) findViewById(R.id.etComentarios);
        userLabel= (TextView) findViewById(R.id.etNombreProtectora);
        //set value to name of the user field
        userLabel.setText(nameUser);
        acceptOffer= (Button) findViewById(R.id.btApadrinarMascota);
        //set situation flag depending on the case
        if (myOffer.getIdUserShelterInterested1()==userId | myOffer.getIdUserShelterInterested2()==userId |
                myOffer.getIdUserShelterInterested3()==userId){
            situationFlag=-2;
        } else if (myOffer.getIdUserShelterInterested1()!=0 & myOffer.getIdUserShelterInterested2()!=0 &
                myOffer.getIdUserShelterInterested3()!=0){
            situationFlag=-3;
        } else if (phoneUser==null) {
            situationFlag=-1;
        }
        //set value of text of acceptOffer button
        setButtonAcceptOffer();
    }

    /**
     * Method for setting the value of text in offerme button
     * depending on the situation flag value
     */
    private void setButtonAcceptOffer() {
        //if we are the shelter that post this demand...
        if (isShelter & userId!=0 & userId==myOffer.getIdeUserPersonOffering()) {
            acceptOffer.setText("Modificar oferta");
            //if not...
        } else {
            //depending on the situation flag
            switch (situationFlag) {
                case 0: //normal case
                    acceptOffer.setText("¡Quiero pedirle que acompañe!");
                    acceptOffer.setEnabled(true);
                    acceptOffer.setTextColor(Color.WHITE);
                    break;
                case -1: // missing phone
                    acceptOffer.setText("Falta tu teléfono para solicitarlo");
                    acceptOffer.setEnabled(true);
                    acceptOffer.setTextColor(Color.RED);
                    break;
                case -2: //person has applied already
                    acceptOffer.setText("Ya no quiero que acompañe");
                    acceptOffer.setEnabled(true);
                    acceptOffer.setTextColor(Color.RED);
                    break;
                case -3: //no more application accepted
                    acceptOffer.setText("Ya no acepta más solicitudes");
                    acceptOffer.setEnabled(false);
                    break;
                case -4: //there is an error
                    acceptOffer.setText("Prueba más tarde");
                    acceptOffer.setTextColor(Color.RED);
                    acceptOffer.setEnabled(true);
                    break;
            }
        }
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
                //if we are the person that post this offer, open modify offer activity
                if (!isShelter & userId!=0 & userId==myOffer.getIdeUserPersonOffering()){
                    //Intent intent  = new Intent(ShowOfferActivity.this, MODIFICAR_OFERTA.class);
                    //startActivity(intent);
                //if not...
                } else {
                    switch (situationFlag) {
                        case 0: //normal case: shelter apply to the offer
                            //Open applicationActivity
                            Intent intent1  = new Intent(ShowOfferActivity.this, ApplyForOffer.class);
                            //Create a bundle object
                            Bundle bundle = new Bundle();
                            //set interesting data
                            bundle.putInt("idOffer", idOffer);
                            intent1.putExtras(bundle);
                            startActivity(intent1);

                            break;
                        case -1: // missing phone: open activity to go to change account details
                            Intent intent  = new Intent(ShowOfferActivity.this, ViewAccountActivity.class);
                            startActivity(intent);
                            break;
                        case -2: //shelter has applied already: shelter un-apply the offer
                            Boolean control2= unApplyShelterToOffer();
                            if (control2){
                                //if unapply suscesfully
                                situationFlag=0;
                            } else {
                                //if not
                                situationFlag=-4;
                            }
                            //set text to accept offer button
                            setButtonAcceptOffer();
                            break;
                    }
                }
            }
        };
    }

    /**
     * Method for adding listener to the elements
     */
    private void addElementsToListener() {
        acceptOffer.setOnClickListener (listener);
    }

    /**
     * Method for apply for an offer
     * @return true if applied is done, false otherwise
     */
    private Boolean shelterApplyForOffer() {
        Boolean result= false;
        result= myModel.addShelterToOffer(userId, nameUser, myOffer.getId());
        return result;

    }

    /**
     * Method for un-apply for a offer
     * @return true if un-applied is done, false otherwise
     */
    private Boolean unApplyShelterToOffer() {
        return false;
    }

    /**
     * Method for loading offer data in the view
     */
    private void loadData() {
        //if the offer is null (does not arrived to this activity), notify
        if(myOffer==null){
            //namePerson.setText("No encontrado");
            OriginCity.setText("No encontrado");
            Destination.setText("No encontrado");
            typePet.setText("No encontrado");
            date.setText("No encontrado");
            transport.setText("No encontrado");
            comments.setText("No encontrado");
            acceptOffer.setText("No se ha encontrado");
            acceptOffer.setEnabled(false);
            //if there is an offer
        } else {
            //namePerson.setText(myOffer.getNamePerson());
            OriginCity.setText(myOffer.getOriginCity());
            Destination.setText(myOffer.getDestinyCity());
            typePet.setText(myOffer.getPetType());
            date.setText(new SimpleDateFormat("dd-MM-yyyy").format(myOffer.getDateTravel().getTime()));
            transport.setText(myOffer.getTransport());
            comments.setText(myOffer.getComments());
        }
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
                Intent intent1 = new Intent(ShowOfferActivity.this, ViewAccountActivity.class);
                startActivity(intent1);
                break;
            case 2:
                //If is Shelter, go to show my demands activity
                if(isShelter) {
                    Intent intent2 = new Intent(ShowOfferActivity.this, SearchDemandsActivity.class);
                    startActivity(intent2);
                //if is person, go to show my offers activity
                } else {
                    Intent intent2 = new Intent(ShowOfferActivity.this, SearchOffersActivity.class);
                    startActivity(intent2);
                }
                break;
            case 3:
                //If is Shelter, go to add a demands activity
                if (isShelter) {
                    Intent intent3 = new Intent(ShowOfferActivity.this, ManageDemandActivity.class);
                    startActivity(intent3);
                    //if is person, go to add an offer activity
                } else {
                    Intent intent3 = new Intent(ShowOfferActivity.this, ManageOfferActivity.class);
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
