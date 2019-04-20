package com.yezz.company.yezzclub.lData.entities.contracts;

import android.content.ContentValues;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import com.yezz.company.yezzclub.Interfaces.IFields;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Sairio on 25/06/17.
 */

public class StateContract implements IFields {
    public String id,
            name,
            country_id;

    public StateContract(@Nullable String id, String name, String country_id) {
        this.id = id;
        this.name = name;
        this.country_id = country_id;
    }

    private void initId(@Nullable String id){
        if (id == null){
            this.id = UUID.randomUUID().toString();
        }else{
            this.id = id;
        }
    }

    public StateContract() {
        this.initId(null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    @Override
    public String getFieldName() {
        return this.getName();
    }

    @Override
    public String getFieldId() {
        return this.getId();
    }

    public ContentValues toContentValues(){
        ContentValues values=new ContentValues();
        values.put(StateContract.StateEntry.ID,this.id);
        values.put(StateContract.StateEntry.COUNTRY_ID,this.country_id);
        values.put(StateContract.StateEntry.NAME,this.name);
        return values;
    }

    public ContentValues toSpecificContentValues(){
        ContentValues values=new ContentValues();
        if (this.id!=null){
            values.put(StateContract.StateEntry.ID,this.id);
        }
        if (this.country_id!=null){
            values.put(StateContract.StateEntry.COUNTRY_ID,this.country_id);
        }
        if (this.name!=null){
            values.put(StateContract.StateEntry.NAME,this.name);
        }
        return values;
    }
    public String getFilterArguments(){
        String arguments="";
        if (this.id!=null){
            arguments=arguments.concat(StateContract.StateEntry.ID+ " =?");
        }
        if (this.country_id!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(StateContract.StateEntry.COUNTRY_ID+ " =?");
        }
        if (this.name!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(StateContract.StateEntry.NAME+ " =?");
        }
        return arguments;
    }
    public String[] getFilterArgumentsValues(){
        List<String> values=new ArrayList<String>();
        if (this.id!=null){
            values.add(this.id);
        }
        if (this.country_id!=null){
            values.add(this.country_id);
        }
        if (this.name!=null){
            values.add(this.name);
        }
        return values.toArray(new String[values.size()]);
    }

    public static abstract class StateEntry implements BaseColumns {
        public static final String TABLE_NAME = "states";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String COUNTRY_ID = "country_id";

        public static final String CREATE_TABLE="CREATE TABLE "+ TABLE_NAME+"(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ID + " TEXT NOT NULL," +
                NAME + " TEXT NOT NULL," +
                COUNTRY_ID + " TEXT NOT NULL," +
                "UNIQUE("+ID+"))";
    }
}
