package com.yezz.company.yezzclub.lData.entities;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.yezz.company.yezzclub.Interfaces.ICrud;
import com.yezz.company.yezzclub.lData.YezzDB;
import com.yezz.company.yezzclub.lData.entities.contracts.TownshipsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arkanhell on 01/07/17.
 */

public class Township implements ICrud<TownshipsContract>{

    private YezzDB db;
    private Context context;
    private long lastId;

    public Township(Context context) {
        this.context = context;
    }

    public long getLastId() {
        return lastId;
    }

    @Override
    public void create(TownshipsContract entity) {
        this.db = new YezzDB(context);
        entity.setStatus("new");
        long result = this.db.save(TownshipsContract.TownshipsEntry.TABLE_NAME,entity.toContentValues());
        this.lastId = result;
    }

    @Override
    public void update(TownshipsContract entity, TownshipsContract filter) {
        this.db = new YezzDB(context);
        this.db.update(TownshipsContract.TownshipsEntry.TABLE_NAME,entity.toSpecificContentValues(),filter.getFilterArguments(),filter.getFilterArgumentsValues());
        this.db.close();
    }

    @Override
    public void update(TownshipsContract entity, String where) {

    }

    @Override
    public void update(TownshipsContract entity, String filter, String[] filterValues) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public TownshipsContract getRegister(TownshipsContract entity) {
        return this.getRegister(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }

    @Override
    public TownshipsContract getRegister(@Nullable String filter, @Nullable String[] filterValues) {
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(TownshipsContract.TownshipsEntry.TABLE_NAME,null,filter,filterValues);
            while (cursor.moveToNext()){
                TownshipsContract categoryContract =this.getStateFromCursor(cursor);
                cursor.close();
                this.db.close();
                return categoryContract;
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
    public List<TownshipsContract> getRegisters(TownshipsContract entity) {
        return this.getRegisters(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }

    @Override
    public List<TownshipsContract> getRegisters(@Nullable String filter, @Nullable String[] filterValues) {
        List<TownshipsContract> list = new ArrayList<>();
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(TownshipsContract.TownshipsEntry.TABLE_NAME,null,filter,filterValues);
            while (cursor.moveToNext()){
                list.add(this.getStateFromCursor(cursor));
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
    public List<TownshipsContract> getAllRegister() {
        return this.getRegisters(null, null);
    }

    @Override
    public String getName() {
        return null;
    }

    private TownshipsContract getStateFromCursor(Cursor cursor){
        TownshipsContract township = new TownshipsContract();
        township.setId(cursor.getString(cursor.getColumnIndex(TownshipsContract.TownshipsEntry.ID)));
        township.setName(cursor.getString(cursor.getColumnIndex(TownshipsContract.TownshipsEntry.NAME)));
        township.setCity_id(cursor.getString(cursor.getColumnIndex(TownshipsContract.TownshipsEntry.CITY_ID)));
        return township;
    }
}
