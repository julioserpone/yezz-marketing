package com.yezz.company.yezzclub.lData.entities.contracts;

import android.content.ContentValues;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by SairioA on 20/04/17.
 */

public class LocalizationLogContract {
    private String id,
                    store_id,
                    longitude,
                    latitude,
                    created,
                    status,
                    user;

    public LocalizationLogContract(@Nullable String id, @Nullable String store_id, String longitude,
                                   String latitude, String created, String status, String user) {
        initId(id);
        this.store_id = store_id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.created = created;
        this.status = status;
        this.user = user;
    }

    public LocalizationLogContract() {
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

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser() { return user; }

    public void setUser(String user) { this.user = user; }

    public ContentValues toContentValues(){
        ContentValues values=new ContentValues();
        values.put(LocalizationLogContract.LocalizationLogEntry.ID,this.id);
        values.put(LocalizationLogContract.LocalizationLogEntry.STORE_ID,this.store_id);
        values.put(LocalizationLogContract.LocalizationLogEntry.LONGITUDE,this.longitude);
        values.put(LocalizationLogContract.LocalizationLogEntry.LATITUDE,this.latitude);
        values.put(LocalizationLogContract.LocalizationLogEntry.CREATED,this.created);
        values.put(LocalizationLogContract.LocalizationLogEntry.STATUS,this.status);
        values.put(LocalizationLogContract.LocalizationLogEntry.USER,this.user);
        return values;
    }

    public ContentValues toSpecificContentValues(){
        ContentValues values=new ContentValues();
        if(this.id!=null){
            values.put(LocalizationLogEntry.ID,this.id);
        }
        if(this.store_id!=null){
            values.put(LocalizationLogEntry.STORE_ID,this.store_id);
        }
        if(this.longitude!=null){
            values.put(LocalizationLogEntry.LONGITUDE,this.longitude);
        }
        if(this.latitude!=null){
            values.put(LocalizationLogEntry.LATITUDE,this.latitude);
        }
        if(this.created!=null){
            values.put(LocalizationLogEntry.CREATED,this.created);
        }
        if(this.status!=null) {
            values.put(LocalizationLogEntry.STATUS,this.status);
        }
        if(this.user!=null) {
            values.put(LocalizationLogEntry.USER,this.status);
        }
        return values;
    }

    public String getFilterArguments(){
        String arguments="";
        if(this.id!=null){
            arguments=arguments.concat(LocalizationLogEntry.ID+" = ?");
        }
        if(this.store_id!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(LocalizationLogEntry.STORE_ID+" = ?");
        }
        if(this.longitude!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(LocalizationLogEntry.LONGITUDE+" = ?");
        }
        if(this.latitude!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(LocalizationLogEntry.LATITUDE+" = ?");
        }
        if(this.created!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(LocalizationLogEntry.CREATED+" = ?");
        }
        if(this.status!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(LocalizationLogEntry.STATUS+" = ?");
        }
        if(this.user!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(LocalizationLogEntry.USER+" = ?");
        }
        return arguments;
    }

    public String[] getFilterArgumentsValues(){
        List<String> values=new ArrayList<String>();
        if(this.id!=null){
            values.add(this.id);
        }
        if(this.store_id!=null){
            values.add(this.store_id);
        }
        if(this.latitude!=null){
            values.add(this.latitude);
        }
        if(this.longitude!=null){
            values.add(this.longitude);
        }
        if(this.created!=null){
            values.add(this.created);
        }
        if(this.status!=null){
            values.add(this.status);
        }
        if(this.user!=null){
            values.add(this.user);
        }
        return values.toArray(new String[values.size()]);
    }

    public static abstract class LocalizationLogEntry implements BaseColumns {
        public static final String TABLE_NAME="localization_log";
        public static final String ID="id";
        public static final String STORE_ID = "store_id";
        public static final String LONGITUDE ="longitude";
        public static final String LATITUDE ="latitude";
        public static final String CREATED = "created";
        public static final String STATUS = "status";
        public static final String USER = "user";

        public static final String CREATE_TABLE="CREATE TABLE "+ TABLE_NAME+"(" +
                _ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ID+ " TEXT NOT NULL," +
                STORE_ID+" TEXT NULL,"+
                LONGITUDE+" TEXT NOT NULL,"+
                LATITUDE+" TEXT NOT NULL,"+
                CREATED +" TEXT NOT NULL,"+
                STATUS +" TEXT NOT NULL,"+
                USER  +" TEXT NOT NULL,"+
                "UNIQUE("+ID+")"+
                ")";
    }
}
