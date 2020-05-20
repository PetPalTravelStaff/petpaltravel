package com.petpal.petpaltravel.helpers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.petpal.petpaltravel.R;
import com.petpal.petpaltravel.model.ApplicationForDemand;

import java.text.SimpleDateFormat;
import java.util.List;

public class ViewPersonAdapter extends ArrayAdapter<ApplicationForDemand> {
    private Context myContext;
    private int myResource;

    public ViewPersonAdapter(@NonNull Context context, int resource, @NonNull List<ApplicationForDemand> objects) {
        super(context, resource, objects);
        myContext= context;
        myResource= resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= LayoutInflater.from(myContext);
        convertView= inflater.inflate(myResource, parent, false);

        TextView personName= (TextView) convertView.findViewById(R.id.tvpersona);
        TextView transport= (TextView) convertView.findViewById(R.id.tvcomoviaja);
        ImageView selected= (ImageView) convertView.findViewById(R.id.imagenseleccion);

        personName.setText("Nombre: "+getItem(position).getNamePerson());
        transport.setText("Viaja en: "+ getItem(position).getTransport());
        if (getItem(position).getChoosed()){
            selected.setImageResource(R.drawable.selected);
        } else {
            selected.setImageResource(R.drawable.notselected);
        }

        return convertView;
    }

}