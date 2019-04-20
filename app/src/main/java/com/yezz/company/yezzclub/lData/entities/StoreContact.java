package com.yezz.company.yezzclub.lData.entities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.yezz.company.yezzclub.Interfaces.ICrud;
import com.yezz.company.yezzclub.lData.YezzDB;
import com.yezz.company.yezzclub.lData.entities.contracts.StoreContactContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SairioA on 20/04/17.
 */

public class StoreContact implements ICrud<StoreContactContract>{
    private YezzDB db;
    private Context context;
    private long last_id;

    public StoreContact(Context context) {
        this.context = context;
        this.last_id = 0;
    }

    public long getLast_id() {
        return last_id;
    }

    @Override
    public void create(StoreContactContract entity) {
        this.db = new YezzDB(context);
        entity.setStatus("new");
        long result = this.db.save(StoreContactContract.StoreContactEntry.TABLE_NAME,entity.toContentValues());
        this.db.close();
        this.last_id = result;
    }

    @Override
    public void update(StoreContactContract entity, StoreContactContract filter) {
        this.db = new YezzDB(context);
        this.db.update(StoreContactContract.StoreContactEntry.TABLE_NAME,entity.toSpecificContentValues(),filter.getFilterArguments(),filter.getFilterArgumentsValues());
        this.db.close();
    }

    public void update(String table, ContentValues values, String field, String value){
        this.db = new YezzDB(context);
        this.db.update(table,values,field,value);
        this.db.close();
    }

    @Override
    public void update(StoreContactContract entity, String where) {

    }

    @Override
    public void update(StoreContactContract entity, String filter, String[] filterValues) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(String id) {

    }


    @Override
    public List<StoreContactContract> getAllRegister() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public StoreContactContract getRegister(StoreContactContract entity) {
        return this.getRegister(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }

    public StoreContactContract getRegister(@Nullable String filter, @Nullable String[] filterValues) {
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(StoreContactContract.StoreContactEntry.TABLE_NAME,null,filter,filterValues);
            while (cursor.moveToNext()){
                StoreContactContract storeContactContract=this.getStoreContactContract(cursor);
                cursor.close();
                this.db.close();
                return storeContactContract;
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
    public List<StoreContactContract> getRegisters(StoreContactContract entity) {
        return this.getRegisters(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }
    @Override
    public List<StoreContactContract> getRegisters(@Nullable String filter, @Nullable String[] filterValues) {
        List<StoreContactContract> list = new ArrayList<>();
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(StoreContactContract.StoreContactEntry.TABLE_NAME,null,filter,filterValues);
            while (cursor.moveToNext()){
                list.add(this.getStoreContactContract(cursor));
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

    private StoreContactContract getStoreContactContract(Cursor cursor){
        StoreContactContract contact  = new StoreContactContract();
        contact.setId(cursor.getString(cursor.getColumnIndex(StoreContactContract.StoreContactEntry.ID)));
        contact.setName(cursor.getString(cursor.getColumnIndex(StoreContactContract.StoreContactEntry.NAME)));
        contact.setSurname(cursor.getString(cursor.getColumnIndex(StoreContactContract.StoreContactEntry.SURNAME)));
        contact.setStore_position(cursor.getString(cursor.getColumnIndex(StoreContactContract.StoreContactEntry.STORE_POSITION)));
        contact.setPhone(cursor.getString(cursor.getColumnIndex(StoreContactContract.StoreContactEntry.PHONE)));
        contact.setEmail(cursor.getString(cursor.getColumnIndex(StoreContactContract.StoreContactEntry.EMAIL)));
        contact.setAddress(cursor.getString(cursor.getColumnIndex(StoreContactContract.StoreContactEntry.ADDRESS)));
        return contact;
    }
}
