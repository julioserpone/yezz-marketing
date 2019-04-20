package com.yezz.company.yezzclub.lData.entities;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.yezz.company.yezzclub.Interfaces.ICrud;
import com.yezz.company.yezzclub.lData.YezzDB;
import com.yezz.company.yezzclub.lData.entities.contracts.LocalizationLogContract;
import com.yezz.company.yezzclub.lData.entities.contracts.StoreContactContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SairioA on 20/04/17.
 */

public class LocalizationLog implements ICrud<LocalizationLogContract>{

    private YezzDB db;
    private Context context;
    private long last_id;

    public LocalizationLog(Context context) {
        this.context = context;
        this.last_id = 0;
    }

    public long getLast_id() {
        return last_id;
    }

    @Override
    public void create(LocalizationLogContract entidad) {
        this.db = new YezzDB(context);
        entidad.setStatus("new");
        long result = this.db.save(LocalizationLogContract.LocalizationLogEntry.TABLE_NAME,entidad.toContentValues());
        this.db.close();
        this.last_id = result;
    }

    @Override
    public void update(LocalizationLogContract entity, LocalizationLogContract filter) {

    }

    @Override
    public void update(LocalizationLogContract entity, String where) {

    }

    @Override
    public void update(LocalizationLogContract entity, String filter, String[] filterValues) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public LocalizationLogContract getRegister(LocalizationLogContract entity) {
        return this.getRegister(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }

    @Override
    public LocalizationLogContract getRegister(@Nullable String filter, @Nullable String[] filterValues) {
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(LocalizationLogContract.LocalizationLogEntry.TABLE_NAME,null,filter,filterValues);
            while (cursor.moveToNext()){
                LocalizationLogContract localizationLogContract = this.getLocalizationLogContract(cursor);
                cursor.close();
                this.db.close();
                return localizationLogContract;
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
    public List<LocalizationLogContract> getRegisters(LocalizationLogContract entity) {
        return this.getRegisters(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }

    @Override
    public List<LocalizationLogContract> getRegisters(@Nullable String filter, @Nullable String[] filterValues) {
        List<LocalizationLogContract> list = new ArrayList<>();
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(LocalizationLogContract.LocalizationLogEntry.TABLE_NAME,null,filter,filterValues);
            while (cursor.moveToNext()){
                list.add(this.getLocalizationLogContract(cursor));
            }
        }catch (Exception ex){

        }
        finally{
            if(cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return list;
    }

    @Override
    public List<LocalizationLogContract> getAllRegister() {
        return null;
    }


    @Override
    public String getName() {
        return null;
    }

    private LocalizationLogContract getLocalizationLogContract(Cursor cursor){
        LocalizationLogContract contact  = new LocalizationLogContract();
        contact.setId(cursor.getString(cursor.getColumnIndex(LocalizationLogContract.LocalizationLogEntry.ID)));
        contact.setStore_id(cursor.getString(cursor.getColumnIndex(LocalizationLogContract.LocalizationLogEntry.STORE_ID)));
        contact.setLatitude(cursor.getString(cursor.getColumnIndex(LocalizationLogContract.LocalizationLogEntry.LATITUDE)));
        contact.setLongitude(cursor.getString(cursor.getColumnIndex(LocalizationLogContract.LocalizationLogEntry.LONGITUDE)));
        contact.setCreated(cursor.getString(cursor.getColumnIndex(LocalizationLogContract.LocalizationLogEntry.CREATED)));
        contact.setUser(cursor.getString(cursor.getColumnIndex(LocalizationLogContract.LocalizationLogEntry.USER)));
        return contact;
    }
}
