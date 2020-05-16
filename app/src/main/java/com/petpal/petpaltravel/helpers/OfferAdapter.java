package com.petpal.petpaltravel.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.petpal.petpaltravel.R;
import com.petpal.petpaltravel.model.CompanionOfPet;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OfferAdapter extends ArrayAdapter<CompanionOfPet> {
    private Context myContext;
    private int myResource;

    public OfferAdapter(@NonNull Context context, int resource,  @NonNull ArrayList<CompanionOfPet> objects) {
        super(context, resource, objects);
        myContext= context;
        myResource= resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= LayoutInflater.from(myContext);
        convertView= inflater.inflate(myResource, parent, false);

        TextView userName= (TextView) convertView.findViewById(R.id.tvuser);
        TextView fecha= (TextView) convertView.findViewById(R.id.tvfechaOf);
        TextView originCity= (TextView) convertView.findViewById(R.id.tvdesde);
        TextView destination= (TextView) convertView.findViewById(R.id.tvhasta);

        StringBuilder sb2= new StringBuilder();
        sb2.append(getItem(position).getNamePerson()); sb2.append(" acompaña a ");
        sb2.append(getItem(position).getPetType());
        userName.setText(sb2.toString());
        fecha.setText(new SimpleDateFormat("dd-MM-yyyy").format(getItem(position).getDateTravel().getTime()));
        originCity.setText("Va desde: " + getItem(position).getOriginCity());
        destination.setText("hasta : " + getItem(position).getDestinyCity());

        return convertView;

    }
}
