package com.petpal.petpaltravel.helpers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.petpal.petpaltravel.R;
import com.petpal.petpaltravel.model.ApplicationForDemand;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ViewPersonAdapter extends BaseAdapter {
    private Context myContext;
    private ArrayList<ApplicationForDemand> myList;

//    public ViewPersonAdapter(@NonNull Context context, int resource, @NonNull List<ApplicationForDemand> objects) {
//        super(context, resource, objects);
//        myContext= context;
//        myResource= resource;
//    }


    public ViewPersonAdapter(Context myContext, ArrayList<ApplicationForDemand> myList) {
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
        //LayoutInflater inflater= LayoutInflater.from(myContext);
        //convertView= inflater.inflate(myResource, parent, false);

        ApplicationForDemand item= (ApplicationForDemand) getItem(position);
        convertView= LayoutInflater.from(myContext).inflate(R.layout.shelteradapterinterpersons_layout,null);

        TextView personName= (TextView) convertView.findViewById(R.id.tvpersona);
        TextView transport= (TextView) convertView.findViewById(R.id.tvcomoviaja);
        ImageView selected= (ImageView) convertView.findViewById(R.id.imagenseleccion);



        personName.setText("Nombre: "+item.getNamePerson());
        transport.setText("Viaja en: "+ item.getTransport());
        if (item.getChoosed()){
            selected.setImageResource(R.drawable.pequever);
        } else {
            selected.setImageResource(R.drawable.pequegris);
        }
//        personName.setText("Nombre: "+getItem(position).getNamePerson());
//        transport.setText("Viaja en: "+ getItem(position).getTransport());
//        if (getItem(position).getChoosed()){
//            selected.setImageResource(R.drawable.selected);
//        } else {
//            selected.setImageResource(R.drawable.notselected);
//        }

        return convertView;
    }

}