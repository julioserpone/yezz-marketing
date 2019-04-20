package com.yezz.company.yezzclub.lData.entities.contracts;

import android.content.ContentValues;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import com.yezz.company.yezzclub.Interfaces.IFields;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ramon_000 on 27-09-2016.
 */

public class BrandPhoneContract implements IFields {
    private String id,name,is_yezz,status;

    public BrandPhoneContract(@Nullable String id, String name, String is_yezz, String status) {
        this.initId(id);
        this.name = name;
        this.is_yezz = is_yezz;
        this.status = status;
    }

    public BrandPhoneContract() {
        this.initId(null);
    }

    public void initId(@Nullable String id){
        this.id = (id==null) ? this.id= UUID.randomUUID().toString() : id;
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

    public void setIs_yezz(String is_yezz) {
        this.is_yezz = is_yezz;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ContentValues toContentValues(){
        ContentValues values=new ContentValues();
        values.put(BrandEntry.ID,this.id);
        values.put(BrandEntry.NAME,this.name);
        values.put(BrandEntry.ISYEZZ,this.is_yezz);
        values.put(BrandEntry.STATUS,this.status);
        return values;
    }

    public ContentValues toSpecificContentValues(){
        ContentValues values=new ContentValues();
        if (this.id!=null){
            values.put(BrandEntry.ID,this.id);
        }
        if (this.name!=null){
            values.put(BrandEntry.NAME,this.name);
        }
        if (this.is_yezz!=null){
            values.put(BrandEntry.ISYEZZ,this.is_yezz);
        }
        if (this.status!=null){
            values.put(BrandEntry.STATUS,this.status);
        }
        return values;
    }

    public String getFilterArguments(){
        String arguments="";
        if (this.id!=null){
            arguments=arguments.concat(BrandEntry.ID+" = ?");
        }
        if (this.name!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(BrandEntry.NAME+" = ?");
        }
        if (this.is_yezz!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(BrandEntry.ISYEZZ+" = ?");
        }
        if (this.status!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(BrandEntry.STATUS+" = ?");
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
        if (this.is_yezz!=null){
            values.add(this.is_yezz);
        }
        if (this.status!=null){
            values.add(this.status);
        }
        return values.toArray(new String[values.size()]);
    }

    @Override
    public String getFieldName() {
        return this.getName();
    }

    @Override
    public String getFieldId() {
        return this.getId();
    }


    public static abstract class BrandEntry  implements BaseColumns {
        public static final String TABLE_NAME = "phone_brand";

        public static final String  ID = "id";
        public static final String  NAME = "name";
        public static final String  ISYEZZ = "is_yezz";
        public static final String  STATUS = "status";

        public static final String CREATE_TABLE ="CREATE TABLE " + TABLE_NAME + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ID + " TEXT NOT NULL," +
                NAME + " TEXT NOT NULL," +
                ISYEZZ + " INTEGER NOT NULL," +
                STATUS + " INTEGER NOT NULL," +
                "UNIQUE("+ID+"))";
    }
}
