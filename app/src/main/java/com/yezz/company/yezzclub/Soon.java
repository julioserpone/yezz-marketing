package com.yezz.company.yezzclub;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

/**
 * Created by ramon_000 on 07-09-2016.
 */
public class Soon extends YezzMeta{
    private DrawerNavigation menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        this.setContentView(R.layout.activity_menu_soon);

        this.iniComponent();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
    }

    private void iniComponent(){
        this.menu=new DrawerNavigation(this,
                (DrawerLayout) this.findViewById(R.id.drawer_soon),
                (ListView) this.findViewById(R.id.left_drawer_soon));
    }

    public void clickSoonBack(View view){
        this.finish();
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
