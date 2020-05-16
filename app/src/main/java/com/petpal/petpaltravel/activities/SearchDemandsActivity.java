package com.petpal.petpaltravel.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.petpal.petpaltravel.R;
import com.petpal.petpaltravel.helpers.DemandAdapter;
import com.petpal.petpaltravel.helpers.OfferAdapter;
import com.petpal.petpaltravel.model.CompanionForPet;
import com.petpal.petpaltravel.model.PPTModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class SearchDemandsActivity extends AppCompatActivity {
    //Attributes
    PPTModel myModel;
    ListView myListView;
    TextView nameLabel, notification;
    ArrayList<CompanionForPet> listOfDemands;
    AdapterView.OnItemClickListener listener;
    String nameUser;
    Boolean isShelter;
    DemandAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchingdemands_layout);
        //instantiate model
        myModel = new PPTModel();
        //recover list of all demands from model
        listOfDemands= (ArrayList<CompanionForPet>) myModel.getAllDemands();
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
        nameLabel= (TextView) findViewById(R.id.etNombrePersona);
        notification= (TextView) findViewById(R.id.tverrordem);
    }

    /**
     * Method for creating a listener and identify what to do
     */
    private void createListener() {
        listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //save details of the demand touched of the list and open new activity
                showDemandsDetails(listOfDemands.get(position));
            }
        };
    }

    /**
     * Method for saving demand details by bundle
     * and open a new activity
     * @param demand with will be showed in new activity
     */
    public void showDemandsDetails(CompanionForPet demand) {
        //set with new activity will be opened
        Intent intent = new Intent(this, ShowDemandActivity.class);
        //Create a bundle object
        Bundle bundle = new Bundle();
        //set interesting data
        bundle.putInt("idDemand", demand.getId());
        intent.putExtras(bundle);
        //open new activity
        startActivity(intent);
        }

    /**
     * Method fot loading data in the list view, using a personal apapter
     */
    private void loadData () {
        //if there is demands to show
        if (listOfDemands != null) {
            //create adapter
             myadapter= new DemandAdapter(this, R.layout.demanditem_layout, listOfDemands);
             //set adapter to the listview
             myListView.setAdapter(myadapter);
             //set listener to the listview
             myListView.setOnItemClickListener(listener);
        } else {
            notification.setText("No se han encontrado peticiones de acompañamiento... aún.");
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
        menu.add(0, 4, 3, "Salir");
        return true;
    }

    // Handles item selections from Option MENU
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                //Go to view account activity
                Intent intent1 = new Intent(SearchDemandsActivity.this, ViewAccountActivity.class);
                startActivity(intent1);
                break;
            case 2:
                //Go to show my offers  activity
                Intent intent2 = new Intent(SearchDemandsActivity.this, ShowMyOffersActivity.class);
                startActivity(intent2);
                break;
            case 3:
                //Go to add an offer activity
                Intent intent3 = new Intent(SearchDemandsActivity.this, AddOfferActivity.class);
                startActivity(intent3);
                break;
            case 4://Exit
                finish();
                break;
        }
        return true;
    }


}
