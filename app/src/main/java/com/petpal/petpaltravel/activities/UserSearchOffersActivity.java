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

public class UserSearchOffersActivity extends AppCompatActivity {
    //Attributes
    PPTModel myModel;
    ListView myListView;
    TextView nameLabel, notification, titulo;
    ArrayList<CompanionOfPet> listOfOffers;
    AdapterView.OnItemClickListener listener;
    String nameUser;
    int idUser;
    Boolean isShelter;
    OfferAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersearchoffers_layout);
        //instantiate model
        myModel = new PPTModel();
        //recover interesting data by Shared Preferences
        recoverShared();
        //recover list of all offers from model
        if (isShelter) {
            listOfOffers = (ArrayList<CompanionOfPet>) myModel.getAllOffers();
        } else {
            listOfOffers= (ArrayList<CompanionOfPet>) myModel.getOffersPostedByPerson(idUser);
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
        nameLabel= (TextView) findViewById(R.id.etNombreProtectora);
        notification= (TextView) findViewById(R.id.tverroroff);
        titulo= (TextView)findViewById(R.id.tvTituloPersonasAcompa);
        if (!isShelter) {
            titulo.setText("Estas son tus ofertas publicadas");
        }
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
        Intent intent;
        if (isShelter) {
            //set with new activity will be opened
            intent = new Intent(this, ShelterManageOfferActivity.class);
        } else {
            //set with new activity will be opened
            intent = new Intent(this, PersonManageOfferActivity.class);
        }

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
            if (isShelter) {
                notification.setText("No se han encontrado ofertas de acompañamiento... aún.");
            } else {
                notification.setText("No has ofrecido tu grata compañía.");
            }
        //if list of Offers has lines
        } else {
            //create adapter
            myadapter= new OfferAdapter(this, R.layout.useradapteritemoffer_layout,listOfOffers);
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
            menu.add(0, 2, 1, "Ver mis peticiones");
            menu.add(0, 3, 2, "Publicar petición");
            //menu.add(0, 4, 3, "Buscar ofertas");
            menu.add(0, 5, 4, "Salir");
        } else {
            menu.add(0, 1, 0, "Mi perfil");
            //menu.add(0, 2, 1, "Ver mis ofertas");
            menu.add(0, 3, 2, "Publicar oferta");
            menu.add(0, 4, 3, "Buscar peticiones");
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
                Intent intent1 = new Intent(UserSearchOffersActivity.this, UserViewAccountActivity.class);
                startActivity(intent1);
                break;
            case 2:
                Intent intent2 = new Intent(UserSearchOffersActivity.this, UserSearchDemandsActivity.class);
                startActivity(intent2);
                break;
            case 3:
                if (isShelter) {//Go to add an offer activity
                    Intent intent3 = new Intent(UserSearchOffersActivity.this, ShelterPostDemandActivity.class);
                    startActivity(intent3);
                } else {//Go to add an offer activity
                    Intent intent3 = new Intent(UserSearchOffersActivity.this, PersonPostOfferActivity.class);
                    startActivity(intent3);
                }
                break;
            case 4:
                Intent intent4 = new Intent(UserSearchOffersActivity.this, UserSearchDemandsActivity.class);
                startActivity(intent4);
                break;
            case 5://Exit
                finish();
                break;
        }
        return true;
    }
}
