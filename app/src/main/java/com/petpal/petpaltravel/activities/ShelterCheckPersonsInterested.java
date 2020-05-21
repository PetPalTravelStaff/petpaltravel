package com.petpal.petpaltravel.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.petpal.petpaltravel.R;
import com.petpal.petpaltravel.helpers.DemandAdapter;
import com.petpal.petpaltravel.helpers.ViewPersonAdapter;
import com.petpal.petpaltravel.model.ApplicationForDemand;
import com.petpal.petpaltravel.model.CompanionForPet;
import com.petpal.petpaltravel.model.PPTModel;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class ShelterCheckPersonsInterested extends AppCompatActivity {
    //Attributes
    private PPTModel myModel;
    private ListView myListView;
    private TextView nameLabel, notification, selected;
    private ArrayList<ApplicationForDemand> listOfInterestedPerson;
    private AdapterView.OnItemClickListener listener;
    private String nameUser;
    private Boolean isShelter;
    private int idUser, idDemand;
    private ViewPersonAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sheltercheckallpersoninterested_layout);
        //instantiate model
        myModel = new PPTModel();
        //recover interesting data by Shared Preferences
        recoverShared();
        recoverDemandId();
        //recover list of all demands from model
        listOfInterestedPerson= (ArrayList<ApplicationForDemand>) myModel.listPersonInterestedByDemand(idDemand);
        //Create view elements in activity
        initElements();
        //create a listener
        createListener();
        //load data in view
        loadData();
    }


    /**
     * Method for recovering data needed by bundle
     */
    private void recoverDemandId() {
        Bundle bun = this.getIntent().getExtras();
        idDemand= bun.getInt("idDemand",0);
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
            idUser= shared.getInt("id", 0);
            isShelter = shared.getBoolean("isShelter", false);
        }
    }

    /**
     * Method for creating the elements of the activity
     */
    private void initElements () {
        myListView = (ListView) findViewById(R.id.lvProtectoras);
        nameLabel= (TextView) findViewById(R.id.etNombrePersona);
        notification= (TextView) findViewById(R.id.tverroroff);
        selected= (TextView) findViewById(R.id.textView);
    }

    /**
     * Method for creating a listener and identify what to do
     */
    private void createListener() {
        listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //save details of the demand touched of the list and open new activity
                showPersonDetails(listOfInterestedPerson.get(position));
            }
        };
    }

    /**
     * Method for saving demand details by bundle
     * and open a new activity
     * @param application with will be showed in new activity
     */
    public void showPersonDetails(ApplicationForDemand application) {
        Intent intent = new Intent(ShelterCheckPersonsInterested.this, ShelterViewAndConfirmOnePersonInterested.class);
        //Create a bundle object
        Bundle bundle = new Bundle();
        //set interesting data
        bundle.putInt("idDemand", idDemand);
        bundle.putInt("idApplyDem", application.getIdApplForDem());
        intent.putExtras(bundle);
        //open new activity
        startActivity(intent);
    }

    /**
     * Method fot loading data in the list view, using a personal apapter
     */
    private void loadData () {
        //Set the name of the user in the view
        nameLabel.setText(nameUser);
        //if there no list
        if (listOfInterestedPerson == null) {
            selected.setVisibility(View.GONE);
            notification.setText("Problemas al cargar los datos.\n\t Intentalo más tarde");
            //if list is empty
        } else if (listOfInterestedPerson.size()==0){
            selected.setVisibility(View.GONE);
            notification.setText("No se hay personas voluntarias apuntadas... aún.");
            //if list has lines
        } else {
            //create adapter
            //myadapter= new ViewPersonAdapter(this, R.layout.shelteradapterinterpersons_layout, listOfInterestedPerson);
            myadapter= new ViewPersonAdapter(this, listOfInterestedPerson);
            //set adapter to the listview
            myListView.setAdapter(myadapter);
            //set listener to the listview
            myListView.setOnItemClickListener(listener);
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
                Intent intent1 = new Intent(ShelterCheckPersonsInterested.this, UserViewAccountActivity.class);
                startActivity(intent1);
                break;
            case 2:
                Intent intent2 = new Intent(ShelterCheckPersonsInterested.this, ShelterManageDemandActivity.class);
                startActivity(intent2);
                break;
            case 3:
                 Intent intent3 = new Intent(ShelterCheckPersonsInterested.this, ShelterPostDemandActivity.class);
                 startActivity(intent3);
                break;
            case 4:
                Intent intent4 = new Intent(ShelterCheckPersonsInterested.this, UserSearchOffersActivity.class);
                startActivity(intent4);
                break;
            case 5://Exit
                finishAffinity();
                break;
        }
        return true;
    }
}
