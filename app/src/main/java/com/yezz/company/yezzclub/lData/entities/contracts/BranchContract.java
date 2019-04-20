package com.yezz.company.yezzclub.lData.entities.contracts;

import android.content.ContentValues;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import com.yezz.company.yezzclub.Interfaces.IFields;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ramon_000 on 24-08-2016.
 */
public class BranchContract implements IFields {

    private String  id,
            name,
            code,
            address,
            phone,
            zip_code,
            type_id,
            chain_id,
            country_id,
            state_id,
            city_id,
            township_id,
            latitude,
            longitude,
            category,
            contact,
            is_customer,
            has_pop,
            comments,
            status;

    private StoreContactContract objContact;
    private CategoryContract objCategory;
    private CityContract objCity;
    private StateContract objState;
    private TownshipsContract objTownShip;
    private StoreTypeContract objType;
    private ChainContract objChain;


    private void initId(@Nullable String id) {
        if (id==null){
            this.id= UUID.randomUUID().toString();
        }else{
            this.id=id;
        }
    }

    public BranchContract(@Nullable String id, String name, String code, String address, String phone,
                          String zip_code, String type_id, String chain_id, String country_id,
                          String state_id, String city_id, String township_id, String latitude,
                          String longitude, String category, String contact, String is_customer, String status, String has_pop, String comments) {
        this.initId(id);
        this.name = name;
        this.code = code;
        this.address = address;
        this.phone = phone;
        this.zip_code = zip_code;
        this.type_id = type_id;
        this.chain_id = chain_id;
        this.country_id = country_id;
        this.state_id = state_id;
        this.city_id = city_id;
        this.township_id = township_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.contact = contact;
        this.is_customer = is_customer;
        this.has_pop = has_pop;
        this.status = status;
        this.comments = comments;
    }


    public BranchContract(@Nullable String id, String name, String code, String address, String phone,
                          String zip_code, String type_id, String chain_id, String country_id,
                          String state_id, String city_id, String township_id, String latitude,
                          String longitude, String category, String contact, String is_customer,
                          StoreContactContract objContact, CategoryContract objCategory, String status, String has_pop, String comments) {
        this.initId(id);
        this.name = name;
        this.code = code;
        this.address = address;
        this.phone = phone;
        this.zip_code = zip_code;
        this.type_id = type_id;
        this.chain_id = chain_id;
        this.country_id = country_id;
        this.state_id = state_id;
        this.city_id = city_id;
        this.township_id = township_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.contact = contact;
        this.is_customer = is_customer;
        this.has_pop = has_pop;
        this.objContact = objContact;
        this.objCategory = objCategory;
        this.status = status;
        this.comments = comments;
    }

    public BranchContract(){this.initId(null);}

    public BranchContract(@Nullable String id, String name, String code, String address, String phone, String zip_code,
                          String type_id, String chain_id, String country_id, String state_id, String city_id,
                          String township_id, String latitude, String longitude, String category,
                          String contact, String is_customer, String status, StoreContactContract objContact,
                          CategoryContract objCategory, CityContract objCity, StateContract objState,
                          TownshipsContract objTownShip, StoreTypeContract objType, ChainContract objChain, String has_pop, String comments) {
        this.initId(id);
        this.name = name;
        this.code = code;
        this.address = address;
        this.phone = phone;
        this.zip_code = zip_code;
        this.type_id = type_id;
        this.chain_id = chain_id;
        this.country_id = country_id;
        this.state_id = state_id;
        this.city_id = city_id;
        this.township_id = township_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.category = category;
        this.contact = contact;
        this.is_customer = is_customer;
        this.has_pop = has_pop;
        this.status = status;
        this.objContact = objContact;
        this.objCategory = objCategory;
        this.objCity = objCity;
        this.objState = objState;
        this.objTownShip = objTownShip;
        this.objType = objType;
        this.objChain = objChain;
        this.comments = comments;
    }

    public ContentValues toContentValues(){
        ContentValues values=new ContentValues();
        values.put(BranchEntry.ID,this.id);
        values.put(BranchEntry.NAME,this.name);
        values.put(BranchEntry.CODE,this.code);
        values.put(BranchEntry.ADDRESS,this.address);
        values.put(BranchEntry.PHONE, this.phone);
        values.put(BranchEntry.ZIP_CODE, this.zip_code);
        values.put(BranchEntry.TYPE_ID, this.type_id);
        values.put(BranchEntry.CHAIN_ID, this.chain_id);
        values.put(BranchEntry.COUNTRY_ID, this.country_id);
        values.put(BranchEntry.STATE_ID, this.state_id);
        values.put(BranchEntry.CITY_ID, this.city_id);
        values.put(BranchEntry.TOWNSHIP_ID, this.township_id);
        values.put(BranchEntry.LATITUDE, this.latitude);
        values.put(BranchEntry.LONGITUDE, this.longitude);
        values.put(BranchEntry.STATUS,this.status);
        values.put(BranchEntry.CATEGORY_ID,this.category);
        values.put(BranchEntry.CONTACT_ID,this.contact);
        values.put(BranchEntry.IS_CUSTOMER,this.is_customer);
        values.put(BranchEntry.HAS_POP,this.has_pop);
        values.put(BranchEntry.COMMENTS,this.comments);
        return values;
    }

