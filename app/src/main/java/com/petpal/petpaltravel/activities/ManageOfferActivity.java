package com.petpal.petpaltravel.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.petpal.petpaltravel.R;
import com.petpal.petpaltravel.model.PPTModel;

public class ManageOfferActivity extends AppCompatActivity {
    //Attributes
    EditText personName, dateTravel, originCity, destinyCity, comments;
    CheckBox cat, dog, others;
    RadioGroup transport;
    RadioButton car, ship, trein, plane;
    private Button btPostOffer;
    View.OnClickListener listener;
    PPTModel myModel;
    int userId;
    String phoneUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personoffer_layout);
        //instantiate model
        myModel= new PPTModel();

    }


}
