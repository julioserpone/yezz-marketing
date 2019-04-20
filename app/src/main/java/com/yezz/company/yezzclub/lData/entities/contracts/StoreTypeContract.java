package com.yezz.company.yezzclub.lData.entities.contracts;

import android.content.ContentValues;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import com.yezz.company.yezzclub.Interfaces.IFields;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ramon_000 on 29-08-2016.
 */
public class StoreTypeContract implements IFields {

    private String  id,
            name;

    private void initId(@Nullable String id) {
        if (id==null){
            this.id= UUID.randomUUID().toString();
        }else{
            this.id= id;
        }
    }

    public StoreTypeContract(@Nullable String id, String name) {
        this.initId(id);
        this.name= name;
    }

    public StoreTypeContract(){this.initId(null);}

    public ContentValues toContentValues(){
        ContentValues values = new ContentValues();
        values.put(StoreTypeEntry.ID,this.id);
        values.put(StoreTypeEntry.NAME,this.name);
        return values;
    }

    public ContentValues toSpecificContentValues(){
        ContentValues values = new ContentValues();
        if (this.id!=null){
            values.put(StoreTypeEntry.ID,this.id);
        }

        if (this.name!=null){
            values.put(StoreTypeEntry.NAME,this.name);
        }

        return values;
    }

    public String getFilterArguments(){
        String arguments="";
        if (this.id!=null){
            arguments=arguments.concat(StoreTypeEntry.ID+" = ?");
        }

        if (this.name!=null){
            arguments=arguments.concat(StoreTypeEntry.NAME+" = ?");
        }

        return arguments;
    }

    public String[] getFilterArgumentsValues(){
        List<String> values=new ArrayList<String>();
        if (this.id!=null){
            values.add(this.id);
        }

        if (this.name!=null){
            values.add(this.name);
        }
        return values.toArray(new String[values.size()]);
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

    @Override
    public String getFieldName() {
        return this.getName();
    }

    @Override
    public String getFieldId() {
        return this.getId();
    }

    public static abstract class StoreTypeEntry implements BaseColumns {
        public static final String TABLE_NAME="type_channel";

        public static final String ID="id";
        public static final String NAME="name";

        public static final String CREATE_TABLE="CREATE TABLE "+ TABLE_NAME+"(" +
                _ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ID+ " TEXT NOT NULL," +
                NAME+ " TEXT NOT NULL," +
                "UNIQUE("+ID+"))";
    }
}
