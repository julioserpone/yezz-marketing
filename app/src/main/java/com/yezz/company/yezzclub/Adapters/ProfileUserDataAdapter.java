package com.yezz.company.yezzclub.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yezz.company.yezzclub.ProfileUserDataItem;
import com.yezz.company.yezzclub.R;

import java.util.List;

/**
 * Created by ramon_000 on 19-08-2016.
 */
public class ProfileUserDataAdapter extends ArrayAdapter{

    public ProfileUserDataAdapter (Context context, List objects){
        super(context,0,objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)parent.getContext().
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_data_list, null);
        }

        TextView meta=(TextView) convertView.findViewById(R.id.tvUserDataListMeta);
        TextView data=(TextView) convertView.findViewById(R.id.tvUserDataListData);

        ProfileUserDataItem item=(ProfileUserDataItem) getItem(position);
        meta.setText(convertView.getResources().getString(item.getMeta()));
        data.setText(item.getData());

        return convertView;
    }
}
