package com.petpal.petpaltravel.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.petpal.petpaltravel.R;
import com.petpal.petpaltravel.helpers.OfferAdapter;
import com.petpal.petpaltravel.model.CompanionOfPet;
import com.petpal.petpaltravel.model.PPTModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SearchOffersActivity extends AppCompatActivity {
    View.OnClickListener listener2;
    PPTModel myModel;
    ListView myListView;
    TextView nameLabel;
    ArrayList<CompanionOfPet> listOfOffers;
    AdapterView.OnItemClickListener listener;
    String nameUser;
    Boolean isShelter;
    OfferAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchingoffers_layout);
        myModel = new PPTModel();
        listOfOffers= (ArrayList<CompanionOfPet>) myModel.getAllOffers();
        //Create view elements in activity
        initElements();
        recoverShared();
        nameLabel.setText(nameUser);
        createListener();
        loadData();
    }

    private void recoverShared() {
        //Creem un objecte amb el shared preferences que li pasem per paràmetre
        SharedPreferences shared = getSharedPreferences("dades", MODE_PRIVATE);
        if (shared!=null) {
            //comencem a llegir
            nameUser = shared.getString("userName", "");
            isShelter = shared.getBoolean("isShelter", false);
        }
    }


    private void initElements () {
        myListView = (ListView) findViewById(R.id.lvLista);
        nameLabel= (TextView) findViewById(R.id.etNombreProtectora);


    }

    private void createListener() {
        listener = new AdapterView.OnItemClickListener() {
            //se sobreescribe este método
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //llamamos a un método para que nos abra la siguiente activity con los datos del personaje en cuestión
                showOfferDetails(listOfOffers.get(position));
            }
        };
    }

    public void showOfferDetails(CompanionOfPet offer) {
        Intent intent = new Intent(this, ShowOfferActivity.class);
        Bundle bundle = new Bundle();

        bundle.putInt("id", offer.getId());
        bundle.putInt("idPerson", offer.getIdeUserPersonOffering());
        bundle.putString("dateTrav", new SimpleDateFormat("dd-MM-yyyy").format(offer.getDateTravel().getTime()));
        bundle.putString("cityOr", offer.getOriginCity());
        bundle.putString("cityDes", offer.getDestinyCity());
        bundle.putString("transport", offer.getTransport());
        bundle.putString("typepet", offer.getPetType());
        bundle.putString("comments", offer.getComments());
        intent.putExtras(bundle);
        startActivity(intent);
    }


    private void loadData () {
        if (listOfOffers != null) {
            myadapter= new OfferAdapter(this, R.layout.offeritem_layout,listOfOffers);
            myListView.setAdapter(myadapter);
            myListView.setOnItemClickListener(listener);
        }
    }

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
                //To main_Activity
                Intent intent1 = new Intent(SearchOffersActivity.this, ViewAccountActivity.class);
                startActivity(intent1);
                break;
            case 2:
                //To game_Activity
                Intent intent2 = new Intent(SearchOffersActivity.this, ShowMyDemandsActivity.class);
                startActivity(intent2);
                break;
            case 3:
                //To config_Activity
                Intent intent3 = new Intent(SearchOffersActivity.this, AddDemandActivity.class);
                startActivity(intent3);
                break;
            case 4://Exit
                finish();
                break;
        }
        return true;
    }


}
