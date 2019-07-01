package com.example.aa.service;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Cadapter extends BaseAdapter {

    Context context ;
    ArrayList<Bean> beans;

    public Cadapter(Context context, ArrayList<Bean> beans) {
        this.context = context;
        this.beans = beans;
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_cell,null);

        Holder holder = new Holder();
        holder.imageView = view.findViewById(R.id.img);
        holder.textView = view.findViewById(R.id.text);
        holder.imageView.setImageURI(beans.get(i).getUri());
        holder.textView.setText(beans.get(i).getName());
        Log.i("----", "getView: "+beans.get(i).getUri());
        return view;
    }

}

class Holder{
    ImageView imageView;
    TextView textView;
}
