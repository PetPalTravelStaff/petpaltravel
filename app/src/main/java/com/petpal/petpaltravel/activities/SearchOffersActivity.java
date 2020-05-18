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
import com.petpal.petpaltravel.helpers.OfferAdapter;
import com.petpal.petpaltravel.model.CompanionOfPet;
import com.petpal.petpaltravel.model.PPTModel;

import java.util.ArrayList;

public class SearchOffersActivity extends AppCompatActivity {
    //Attributes
    PPTModel myModel;
    ListView myListView;
    TextView nameLabel, notification;
    ArrayList<CompanionOfPet> listOfOffers;
    AdapterView.OnItemClickListener listener;
    String nameUser;
    Boolean isShelter;
    OfferAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchingoffers_layout);
        //instantiate model
        myModel = new PPTModel();
        //recover list of all offers from model
        listOfOffers= (ArrayList<CompanionOfPet>) myModel.getAllOffers();
        //Create view elements in activity
        initElements();
        //recover interesting data by Shared Preferences
        recoverShared();
        //Set the name of the user in the view
        nameLabel.setText(nameUser);
        //create a listener
        createListener();
        //load data in view
        loadData();
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
            isShelter = shared.getBoolean("isShelter", false);
        }
    }

    /**
     * Method for creating the elements of the activity
     */
    private void initElements () {
        myListView = (ListView) findViewById(R.id.lvLista);
        nameLabel= (TextView) findViewById(R.id.etNombreProtectora);
        notification= (TextView) findViewById(R.id.tverroroff);
    }

    /**
     * Method for creating a listener and identify what to do
     */
    private void createListener() {
        listener = new AdapterView.OnItemClickListener() {
            //se sobreescribe este método
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //save details of the offer touched of the list and open new activity
                showOfferDetails(listOfOffers.get(position));
            }
        };
    }

    /**
     * Method for saving offer details by bundle
     * and open a new activity
     * @param offer with will be showed in new activity
     */
    public void showOfferDetails(CompanionOfPet offer) {
        //set with new activity will be opened
        Intent intent = new Intent(this, ShowOfferActivity.class);
        //Create a bundle object
        Bundle bundle = new Bundle();
        //set interesting data
        bundle.putInt("idOffer", offer.getId());
        intent.putExtras(bundle);
        //open new activity
        startActivity(intent);
    }

    /**
     * Method fot loading data in the list view, using a personal apapter
     */
    private void loadData () {
        //if list of Offers is null
        if (listOfOffers == null) {
            notification.setText("Problemas al cargar los datos.\n\t Intentalo más tarde");
        //if list of offers is empty
        } else if (listOfOffers.size()==0) {
            notification.setText("No se han encontrado ofertas de acompañamiento... aún.");
        //if list of Offers has lines
        } else {
            //create adapter
            myadapter= new OfferAdapter(this, R.layout.offeritem_layout,listOfOffers);
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
        menu.add(0, 4, 3, "Salir");
        return true;
    }

    // Handles item selections from Option MENU
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                //Go to view account activity
                Intent intent1 = new Intent(SearchOffersActivity.this, ViewAccountActivity.class);
                startActivity(intent1);
                break;
            case 2:
                //Go to show my demands  activity
                Intent intent2 = new Intent(SearchOffersActivity.this, SearchDemandsActivity.class);
                startActivity(intent2);
                break;
            case 3:
                //Go to add a demand activity
                Intent intent3 = new Intent(SearchOffersActivity.this, ManageDemandActivity.class);
                startActivity(intent3);
                break;
            case 4://Exit
                finish();
                break;
        }
        return true;
    }
}
