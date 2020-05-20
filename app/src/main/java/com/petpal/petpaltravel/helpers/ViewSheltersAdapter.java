package com.petpal.petpaltravel.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.petpal.petpaltravel.R;
import com.petpal.petpaltravel.model.ApplicationForOffer;

import java.util.ArrayList;

public class ViewSheltersAdapter extends BaseAdapter {
    private Context myContext;
    private ArrayList<ApplicationForOffer> myList;

    public ViewSheltersAdapter(Context myContext, ArrayList<ApplicationForOffer> myList) {
        this.myContext = myContext;
        this.myList = myList;
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int position) {
        return myList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ApplicationForOffer item= (ApplicationForOffer) getItem(position);
        convertView= LayoutInflater.from(myContext).inflate(R.layout.personadapterintershelters_layout,null);

        TextView shelterName= (TextView) convertView.findViewById(R.id.tvshelter);
        TextView petName= (TextView) convertView.findViewById(R.id.tvmascota);
        TextView petType= (TextView) convertView.findViewById(R.id.tvtipo);
        ImageView selected= (ImageView) convertView.findViewById(R.id.imagenseleccion);

        shelterName.setText(item.getNameShelter());
        petName.setText("Nombre: "+item.getNamePet());
        petType.setText("Tipo: "+ item.getTypePet());
        if (item.getChoosed()){
            selected.setImageResource(R.drawable.pequever);
        } else {
            selected.setImageResource(R.drawable.pequegris);
        }

        return convertView;
    }

}