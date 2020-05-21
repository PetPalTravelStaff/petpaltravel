package com.petpal.petpaltravel.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.petpal.petpaltravel.R;
import com.petpal.petpaltravel.helpers.NotificationsBroadcast;
import com.petpal.petpaltravel.model.ApplicationForOffer;
import com.petpal.petpaltravel.model.CompanionOfPet;
import com.petpal.petpaltravel.model.PPTModel;

import java.text.SimpleDateFormat;

public class PersonViewAndConfirmOneShelterInterested extends AppCompatActivity {
    //Attributes
    TextView nameBox, namePetBox, typeBox, mailBox, phoneBox, commentBox, nameLabel;
    Button btChoose;
    ApplicationForOffer myApplication;
    String nameUser, userPhone;
    int idUser, idOffer, idApplication;
    PPTModel myModel;
    private Boolean isShelter, isSelected;
    View.OnClickListener listener;
    private CompanionOfPet myOffer;
    private NotificationCompat.Builder notification;
    private static int idNotification= 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personviewandconfirmshelterapply_layout);
        //instantiate model
        myModel = new PPTModel();
        //recover interesting data by Shared Preferences and Bundle
        recoverOfferId();
        recoverShared();
        //recover list of all demands from model
        System.out.println("El id aplication es "+ idApplication);
        myApplication = myModel.searchOfferApplyById(idApplication);
        myOffer= myModel.recoverOfferById(idOffer);
        isSelected= myApplication.getChoosed();

        //Create view elements in activity
        initElements();
        //create a listener
        createListener();
        //Add elements to listener
        addElementsToListener();
        //load data in view
        loadData();

    }

    /**
     * Method for recovering data needed by bundle
     */
    private void recoverOfferId() {
        Bundle bun = this.getIntent().getExtras();
        idOffer= bun.getInt("idOffer",0);
        idApplication = bun.getInt("idApplyOff", 0);
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
            idUser = shared.getInt("id", 0);
            isShelter = shared.getBoolean("isShelter", false);
            userPhone= shared.getString("userPhone", null);
        }
    }

    /**
     * Method for creating the elements of the activity
     */
    private void initElements () {
        nameBox = (TextView) findViewById(R.id.etNombre);
        namePetBox = (TextView) findViewById(R.id.etNombreMascota);
        typeBox = (TextView) findViewById(R.id.etTipoMascota);
        mailBox = (TextView) findViewById(R.id.etMiEmail);
        phoneBox = (TextView) findViewById(R.id.etMiTelefono);
        commentBox = (TextView) findViewById(R.id.etComentarios);
        //nameLabel= (TextView) findViewById(R.id.etNombrePersona);
        btChoose = (Button) findViewById(R.id.btLeAcompa);
        notification= new NotificationCompat.Builder(this);
        notification.setAutoCancel(false);
    }

    /**
     * Method for creating a listener and identify what to do
     */
    private void createListener() {
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.btLeAcompa) {
                    if (!isSelected) {
                        Boolean control = myModel.confirmSelectedShelter(myApplication);
                        String dateTravel= new SimpleDateFormat("dd-MM-yyyy").format(myOffer.getDateTravel().getTime());
                        if (control) {
                            notification.setSmallIcon(R.drawable.logo);
                            //notification.setTicker("Aviso");
                            notification.setWhen((System.currentTimeMillis()));
                            notification.setContentTitle("¡Has escogido!");
                            notification.setContentText("Contacta con la protectora");
                            String text= "Has escogido a " + myApplication.getNamePet() + " (" + myApplication.getTypePet() + ") para acompañarlo el "
                                    + dateTravel +  ". Contacta con la protectora  para ultimar detalles. Gracias y... ¡buen viaje!";
                            notification.setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(text));
                            Intent intent= new Intent(PersonViewAndConfirmOneShelterInterested.this, PersonViewAndConfirmOneShelterInterested.class);
                            Bundle bundle = new Bundle();
                            //set interesting data
                            bundle.putInt("idOffer", idOffer);
                            bundle.putInt("idApplyOff", myApplication.getIdAppliForOf());
                            intent.putExtras(bundle);
                            PendingIntent pendingIntent= PendingIntent.getActivity(PersonViewAndConfirmOneShelterInterested.this,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            notification.setContentIntent(pendingIntent);
                            NotificationManager nm= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            nm.notify(idNotification, notification.build());
                            isSelected=true;
                            btChoose.setText("La dejo en espera");
                            btChoose.setTextColor(Color.BLACK);
                        } else {
                            btChoose.setText("Prueba más tarde");
                            btChoose.setTextColor(Color.RED);
                        }
                    } else {
                        //TODO deseleccionar
                    }
                }
            }
        };
    }

    /**
     * Method for adding listener to the elements
     */
    private void addElementsToListener() {
        btChoose.setOnClickListener(listener);
    }

    /**
     * Method for loading offer data in the view
     */
    private void loadData() {
        //nameLabel.setText(nameUser);
        nameBox.setText(myApplication.getNameShelter());
        namePetBox.setText(myApplication.getNamePet());
        typeBox.setText(myApplication.getTypePet());
        mailBox.setText(myApplication.getMail());
        phoneBox.setText(myApplication.getPhone());
        commentBox.setText(myApplication.getComments());
        if (isSelected) {
            btChoose.setText("Lo dejo en espera");
            btChoose.setTextColor(Color.BLACK);
        } else {
            btChoose.setText("¡La elijo!");
            btChoose.setTextColor(Color.WHITE);
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
                Intent intent1 = new Intent(PersonViewAndConfirmOneShelterInterested.this, UserViewAccountActivity.class);
                startActivity(intent1);
                break;
            case 2:
                Intent intent2 = new Intent(PersonViewAndConfirmOneShelterInterested.this, UserSearchOffersActivity.class);
                startActivity(intent2);
                break;
            case 3:
                Intent intent3 = new Intent(PersonViewAndConfirmOneShelterInterested.this, PersonPostOfferActivity.class);
                startActivity(intent3);
                break;
            case 4://Exit
                Intent intent4 = new Intent(PersonViewAndConfirmOneShelterInterested.this, UserSearchDemandsActivity.class);
                startActivity(intent4);
                break;
            case 5://Exit
                finishAffinity();
                break;
        }
        return true;
    }
}