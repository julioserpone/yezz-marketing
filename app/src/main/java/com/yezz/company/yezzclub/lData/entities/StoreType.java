package com.yezz.company.yezzclub.lData.entities;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.yezz.company.yezzclub.Interfaces.ICrud;
import com.yezz.company.yezzclub.lData.YezzDB;
import com.yezz.company.yezzclub.lData.entities.contracts.StoreTypeContract;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ramon_000 on 29-08-2016.
 */
public class StoreType implements ICrud<StoreTypeContract> {

    private YezzDB db;
    private Context context;

    public  StoreType(Context context){
        this.context = context;
    }


    @Override
    public void create(StoreTypeContract entidad) {

    }

    @Override
    public void update(StoreTypeContract entity, StoreTypeContract filter) {

    }

    @Override
    public void update(StoreTypeContract entity, String where) {

    }

    @Override
    public void update(StoreTypeContract entity, String filter, String[] filterValues) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public StoreTypeContract getRegister(StoreTypeContract entity) {
        return null;
    }

    @Override
    public StoreTypeContract getRegister(@Nullable String filter, @Nullable String[] filterValues) {
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(StoreTypeContract.StoreTypeEntry.TABLE_NAME,null,filter,filterValues);
            while (cursor.moveToNext()) {
                StoreTypeContract storeTypeContract=this.getStoreTypeContract(cursor);
                cursor.close();
                this.db.close();
                return storeTypeContract;
            }
        }catch (Exception ex){

        }
        finally{
            if(cursor != null)
                cursor.close();
            db.close();
        }
        return null;
    }

    @Override
    public List<StoreTypeContract> getRegisters(StoreTypeContract entity) {
        return this.getRegisters(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }

    @Override
    public List<StoreTypeContract> getRegisters(@Nullable String filter, @Nullable String[] filterValues) {
        List<StoreTypeContract> list = new ArrayList<>();
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(StoreTypeContract.StoreTypeEntry.TABLE_NAME,null,filter,filterValues);
            while (cursor.moveToNext()) {
                list.add(this.getStoreTypeContract(cursor));
            }
        }catch (Exception ex){

        }
        finally{
            if(cursor != null)
                cursor.close();
            db.close();
        }
        return list;
    }

    @Override
    public List<StoreTypeContract> getAllRegister() {
        return this.getRegisters(null,null);
    }

    @Override
    public String getName() {
        return null;
    }

    private StoreTypeContract getStoreTypeContract(Cursor cursor){
        StoreTypeContract typeContract  = new StoreTypeContract();
        typeContract.setId(cursor.getString(cursor.getColumnIndex(StoreTypeContract.StoreTypeEntry.ID)));
        typeContract.setName(cursor.getString(cursor.getColumnIndex(StoreTypeContract.StoreTypeEntry.NAME)));
        //typeContract.setCode(cursor.getString(cursor.getColumnIndex(StoreTypeContract.StoreTypeEntry.CODE)));
        //typeContract.setChannel(cursor.getString(cursor.getColumnIndex(StoreTypeContract.StoreTypeEntry.CHANNEL_ID)));
        return typeContract;
    }
}
