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

public class PhoneModelContract implements IFields {
    private String name,id, brand,status, product_id;

    private void initId(@Nullable String id) {
        this.id = (id==null) ? this.id= UUID.randomUUID().toString() : id;
    }

    public PhoneModelContract(@Nullable String id, String name, String brand, String product_id, String status) {
        this.name = name;
        this.initId(id);
        this.brand = brand;
        this.status = status;
        this.product_id = product_id;
    }

    public PhoneModelContract() {
        this.initId(null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public ContentValues toContentValues(){
        ContentValues values=new ContentValues();
        values.put(PhoneModelContract.PhoneModelEntry.ID,this.id);
        values.put(PhoneModelContract.PhoneModelEntry.NAME,this.name);
        values.put(PhoneModelContract.PhoneModelEntry.BRAND,this.brand);
        values.put(PhoneModelContract.PhoneModelEntry.PRODUCT_ID,this.product_id);
        values.put(PhoneModelContract.PhoneModelEntry.STATUS,this.status);
        return values;
    }

    public ContentValues toSpecificoContentValues(){
        ContentValues values=new ContentValues();
        if (this.id!=null){
            values.put(PhoneModelContract.PhoneModelEntry.ID,this.id);
        }
        if (this.product_id!=null){
            values.put(PhoneModelContract.PhoneModelEntry.PRODUCT_ID,this.product_id);
        }
        if (this.name!=null){
            values.put(PhoneModelEntry.NAME,this.name);
        }
        if (this.status!=null){
            values.put(PhoneModelContract.PhoneModelEntry.STATUS,this.status);
        }
        if(this.brand !=null) {
            values.put(PhoneModelEntry.BRAND,this.brand);
        }
        return values;
    }

    public String getFilterArguments(){
        String arguments="";
        if (this.id!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(PhoneModelContract.PhoneModelEntry.ID+" = ?");
        }
        if (this.brand!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(PhoneModelContract.PhoneModelEntry.BRAND+" = ?");
        }
        if (this.name!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(PhoneModelEntry.NAME+" = ?");
        }
        if (this.status!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(PhoneModelContract.PhoneModelEntry.STATUS+" = ?");
        }
        if (this.product_id!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(PhoneModelContract.PhoneModelEntry.PRODUCT_ID+" = ?");
        }
        return arguments;
    }

    public String[] getFilterArgumentsValues(){
        List<String> values=new ArrayList<String>();
        if (this.id!=null){
            values.add(this.id);
        }
        if (this.brand!=null){
            values.add(this.brand);
        }
        if (this.name!=null){
            values.add(this.name);
        }
        if (this.status!=null){
            values.add(this.status);
        }
        if(this.product_id != null) {
            values.add(this.product_id);
        }
        return values.toArray(new String[values.size()]);
    }

    @Override
    public String getFieldName() {
        return this.getName();
    }

    @Override
    public String getFieldId() {
        return String.valueOf(this.getId());
    }

    public static abstract class PhoneModelEntry  implements BaseColumns{

        public static final String TABLE_NAME = "phone_model";

        public static final String  ID = "id",
                                    NAME = "name",
                                    BRAND = "brand",
                                    STATUS = "status",
                                    PRODUCT_ID = "product_id";

        public static final String CREATE_TABLE ="CREATE TABLE " + TABLE_NAME + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ID + " TEXT NOT NULL," +
                NAME + " TEXT NOT NULL," +
                BRAND + " TEXT NOT NULL," +
                PRODUCT_ID + " TEXT NOT NULL," +
                STATUS + " TEXT NOT NULL," +
                "UNIQUE("+ID+"))";
    }
}
