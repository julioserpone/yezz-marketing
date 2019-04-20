package com.yezz.company.yezzclub.lData.entities;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.yezz.company.yezzclub.Interfaces.ICrud;
import com.yezz.company.yezzclub.lData.YezzDB;
import com.yezz.company.yezzclub.lData.entities.contracts.PhoneContract;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Ramon_000 on 17-09-2016.
 */
public class Phone implements ICrud<PhoneContract>{

    private YezzDB db;
    private Context context;

    public Phone(Context context) {
        this.context = context;
    }

    @Override
    public void create(PhoneContract entity) {
        this.db = new YezzDB(context);
        entity.setStatus("new");
        this.db.save(PhoneContract.PhoneEntry.TABLE_NAME,entity.toContentValues());
    }

    @Override
    public void update(PhoneContract entity, PhoneContract filter) {
        this.db = new YezzDB(context);
        this.db.update(PhoneContract.PhoneEntry.TABLE_NAME,entity.toSpecificContentValues(),filter.getFilterArguments(),filter.getFilterArgumentsValues());
        this.db.close();
    }

    @Override
    public void update(PhoneContract entity, String where) {

    }

    @Override
    public void update(PhoneContract entity, String filter, String[] filterValues) {

    }

    public long createAndReturn(PhoneContract entity) {
        this.db = new YezzDB(context);
        long id;
        entity.setStatus("new");
        id = db.save(PhoneContract.PhoneEntry.TABLE_NAME,entity.toContentValues());
        db.close();
        return id;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public PhoneContract getRegister(PhoneContract entity) {
        return this.getRegister(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }

    @Override
    public PhoneContract getRegister(@Nullable String filter, @Nullable String[] filterValues) {
        this.db = new YezzDB(context);
        Cursor cursor = null;
        PhoneContract phoneContract = null;
        try{
            cursor = this.db.getCursor(PhoneContract.PhoneEntry.TABLE_NAME,null,filter,filterValues);
            while (cursor.moveToNext()) {
                phoneContract = this.getPhoneContract(cursor);
                cursor.close();
                this.db.close();
                return  phoneContract;
            }
        }catch (Exception ex){

        }finally{
            if(cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return phoneContract;
    }

    @Override
    public List<PhoneContract> getRegisters(PhoneContract entity) {
        return this.getRegisters(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }

    @Override
    public List<PhoneContract> getRegisters(@Nullable String filter, @Nullable String[] filterValues) {
        List<PhoneContract> list = new ArrayList<>();
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(PhoneContract.PhoneEntry.TABLE_NAME,null,filter,filterValues);
            while (cursor.moveToNext()) {
                list.add(this.getPhoneContract(cursor));
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
    public List<PhoneContract> getAllRegister() {
        return this.getRegisters(null,null);
    }

    @Override
    public String getName() {
        return null;
    }

    private PhoneContract getPhoneContract(Cursor cursor){
        PhoneContract phone  = new PhoneContract();
        phone.setId(cursor.getString(cursor.getColumnIndex(PhoneContract.PhoneEntry.ID)));
        phone.setBrand(cursor.getString(cursor.getColumnIndex(PhoneContract.PhoneEntry.BRAND)));
        phone.setModel(cursor.getString(cursor.getColumnIndex(PhoneContract.PhoneEntry.MODEL)));
        phone.setExhibition_media(cursor.getString(cursor.getColumnIndex(PhoneContract.PhoneEntry.EXHIBITION_MEDIA)));
        phone.setExhibition_media_comment(cursor.getString(cursor.getColumnIndex(PhoneContract.PhoneEntry.EXHIBITION_MEDIA_COMMET)));
        phone.setStock(cursor.getString(cursor.getColumnIndex(PhoneContract.PhoneEntry.STOCK)));
        phone.setExhibition(cursor.getString(cursor.getColumnIndex(PhoneContract.PhoneEntry.EXHIBITION)));
        phone.setParent_id(cursor.getString(cursor.getColumnIndex(PhoneContract.PhoneEntry.PARENT_ID)));
        phone.setSales(cursor.getString(cursor.getColumnIndex(PhoneContract.PhoneEntry.SALES)));
        phone.setPurchase_price(cursor.getString(cursor.getColumnIndex(PhoneContract.PhoneEntry.PURCHASE_PRICE)));
        phone.setSale_price(cursor.getString(cursor.getColumnIndex(PhoneContract.PhoneEntry.SALE_PRICE)));
        phone.setStatus(cursor.getString(cursor.getColumnIndex(PhoneContract.PhoneEntry.STATUS)));
        phone.setStore(cursor.getString(cursor.getColumnIndex(PhoneContract.PhoneEntry.STORE)));
        phone.setUser(cursor.getString(cursor.getColumnIndex(PhoneContract.PhoneEntry.USER)));
        phone.setBranch_id(cursor.getString(cursor.getColumnIndex(PhoneContract.PhoneEntry.BRANCHID)));
        return phone;
    }

}
