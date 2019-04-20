package com.yezz.company.yezzclub.lData.entities;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.yezz.company.yezzclub.Interfaces.ICrud;
import com.yezz.company.yezzclub.lData.YezzDB;
import com.yezz.company.yezzclub.lData.entities.contracts.BrandPhoneContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramon_000 on 27-09-2016.
 */

public class PhoneBrand implements ICrud<BrandPhoneContract> {

    private YezzDB db;
    private Context context;
    private long last_id;

    public PhoneBrand(Context context) {
        this.context = context;
    }

    public long getLast_id() {
        return last_id;
    }

    @Override
    public void create(BrandPhoneContract entidad) {
        this.db = new YezzDB(context);
        entidad.setStatus("new");
        long result = this.db.save(BrandPhoneContract.BrandEntry.TABLE_NAME,entidad.toContentValues());
        this.db.close();
        this.last_id = result;
    }

    @Override
    public void update(BrandPhoneContract entity, BrandPhoneContract filter) {

    }

    @Override
    public void update(BrandPhoneContract entity, String where) {

    }

    @Override
    public void update(BrandPhoneContract entity, String filter, String[] filterValues) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public BrandPhoneContract getRegister(BrandPhoneContract entity) {
        return this.getRegister(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }

    @Override
    public BrandPhoneContract getRegister(@Nullable String filter, @Nullable String[] filterValues) {
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(BrandPhoneContract.BrandEntry.TABLE_NAME,null,filter,filterValues);
            while (cursor.moveToNext()) {
                BrandPhoneContract brandPhoneContract=this.getBrandPhoneContract(cursor);
                cursor.close();
                this.db.close();
                return  brandPhoneContract;
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
    public List<BrandPhoneContract> getRegisters(BrandPhoneContract entity) {
        return this.getRegisters(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }


    @Override
    public List<BrandPhoneContract> getRegisters(@Nullable String filter, @Nullable String[] filterValues) {
        List<BrandPhoneContract> list = new ArrayList<>();
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(BrandPhoneContract.BrandEntry.TABLE_NAME,null,filter,filterValues);
            while (cursor.moveToNext()){
                list.add(this.getBrandPhoneContract(cursor));
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

    public List<BrandPhoneContract> getPhoneBrandYezz(boolean isYezz)
    {
        String[] args = new String[] {String.valueOf((isYezz ? 1 : 0))};
        String filter = BrandPhoneContract.BrandEntry.ISYEZZ +"=?";
        return this.getRegisters(filter,args);
    }

    @Override
    public List<BrandPhoneContract> getAllRegister() {
        List<BrandPhoneContract> list = new ArrayList<>();
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(BrandPhoneContract.BrandEntry.TABLE_NAME);
            while (cursor.moveToNext()) {
                list.add(this.getBrandPhoneContract(cursor));
            }
        }catch (Exception ex){

        }finally{
            if(cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return list;
    }

    @Override
    public String getName() {
        return null;
    }

    public BrandPhoneContract getBrandPhoneContract(Cursor cursor){
        BrandPhoneContract b = new BrandPhoneContract();
        b.setId(cursor.getString(cursor.getColumnIndex(BrandPhoneContract.BrandEntry.ID)));
        b.setName(cursor.getString(cursor.getColumnIndex(BrandPhoneContract.BrandEntry.NAME)));
        b.setStatus(cursor.getString(cursor.getColumnIndex(BrandPhoneContract.BrandEntry.STATUS)));
        b.setIs_yezz(cursor.getString(cursor.getColumnIndex(BrandPhoneContract.BrandEntry.ISYEZZ)));
        return b;
    }
}
