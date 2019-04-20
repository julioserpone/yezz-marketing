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

public class StoreContactContract {

    private String  id,
                    name,
                    surname,
                    store_position,
                    phone,
                    email,
                    address,
                    status;

    public StoreContactContract(@Nullable String id, String name, String surname, String store_position,
                                String phone, String email, String address, String status) {
        initId(id);
        this.name = name;
        this.surname = surname;
        this.store_position = store_position;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.status = status;
    }

    public StoreContactContract() { initId(null); }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getStore_position() {
        return store_position;
    }

    public void setStore_position(String store_position) {
        this.store_position = store_position;
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

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public ContentValues toContentValues(){
        ContentValues values=new ContentValues();
        values.put(StoreContactContract.StoreContactEntry.ID,this.id);
        values.put(StoreContactContract.StoreContactEntry.NAME,this.name);
        values.put(StoreContactContract.StoreContactEntry.SURNAME,this.surname);
        values.put(StoreContactContract.StoreContactEntry.STORE_POSITION,this.store_position);
        values.put(StoreContactContract.StoreContactEntry.PHONE,this.phone);
        values.put(StoreContactContract.StoreContactEntry.EMAIL,this.email);
        values.put(StoreContactContract.StoreContactEntry.ADDRESS,this.address);
        values.put(StoreContactContract.StoreContactEntry.STATUS,this.status);
        return values;
    }

    public ContentValues toSpecificContentValues(){
        ContentValues values=new ContentValues();
        if(this.id!=null){
            values.put(StoreContactEntry.ID,this.id);
        }
        if(this.name!=null){
            values.put(StoreContactEntry.NAME,this.name);
        }
        if(this.surname!=null){
            values.put(StoreContactEntry.SURNAME,this.surname);
        }
        if(this.store_position!=null){
            values.put(StoreContactEntry.STORE_POSITION,this.store_position);
        }
        if(this.phone!=null){
            values.put(StoreContactEntry.PHONE,this.phone);
        }
        if(this.email!=null){
            values.put(StoreContactEntry.EMAIL,this.email);
        }
        if(this.address!=null) {
            values.put(StoreContactEntry.ADDRESS,this.address);
        }
        if(this.status!=null) {
            values.put(StoreContactEntry.STATUS,this.status);
        }
        return values;
    }

    public String getFilterArguments(){
        String arguments="";
        if(this.id!=null){
            arguments=arguments.concat(StoreContactEntry.ID+" = ?");
        }
        if(this.name!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(StoreContactEntry.NAME+" = ?");
        }
        if(this.surname!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(StoreContactEntry.SURNAME+" = ?");
        }
        if(this.store_position!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(StoreContactEntry.STORE_POSITION+" = ?");
        }
        if(this.phone!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(StoreContactEntry.PHONE+" = ?");
        }
        if(this.email!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(StoreContactEntry.EMAIL+" = ?");
        }
        if(this.address!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(StoreContactEntry.ADDRESS+" = ?");
        }
        if(this.status!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(StoreContactEntry.STATUS+" = ?");
        }
        return arguments;
    }

    public String[] getFilterArgumentsValues(){
        List<String> values=new ArrayList<String>();
        if(this.id!=null){
            values.add(this.id);
        }
        if(this.name!=null){
            values.add(this.name);
        }
        if(this.surname!=null){
            values.add(this.surname);
        }
        if(this.store_position!=null){
            values.add(this.store_position);
        }
        if(this.phone!=null){
            values.add(this.phone);
        }
        if(this.email!=null){
            values.add(this.email);
        }
        if(this.address!=null){
            values.add(this.address);
        }
        if(this.status!=null){
            values.add(this.status);
        }
        return values.toArray(new String[values.size()]);
    }


    public static abstract class StoreContactEntry implements BaseColumns {
        public static final String TABLE_NAME="store_contact";
        public static final String ID="id";
        public static final String NAME ="name";
        public static final String SURNAME ="surname";
        public static final String STORE_POSITION = "store_position";
        public static final String PHONE = "phone";
        public static final String EMAIL = "email";
        public static final String ADDRESS = "address";
        public static final String STATUS = "status";

        public static final String CREATE_TABLE="CREATE TABLE "+ TABLE_NAME+"(" +
                _ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ID+ " TEXT NOT NULL," +
                NAME+" TEXT NOT NULL,"+
                SURNAME+" TEXT NOT NULL,"+
                STORE_POSITION+" TEXT NULL,"+
                PHONE +" TEXT NOT NULL,"+
                EMAIL +" TEXT NOT NULL,"+
                ADDRESS +" TEXT NULL,"+
                STATUS +" TEXT NOT NULL,"+
                "UNIQUE("+ID+")"+
                ")";
    }
}
