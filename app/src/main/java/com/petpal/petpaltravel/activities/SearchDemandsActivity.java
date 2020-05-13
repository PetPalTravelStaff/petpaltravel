package com.petpal.petpaltravel.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.petpal.petpaltravel.R;
import com.petpal.petpaltravel.model.CompanionForPet;
import com.petpal.petpaltravel.model.PPTModel;

import java.util.List;

public class SearchDemandsActivity extends AppCompatActivity {

    View.OnClickListener listener;
    PPTModel myModel;
    ListView myListView;
    List<CompanionForPet> listOfDemand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchingdemands_layout);
        myModel= PPTModel.getInstance();
        //Create view elements in activity
        initElements();

        loadData();

    }


    private void initElements() {
        myListView= (ListView) findViewById(R.id.lvLista);
    }

    private void loadData() {
    //Si la array es diferente de null (está llena)
        if(listOfDemand != null) {
            //Crear una llista amb els noms de cad personatges (name)

            if (listOfDemand.size()!=0) {
                //String [] lista = new String [personatges.length];
                for (int i = 0; i < listOfDemand.size(); i++) {
                    myListView.( listOfDemand.get(i);
                    tvText.setText(personatges[i].name);
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, // context
                        R.layout.list_item, // layout description for each list item
                        sList);
                myListView.setAdapter(arrayAdapter);
                myListView.setOnItemClickListener(listener);
            } else {
                tvText.setText("Problemas al cargar la lista de personajes (lista tamaño 0)");
            }
        }else{
            tvText.setText("Problemas al cargar la lista de personajes (lista null)");

        }
    }


}
