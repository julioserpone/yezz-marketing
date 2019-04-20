package com.yezz.company.yezzclub;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.yezz.company.yezzclub.services.Localizacion;
import com.yezz.company.yezzclub.services.SyncDataService;

import org.json.JSONObject;

/**
 * Created by Ramon_000 on 24-07-2016.
 */
public class Dashboard extends YezzMeta {
    private TextView tvUserName;
    private DrawerNavigation menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        this.setContentView(R.layout.activity_menu_dashboard);

        this.iniComponent();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
        ShowMessageGps();

        Intent intent = new Intent(this, SyncDataService.class);
        startService(intent);

        //iniciar gps
        Localizacion.latitud = 0.0;
        Localizacion.longitud = 0.0;
        this.locationStart();
    }

    private void iniComponent(){
        this.tvUserName=(TextView) this.findViewById(R.id.tvDashboardUserName);
        this.iniUserJson();
        this.tvUserName.setText(this.getUserNameData());

        this.menu=new DrawerNavigation(this,
                (DrawerLayout) this.findViewById(R.id.drawer_dashboard),
                (ListView) this.findViewById(R.id.left_drawer_dashboard));
    }

    public void clickDashboardLogout(View view){
        this.logout(this);
    }

    public void clickDashboardStore(View view){
        if (this.checkBranchSession()){
            this.startActivity(new Intent(this,StoreMenu.class));
        }else{
            this.startActivity(new Intent(this,StoreIdentify.class));
        }
    }

    public void clickDashboardRoute(View view){
        this.startActivity(new Intent(this,Route.class));
    }

    private String getUserNameData(){
        try {
            JSONObject person=this.getPersonData();
            return person.getString("first_name").concat(" ").concat(person.getString("last_name")).toUpperCase();
        }catch (Exception e){
            Log.d("getUserNameData",e.getMessage());
        }
        return null;
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
