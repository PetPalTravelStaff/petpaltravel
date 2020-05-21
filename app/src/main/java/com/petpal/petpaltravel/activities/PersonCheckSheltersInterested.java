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
import com.petpal.petpaltravel.helpers.ViewSheltersAdapter;
import com.petpal.petpaltravel.model.ApplicationForOffer;
import com.petpal.petpaltravel.model.PPTModel;

import java.util.ArrayList;

public class PersonCheckSheltersInterested extends AppCompatActivity {
    //Attributes
    private PPTModel myModel;
    private ListView myListView;
    private TextView nameLabel, notification, selected;
    private ArrayList<ApplicationForOffer> listOfInterestedShelter;
    private AdapterView.OnItemClickListener listener;
    private String nameUser;
    private Boolean isShelter;
    private int idUser, idOffer;
    private ViewSheltersAdapter myadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personcheckallshelterinterested_layout);
        //instantiate model
        myModel = new PPTModel();
        //recover interesting data by Shared Preferences
        recoverShared();
        recoverOfferId();
        //recover list of all demands from model
        listOfInterestedShelter= (ArrayList<ApplicationForOffer>) myModel.listShelterInterestedByOffer(idOffer);
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
    private void recoverOfferId() {
        Bundle bun = this.getIntent().getExtras();
        idOffer= bun.getInt("idOffer",0);
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
                showShelterDetails(listOfInterestedShelter.get(position));
            }
        };
    }

    /**
     * Method for saving demand details by bundle
     * and open a new activity
     * @param application with will be showed in new activity
     */
    public void showShelterDetails(ApplicationForOffer application) {
        Intent intent = new Intent(PersonCheckSheltersInterested.this, PersonViewAndConfirmOneShelterInterested.class);
        //Create a bundle object
        Bundle bundle = new Bundle();
        //set interesting data
        bundle.putInt("idOffer", idOffer);
        bundle.putInt("idApplyOff", application.getIdAppliForOf());
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
        if (listOfInterestedShelter == null) {
            selected.setVisibility(View.GONE);
            notification.setText("Problemas al cargar los datos.\n\t Intentalo más tarde");
            //if list is empty
        } else if (listOfInterestedShelter.size()==0){
            selected.setVisibility(View.GONE);
            notification.setText("Ninguna protectora te ha solicitado... aún.");
            //if list has lines
        } else {
            //create adapter
            myadapter= new ViewSheltersAdapter(this, listOfInterestedShelter);
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
                Intent intent1 = new Intent(PersonCheckSheltersInterested.this, UserViewAccountActivity.class);
                startActivity(intent1);
                break;
            case 2:
                Intent intent2 = new Intent(PersonCheckSheltersInterested.this, UserSearchOffersActivity.class);
                startActivity(intent2);
                break;
            case 3:
                Intent intent3 = new Intent(PersonCheckSheltersInterested.this, UserSearchDemandsActivity.class);
                startActivity(intent3);
                break;
            case 4:
                Intent intent4 = new Intent(PersonCheckSheltersInterested.this, PersonPostOfferActivity.class);
                startActivity(intent4);
                break;
            case 5://Exit
                finishAffinity();
                break;
        }
        return true;
    }
}
