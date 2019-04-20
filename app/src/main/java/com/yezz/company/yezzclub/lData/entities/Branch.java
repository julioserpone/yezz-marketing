package com.yezz.company.yezzclub.lData.entities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.yezz.company.yezzclub.Interfaces.ICrud;
import com.yezz.company.yezzclub.lData.YezzDB;
import com.yezz.company.yezzclub.lData.entities.contracts.BranchContract;
import com.yezz.company.yezzclub.lData.entities.contracts.CategoryContract;
import com.yezz.company.yezzclub.lData.entities.contracts.ChainContract;
import com.yezz.company.yezzclub.lData.entities.contracts.CityContract;
import com.yezz.company.yezzclub.lData.entities.contracts.StateContract;
import com.yezz.company.yezzclub.lData.entities.contracts.StoreContactContract;
import com.yezz.company.yezzclub.lData.entities.contracts.StoreTypeContract;
import com.yezz.company.yezzclub.lData.entities.contracts.TownshipsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramon_000 on 24-08-2016.
 */
public class Branch implements ICrud<BranchContract> {

    private YezzDB db;
    private Context context;
    private long lastId;

    public Branch(Context context){
        this.context = context;
    }


    @Override
    public void create(BranchContract entity) {
        this.db = new YezzDB(context);
        entity.setStatus("new");
        long result = this.db.save(BranchContract.BranchEntry.TABLE_NAME,entity.toContentValues());
        this.lastId = result;
        db.close();
    }

    public long getLasId(){ return this.lastId;}

    @Override
    public void update(BranchContract entity, BranchContract filter) {
        this.db = new YezzDB(context);
        this.db.update(BranchContract.BranchEntry.TABLE_NAME,entity.toSpecificContentValues(),filter.getFilterArguments(),filter.getFilterArgumentsValues());
        this.db.close();
    }

    public void update(String table, ContentValues values,String field,String value){
        this.db = new YezzDB(context);
        this.db.update(table,values,field,value);
        this.db.close();
    }

    @Override
    public void update(BranchContract entity, String where) {

    }

    @Override
    public void update(BranchContract entity, String filter, String[] filterValues) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public BranchContract getRegister(BranchContract entity) {
        return this.getRegister(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }

    @Override
    public BranchContract getRegister(@Nullable String filter, @Nullable String[] filterValues) {
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(BranchContract.BranchEntry.TABLE_NAME,null,filter,filterValues);
            while (cursor.moveToNext()) {
                BranchContract branchContract= this.getBranchContractFromCursor(cursor);
                cursor.close();
                this.db.close();
                return branchContract;
                //return null;
            }
        }catch (Exception ex){

        }finally{
            if(cursor != null)
                cursor.close();
            db.close();
        }
        return null;
    }

    public BranchContract getRegisterFull(BranchContract entity) {
        return this.getRegisterFull(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }

    public BranchContract getRegisterFull(@Nullable String filter, @Nullable String[] filterValues) {
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(BranchContract.BranchEntry.TABLE_NAME,null,filter,filterValues);

            while (cursor.moveToNext()) {
                BranchContract branchContract= this.getBranchContractFromCursor(cursor, new Category(this.context),
                        new StoreContact(this.context),new City(this.context),new State(this.context),
                        new Township(this.context),new StoreType(this.context),new Chain(this.context));
                cursor.close();
                this.db.close();
                return branchContract;
            }
        }catch (Exception ex){

        }finally{
            if(cursor != null)
                cursor.close();
            db.close();
        }
        return null;
    }

