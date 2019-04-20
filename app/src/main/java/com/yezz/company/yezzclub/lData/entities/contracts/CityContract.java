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

public class CityContract implements IFields{
    public String id,
            name,
            state_id,
            status;

    public CityContract(@Nullable String id, String name, String state_id) {
        this.id = id;
        this.name = name;
        this.state_id = state_id;
    }

    public CityContract(String id, String name, String state_id, String status) {
        this.id = id;
        this.name = name;
        this.state_id = state_id;
        this.status = status;
    }

    private void initId(@Nullable String id){
        if (id == null){
            this.id = UUID.randomUUID().toString();
        }else{
            this.id = id;
        }
    }

    public CityContract() {
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

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    @Override
    public String getFieldName() {
        return this.name;
    }

    @Override
    public String getFieldId() {
        return this.id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ContentValues toContentValues(){
        ContentValues values=new ContentValues();
        values.put(CityEntry.ID,this.id);
        values.put(CityEntry.STATE_ID,this.state_id);
        values.put(CityEntry.NAME,this.name);
        values.put(CityEntry.STATUS, this.status);
        return values;
    }

    public ContentValues toSpecificContentValues(){
        ContentValues values=new ContentValues();
        if (this.id!=null){
            values.put(CityEntry.ID,this.id);
        }
        if (this.state_id!=null){
            values.put(CityEntry.STATE_ID,this.state_id);
        }
        if (this.name!=null){
            values.put(CityEntry.NAME,this.name);
        }

        if(this.status != null) {
            values.put(CityEntry.STATUS,this.status);
        }
        return values;
    }
    public String getFilterArguments(){
        String arguments="";
        if (this.id!=null){
            arguments=arguments.concat(CityEntry.ID+ " =?");
        }
        if (this.state_id!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(CityEntry.STATE_ID+ " =?");
        }
        if (this.name!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(CityEntry.NAME+ " =?");
        }

        if (this.status!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(CityEntry.STATUS+ " =?");
        }
        return arguments;
    }
    public String[] getFilterArgumentsValues(){
        List<String> values=new ArrayList<String>();
        if (this.id!=null){
            values.add(this.id);
        }
        if (this.state_id!=null){
            values.add(this.state_id);
        }
        if (this.name!=null){
            values.add(this.name);
        }
        if (this.status!=null){
            values.add(this.status);
        }
        return values.toArray(new String[values.size()]);
    }

    public static abstract class CityEntry implements BaseColumns {
        public static final String TABLE_NAME = "cities";

        public static final String ID = "id",
                                    NAME = "name",
                                    STATE_ID = "state_id",
                                    STATUS = "status";

        public static final String CREATE_TABLE="CREATE TABLE "+ TABLE_NAME+"(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ID + " TEXT NOT NULL," +
                NAME + " TEXT NOT NULL," +
                STATE_ID + " TEXT NOT NULL," +
                STATUS + " TEXT NULL," +
                "UNIQUE("+ID+"))";
    }
}
