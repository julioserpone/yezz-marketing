package com.yezz.company.yezzclub;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yezz.company.yezzclub.Adapters.StoreMetaCursorAdapter;
import com.yezz.company.yezzclub.lData.YezzDB;
import com.yezz.company.yezzclub.lData.entities.contracts.StoreTypeContract;

/**
 * Created by ramon_000 on 01-09-2016.
 */
public class StoreMeta extends YezzMeta {
    private ListView lvData;
    private DrawerNavigation menu;
    private YezzDB dbHelper;
    private String type;
    private String[] keys;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        this.setContentView(R.layout.activity_menu_store_meta);
        this.iniComponent();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
    }

    public void iniComponent(){
        this.lvData=(ListView) this.findViewById(R.id.lvStoreMetaData);
        this.dbHelper=new YezzDB(this.getApplicationContext());
        this.menu=new DrawerNavigation(this,
                (DrawerLayout)this.findViewById(R.id.drawer_store_meta),
                (ListView)this.findViewById(R.id.left_drawer_store_meta));
        this.type=this.getIntent().getExtras().getString("type",null);

        this.showList();
        this.lvData.setOnItemClickListener(new ItemClickListener());
    }

    private void showList(){
        /*String[] params=null;
        int i=0;
        Cursor cursor;
        switch (this.type){
            case "type":
                cursor=dbHelper.getCursor(StoreTypeContract.StoreTypeEntry.TABLE_NAME,null,
                        StoreTypeContract.StoreTypeEntry.CHANNEL_ID+" = ?",new String[]{this.getIntent().getExtras().getString("key")});
                params=new String[]{
                        StoreTypeContract.StoreTypeEntry.NAME,
                        StoreTypeContract.StoreTypeEntry.ID,
                        this.getString(R.string.type)
                };
                break;*/
            /*case "segment":
                cursor=dbHelper.getCursor(StoreSegmentContract.StoreSegmentEntry.TABLE_NAME,null,
                        StoreSegmentContract.StoreSegmentEntry.TYPE_ID+" = ?",new String[]{this.getIntent().getExtras().getString("key")});
                params=new String[]{
                        StoreSegmentContract.StoreSegmentEntry.NAME,
                        StoreSegmentContract.StoreSegmentEntry.ID,
                        this.getString(R.string.segment)
                };
                break;*/
            /*case "chain":
                cursor=dbHelper.getCursor(ChainContract.StoreChainEntry.TABLE_NAME,null,
                        ChainContract.StoreChainEntry.SEGMENT_ID+" = ?",new String[]{this.getIntent().getExtras().getString("key")});
                params=new String[]{
                        ChainContract.StoreChainEntry.NAME,
                        ChainContract.StoreChainEntry.ID,
                        this.getString(R.string.chain)
                };
                break;*/
      /*      default:
                this.type="channel";
                cursor=dbHelper.getCursor(StoreChannelContract.StoreChannelEntry.TABLE_NAME);
                params=new String[]{
                        StoreChannelContract.StoreChannelEntry.NAME,
                        StoreChannelContract.StoreChannelEntry.ID,
                        this.getString(R.string.channel)
                };
        }
        if (cursor.getCount()>0){
            startManagingCursor(cursor);
            this.lvData.setAdapter(new StoreMetaCursorAdapter(this,cursor,params));
            this.keys=new String[cursor.getCount()];
            while (cursor.moveToNext()){
                this.keys[i++]=cursor.getString(cursor.getColumnIndex(params[1]));
            }
        }else{
            this.showMsg(this.getString(R.string.no_records));
            this.finish();
        }*/
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

    private class ItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            Intent intent=null;
            String typeItent="";
            switch (type){
                case "channel":
                    intent=new Intent(StoreMeta.this,StoreMeta.class);
                    typeItent="type";
                    break;
                case "type":
                    intent=new Intent(StoreMeta.this,StoreMeta.class);
                    typeItent="segment";
                    break;
                case "segment":
                    intent=new Intent(StoreMeta.this,StoreMeta.class);
                    typeItent="chain";
                    break;
                case "chain":
                    showMsg(getString(R.string.will_soon_be_available_this_module));
                    break;
            }
            if (intent!=null){
                intent.putExtra("key",keys[position]);
                intent.putExtra("type",typeItent);
                startActivity(intent);
            }
        }
    }
}
