package com.yezz.company.yezzclub;

import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;

import com.yezz.company.yezzclub.Adapters.RouteCursorAdapter;
import com.yezz.company.yezzclub.lData.YezzDB;
import com.yezz.company.yezzclub.lData.entities.contracts.BranchContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ramon_000 on 16-09-2016.
 */
public class Route extends YezzMeta {
    private DrawerNavigation menu;
    private ListView lvRoute;
    private YezzDB dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        this.setContentView(R.layout.activity_menu_route);
        this.iniComponent();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
    }

    private void iniComponent(){
        this.lvRoute=(ListView) this.findViewById(R.id.lvRouteData);

        this.dbHelper=new YezzDB(this.getApplicationContext());

        this.menu=new DrawerNavigation(this,
                (DrawerLayout) this.findViewById(R.id.drawer_route),
                (ListView) this.findViewById(R.id.left_drawer_route));
        this.showList();
    }

    private void showList(){
        JSONArray routes=this.getRouteData();
        if (routes.length()>0){
            try {
                String where="";
                String values[]=new String[routes.length()];
                for (int i=0;i<routes.length();i++){
                    where+=where.equals("")?"":" OR ";
                    where+= BranchContract.BranchEntry.ID+" = ?";
                    JSONObject route=routes.getJSONObject(i);
                    values[i]=route.getString("branch_id");
                }
                Cursor cursor=dbHelper.getCursor(BranchContract.BranchEntry.TABLE_NAME,null,where,values);
                if (cursor.getCount()>0){
                    startManagingCursor(cursor);
                    this.lvRoute.setAdapter(new RouteCursorAdapter(this,cursor,routes));
                }else{
                    this.showMsg("Error al consultar");
                }
            }catch (JSONException e){
                Log.d("showList",e.getMessage());
            }

        }else{
            this.showMsg(this.getString(R.string.no_route));
            this.finish();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.menu.getActionBar().syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.menu.getActionBar().onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.menu.getActionBar().onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
