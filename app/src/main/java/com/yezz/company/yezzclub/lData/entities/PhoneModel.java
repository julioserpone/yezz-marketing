package com.yezz.company.yezzclub.lData.entities;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.yezz.company.yezzclub.Interfaces.ICrud;
import com.yezz.company.yezzclub.lData.YezzDB;
import com.yezz.company.yezzclub.lData.entities.contracts.PhoneModelContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramon_000 on 27-09-2016.
 */

public class PhoneModel implements ICrud<PhoneModelContract> {

    private YezzDB db;
    private Context context;
    private long last_id;


    public PhoneModel(Context context) {
        this.context = context;
        this.last_id = 0;
    }

    public long getLast_id() {
        return last_id;
    }

    @Override
    public void create(PhoneModelContract entidad) {
        this.db = new YezzDB(context);
        entidad.setStatus("new");
        long result = this.db.save(PhoneModelContract.PhoneModelEntry.TABLE_NAME,entidad.toContentValues());
        this.db.close();
        this.last_id = result;
    }

    @Override
    public void update(PhoneModelContract entity, PhoneModelContract filter) {

    }

    @Override
    public void update(PhoneModelContract entity, String where) {

    }

    @Override
    public void update(PhoneModelContract entity, String filter, String[] filterValues) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public PhoneModelContract getRegister(PhoneModelContract entity) {
        return this.getRegister(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }

    @Override
    public PhoneModelContract getRegister(@Nullable String filter, @Nullable String[] filterValues) {
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(PhoneModelContract.PhoneModelEntry.TABLE_NAME,null,filter,filterValues);
            while (cursor.moveToNext()) {
                PhoneModelContract phoneModelContract=this.getPhoneModelContract(cursor);
                cursor.close();
                this.db.close();
                return  phoneModelContract;
            }
        }catch (Exception ex){

        }finally{
            if(cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return null;
    }

    @Override
    public List<PhoneModelContract> getRegisters(PhoneModelContract entity) {
        return this.getRegisters(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }

    @Override
    public List<PhoneModelContract> getRegisters(@Nullable String filter, @Nullable String[] filterValues) {
        List<PhoneModelContract> list = new ArrayList<>();
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(PhoneModelContract.PhoneModelEntry.TABLE_NAME,null,filter,filterValues);
            while (cursor.moveToNext()){
                list.add(this.getPhoneModelContract(cursor));
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

    public List<PhoneModelContract> getModelByBrand(String brand){
        String[] args = new String[] {brand};
        String filter = PhoneModelContract.PhoneModelEntry.BRAND +"=?";
        return this.getRegisters(filter,args);
    }

    @Override
    public List<PhoneModelContract> getAllRegister() {
        return this.getRegisters(null,null);
    }

    @Override
    public String getName() {
        return null;
    }

    public PhoneModelContract getPhoneModelContract(Cursor cursor){
        PhoneModelContract b = new PhoneModelContract();
        b.setId(cursor.getString(cursor.getColumnIndex(PhoneModelContract.PhoneModelEntry.ID)));
        b.setName(cursor.getString(cursor.getColumnIndex(PhoneModelContract.PhoneModelEntry.NAME)));
        b.setStatus(cursor.getString(cursor.getColumnIndex(PhoneModelContract.PhoneModelEntry.STATUS)));
        b.setBrand(cursor.getString(cursor.getColumnIndex(PhoneModelContract.PhoneModelEntry.BRAND)));
        b.setProduct_id(cursor.getString(cursor.getColumnIndex(PhoneModelContract.PhoneModelEntry.PRODUCT_ID)));
        return b;
    }
}
