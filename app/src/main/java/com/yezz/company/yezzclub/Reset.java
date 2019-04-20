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

import com.yezz.company.yezzclub.lData.YezzDB;

import org.json.JSONObject;

/**
 * Created by rramon on 22/2/2017.
 */

public class Reset extends YezzMeta {    private TextView tvUserName;
    private DrawerNavigation menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        this.setContentView(R.layout.activity_menu_reset);

        this.iniComponent();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
    }

    private void iniComponent(){
        this.menu=new DrawerNavigation(this,
                (DrawerLayout) this.findViewById(R.id.drawer_reset),
                (ListView) this.findViewById(R.id.left_drawer_reset));
    }

    public void clickResetReset(View view){
        YezzDB db=new YezzDB(this);
        db.deleteThisDatabase(this);
        this.clearFileJson(this.getString(R.string.fileLastUser));
        this.logout(this);
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
