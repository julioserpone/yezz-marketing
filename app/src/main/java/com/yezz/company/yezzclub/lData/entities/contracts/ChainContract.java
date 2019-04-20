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
public class ChainContract implements IFields {

    private String  id,
                    user_id,
                    country_id,
                    identification,
                    name,
                    phone,
                    email,
                    address,
                    status;

    private void initId(@Nullable String id) {
        if (id == null){
            this.id = UUID.randomUUID().toString();
        }else{
            this.id = id;
        }
    }

    public ChainContract() {
        initId(null);
    }

    public ChainContract(String id, String user_id, String country_id, String identification,
                         String name, String phone, String email, String address, String status) {
        this.id = id;
        this.user_id = user_id;
        this.country_id = country_id;
        this.identification = identification;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        ContentValues values = new ContentValues();
        values.put(StoreChainEntry.ID, this.id);
        values.put(StoreChainEntry.NAME, this.name);
        values.put(StoreChainEntry.STATUS, this.status);
        values.put(StoreChainEntry.USER_ID, this.user_id);
        values.put(StoreChainEntry.COUNTRY_ID, this.country_id);
        values.put(StoreChainEntry.IDENTIFICATION,this.identification);
        values.put(StoreChainEntry.PHONE, this.phone);
        values.put(StoreChainEntry.EMAIL, this.email);
        values.put(StoreChainEntry.ADDRESS, this.address);
        return values;
    }

    public ContentValues toSpecificContentValues(){
        ContentValues values = new ContentValues();
        if (this.id != null) {
            values.put(StoreChainEntry.ID, this.id);
        }

        if (this.user_id != null) {
            values.put(StoreChainEntry.USER_ID, this.user_id);
        }

        if (this.name != null) {
            values.put(StoreChainEntry.NAME, this.name);
        }

        if (this.country_id != null) {
            values.put(StoreChainEntry.COUNTRY_ID, this.country_id);
        }

        if (this.identification != null) {
            values.put(StoreChainEntry.IDENTIFICATION,this.identification);
        }

        if (this.phone != null){
            values.put(StoreChainEntry.PHONE, this.phone);
        }

        if (this.email != null) {
            values.put(StoreChainEntry.EMAIL, this.email);
        }

        if (this.status != null) {
            values.put(StoreChainEntry.STATUS,this.status);
        }
        return values;
    }
    public String getFilterArguments(){
        String arguments="";
        if (this.id != null){
            arguments=arguments.concat(StoreChainEntry.ID + " =?");
        }

        if (this.user_id != null){
            arguments=arguments.concat(arguments != "" ? " AND " : "");
            arguments=arguments.concat(StoreChainEntry.USER_ID + " =?");
        }

        if (this.name != null){
            arguments=arguments.concat(arguments != "" ? " AND " : "");
            arguments=arguments.concat(StoreChainEntry.NAME + " =?");
        }

        if (this.country_id != null){
            arguments=arguments.concat(arguments != "" ? " AND " : "");
            arguments=arguments.concat(StoreChainEntry.COUNTRY_ID + " =?");
        }

        if (this.identification != null) {
            arguments=arguments.concat(arguments != "" ? " AND " : "");
            arguments=arguments.concat(StoreChainEntry.IDENTIFICATION + " =?");
        }

        if (this.phone != null){
            arguments=arguments.concat(arguments!="" ? " AND " : "");
            arguments=arguments.concat(StoreChainEntry.PHONE + " =?");
        }

        if (this.email != null) {
            arguments=arguments.concat(arguments!="" ? " AND " : "");
            arguments=arguments.concat(StoreChainEntry.EMAIL + " =?");
        }

        if (this.status!=null){
            arguments=arguments.concat(arguments!="" ? " AND " : "");
            arguments=arguments.concat(StoreChainEntry.STATUS+ " =?");
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

        if (this.user_id != null) {
            values.add(this.user_id);
        }

        if (this.country_id != null) {
            values.add(this.country_id);
        }

        if (this.identification != null) {
            values.add(this.identification);
        }

        if (this.phone != null){
            values.add(this.phone);
        }

        if (this.email != null) {
            values.add(this.email);
        }

        if (this.status!=null){
            values.add(this.status);
        }
        return values.toArray(new String[values.size()]);
    }

    public static abstract class StoreChainEntry implements BaseColumns {
        public static final String TABLE_NAME="store_chains";

        public static final String ID = "id",
                                    USER_ID = "user_id",
                                    NAME = "name",
                                    COUNTRY_ID = "country_id",
                                    STATUS = "status",
                                    IDENTIFICATION = "identification",
                                    PHONE = "phone",
                                    EMAIL = "email",
                                    ADDRESS = "address";

        public static final String CREATE_TABLE="CREATE TABLE "+ TABLE_NAME+"(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ID + " TEXT NOT NULL," +
                NAME + " TEXT NOT NULL," +
                USER_ID + " TEXT NOT NULL," +
                COUNTRY_ID + " TEXT NULL," +
                IDENTIFICATION + " TEXT NULL," +
                PHONE + " TEXT NULL," +
                EMAIL + " TEXT NULL," +
                ADDRESS + " TEXT NULL," +
                STATUS+ " TEXT NOT NULL," +
                "UNIQUE("+ID+"))";
    }
}
