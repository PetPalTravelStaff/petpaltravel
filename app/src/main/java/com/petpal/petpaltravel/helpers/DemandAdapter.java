package com.petpal.petpaltravel.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.petpal.petpaltravel.R;
import com.petpal.petpaltravel.model.CompanionForPet;
import com.petpal.petpaltravel.model.CompanionOfPet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DemandAdapter extends ArrayAdapter<CompanionForPet> {
    private Context myContext;
    private int myResource;

    public DemandAdapter(@NonNull Context context, int resource, @NonNull List<CompanionForPet> objects) {
        super(context, resource, objects);
        myContext= context;
        myResource= resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= LayoutInflater.from(myContext);
        convertView= inflater.inflate(myResource, parent, false);

        TextView sheltName= (TextView) convertView.findViewById(R.id.tvprote);
        TextView petName= (TextView) convertView.findViewById(R.id.tvnombrepet);
        TextView petType= (TextView) convertView.findViewById(R.id.tvtipopet);
        TextView fecha= (TextView) convertView.findViewById(R.id.tvfechaDem);

        sheltName.setText(getItem(position).getNameShelter());
        petName.setText("Nombre: "+ getItem(position).getNamePet());
        petType.setText("Tipo: "+ getItem(position).getTypePet());
        fecha.setText((new SimpleDateFormat("dd-MM-yyyy").format(getItem(position).getAvailableFrom().getTime())));

        return convertView;
    }
}
