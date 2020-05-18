package com.petpal.petpaltravel.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.petpal.petpaltravel.R;
import com.petpal.petpaltravel.model.PPTModel;

public class ManageOfferActivity extends AppCompatActivity {
    //Attributes
    EditText nameBox, personName, dateTravel, comments;
    Spinner originCity, destinyCity;
    CheckBox cat, dog, others;
    Spinner transport;
    private Button btPostOffer;
    View.OnClickListener listener;
    PPTModel myModel;
    int userId;
    String nameUser;
    Boolean isShelter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personoffer_layout);
        //instantiate model
        //instantiate model
        myModel = new PPTModel();
        //recover needed data
        recoverShared();
        //Create view elements in activity
        initElements();
        //create a listener
        createListener();
        //load Data in view
        loadData();
        //add listener to elements
        addElementsToListener();
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
            userId= shared.getInt("id", 0);
            isShelter = shared.getBoolean("isShelter", false);
        }
    }

    /**
     * Method for create elements of activity
     */
    private void initElements() {
        nameBox= (TextView) findViewById(R.id.etNombrePersona);
        originCity= (Spinner) findViewById(R.id.etCiudadOrigen);
        destinyCity= (Spinner) findViewById(R.id.etCiudadDestino);
        cat= (CheckBox) findViewById(R.id.cbGatos);
        dog= (CheckBox) findViewById(R.id.cbPerros);
        others= (CheckBox) findViewById(R.id.cbOtros);
        transport= (Spinner) findViewById(R.id.etTransporte);
        comments= (TextView) findViewById(R.id.etComentarios);

        //set value to name of the user field
        btPostOffer= (Button) findViewById(R.id.btPublicar);
    }

    /**
     * Method for creating a listener and identify what to do
     */
    private void createListener() {
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
    }
}
