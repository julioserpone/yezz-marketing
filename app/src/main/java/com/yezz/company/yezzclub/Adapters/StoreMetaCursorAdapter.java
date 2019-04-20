package com.yezz.company.yezzclub.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by ramon_000 on 01-09-2016.
 */
public class StoreMetaCursorAdapter extends CursorAdapter {
    private String name=null,type="";

    public StoreMetaCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }
    public StoreMetaCursorAdapter(Context context, Cursor c,String[] data) {
        super(context, c);
        this.name= data[0];
        this.type=data[2];
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(android.R.layout.simple_dropdown_item_1line, viewGroup, false);
//        final View view = inflater.inflate(R.layout.list_meta_store_items, viewGroup, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView name=(TextView) view;
//        TextView name=(TextView) view.findViewById(R.id.tvListMetaStoreName);
        if (this.name!=null){
            name.setTextColor(Color.BLACK);
            name.setText(this.type.toUpperCase() +":"+ cursor.getString(cursor.getColumnIndex(this.name)));
        }
    }
}