    public ContentValues toSpecificContentValues(){
        ContentValues values=new ContentValues();
        if (this.id!=null){
            values.put(BranchEntry.ID,this.id);
        }
        if (this.name!=null){
            values.put(BranchEntry.NAME,this.name);
        }
        if (this.code!=null){
            values.put(BranchEntry.CODE,this.code);
        }
        if (this.address!=null){
            values.put(BranchEntry.ADDRESS,this.address);
        }
        if (this.type_id != null){
            values.put(BranchEntry.TYPE_ID,this.type_id);
        }
        if (this.chain_id != null){
            values.put(BranchEntry.CHAIN_ID,this.chain_id);
        }
        if (this.country_id != null){
            values.put(BranchEntry.COUNTRY_ID,this.country_id);
        }
        if (this.state_id != null){
            values.put(BranchEntry.STATE_ID,this.state_id);
        }
        if (this.city_id != null){
            values.put(BranchEntry.CITY_ID,this.city_id);
        }
        if (this.latitude != null) {
            values.put(BranchEntry.LATITUDE, this.latitude);
        }
        if (this.longitude != null) {
            values.put(BranchEntry.LONGITUDE, this.longitude);
        }
        if (this.status!=null){
            values.put(BranchEntry.STATUS,this.status);
        }
        if (this.category!=null) {
            values.put(BranchEntry.CATEGORY_ID, this.category);
        }
        if (this.contact!=null) {
            values.put(BranchEntry.CONTACT_ID, this.contact);
        }
        if (this.is_customer!=null) {
            values.put(BranchEntry.IS_CUSTOMER, this.is_customer);
        }
        if (this.has_pop!=null) {
            values.put(BranchEntry.HAS_POP, this.has_pop);
        }
        if (this.comments!=null){
            values.put(BranchEntry.COMMENTS,this.comments);
        }
        return values;
    }

    public String getFilterArguments(){
        String arguments="";
        if (this.id!=null){
            arguments=arguments.concat(BranchEntry.ID+" =?");
        }
        if (this.name!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(BranchEntry.NAME+" =?");
        }
        if (this.code!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(BranchEntry.CODE+" =?");
        }
        if (this.address!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(BranchEntry.ADDRESS+" =?");
        }
        if (this.phone!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(BranchEntry.PHONE+" =?");
        }

        if (this.zip_code != null) {
            arguments=arguments.concat(arguments != "" ? " AND " : "");
            arguments=arguments.concat(BranchEntry.ZIP_CODE + " =?");
        }

        if (this.type_id != null) {
            arguments=arguments.concat(arguments != "" ? " AND " : "");
            arguments=arguments.concat(BranchEntry.TYPE_ID + " =?");
        }

        if (this.chain_id != null) {
            arguments=arguments.concat(arguments != "" ? " AND " : "");
            arguments=arguments.concat(BranchEntry.CHAIN_ID + " =?");
        }

        if (this.country_id != null) {
            arguments=arguments.concat(arguments != "" ? " AND " : "");
            arguments=arguments.concat(BranchEntry.COUNTRY_ID + " =?");
        }

        if (this.state_id != null) {
            arguments=arguments.concat(arguments != "" ? " AND " : "");
            arguments=arguments.concat(BranchEntry.STATE_ID + " =?");
        }

        if (this.city_id != null){
            arguments=arguments.concat(arguments != "" ? " AND " : "");
            arguments=arguments.concat(BranchEntry.CITY_ID + " =?");
        }
        if (this.township_id != null){
            arguments=arguments.concat(arguments!="" ? " AND " : "");
            arguments=arguments.concat(BranchEntry.TOWNSHIP_ID + " =?");
        }
        if (this.latitude != null){
            arguments=arguments.concat(arguments!="" ? " AND ":"");
            arguments=arguments.concat(BranchEntry.LATITUDE + " =?");
        }
        if (this.longitude != null){
            arguments=arguments.concat(arguments!="" ? " AND " : "");
            arguments=arguments.concat(BranchEntry.LONGITUDE + " =?");
        }
        if (this.status!=null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(BranchEntry.STATUS+" =?");
        }
        if(this.category!=null) {
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(BranchEntry.CATEGORY_ID+" = ?");
        }
        if(this.contact!=null) {
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(BranchEntry.CONTACT_ID+" = ?");
        }
        if(this.is_customer!=null) {
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(BranchEntry.IS_CUSTOMER+" = ?");
        }
        if(this.has_pop!=null) {
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(BranchEntry.HAS_POP+" = ?");
        }
        if (this.comments != null){
            arguments=arguments.concat(arguments!=""?" AND ":"");
            arguments=arguments.concat(BranchEntry.COMMENTS+" = ?");
        }
        return arguments;
    }

