package com.yezz.company.yezzclub.lData.entities;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.yezz.company.yezzclub.Interfaces.ICrud;
import com.yezz.company.yezzclub.lData.YezzDB;
import com.yezz.company.yezzclub.lData.entities.contracts.CityContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arkanhell on 01/07/17.
 */

public class City implements ICrud<CityContract> {

    private YezzDB db;
    private Context context;
    private long lastId;

    public City(Context context) {
        this.context = context;
    }

    public long getLastId() {
        return lastId;
    }

    @Override
    public void create(CityContract entity) {
        this.db = new YezzDB(context);
        entity.setStatus("new");
        long result = this.db.save(CityContract.CityEntry.TABLE_NAME,entity.toContentValues());
        this.lastId = result;
        this.db.close();
    }

    @Override
    public void update(CityContract entity, CityContract filter) {
        this.db = new YezzDB(context);
        this.db.update(CityContract.CityEntry.TABLE_NAME,entity.toSpecificContentValues(),filter.getFilterArguments(),filter.getFilterArgumentsValues());
        this.db.close();
    }

    @Override
    public void update(CityContract entity, String where) {

    }

    @Override
    public void update(CityContract entity, String filter, String[] filterValues) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public CityContract getRegister(CityContract entity) {
        return this.getRegister(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }

    @Override
    public CityContract getRegister(@Nullable String filter, @Nullable String[] filterValues) {
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(CityContract.CityEntry.TABLE_NAME,null,filter,filterValues);
            while (cursor.moveToNext()){
                CityContract categoryContract =this.getStateFromCursor(cursor);
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
    public List<CityContract> getRegisters(CityContract entity) {
        return this.getRegisters(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }

    @Override
    public List<CityContract> getRegisters(@Nullable String filter, @Nullable String[] filterValues) {
        List<CityContract> list = new ArrayList<>();
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(CityContract.CityEntry.TABLE_NAME,null,filter,filterValues);
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
    public List<CityContract> getAllRegister() {
        return this.getRegisters(null, null);
    }

    @Override
    public String getName() {
        return null;
    }

    private CityContract getStateFromCursor(Cursor cursor){
        CityContract city  = new CityContract();
        city.setId(cursor.getString(cursor.getColumnIndex(CityContract.CityEntry.ID)));
        city.setName(cursor.getString(cursor.getColumnIndex(CityContract.CityEntry.NAME)));
        city.setState_id(cursor.getString(cursor.getColumnIndex(CityContract.CityEntry.STATE_ID)));
        return city;
    }
}
