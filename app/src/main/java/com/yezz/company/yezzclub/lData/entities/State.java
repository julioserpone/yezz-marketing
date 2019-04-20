package com.yezz.company.yezzclub.lData.entities;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.yezz.company.yezzclub.Interfaces.ICrud;
import com.yezz.company.yezzclub.lData.YezzDB;
import com.yezz.company.yezzclub.lData.entities.contracts.StateContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sairio A on 01/07/17.
 */

public class State implements ICrud<StateContract> {

    private YezzDB db;
    private Context context;

    public State(Context context) {
        this.context = context;
    }

    @Override
    public void create(StateContract entity) {

    }

    @Override
    public void update(StateContract entity, StateContract filter) {

    }

    @Override
    public void update(StateContract entity, String where) {

    }

    @Override
    public void update(StateContract entity, String filter, String[] filterValues) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(String id) {

    }

    @Override
    public StateContract getRegister(StateContract entity) {
        return this.getRegister(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }

    @Override
    public StateContract getRegister(@Nullable String filter, @Nullable String[] filterValues) {
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(StateContract.StateEntry.TABLE_NAME,null,filter,filterValues);
            while (cursor.moveToNext()){
                StateContract categoryContract =this.getStateFromCursor(cursor);
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
    public List<StateContract> getRegisters(StateContract entity) {
        return this.getRegisters(entity.getFilterArguments(),entity.getFilterArgumentsValues());
    }

    @Override
    public List<StateContract> getRegisters(@Nullable String filter, @Nullable String[] filterValues) {
        List<StateContract> list = new ArrayList<>();
        this.db = new YezzDB(context);
        Cursor cursor = null;
        try{
            cursor = this.db.getCursor(StateContract.StateEntry.TABLE_NAME,null,filter,filterValues);
            while (cursor.moveToNext()){
                list.add(this.getStateFromCursor(cursor));
            }
        } catch (Exception ex){}
        finally{
            if(cursor != null)
                cursor.close();
            db.close();
        }
        return list;
    }

    @Override
    public List<StateContract> getAllRegister() {
        return this.getRegisters(null, null);
    }

    @Override
    public String getName() {
        return null;
    }

    private StateContract getStateFromCursor(Cursor cursor){
        StateContract state  = new StateContract();
        state.setId(cursor.getString(cursor.getColumnIndex(StateContract.StateEntry.ID)));
        state.setName(cursor.getString(cursor.getColumnIndex(StateContract.StateEntry.NAME)));
        state.setCountry_id(cursor.getString(cursor.getColumnIndex(StateContract.StateEntry.COUNTRY_ID)));
        return state;
    }
}
