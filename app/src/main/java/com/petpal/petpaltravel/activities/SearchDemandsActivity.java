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

    View.OnClickListener listener2;
    PPTModel myModel;
    ListView myListView;
    TextView nameLabel;
    ArrayList<CompanionForPet> listOfDemands;
    AdapterView.OnItemClickListener listener;
    String nameUser;
    Boolean isShelter;
    DemandAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchingdemands_layout);
        myModel = new PPTModel();
        listOfDemands= (ArrayList<CompanionForPet>) myModel.getAllDemands();
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
        nameLabel= (TextView) findViewById(R.id.etNombrePersona);


    }

    private void createListener() {
        listener = new AdapterView.OnItemClickListener() {
            //se sobreescribe este método
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //llamamos a un método para que nos abra la siguiente activity con los datos del personaje en cuestión
                showDemandsDetails(listOfDemands.get(position));
            }
        };
    }

    public void showDemandsDetails(CompanionForPet demand) {
        Intent intent = new Intent(this, ShowDemandActivity.class);
        Bundle bundle = new Bundle();

        bundle.putInt("id", demand.getId());
        bundle.putInt("idShelter", demand.getIdeUserShelterOffering());
        bundle.putString("namePet", demand.getNamePet());

        bundle.putString("dateAv", new SimpleDateFormat("dd-MM-yyyy").format(demand.getAvailableFrom().getTime()));
        String dateEnd;
        if(demand.getDeadline()!=null) {
            dateEnd = new SimpleDateFormat("dd-MM-yyyy").format(demand.getDeadline().getTime());
        }else {
            dateEnd = "sin fecha límite";
        }
        bundle.putString("dateEnd", dateEnd);
        bundle.putString("cityOr", demand.getOriginCity());
        bundle.putString("cityDes", demand.getDestinyCity());
        bundle.putString("typepet", demand.getTypePet());
        bundle.putString("comments", demand.getComments());
        intent.putExtras(bundle);
        startActivity(intent);
        }



        private void loadData () {
            if (listOfDemands != null) {
                myadapter= new DemandAdapter(this, R.layout.demanditem_layout, listOfDemands);
                myListView.setAdapter(myadapter);
                myListView.setOnItemClickListener(listener);
            }
        }


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
                //To main_Activity
                Intent intent1 = new Intent(SearchDemandsActivity.this, ViewAccountActivity.class);
                startActivity(intent1);
                break;
            case 2:
                //To game_Activity
                Intent intent2 = new Intent(SearchDemandsActivity.this, ShowMyOffersActivity.class);
                startActivity(intent2);
                break;
            case 3:
                //To config_Activity
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