    @Override
    public List<BranchContract> getRegisters(BranchContract entity) {
        return this.getRegisters(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }

    @Override
    public List<BranchContract> getRegisters(@Nullable String filter, @Nullable String[] filterValues) {
        List<BranchContract> list = new ArrayList<>();
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(BranchContract.BranchEntry.TABLE_NAME,null,filter,filterValues);

            while (cursor.moveToNext()) {
                list.add(this.getBranchContractFromCursor(cursor));
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
    public List<BranchContract> getAllRegister() {
        return this.getRegisters(null,null);
    }

    public List<BranchContract> getAllRegisterWithDataAddress() {
        List<BranchContract> list = new ArrayList<>();
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(BranchContract.BranchEntry.TABLE_NAME,null,null,null);
            State state = new State(this.context);
            City city = new City(this.context);
            Township township = new Township(this.context);
            while (cursor.moveToNext()) {
                list.add(this.getBranchContractFromCursor(cursor, state, city, township));
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

    private BranchContract getBranchContractFromCursor(Cursor cursor){
        return this.getBranchContractFromCursor(cursor, null,null,null,null,null,null,null);
    }

    private BranchContract getBranchContractFromCursor(Cursor cursor,
                                                       @Nullable Category category, @Nullable StoreContact contact){
        return this.getBranchContractFromCursor(cursor, category, contact,null,null,null,null,null);
    }

    private BranchContract getBranchContractFromCursor(Cursor cursor,@Nullable State state,
                                                       @Nullable City city, @Nullable Township township){
        return this.getBranchContractFromCursor(cursor, null, null,city,state,township,null,null);
    }

    private BranchContract getBranchContractFromCursor(Cursor cursor,
                                                       @Nullable Category category, @Nullable StoreContact contact,
                                                       @Nullable City city, @Nullable State state,
                                                       @Nullable Township township, @Nullable StoreType type,
                                                       @Nullable Chain chain) {
        BranchContract branch  = new BranchContract();
        branch.setId(cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.ID)));
        branch.setName(cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.NAME)));
        branch.setCode(cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.CODE)));
        branch.setAddress(cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.ADDRESS)));
        branch.setPhone(cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.PHONE)));
        branch.setZip_code(cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.ZIP_CODE)));
        branch.setType_id(cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.TYPE_ID)));
        branch.setChain_id(cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.CHAIN_ID)));
        branch.setCountry_id(cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.COUNTRY_ID)));
        branch.setState_id(cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.STATE_ID)));
        branch.setCity_id(cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.CITY_ID)));
        branch.setTownship_id(cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.TOWNSHIP_ID)));
        branch.setLatitude(cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.LATITUDE)));
        branch.setLongitude(cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.LONGITUDE)));
        branch.setStatus(cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.STATUS)));
        branch.setIs_customer(cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.IS_CUSTOMER)));
        branch.setHas_pop(cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.HAS_POP)));
        branch.setContact(cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.CONTACT_ID)));
        branch.setCategory(cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.CATEGORY_ID)));
        branch.setComments(cursor.getString(cursor.getColumnIndex(BranchContract.BranchEntry.COMMENTS)));

        if(category != null) {
            branch.setObjCategory(category.getRegister(new CategoryContract(branch.getCategory(),null,null)));
        }

        if(contact != null) {
            branch.setObjContact(contact.getRegister(new StoreContactContract(branch.getContact(), null,null,null,null,null,null,null)));
        }

        if (type != null) {
            branch.setObjType(type.getRegister(StoreTypeContract.StoreTypeEntry.ID + " = ?", new String[]{branch.getType_id()}));
        }

        if (chain != null) {
            branch.setObjChain(chain.getRegister(ChainContract.StoreChainEntry.ID + " = ?", new String[] {branch.getChain_id()}));
        }

        if (state != null) {
            branch.setObjState(state.getRegister(StateContract.StateEntry.ID + " = ?", new String[] {branch.getState_id()}));
        }

        if (city != null) {
            branch.setObjCity(city.getRegister(CityContract.CityEntry.ID + " = ?", new String[] {branch.getCity_id()}));
        }

        if (township != null) {
            branch.setObjTownShip(township.getRegister(TownshipsContract.TownshipsEntry.ID + " = ?", new String[] {branch.getTownship_id()}));
        }
        return branch;
    }

}
