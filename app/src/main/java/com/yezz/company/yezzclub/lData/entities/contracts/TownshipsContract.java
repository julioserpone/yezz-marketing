package com.yezz.company.yezzclub.lData.entities.contracts;

import android.content.ContentValues;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import com.yezz.company.yezzclub.Interfaces.IFields;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by sairio on 29/06/17.
 */

public class TownshipsContract implements IFields{

    private String id,
                    city_id,
                    name,
                    status;

    public TownshipsContract(@Nullable String id, String city_id, String name, String status) {
        initId(id);
        this.city_id = city_id;
        this.name = name;
        this.status = status;
    }

    public TownshipsContract() {
        initId(null);
    }

    private void initId(@Nullable String id) {
        if (id==null){
            this.id= UUID.randomUUID().toString();
        }else{
            this.id=id;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        values.put(TownshipsEntry.ID,this.id);
        values.put(TownshipsEntry.CITY_ID,this.city_id);
        values.put(TownshipsEntry.NAME,this.name);
        values.put(TownshipsEntry.STATUS,this.status);
        return values;
    }

    public ContentValues toSpecificContentValues(){
        ContentValues values=new ContentValues();
        if (this.id!=null){
            values.put(TownshipsEntry.ID,this.id);
        }
        if (this.city_id!=null){
            values.put(TownshipsEntry.CITY_ID,this.city_id);
        }
        if (this.name!=null){
            values.put(TownshipsEntry.NAME,this.name);
        }

        if (this.status!=null){
            values.put(TownshipsEntry.STATUS,this.status);
        }
        return values;
    }
    public String getFilterArguments(){
        String arguments="";
        if (this.id!=null){
            arguments=arguments.concat(TownshipsEntry.ID+ " =?");
        }
        if (this.city_id!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(TownshipsEntry.CITY_ID+ " =?");
        }
        if (this.name!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(TownshipsEntry.NAME+ " =?");
        }

        if (this.status!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(TownshipsEntry.STATUS+ " =?");
        }
        return arguments;
    }
    public String[] getFilterArgumentsValues(){
        List<String> values=new ArrayList<String>();
        if (this.id!=null){
            values.add(this.id);
        }
        if (this.city_id!=null){
            values.add(this.city_id);
        }
        if (this.name!=null){
            values.add(this.name);
        }

        if (this.status!=null){
            values.add(this.status);
        }
        return values.toArray(new String[values.size()]);
    }

    public static abstract class TownshipsEntry implements BaseColumns {
        public static final String TABLE_NAME = "townships";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String CITY_ID = "city_id";
        public static final String STATUS = "status";

        public static final String CREATE_TABLE="CREATE TABLE "+ TABLE_NAME+"(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ID + " TEXT NOT NULL," +
                NAME + " TEXT NOT NULL," +
                CITY_ID + " TEXT NOT NULL," +
                STATUS + " TEXT NULL," +
                "UNIQUE("+ID+"))";
    }
}
