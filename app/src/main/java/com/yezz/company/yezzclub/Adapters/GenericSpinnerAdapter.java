package com.yezz.company.yezzclub.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yezz.company.yezzclub.Interfaces.IFields;

import java.util.List;

/**
 * Created by Ramon_000 on 04-09-2016.
 */
public class GenericSpinnerAdapter<T extends IFields> extends ArrayAdapter<T> {
    private Context mContext;
    private List<T> mValues;

    public GenericSpinnerAdapter(Context context, int textViewResourceId, List<T> objects)
    {
        super(context, textViewResourceId, objects);
        this.mContext = context;
        this.mValues = objects;
    }

    @Override
    public int getCount() {
        return this.mValues.size();
    }

    @Override
    public T getItem(int position) {
        return mValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //
        TextView label = new TextView(mContext);
        label.setTextColor(Color.BLACK);
        //label.setTextSize(18);
        label.setText("" + mValues.get(position).getFieldName());
        //label.setHeight(50);
        label.setGravity(Gravity.LEFT | Gravity.CENTER);
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        //Este es para cuando el usuario hace click al spinner y la lista de despliega
        TextView label = new TextView(mContext);
        label.setTextColor(Color.BLACK);
        //label.setTextSize(18);
        label.setText("" + mValues.get(position).getFieldName());
        //label.setHeight(70);
        label.setGravity(Gravity.LEFT | Gravity.CENTER);
        return label;
    }
}
