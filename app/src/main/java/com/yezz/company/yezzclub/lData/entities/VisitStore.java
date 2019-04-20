package com.yezz.company.yezzclub.lData.entities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.yezz.company.yezzclub.Interfaces.ICrud;
import com.yezz.company.yezzclub.lData.YezzDB;
import com.yezz.company.yezzclub.lData.entities.contracts.VisitStoreContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SairioP on 10/09/17.
 */

public class VisitStore  implements ICrud<VisitStoreContract> {
    private YezzDB db;
    private Context context;
    private long lastId;

    public VisitStore(Context context) {
        db = new YezzDB(context);
        this.context = context;
        this.lastId = 0;

    }

    @Override
    public void create(VisitStoreContract entity) {
        this.db = new YezzDB(context);
        entity.setStatus("active");
        long result = this.db.save(VisitStoreContract.VisitStoreEntry.TABLE_NAME,entity.toContentValues());
        db.close();
        this.lastId = result;
    }

    @Override
    public void update(VisitStoreContract entity, VisitStoreContract filter) {
        this.db = new YezzDB(context);
        this.db.update(VisitStoreContract.VisitStoreEntry.TABLE_NAME,entity.toSpecificContentValues(),
                filter.getFilterArguments(),filter.getFilterArgumentsValues());
        this.db.close();
    }

    @Override
    public void update(VisitStoreContract entity, String where) {

    }

    @Override
    public void update(VisitStoreContract entity, String filter, String[] filterValues) {

    }

    public void update(String table, ContentValues values, String field, String value){
        this.db = new YezzDB(context);
        this.db.update(table,values,field,value);
        this.db.close();
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public VisitStoreContract getRegister(VisitStoreContract entity) {
        return null;
    }

    @Override
    public VisitStoreContract getRegister(@Nullable String filter, @Nullable String[] filterValues) {
        this.db = new YezzDB(context);
        Cursor cursor = null;
        VisitStoreContract visitStore = null;
        try{
            cursor = this.db.getCursor(VisitStoreContract.VisitStoreEntry.TABLE_NAME,null,filter,filterValues);
            while (cursor.moveToNext()) {
                visitStore= this.getVisitStoreContractFromCursor(cursor);
                cursor.close();
                this.db.close();
                return visitStore;
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally{
            if(cursor != null)
                cursor.close();
            db.close();
        }
        return visitStore;
    }

    @Override
    public List<VisitStoreContract> getRegisters(VisitStoreContract entity) {
        return null;
    }

    @Override
    public List<VisitStoreContract> getRegisters(@Nullable String filter, @Nullable String[] filterValues) {
        List<VisitStoreContract> list = new ArrayList<>();
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(VisitStoreContract.VisitStoreEntry.TABLE_NAME,null,filter,filterValues);

            while (cursor.moveToNext()) {
                list.add(this.getVisitStoreContractFromCursor(cursor));
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
    public List<VisitStoreContract> getAllRegister() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    private VisitStoreContract getVisitStoreContractFromCursor(Cursor cursor) {
        VisitStoreContract visitStore  = new VisitStoreContract();
        visitStore.setStatus(cursor.getString(cursor.getColumnIndex(VisitStoreContract.VisitStoreEntry.STATUS)));
        visitStore.setBranchId(cursor.getString(cursor.getColumnIndex(VisitStoreContract.VisitStoreEntry.BRANCHID)));
        visitStore.setComment(cursor.getString(cursor.getColumnIndex(VisitStoreContract.VisitStoreEntry.COMMENT)));
        visitStore.setDate(cursor.getString(cursor.getColumnIndex(VisitStoreContract.VisitStoreEntry.DATE)));
        visitStore.setStart(cursor.getString(cursor.getColumnIndex(VisitStoreContract.VisitStoreEntry.START)));
        visitStore.setUserId(cursor.getString(cursor.getColumnIndex(VisitStoreContract.VisitStoreEntry.USERID)));
        visitStore.setId(cursor.getString(cursor.getColumnIndex(VisitStoreContract.VisitStoreEntry.ID)));
        return visitStore;
    }
}
