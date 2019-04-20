package com.yezz.company.yezzclub.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.yezz.company.yezzclub.R;
import com.yezz.company.yezzclub.lData.entities.contracts.BranchContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ramon_000 on 16-09-2016.
 */
public class RouteCursorAdapter extends CursorAdapter {
    private JSONArray array;


    public RouteCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    public RouteCursorAdapter(Context context, Cursor c, JSONArray array) {
        super(context, c);
        this.array=array;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.list_route_item, viewGroup, false);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView name=(TextView) view.findViewById(R.id.tvRouteItemBranchName);
        TextView address=(TextView) view.findViewById(R.id.tvRouteItemBranchAddress);
        TextView location=(TextView) view.findViewById(R.id.tvRouteItemBranchLocation);
        TextView city=(TextView) view.findViewById(R.id.tvRouteItemBranchCity);
        TextView zone=(TextView) view.findViewById(R.id.tvRouteItemBranchZone);
        TextView status=(TextView) view.findViewById(R.id.tvRouteItemBranchStatus);
        TextView route=(TextView) view.findViewById(R.id.tvRouteItemBranchRoute);

        name.setText(cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.NAME)));
        address.setText(cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.ADDRESS)));
       /* location.setText(cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.LOCATION)));
        city.setText(cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.CITY)));
        zone.setText(cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.ZONE)));*/

        try {
            for (int i=0;i<array.length();i++){
                JSONObject row=array.getJSONObject(i);
                if(row.getString("branch_id")==cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.ID))){
                    route.setText(row.getString("route").equals("true")?context.getString(R.string.belong_route):context.getString(R.string.not_belong_route));
                    status.setText(row.getString("checked").equals("true")?context.getString(R.string.store_visited):context.getString(R.string.store_unvisited));
                    break;
                }
            }
        }catch (JSONException e){
            Log.d("Route Curso Adapter",e.getMessage());
        }

    }
}