    public String[] getFilterArgumentsValues(){

        List<String> values=new ArrayList<String>();

        if (this.id != null){
            values.add(this.id);
        }
        if (this.name!=null){
            values.add(this.name);
        }
        if (this.code != null){
            values.add(this.code);
        }
        if (this.address != null){
            values.add(this.address);
        }

        if (this.phone != null) {
            values.add(this.phone);
        }
        if (this.zip_code != null) {
            values.add(this.zip_code);
        }
        if (this.type_id != null) {
            values.add(this.type_id);
        }
        if (this.chain_id != null) {
            values.add(this.chain_id);
        }
        if(this.country_id != null) {
            values.add(this.country_id);
        }
        if(this.state_id != null) {
            values.add(this.state_id);
        }
        if(this.city_id != null) {
            values.add(this.city_id);
        }
        if(this.township_id != null) {
            values.add(this.township_id);
        }
        if(this.latitude != null) {
            values.add(this.latitude);
        }
        if (this.longitude != null){
            values.add(this.longitude);
        }
        if (this.city_id != null){
            values.add(this.city_id);
        }
        if (this.status != null){
            values.add(this.status);
        }
        if(this.category != null){
            values.add(this.category);
        }
        if(this.contact != null){
            values.add(this.contact);
        }
        if(this.is_customer != null) {
            values.add(this.is_customer);
        }
        if(this.has_pop!= null) {
            values.add(this.has_pop);
        }
        if (this.comments != null){
            values.add(this.comments);
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getChain_id() {
        return chain_id;
    }

    public void setChain_id(String chain_id) {
        this.chain_id = chain_id;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getTownship_id() {
        return township_id;
    }

    public void setTownship_id(String township_id) {
        this.township_id = township_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getIs_customer() {
        return is_customer;
    }

    public void setIs_customer(String is_customer) {
        this.is_customer = is_customer;
    }

    public String getHas_pop() {
        return has_pop;
    }

    public void setHas_pop(String has_pop) {
        this.has_pop= has_pop;
    }

    public StoreContactContract getObjContact() {
        return objContact;
    }

    public void setObjContact(StoreContactContract objContact) {
        this.objContact = objContact;
    }

    public CategoryContract getObjCategory() {
        return objCategory;
    }

    public void setObjCategory(CategoryContract objCategory) {
        this.objCategory = objCategory;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public CityContract getObjCity() {
        return objCity;
    }

    public void setObjCity(CityContract objCity) {
        this.objCity = objCity;
    }

    public StateContract getObjState() {
        return objState;
    }

    public void setObjState(StateContract objState) {
        this.objState = objState;
    }

    public TownshipsContract getObjTownShip() {
        return objTownShip;
    }

    public void setObjTownShip(TownshipsContract objTownShip) {
        this.objTownShip = objTownShip;
    }

    public StoreTypeContract getObjType() {
        return objType;
    }

    public void setObjType(StoreTypeContract objType) {
        this.objType = objType;
    }

    public ChainContract getObjChain() {
        return objChain;
    }

    public void setObjChain(ChainContract objChain) {
        this.objChain = objChain;
    }

    public static abstract class BranchEntry implements BaseColumns {

        public static final String TABLE_NAME="branchs";

        public static final String  ID="id",
                                    NAME = "name",
                                    CODE = "code",
                                    ADDRESS = "address",
                                    PHONE = "phone",
                                    ZIP_CODE = "zip_code",
                                    TYPE_ID = "type_id",
                                    CHAIN_ID = "chain_id",
                                    COUNTRY_ID = "country_id",
                                    STATE_ID = "state_id",
                                    CITY_ID = "city_id",
                                    TOWNSHIP_ID = "township_id",
                                    LATITUDE = "latitude",
                                    LONGITUDE = "longitude",
                                    IS_CUSTOMER = "is_customer",
                                    HAS_POP = "has_pop",
                                    CATEGORY_ID = "category_id",
                                    CONTACT_ID = "contact_id",
                                    COMMENTS = "comments",
                                    STATUS="status";

        public static final String CREATE_TABLE="CREATE TABLE " + TABLE_NAME + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                ID + " TEXT NOT NULL," +
                NAME + " TEXT NOT NULL," +
                CODE + " TEXT NULL," +
                ADDRESS + " TEXT NULL," +
                PHONE + " TEXT NULL," +
                ZIP_CODE + " TEXT NULL," +
                TYPE_ID + " TEXT NULL," +
                CHAIN_ID + " TEXT NULL," +
                COUNTRY_ID + " TEXT NULL," +
                STATE_ID + " TEXT NULL," +
                CITY_ID  + " TEXT NULL," +
                TOWNSHIP_ID + " TEXT NULL," +
                LATITUDE+ " TEXT NULL," +
                LONGITUDE+ " TEXT NULL," +
                STATUS + " TEXT NOT NULL," +
                IS_CUSTOMER + " TEXT NULL," +
                HAS_POP + " TEXT NULL," +
                CATEGORY_ID + " TEXT NULL," +
                CONTACT_ID + " TEXT NULL," +
                COMMENTS + " TEXT NULL," +
                "UNIQUE(" + ID + ") )";
    }
}