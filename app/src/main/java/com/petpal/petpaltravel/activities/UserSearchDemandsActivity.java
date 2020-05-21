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
import com.petpal.petpaltravel.model.CompanionForPet;
import com.petpal.petpaltravel.model.PPTModel;

import java.util.ArrayList;

public class UserSearchDemandsActivity extends AppCompatActivity {
    //Attributes
    PPTModel myModel;
    ListView myListView;
    TextView nameLabel, notification, titulo;
    ArrayList<CompanionForPet> listOfDemands;
    AdapterView.OnItemClickListener listener;
    String nameUser;
    Boolean isShelter;
    int idUser;
    DemandAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersearchdemands_layout);
        //instantiate model
        myModel = new PPTModel();
        //recover interesting data by Shared Preferences
        recoverShared();
        //recover list of all demands from model
        if (!isShelter) {
            listOfDemands= (ArrayList<CompanionForPet>) myModel.getAllDemands();
        } else {
            listOfDemands= (ArrayList<CompanionForPet>) myModel.getDemandsPostedByShelter(idUser);
        }
        //Create view elements in activity
        initElements();

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
            idUser= shared.getInt("id", 0);
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
        titulo= (TextView) findViewById(R.id.tvTituloVerQuienAcompa);
        if (isShelter) {
            titulo.setText("Estas son tus peticiones publicadas");
        }
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
        Intent intent;
        if (isShelter){
            //set with new activity will be opened
            intent = new Intent(UserSearchDemandsActivity.this, ShelterManageDemandActivity.class);
        } else {
            //set with new activity will be opened
            intent = new Intent(UserSearchDemandsActivity.this, PersonManageDemandActivity.class);
        }
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
        //if there no list
        if (listOfDemands == null) {
            notification.setText("Problemas al cargar los datos.\n\t Intentalo más tarde");
        //if list is empty
        } else if (listOfDemands.size()==0){
            notification.setText("No se han encontrado peticiones de acompañamiento... aún.");
        //if list has lines
        } else {
            //create adapter
            myadapter= new DemandAdapter(this, R.layout.useradapteritemdemand_layout, listOfDemands);
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
        if (isShelter) {
            super.onCreateOptionsMenu(menu);
            menu.add(0, 1, 0, "Mi perfil");
            //menu.add(0, 2, 1, "Ver mis peticiones");
            menu.add(0, 3, 2, "Publicar petición");
            menu.add(0, 4, 3, "Buscar ofertas");
            menu.add(0, 5, 4, "Salir");
        } else {
            menu.add(0, 1, 0, "Mi perfil");
            menu.add(0, 2, 1, "Ver mis ofertas");
            menu.add(0, 3, 2, "Publicar oferta");
            //menu.add(0, 4, 3, "Buscar peticiones");
            menu.add(0, 5, 4, "Salir");
        }

        return true;
    }

    // Handles item selections from Option MENU
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                //Go to view account activity
                Intent intent1 = new Intent(UserSearchDemandsActivity.this, UserViewAccountActivity.class);
                startActivity(intent1);
                break;
            case 2:
                    Intent intent2 = new Intent(UserSearchDemandsActivity.this, UserSearchOffersActivity.class);
                    startActivity(intent2);
                break;
            case 3:
               if (isShelter) {//Go to add an offer activity
                   Intent intent3 = new Intent(UserSearchDemandsActivity.this, ShelterPostDemandActivity.class);
                   startActivity(intent3);
               } else {//Go to add an offer activity
                   Intent intent3 = new Intent(UserSearchDemandsActivity.this, PersonPostOfferActivity.class);
                   startActivity(intent3);
               }
                break;
            case 4:
                    Intent intent4 = new Intent(UserSearchDemandsActivity.this, UserSearchOffersActivity.class);
                    startActivity(intent4);
                break;
            case 5://Exit
                finishAffinity();
                break;
        }
        return true;
    }
}
