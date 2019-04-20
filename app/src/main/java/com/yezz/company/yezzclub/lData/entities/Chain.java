package com.yezz.company.yezzclub.lData.entities;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.yezz.company.yezzclub.Interfaces.ICrud;
import com.yezz.company.yezzclub.lData.YezzDB;
import com.yezz.company.yezzclub.lData.entities.contracts.ChainContract;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ramon_000 on 29-08-2016.
 */
public class Chain implements ICrud<ChainContract> {

    private YezzDB db;
    private Context context;
    private long lastId;

    public Chain(Context context) {
        this.context = context;
        lastId = 0;
    }

    @Override
    public void create(ChainContract entity) {
        this.db = new YezzDB(context);
        entity.setStatus("new");
        //entity.setCode("none");
        long result = this.db.save(ChainContract.StoreChainEntry.TABLE_NAME,entity.toContentValues());
        this.lastId = result;
        this.db.close();
    }

    public long getLastId(){
        return lastId;
    }

    @Override
    public void update(ChainContract entity, ChainContract filter) {
        this.db = new YezzDB(context);
        this.db.update(ChainContract.StoreChainEntry.TABLE_NAME,entity.toSpecificContentValues(),filter.getFilterArguments(),filter.getFilterArgumentsValues());
        this.db.close();
    }

    @Override
    public void update(ChainContract entity, String where) {

    }

    @Override
    public void update(ChainContract entity, String filter, String[] filterValues) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public ChainContract getRegister(ChainContract entity) {
        return this.getRegister(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }

    @Override
    public ChainContract getRegister(@Nullable String filter, @Nullable String[] filterValues) {
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(ChainContract.StoreChainEntry.TABLE_NAME,null,filter,filterValues);
            while (cursor.moveToNext()){
                ChainContract chainContract =this.getStoreChainContract(cursor);
                cursor.close();
                this.db.close();
                return chainContract;
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
    public List<ChainContract> getRegisters(ChainContract entity) {
        return this.getRegisters(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }

    @Override
    public List<ChainContract> getRegisters(@Nullable String filter, @Nullable String[] filterValues) {
        List<ChainContract> list = new ArrayList<>();
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(ChainContract.StoreChainEntry.TABLE_NAME,null,filter,filterValues);
            while (cursor.moveToNext()){
                list.add(this.getStoreChainContract(cursor));
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
    public List<ChainContract> getAllRegister() {
        return this.getRegisters(null,null);
    }

    public List<ChainContract> getChainsBySegment(String segmentId)
    {
        String[] args = new String[] {segmentId};
        //String filter = ChainContract.StoreChainEntry.SEGMENT_ID +"=?";
        //return this.getRegisters(filter,args);
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    private ChainContract getStoreChainContract(Cursor cursor){
        ChainContract chain  = new ChainContract();
        chain.setId(cursor.getString(cursor.getColumnIndex(ChainContract.StoreChainEntry.ID)));
        chain.setName(cursor.getString(cursor.getColumnIndex(ChainContract.StoreChainEntry.NAME)));
        chain.setCountry_id(cursor.getString(cursor.getColumnIndex(ChainContract.StoreChainEntry.COUNTRY_ID)));
        //chain.setSegment(cursor.getString(cursor.getColumnIndex(ChainContract.StoreChainEntry.SEGMENT_ID)));
        chain.setStatus(cursor.getString(cursor.getColumnIndex(ChainContract.StoreChainEntry.STATUS)));
        return chain;
    }

    public String getRandomID(){
        this.db = new YezzDB(context);
        String id= UUID.randomUUID().toString();
        if (this.getRegister(ChainContract.StoreChainEntry.ID+ "=? ",new String[]{id})!=null){
            return this.getRandomID();
        }
        return id;
    }
}
