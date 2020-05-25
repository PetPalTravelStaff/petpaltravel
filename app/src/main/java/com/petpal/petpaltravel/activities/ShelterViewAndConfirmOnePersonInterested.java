package com.petpal.petpaltravel.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.petpal.petpaltravel.R;
import com.petpal.petpaltravel.model.ApplicationForDemand;
import com.petpal.petpaltravel.model.CompanionForPet;
import com.petpal.petpaltravel.model.PPTModel;

import java.text.SimpleDateFormat;

public class ShelterViewAndConfirmOnePersonInterested extends AppCompatActivity {
    private static int idNotification= 2222;
    //Attributes
    TextView nameBox, transportBox, mailBox, phoneBox, commentBox, nameLabel;
    Button btChoose, btReject;
    ApplicationForDemand myApplication;
    String nameUser, userPhone;
    int idUser, idDemand, idApplication;
    PPTModel myModel;
    View.OnClickListener listener;
    private Boolean isShelter, isSelected;
    private CompanionForPet myDemand;
    private NotificationCompat.Builder notification;
    String channel_Id= "my_channel_01";
    //0= can choose and reject, 1= choosed, so can unchoose and reject
    // 2= rejected, -1= problems in choose/unchoose -2= problems in reject
    int situationFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shelterviewandconfirmpersonapply_layout);
        //instantiate model
        myModel = new PPTModel();
        //recover interesting data by Shared Preferences and Bundle
        recoverDemandId();
        recoverShared();
        //recover list of all demands from model
        myApplication = myModel.searchDemandApplyById(idApplication);
        myDemand = myModel.recoverDemandById(idDemand);
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
    private void recoverDemandId() {
        Bundle bun = this.getIntent().getExtras();
        idDemand = bun.getInt("idDemand", 0);
        idApplication = bun.getInt("idApplyDem", 0);
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
        transportBox = (TextView) findViewById(R.id.etViajaEn);
        mailBox = (TextView) findViewById(R.id.etMiEmail);
        phoneBox = (TextView) findViewById(R.id.etMiTelefono);
        commentBox = (TextView) findViewById(R.id.etComentarios);
        //nameLabel= (TextView) findViewById(R.id.etNombrePersona);
        btChoose = (Button) findViewById(R.id.btLoElijo);
        notification= new NotificationCompat.Builder(this);
        notification.setAutoCancel(false);
        btReject = (Button) findViewById(R.id.btRechazarSolicitud);
        if (isSelected) {
            situationFlag = 1;
        } else {
            situationFlag=0;
        }
        paintButtons();
    }

    /**
     * Method for setting the value of buttons
     * depending on the situation flag value
     */
    private void paintButtons() {
        //depending on the situation flag
        switch (situationFlag) {
            case 0: //normal case
                btChoose.setText("¡Lo elijo!");
                btChoose.setEnabled(true);
                btChoose.setTextColor(Color.WHITE);
                btReject.setVisibility(View.VISIBLE);
                btReject.setText("Descartar");
                break;
            case 1: // can unchoose
                btChoose.setText("No elegir");
                btChoose.setEnabled(true);
                btChoose.setTextColor(Color.BLACK);
                btReject.setVisibility(View.VISIBLE);
                btReject.setText("Descartar");
                break;
            case -1: // problems unselect or select
                btChoose.setText("Prueba más tarde");
                btChoose.setEnabled(true);
                btChoose.setTextColor(Color.RED);
                btReject.setVisibility(View.GONE);
                break;
            case 2: //discard apply
                btChoose.setVisibility(View.GONE);
                btReject.setVisibility(View.VISIBLE);
                btReject.setText("Descartado");
                btReject.setEnabled(false);
                break;
            case -2: //problems with discard
                btChoose.setVisibility(View.GONE);
                btReject.setVisibility(View.VISIBLE);
                btReject.setText("Prueba más tarde");
                btReject.setEnabled(false);
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
                if (view.getId() == R.id.btLoElijo) {
                    if (situationFlag == 0) {
                        Boolean control = myModel.confirmSelectedShelter(myApplication);
                        if (control) {

                            notification = new NotificationCompat.Builder(ShelterViewAndConfirmOnePersonInterested.this, null);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                CharSequence name = "Avisos";
                                String description ="Aviso de recordatorio de contacto con persona seleccionada";
                                int importance = NotificationManager.IMPORTANCE_HIGH;
                                NotificationChannel channel = new NotificationChannel(channel_Id, name, importance);
                                channel.setDescription(description);
                                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                                notificationManager.createNotificationChannel(channel);
                                notification = new NotificationCompat.Builder(ShelterViewAndConfirmOnePersonInterested.this, channel_Id);
                            }
                            notification.setSmallIcon(R.drawable.logo);
                            //notification.setTicker("Aviso");
                            notification.setWhen((System.currentTimeMillis()));
                            notification.setContentTitle("¡Has escogido!");
                            notification.setContentText("Contacta con la persona voluntaria");
                            String text = "Has escogido a " + myApplication.getNamePerson() + " para acompañar a "
                                    + myDemand.getNamePet() + " a " + myDemand.getDestinyCity() +
                                    ". Contacta con la persona para ultimar detalles. Gracias y... ¡buen viaje!";
                            notification.setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(text));
                            Intent intent = new Intent(ShelterViewAndConfirmOnePersonInterested.this, ShelterViewAndConfirmOnePersonInterested.class);
                            Bundle bundle = new Bundle();
                            //set interesting data
                            bundle.putInt("idDemand", idDemand);
                            bundle.putInt("idApplyDem", myApplication.getIdApplForDem());
                            intent.putExtras(bundle);
                            PendingIntent pendingIntent = PendingIntent.getActivity(ShelterViewAndConfirmOnePersonInterested.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                            notification.setContentIntent(pendingIntent);
                            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            nm.notify(idNotification, notification.build());
                            isSelected = true;
                            situationFlag = 1;
                        } else {
                            situationFlag = -1;
                        }
                        paintButtons();
                    } else if (situationFlag == 1) {
                        myApplication = myModel.searchDemandApplyById(myApplication.getIdApplForDem());
                        Boolean control2 = false;
                        control2 = myModel.unConfirmSelectedPerson(myApplication);
                        if (control2) {
                            situationFlag = 0;
                        } else {
                            situationFlag = -1;
                        }
                        paintButtons();
                    }
                } else if (view.getId() == R.id.btRechazarSolicitud) {
                    AlertDialog.Builder myAlert= new AlertDialog.Builder(ShelterViewAndConfirmOnePersonInterested.this);
                    myAlert.setMessage("¿Seguro que quieres descartarlo? Esta acción no se puede deshacer")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    myApplication = myModel.searchDemandApplyById(myApplication.getIdApplForDem());
                                    Boolean control3 = false;
                                    control3 = myModel.rejectAplicationForDemand(myApplication);
                                    if (control3) {
                                        Intent intent1 = new Intent(ShelterViewAndConfirmOnePersonInterested.this, ShelterCheckPersonsInterested.class);
                                        //Create a bundle object
                                        Bundle bundle = new Bundle();
                                        //set interesting data
                                        bundle.putInt("idDemand", idDemand);
                                        intent1.putExtras(bundle);
                                        startActivity(intent1);
                                    } else {
                                        situationFlag = -2;
                                    }
                                    paintButtons();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog title= myAlert.create();
                    title.setTitle("Descartar permanentemente solicitud");
                    title.show();
                }
            }
        };
    }

    /**
     * Method for adding listener to the elements
     */
    private void addElementsToListener() {
        btChoose.setOnClickListener(listener);
        btReject.setOnClickListener(listener);
    }

    /**
     * Method for loading demand data in the view
     */
    private void loadData() {
        //nameLabel.setText(nameUser);
        nameBox.setText (myApplication.getNamePerson());
        transportBox.setText(myApplication.getTransport());
        mailBox.setText(myApplication.getMail());
        phoneBox.setText(myApplication.getPhone());
        commentBox.setText(myApplication.getComments());
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
                Intent intent1 = new Intent(ShelterViewAndConfirmOnePersonInterested.this, UserViewAccountActivity.class);
                startActivity(intent1);
                break;
            case 2:
                Intent intent2 = new Intent(ShelterViewAndConfirmOnePersonInterested.this, UserSearchDemandsActivity.class);
                startActivity(intent2);
                break;
            case 3:
                Intent intent3 = new Intent(ShelterViewAndConfirmOnePersonInterested.this, ShelterPostDemandActivity.class);
                startActivity(intent3);
                break;
            case 4:
                Intent intent4 = new Intent(ShelterViewAndConfirmOnePersonInterested.this, UserSearchOffersActivity.class);
                startActivity(intent4);
                break;
            case 5://Exit
                finishAffinity();
                break;
        }
        return true;
    }
}

